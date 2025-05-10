package com.aixbox.system.service.strategy;


import com.aixbox.common.core.exception.ServiceException;
import com.aixbox.common.core.utils.spring.SpringUtils;
import com.aixbox.system.domain.entity.SysClient;
import com.aixbox.system.domain.vo.response.LoginVO;

import static com.aixbox.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.aixbox.system.constant.ErrorCodeConstants.AUTH_TYPE_ERROR;

/**
 *
 */
public interface IAuthStrategy {

    String BASE_NAME = "AuthStrategy";

    /**
     * 登录
     *
     * @param body      登录对象
     * @param client    授权管理视图对象
     * @param grantType 授权类型
     * @return 登录验证信息
     */
    static LoginVO login(String body, SysClient client, String grantType) {
        // 授权类型和客户端id
        String beanName = grantType + BASE_NAME;
        if (!SpringUtils.containsBean(beanName)) {
            throw exception(AUTH_TYPE_ERROR);
        }
        IAuthStrategy instance = SpringUtils.getBean(beanName);
        return instance.login(body, client);
    }

    /**
     * 登录
     *
     * @param body   登录对象
     * @param client 授权管理视图对象
     * @return 登录验证信息
     */
    LoginVO login(String body, SysClient client);

}
