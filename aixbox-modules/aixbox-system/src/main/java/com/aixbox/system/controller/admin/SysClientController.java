package com.aixbox.system.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.vo.request.SysClientPageReqVO;
import com.aixbox.system.domain.vo.request.SysClientSaveReqVO;
import com.aixbox.system.domain.vo.request.SysClientUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysClientRespVO;
import com.aixbox.system.service.SysClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static com.aixbox.common.core.pojo.CommonResult.success;

/**
 * 客户端 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/client")
public class SysClientController {

    private final SysClientService sysClientService;

    /**
     * 新增客户端
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysClientSaveReqVO addReqVO) {
        Long sysClientId = sysClientService.addSysClient(addReqVO);
        return success(sysClientId);
    }

    /**
     * 修改客户端
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysClientUpdateReqVO updateReqVO) {
        Boolean result = sysClientService.updateSysClient(updateReqVO);
        return success(result);
    }

    /**
     * 删除客户端
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysClientService.deleteSysClient(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取客户端详细信息
     * @param id 数据id
     * @return demo对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysClientRespVO> getSysClient(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysClient sysClient = sysClientService.getSysClient(id);
        return success(BeanUtils.toBean(sysClient, SysClientRespVO.class));
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysClientRespVO>> getSysClientPage(@Valid SysClientPageReqVO pageReqVO) {
        PageResult<SysClient> pageResult = sysClientService.getSysClientPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysClientRespVO.class));
    }



}
