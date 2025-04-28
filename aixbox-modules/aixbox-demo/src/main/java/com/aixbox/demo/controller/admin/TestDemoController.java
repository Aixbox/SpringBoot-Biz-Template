package com.aixbox.demo.controller.admin;


import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.demo.domain.entity.TestDemo;
import com.aixbox.demo.domain.vo.request.TestDemoPageReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoSaveReqVO;
import com.aixbox.demo.domain.vo.request.TestDemoUpdateReqVO;
import com.aixbox.demo.domain.vo.response.TestDemoRespVO;
import com.aixbox.demo.service.TestDemoService;
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
 * demo Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo/demo")
public class TestDemoController {

    private final TestDemoService testDemoService;

    /**
     * 新增demo
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody TestDemoSaveReqVO addReqVO) {
        Long testDemoId = testDemoService.addTestDemo(addReqVO);
        return success(testDemoId);
    }

    /**
     * 修改demo
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody TestDemoUpdateReqVO updateReqVO) {
        Boolean result = testDemoService.updateTestDemo(updateReqVO);
        return success(result);
    }

    /**
     * 删除demo
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = testDemoService.deleteTestDemo(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取demo详细信息
     * @param id 数据id
     * @return demo对象
     */
    @GetMapping("/{id}")
    public CommonResult<TestDemoRespVO> getTestDemo(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        TestDemo testDemo = testDemoService.getTestDemo(id);
        return success(BeanUtils.toBean(testDemo, TestDemoRespVO.class));
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<TestDemoRespVO>> getTestDemoPage(@Valid TestDemoPageReqVO pageReqVO) {
        PageResult<TestDemo> pageResult = testDemoService.getTestDemoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TestDemoRespVO.class));
    }



}
