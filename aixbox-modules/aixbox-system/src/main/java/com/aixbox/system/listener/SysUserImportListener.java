package com.aixbox.system.listener;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.http.HtmlUtil;
import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.utils.StreamUtils;
import com.aixbox.common.core.utils.ValidatorUtils;
import com.aixbox.common.core.utils.spring.SpringUtils;
import com.aixbox.common.excel.core.ExcelListener;
import com.aixbox.common.excel.core.ExcelResult;
import com.aixbox.common.security.utils.LoginHelper;
import com.aixbox.system.domain.bo.SysUserBo;
import com.aixbox.system.domain.vo.response.SysUserResp;
import com.aixbox.system.service.SysUserService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 系统用户自定义导入
 */
@Slf4j
public class SysUserImportListener extends AnalysisEventListener<SysUserResp> implements ExcelListener<SysUserResp> {


    private final SysUserService userService;

    private final String password;

    private final Boolean isUpdateSupport;

    private final Long operUserId;

    private int successNum = 0;
    private int failureNum = 0;
    private final StringBuilder successMsg = new StringBuilder();
    private final StringBuilder failureMsg = new StringBuilder();

    public SysUserImportListener(Boolean isUpdateSupport) {
        //todo 创建配置类
        //String initPassword = SpringUtils.getBean(SysConfigService.class).selectConfigByKey("sys.user.initPassword");
        this.userService = SpringUtils.getBean(SysUserService.class);
        //this.password = BCrypt.hashpw(initPassword);
        this.password = BCrypt.hashpw("123456");
        this.isUpdateSupport = isUpdateSupport;
        this.operUserId = LoginHelper.getUserId();
    }

    @Override
    public void invoke(SysUserResp userVo, AnalysisContext context) {
        SysUserResp sysUser = this.userService.selectUserByUserName(userVo.getUserName());
        // todo
        //try {
        //    // 验证是否存在这个用户
        //    if (ObjectUtil.isNull(sysUser)) {
        //        SysUserBo user = BeanUtil.toBean(userVo, SysUserBo.class);
        //        ValidatorUtils.validate(user);
        //        user.setPassword(password);
        //        user.setCreateBy(operUserId);
        //        userService.insertUser(user);
        //        successNum++;
        //        successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUserName()).append(" 导入成功");
        //    } else if (isUpdateSupport) {
        //        Long userId = sysUser.getId();
        //        SysUserBo user = BeanUtil.toBean(userVo, SysUserBo.class);
        //        user.setId(userId);
        //        ValidatorUtils.validate(user);
        //        userService.checkUserAllowed(user.getId());
        //        userService.checkUserDataScope(user.getId());
        //        user.setUpdater(operUserId);
        //        userService.updateUser(user);
        //        successNum++;
        //        successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUserName()).append(" 更新成功");
        //    } else {
        //        failureNum++;
        //        failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(sysUser.getUserName()).append(" 已存在");
        //    }
        //} catch (Exception e) {
        //    failureNum++;
        //    String msg = "<br/>" + failureNum + "、账号 " + HtmlUtil.cleanHtmlTag(userVo.getUserName()) + " 导入失败：";
        //    String message = e.getMessage();
        //    if (e instanceof ConstraintViolationException cvException) {
        //        message = StreamUtils.join(cvException.getConstraintViolations(), ConstraintViolation::getMessage, ", ");
        //    }
        //    failureMsg.append(msg).append(message);
        //    log.error(msg, e);
        //}
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public ExcelResult<SysUserResp> getExcelResult() {
        return new ExcelResult<>() {

            @Override
            public String getAnalysis() {
                if (failureNum > 0) {
                    failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                    throw new ServiceException(failureMsg.toString());
                } else {
                    successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
                }
                return successMsg.toString();
            }

            @Override
            public List<SysUserResp> getList() {
                return null;
            }

            @Override
            public List<String> getErrorList() {
                return null;
            }
        };
    }

}
