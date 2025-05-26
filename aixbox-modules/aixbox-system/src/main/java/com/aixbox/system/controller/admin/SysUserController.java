package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.domain.model.LoginUser;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.excel.core.ExcelResult;
import com.aixbox.common.excel.utils.ExcelUtil;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.bo.SysUserBo;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.request.user.SysUserPageReqVO;
import com.aixbox.system.domain.vo.request.user.SysUserSaveReqVO;
import com.aixbox.system.domain.vo.request.user.SysUserUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysUserResp;
import com.aixbox.system.domain.vo.response.UserInfoResp;
import com.aixbox.system.listener.SysUserImportListener;
import com.aixbox.system.service.SysUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.error;
import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.system.constant.ErrorCodeConstants.USERNAME_NO_PERMISSION;

/**
 * 用户 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    private final SysUserService sysUserService;






    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public CommonResult<UserInfoResp> getInfo() {
        UserInfoResp userInfoVo = new UserInfoResp();
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUserResp user = sysUserService.selectUserById(loginUser.getUserId());
        if (ObjectUtil.isNull(user)) {
            return error(USERNAME_NO_PERMISSION);
        }
        userInfoVo.setUser(user);
        userInfoVo.setPermissions(loginUser.getMenuPermission());
        userInfoVo.setRoles(loginUser.getRolePermission());
        return success(userInfoVo);
    }


    /**
     * 导出用户列表
     */
    @SaCheckPermission("system:user:export")
    @PostMapping("/export")
    public void export(SysUserBo user, HttpServletResponse response) {
        List<SysUserResp> list = sysUserService.selectUserExportList(user);
        ExcelUtil.exportExcel(list, "用户数据", SysUserResp.class, response);
    }












    /**
     * 新增用户
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Long> add(@Valid @RequestBody SysUserSaveReqVO addReqVO) {
        Long sysUserId = sysUserService.addSysUser(addReqVO);
        return success(sysUserId);
    }

    /**
     * 修改用户
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @PutMapping("/update")
    public CommonResult<Boolean> edit(@Valid @RequestBody SysUserUpdateReqVO updateReqVO) {
        Boolean result = sysUserService.updateSysUser(updateReqVO);
        return success(result);
    }

    /**
     * 删除用户
     * @param ids 删除id数组
     * @return 是否成功
     */
    @DeleteMapping("/{ids}")
    public CommonResult<Boolean> remove(@NotEmpty(message = "主键不能为空")
                                     @PathVariable Long[] ids) {
        Boolean result = sysUserService.deleteSysUser(Arrays.asList(ids));
        return success(result);
    }

    /**
     * 获取用户详细信息
     * @param id 数据id
     * @return demo对象
     */
    @GetMapping("/{id}")
    public CommonResult<SysUserResp> getSysUser(@NotNull(message = "主键不能为空")
                                                    @PathVariable("id") Long id) {
        SysUser sysUser = sysUserService.getSysUser(id);
        return success(BeanUtils.toBean(sysUser, SysUserResp.class));
    }

    /**
     * 分页查询demo
     * @param pageReqVO 分页参数
     * @return demo分页对象
     */
    @GetMapping("/page")
    public CommonResult<PageResult<SysUserResp>> getSysUserPage(@Valid SysUserPageReqVO pageReqVO) {
        PageResult<SysUser> pageResult = sysUserService.getSysUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SysUserResp.class));
    }



}
