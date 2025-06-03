package com.aixbox.demo.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.demo.domain.entity.DemoTest;
import com.aixbox.demo.domain.vo.request.DemoTestPageReqVO;
import com.aixbox.demo.domain.vo.request.DemoTestSaveReq;
import com.aixbox.demo.domain.vo.request.DemoTestUpdateReq;
import com.aixbox.demo.domain.vo.response.DemoTestRespVO;
import com.aixbox.demo.service.DemoTestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

import java.util.Arrays;

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
    public CommonResult<DemoTestRespVO> getDemoTest(@PathVariable("id") Long id) {
        DemoTest demoTest = demoTestService.getDemoTest(id);
        return success(BeanUtils.toBean(demoTest, DemoTestRespVO.class));
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<DemoTestRespVO>> getDemoTestPage(@Valid DemoTestPageReqVO pageReqVO) {
        PageResult<DemoTest> pageResult = demoTestService.getDemoTestPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DemoTestRespVO.class));
    }



}
