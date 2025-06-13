package com.aixbox.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.file.FileUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.core.utils.object.MapstructUtils;
import com.aixbox.common.core.utils.spring.SpringUtils;
import com.aixbox.common.oss.core.OssClient;
import com.aixbox.common.oss.entity.UploadResult;
import com.aixbox.common.oss.enums.AccessPolicyType;
import com.aixbox.common.oss.factory.OssFactory;
import com.aixbox.system.domain.vo.response.SysOssResp;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import com.aixbox.system.domain.entity.SysOss;
import com.aixbox.system.domain.vo.request.SysOssPageReq;
import com.aixbox.system.domain.vo.request.SysOssSaveReq;
import com.aixbox.system.domain.vo.request.SysOssUpdateReq;
import com.aixbox.system.mapper.SysOssMapper;
import com.aixbox.system.service.SysOssService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.system.constant.ErrorCodeConstants.FILE_NOT_EXISTS;
import static com.aixbox.system.constant.ErrorCodeConstants.SYS_OSS_NOT_EXISTS;

/**
* OSS对象存储Service实现类
*/
@Slf4j
@RequiredArgsConstructor
@Service
public class SysOssServiceImpl implements SysOssService {

    private final SysOssMapper sysOssMapper;

    /**
     * 新增OSS对象存储
     * @param addReq 新增参数
     * @return OSS对象存储id
     */
    @Override
    public Long addSysOss(SysOssSaveReq addReq) {
        SysOss  sysOss = BeanUtils.toBean(addReq, SysOss.class);
        validEntityBeforeSave(sysOss);
        sysOssMapper.insert(sysOss);
        return sysOss.getId();
    }

    /**
     * 修改OSS对象存储
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @Override
    public Boolean updateSysOss(SysOssUpdateReq updateReq) {
        SysOss sysOss = BeanUtils.toBean(updateReq, SysOss.class);
        validEntityBeforeSave(sysOss);
        return sysOssMapper.updateById(sysOss) > 0;
    }

    /**
     * 删除OSS对象存储
     * @param ids OSS对象存储id数组
     * @return 是否成功
     */
    @Override
    public Boolean deleteSysOss(List<Long> ids) {
        validateSysOssExists(ids);
        List<SysOss> list = sysOssMapper.selectByIds(ids);
        for (SysOss sysOss : list) {
            OssClient storage = OssFactory.instance(sysOss.getService());
            storage.delete(sysOss.getUrl());
        }
        return sysOssMapper.deleteByIds(ids) > 0;
    }

    /**
     * 验证OSS对象存储是否存在
     * @param ids OSS对象存储id数组
     */
    private void validateSysOssExists(List<Long> ids) {
        Long count = sysOssMapper.countByIds(ids);
        if (count != ids.size()) {
            throw exception(SYS_OSS_NOT_EXISTS);
        }
    }

    /**
     * 获取OSS对象存储详细数据
     * @param ids OSS对象存储id
     * @return OSS对象存储对象
     */
    @Override
    public List<SysOss> getSysOss(List<Long> ids) {
        List<SysOss> list = new ArrayList<>();
        for (Long id : ids) {
            SysOss vo = sysOssMapper.selectById(id);
            if (ObjectUtil.isNotNull(vo)) {
                try {
                    list.add(this.matchingUrl(vo));
                } catch (Exception ignored) {
                    // 如果oss异常无法连接则将数据直接返回
                    list.add(vo);
                }
            }
        }
        return list;
    }

    /**
     * 分页查询OSS对象存储
     * @param pageReq 分页查询参数
     * @return OSS对象存储分页对象
     */
    @Override
    public PageResult<SysOss> getSysOssPage(SysOssPageReq pageReq) {
        PageResult<SysOss> result = sysOssMapper.selectPage(pageReq);
        List<SysOss> filterResult = StreamUtils.toList(result.getList(), this::matchingUrl);
        result.setList(filterResult);
        return result;
    }

    /**
     * 文件下载方法，支持一次性下载完整文件
     *
     * @param ossId    OSS对象ID
     * @param response HttpServletResponse对象，用于设置响应头和向客户端发送文件内容
     */
    @Override
    public void download(Long ossId, HttpServletResponse response) throws IOException {
        SysOss sysOss = sysOssMapper.selectById(ossId);
        if (ObjectUtil.isNull(sysOss)) {
            throw exception(FILE_NOT_EXISTS);
        }
        FileUtils.setAttachmentResponseHeader(response, sysOss.getOriginalName());
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
        OssClient storage = OssFactory.instance(sysOss.getService());
        storage.download(sysOss.getFileName(), response.getOutputStream(), response::setContentLengthLong);
    }

    /**
     * 上传 MultipartFile 到对象存储服务，并保存文件信息到数据库
     *
     * @param file 要上传的 MultipartFile 对象
     * @return 上传成功后的 SysOssVo 对象，包含文件信息
     * @throws ServiceException 如果上传过程中发生异常，则抛出 ServiceException 异常
     */
    @Override
    public SysOssResp upload(MultipartFile file) {
        String originalfileName = file.getOriginalFilename();
        String suffix = StrUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
        // 保存文件信息
        return buildResultEntity(originalfileName, suffix, storage.getConfigKey(), uploadResult);
    }

    @NotNull
    private SysOssResp buildResultEntity(String originalfileName, String suffix, String configKey,
                                      UploadResult uploadResult) {
        SysOss oss = new SysOss();
        oss.setUrl(uploadResult.getUrl());
        oss.setFileSuffix(suffix);
        oss.setFileName(uploadResult.getFilename());
        oss.setOriginalName(originalfileName);
        oss.setService(configKey);
        sysOssMapper.insert(oss);
        return BeanUtils.toBean(this.matchingUrl(oss), SysOssResp.class);
    }

    /**
     * 桶类型为 private 的URL 修改为临时URL时长为120s
     *
     * @param oss OSS对象
     * @return oss 匹配Url的OSS对象
     */
    private SysOss matchingUrl(SysOss oss) {
        OssClient storage = OssFactory.instance(oss.getService());
        // 仅修改桶类型为 private 的URL，临时URL时长为120s
        if (AccessPolicyType.PRIVATE == storage.getAccessPolicy()) {
            oss.setUrl(storage.getPrivateUrl(oss.getFileName(), Duration.ofSeconds(120)));
        }
        return oss;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysOss entity){
        //TODO 做一些数据校验,如唯一约束
    }
}




