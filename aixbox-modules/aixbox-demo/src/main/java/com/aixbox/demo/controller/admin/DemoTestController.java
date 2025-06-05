package com.aixbox.demo.controller.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageParam;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.excel.utils.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aixbox.demo.domain.entity.DemoTest;
import com.aixbox.demo.domain.vo.request.DemoTestPageReq;
import com.aixbox.demo.domain.vo.request.DemoTestSaveReq;
import com.aixbox.demo.domain.vo.request.DemoTestUpdateReq;
import com.aixbox.demo.domain.vo.response.DemoTestResp;
import com.aixbox.demo.service.DemoTestService;

import java.util.Arrays;
import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.common.core.pojo.CommonResult.toAjax;
import static com.aixbox.demo.constant.ErrorCodeConstants.DELETE_DEMO_ERROR;
import static com.aixbox.demo.constant.ErrorCodeConstants.UPDATE_DEMO_ERROR;

/**
 * 【请填写功能名称】 Controller
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/test")
public class DemoTestController {

    private final DemoTestService demoTestService;

    /**
     * 新增【请填写功能名称】
     * @param addReq 新增参数
     * @return 新增数据id
     */
    @SaCheckPermission("system:menu:add")
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody DemoTestSaveReq addReq) {
        Long demoTestId = demoTestService.addDemoTest(addReq);
        return success(demoTestId);
    }

    /**
     * 修改【请填写功能名称】
     * @param updateReq 修改参数
     * @return 是否成功
     */
    @SaCheckPermission("system:menu:update")
    @PutMapping("/update")
    public CommonResult<Void> update(@Valid @RequestBody DemoTestUpdateReq updateReq) {
        Boolean result = demoTestService.updateDemoTest(updateReq);
        return toAjax(result, UPDATE_DEMO_ERROR);
    }

    /**
     * 删除【请填写功能名称】
     * @param ids 删除id数组
     * @return 是否成功
     */
    @SaCheckPermission("system:menu:remove")
    @DeleteMapping("/{ids}")
    public CommonResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = demoTestService.deleteDemoTest(Arrays.asList(ids));
        return toAjax(result, DELETE_DEMO_ERROR);
    }

    /**
     * 获取【请填写功能名称】详细信息
     * @param id 数据id
     * @return demo对象
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping("/{id}")
    public CommonResult<DemoTestResp> getDemoTest(@PathVariable("id") Long id) {
        DemoTest demoTest = demoTestService.getDemoTest(id);
        return success(BeanUtils.toBean(demoTest, DemoTestResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReq 分页参数
     * @return demo分页对象
     */
    @SaCheckPermission("system:menu:list")
    @GetMapping("/page")
    public CommonResult<PageResult<DemoTestResp>> getDemoTestPage(@Valid DemoTestPageReq pageReq) {
        PageResult<DemoTest> pageResult = demoTestService.getDemoTestPage(pageReq);
        return success(BeanUtils.toBean(pageResult, DemoTestResp.class));
    }

    /**
     * 导出菜单权限列表
     */
    @SaCheckPermission("system:menu:export")
    @PostMapping("/export")
    public void export(@Valid DemoTestPageReq pageReq, HttpServletResponse response) {
        pageReq.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DemoTest> list = demoTestService.getDemoTestPage(pageReq).getList();
        List<DemoTestResp> respList = BeanUtils.toBean(list, DemoTestResp.class);
        ExcelUtil.exportExcel(respList, "菜单权限", DemoTestResp.class, response);
    }



}
