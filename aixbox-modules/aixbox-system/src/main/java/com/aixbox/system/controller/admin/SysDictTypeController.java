package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.excel.utils.ExcelUtil;
import com.aixbox.system.domain.entity.SysDictType;
import com.aixbox.system.domain.vo.request.dict.SysDictTypePageReq;
import com.aixbox.system.domain.vo.request.dict.SysDictTypeQueryReq;
import com.aixbox.system.domain.vo.request.dict.SysDictTypeSaveReq;
import com.aixbox.system.domain.vo.request.dict.SysDictTypeUpdateReq;
import com.aixbox.system.domain.vo.response.SysDictTypeResp;
import com.aixbox.system.service.SysDictTypeService;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.success;

/**
 * 字典类型 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;


    /**
     * 查询字典类型列表
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    public CommonResult<PageResult<SysDictTypeResp>> list(@Valid SysDictTypePageReq pageQuery) {
        PageResult<SysDictType> pageResult = sysDictTypeService.selectPageDictTypeList(pageQuery);
        return success(BeanUtils.toBean(pageResult, SysDictTypeResp.class));
    }


    /**
     * 刷新字典缓存
     */
    @SaCheckPermission("system:dict:remove")
    @DeleteMapping("/refreshCache")
    public CommonResult<Void> refreshCache() {
        sysDictTypeService.resetDictCache();
        return success();
    }

    /**
     * 导出字典类型列表
     */
    @SaCheckPermission("system:dict:export")
    @PostMapping("/export")
    public void export(SysDictTypeQueryReq dictType, HttpServletResponse response) {
        List<SysDictTypeResp> list = sysDictTypeService.selectDictTypeList(dictType);
        ExcelUtil.exportExcel(list, "字典类型", SysDictTypeResp.class, response);
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public CommonResult<List<SysDictTypeResp>> optionselect() {
        List<SysDictType> dictTypes = sysDictTypeService.selectDictTypeAll();
        return success(BeanUtils.toBean(dictTypes, SysDictTypeResp.class));
    }








    /**
     * 新增字典类型
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysDictTypeSaveReq addReq) {
        Long sysDictTypeId = sysDictTypeService.addSysDictType(addReq);
        return success(sysDictTypeId);
    }

    /**
     * 修改字典类型
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysDictTypeUpdateReq updateReq) {
        Boolean result = sysDictTypeService.updateSysDictType(updateReq);
        return success(result);
    }

    /**
     * 删除字典类型
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysDictTypeService.deleteSysDictType(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取字典类型详细信息
     * @param id 数据id
     * @return 字典类型对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysDictTypeResp> getSysDictType(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysDictType sysDictType = sysDictTypeService.getSysDictType(id);
        return success(BeanUtils.toBean(sysDictType, SysDictTypeResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReq 分页参数
     * @return 字典类型分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysDictTypeResp>> getSysDictTypePage(@Valid SysDictTypePageReq pageReq) {
        PageResult<SysDictType> pageResult = sysDictTypeService.getSysDictTypePage(pageReq);
        return success(BeanUtils.toBean(pageResult, SysDictTypeResp.class));
    }



}
