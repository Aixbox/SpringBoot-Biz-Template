package com.aixbox.common.core.enums;

import com.aixbox.common.core.utils.StrUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备类型
 * 针对多套 用户体系
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum UserType {

    /**
     * pc端
     */
    SYS_USER("sys_user"),

    /**
     * app端
     */
    APP_USER("app_user");

    private final String userType;

    public static UserType getUserType(String str) {
        for (UserType value : values()) {
            if (StrUtils.contains(str, value.getUserType())) {
                return value;
            }
        }
        throw new RuntimeException("'UserType' not found By " + str);
    }
}
