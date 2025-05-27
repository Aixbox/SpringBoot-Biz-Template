package com.aixbox.system.controller.admin;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.aixbox.common.core.constant.SystemConstants;
import com.aixbox.common.core.domain.model.LoginUser;
import com.aixbox.common.core.pojo.CommonResult;
import com.aixbox.common.core.pojo.PageResult;
import com.aixbox.common.core.utils.StrUtils;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.object.BeanUtils;
import com.aixbox.common.excel.core.ExcelResult;
import com.aixbox.common.excel.utils.ExcelUtil;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.bo.SysDeptBo;
import com.aixbox.system.domain.bo.SysPostBo;
import com.aixbox.system.domain.bo.SysRoleBo;
import com.aixbox.system.domain.bo.SysUserBo;
import com.aixbox.system.domain.bo.SysUserImportBo;
import com.aixbox.system.domain.entity.SysPost;
import com.aixbox.system.domain.entity.SysRole;
import com.aixbox.system.domain.entity.SysUser;
import com.aixbox.system.domain.vo.request.role.SysRoleQueryReq;
import com.aixbox.system.domain.vo.request.user.SysUserPageReqVO;
import com.aixbox.system.domain.vo.request.user.SysUserSaveReqVO;
import com.aixbox.system.domain.vo.request.user.SysUserUpdateReqVO;
import com.aixbox.system.domain.vo.response.SysPostResp;
import com.aixbox.system.domain.vo.response.SysRoleResp;
import com.aixbox.system.domain.vo.response.SysUserInfoResp;
import com.aixbox.system.domain.vo.response.SysUserResp;
import com.aixbox.system.domain.vo.response.UserInfoResp;
import com.aixbox.system.listener.SysUserImportListener;
import com.aixbox.system.service.SysDeptService;
import com.aixbox.system.service.SysPostService;
import com.aixbox.system.service.SysRoleService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aixbox.common.core.pojo.CommonResult.error;
import static com.aixbox.common.core.pojo.CommonResult.success;
import static com.aixbox.common.core.pojo.CommonResult.toAjax;
import static com.aixbox.system.constant.ErrorCodeConstants.ADD_USER_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.DELETE_CURRENT_USER_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.DELETE_USER_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.EMAIL_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.PHONE_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.RESET_PASSWORD_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_EMAIL_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_PHONE_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_STATUS_ERROR;
import static com.aixbox.system.constant.ErrorCodeConstants.UPDATE_USERNAME_EXIST;
import static com.aixbox.system.constant.ErrorCodeConstants.USERNAME_NOT_EXIST_OR_NOT_ENABLE;
import static com.aixbox.system.constant.ErrorCodeConstants.USERNAME_NO_PERMISSION;
import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * 用户 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysPostService sysPostService;
    private final SysDeptService sysDeptService;



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
     * 导入数据
     *
     * @param file          导入文件
     * @param updateSupport 是否更新已存在数据
     */
    @SaCheckPermission("system:user:import")
    @PostMapping(value = "/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResult<Void> importData(@RequestPart("file") MultipartFile file, boolean updateSupport) throws Exception {
        ExcelResult<SysUserImportBo> result = ExcelUtil.importExcel(file.getInputStream(),
                SysUserImportBo.class, new SysUserImportListener(updateSupport));
        return success();
    }

    /**
     * 获取导入模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil.exportExcel(new ArrayList<>(), "用户数据", SysUserImportBo.class, response);
    }







    /**
     * 新增用户
     * @param addReqVO 新增参数
     * @return 新增数据id
     */
    @PostMapping("/add")
    public CommonResult<Void> add(@Valid @RequestBody SysUserSaveReqVO addReqVO) {
        SysUserBo user = BeanUtils.toBean(addReqVO, SysUserBo.class);
        sysDeptService.checkDeptDataScope(addReqVO.getDeptId());
        if (!sysUserService.checkUserNameUnique(user)) {
            return error(USERNAME_NOT_EXIST_OR_NOT_ENABLE, user.getUserName());
        } else if (StrUtils.isNotEmpty(user.getPhonenumber()) && !sysUserService.checkPhoneUnique(user)) {
            return error(PHONE_EXIST, user.getUserName());
        } else if (StrUtils.isNotEmpty(user.getEmail()) && !sysUserService.checkEmailUnique(user)) {
            return error(EMAIL_EXIST);
        }
        user.setPassword(BCrypt.hashpw(user.getPassword()));

        return toAjax(sysUserService.insertUser(user), ADD_USER_ERROR);
    }

    /**
     * 修改用户
     * @param updateReqVO 修改参数
     * @return 是否成功
     */
    @SaCheckPermission("system:user:edit")
    @PutMapping("/update")
    public CommonResult<Void> edit(@Valid @RequestBody SysUserUpdateReqVO updateReqVO) {
        SysUserBo user = BeanUtils.toBean(updateReqVO, SysUserBo.class);
        sysUserService.checkUserAllowed(user.getId());
        sysUserService.checkUserDataScope(user.getId());
        sysDeptService.checkDeptDataScope(user.getDeptId());
        if (!sysUserService.checkUserNameUnique(user)) {
            return error(UPDATE_USERNAME_EXIST, user.getUserName());
        } else if (StrUtils.isNotEmpty(user.getPhonenumber()) && !sysUserService.checkPhoneUnique(user)) {
            return error(UPDATE_PHONE_EXIST, user.getUserName());
        } else if (StrUtils.isNotEmpty(user.getEmail()) && !sysUserService.checkEmailUnique(user)) {
            return error(UPDATE_EMAIL_EXIST, user.getUserName());
        }
        return toAjax(sysUserService.updateUser(user), UPDATE_ERROR);
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:user:edit")
    @PutMapping("/changeStatus")
    public CommonResult<Void> changeStatus(@RequestBody SysUserBo user) {
        sysUserService.checkUserAllowed(user.getId());
        sysUserService.checkUserDataScope(user.getId());
        return toAjax(sysUserService.updateUserStatus(user.getId(), user.getStatus()), UPDATE_STATUS_ERROR);
    }


    /**
     * 删除用户
     * @param ids 删除id数组
     * @return 是否成功
     */
    @SaCheckPermission("system:user:remove")
    @DeleteMapping("/{ids}")
    public CommonResult<Void> remove(@PathVariable Long[] ids) {
        if (ArrayUtil.contains(ids, LoginHelper.getUserId())) {
            return error(DELETE_CURRENT_USER_ERROR);
        }
        return toAjax(sysUserService.deleteUserByIds(Arrays.asList(ids)), DELETE_USER_ERROR);
    }

    /**
     * 重置密码
     */
    @SaCheckPermission("system:user:resetPwd")
    @PutMapping("/resetPwd")
    public CommonResult<Void> resetPwd(@RequestBody SysUserBo user) {
        sysUserService.checkUserAllowed(user.getId());
        sysUserService.checkUserDataScope(user.getId());
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        return toAjax(sysUserService.resetUserPwd(user.getId(), user.getPassword()), RESET_PASSWORD_ERROR);
    }

    /**
     * 获取部门树列表
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/deptTree")
    public CommonResult<List<Tree<Long>>> deptTree(SysDeptBo dept) {
        return success(sysDeptService.selectDeptTreeList(dept));
    }

    /**
     * 获取部门下的所有用户信息
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/list/dept/{deptId}")
    public CommonResult<List<SysUserResp>> listByDept(@PathVariable @NotNull Long deptId) {
        return success(sysUserService.selectUserListByDept(deptId));
    }

    /**
     * 根据用户编号获取详细信息
     *
     * @param userId 用户ID
     */
    @SaCheckPermission("system:user:query")
    @GetMapping(value = {"/", "/{userId}"})
    public CommonResult<SysUserInfoResp> getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        SysUserInfoResp userInfoVo = new SysUserInfoResp();
        if (ObjectUtil.isNotNull(userId)) {
            sysUserService.checkUserDataScope(userId);
            SysUserResp sysUser = sysUserService.selectUserById(userId);
            userInfoVo.setUser(sysUser);
            userInfoVo.setRoleIds(sysRoleService.selectRoleListByUserId(userId));
            Long deptId = sysUser.getDeptId();
            if (ObjectUtil.isNotNull(deptId)) {
                SysPostBo postBo = new SysPostBo();
                postBo.setDeptId(deptId);
                userInfoVo.setPosts(sysPostService.selectPostList(postBo));
                userInfoVo.setPostIds(sysPostService.selectPostListByUserId(userId));
            }
        }
        SysRoleQueryReq roleBo = new SysRoleQueryReq();
        roleBo.setStatus(SystemConstants.NORMAL);
        List<SysRole> roles = sysRoleService.selectRoleList(roleBo);
        List<SysRoleResp> list = BeanUtils.toBean(roles, SysRoleResp.class);
        userInfoVo.setRoles(LoginHelper.isSuperAdmin(userId) ? list : StreamUtils.filter(list, r -> !r.isSuperAdmin()));
        return success(userInfoVo);
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
