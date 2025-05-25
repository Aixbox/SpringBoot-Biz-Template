package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.excel.utils.ExcelUtil;
import com.aixbox.system.domain.bo.SysDictDataBo;
import com.aixbox.system.domain.entity.SysDictData;
import com.aixbox.system.domain.vo.request.dict.SysDictDataPageReq;
import com.aixbox.system.domain.vo.request.dict.SysDictDataQueryReq;
import com.aixbox.system.domain.vo.request.dict.SysDictDataSaveReq;
import com.aixbox.system.domain.vo.request.dict.SysDictDataUpdateReq;
import com.aixbox.system.domain.vo.response.SysDictDataResp;
import com.aixbox.system.service.SysDictDataService;
import com.aixbox.system.service.SysDictTypeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.error;
import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.system.constant.ErrorCodeConstants.DICT_VALUE_EXIST;

/**
 * 字典数据 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;
    private final SysDictTypeService dictTypeService;

    /**
     * 导出字典数据列表
     */
    @SaCheckPermission("system:dict:export")
    @PostMapping("/export")
    public void export(SysDictDataQueryReq dictData, HttpServletResponse response) {
        List<SysDictDataResp> list = sysDictDataService.selectDictDataList(dictData);
        ExcelUtil.exportExcel(list, "字典数据", SysDictDataResp.class, response);
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     */
    @GetMapping(value = "/type/{dictType}")
    public CommonResult<List<SysDictDataResp>> dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (ObjectUtil.isNull(data)) {
            data = new ArrayList<>();
        }
        return success(BeanUtils.toBean(data, SysDictDataResp.class));
    }

    /**
     * 新增字典数据
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysDictDataSaveReq addReq) {
        SysDictDataBo dictDataBo = BeanUtils.toBean(addReq, SysDictDataBo.class);
        if (!sysDictDataService.checkDictDataUnique(dictDataBo)) {
            return error(DICT_VALUE_EXIST, addReq.getDictValue());
        }
        Long sysDictDataId = sysDictDataService.addSysDictData(addReq);
        return success(sysDictDataId);
    }

    /**
     * 修改字典数据
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysDictDataUpdateReq updateReq) {
        SysDictDataBo dictDataBo = BeanUtils.toBean(updateReq, SysDictDataBo.class);
        if (!sysDictDataService.checkDictDataUnique(dictDataBo)) {
            return error(DICT_VALUE_EXIST, updateReq.getDictValue());
        }
        Boolean result = sysDictDataService.updateSysDictData(updateReq);
        return success(result);
    }

    /**
     * 删除字典数据
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysDictDataService.deleteSysDictData(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取字典数据详细信息
     * @param id 数据id
     * @return 字典数据对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysDictDataResp> getSysDictData(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysDictData sysDictData = sysDictDataService.getSysDictData(id);
        return success(BeanUtils.toBean(sysDictData, SysDictDataResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReq 分页参数
     * @return 字典数据分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysDictDataResp>> getSysDictDataPage(@Valid SysDictDataPageReq pageReq) {
        PageResult<SysDictData> pageResult = sysDictDataService.getSysDictDataPage(pageReq);
        return success(BeanUtils.toBean(pageResult, SysDictDataResp.class));
    }



}
