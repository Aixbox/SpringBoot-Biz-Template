package com.aixbox.system.domain.vo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 用户注册对象
 *
 * @author Lion Li
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterBody extends LoginBody {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 20, message = "账户长度必须在{min}到{max}个字符之间")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 5, max = 20, message = "用户密码长度必须在{min}到{max}个字符之间")
    private String password;

    private String userType;

}
