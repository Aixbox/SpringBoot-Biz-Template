package com.aixbox.system.domain.vo.request.user;

import com.aixbox.system.domain.entity.SysSocial;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;


/**
 * 社交用户 更新参数
 */
@Data
@AutoMapper(target = SysSocial.class)
public class SysSocialUpdateReqVO {

    /**
    * 主键
    */
    @NotNull
    private Long id;
    /**
    * 用户ID
    */
    private Long userId;
    /**
    * 平台+平台唯一id
    */
    private String authId;
    /**
    * 用户来源
    */
    private String source;
    /**
    * 平台编号唯一id
    */
    private String openId;
    /**
    * 登录账号
    */
    private String userName;
    /**
    * 用户昵称
    */
    private String nickName;
    /**
    * 用户邮箱
    */
    private String email;
    /**
    * 头像地址
    */
    private String avatar;
    /**
    * 用户的授权令牌
    */
    private String accessToken;
    /**
    * 用户的授权令牌的有效期，部分平台可能没有
    */
    private Long expireIn;
    /**
    * 刷新令牌，部分平台可能没有
    */
    private String refreshToken;
    /**
    * 平台的授权信息，部分平台可能没有
    */
    private String accessCode;
    /**
    * 用户的 unionid
    */
    private String unionId;
    /**
    * 授予的权限，部分平台可能没有
    */
    private String scope;
    /**
    * 个别平台的授权信息，部分平台可能没有
    */
    private String tokenType;
    /**
    * id token，部分平台可能没有
    */
    private String idToken;
    /**
    * 小米平台用户的附带属性，部分平台可能没有
    */
    private String macAlgorithm;
    /**
    * 小米平台用户的附带属性，部分平台可能没有
    */
    private String macKey;
    /**
    * 用户的授权code，部分平台可能没有
    */
    private String code;
    /**
    * Twitter平台用户的附带属性，部分平台可能没有
    */
    private String oauthToken;
    /**
    * Twitter平台用户的附带属性，部分平台可能没有
    */
    private String oauthTokenSecret;
                                        
}
