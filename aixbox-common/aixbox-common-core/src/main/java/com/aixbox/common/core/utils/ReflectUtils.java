package com.aixbox.common.core.utils;


import cn.hutool.core.util.ReflectUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 反射工具类. 提供调用getter/setter方法, 访问私有变量, 调用私有方法, 获取泛型类型Class, 被AOP过的真实类等工具函数.
 */
@SuppressWarnings("rawtypes")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectUtils extends ReflectUtil {

    private static final String GETTER_PREFIX = "get";

    /**
     * 调用Getter方法.
     * 支持多级，如：对象名.对象名.方法
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeGetter(Object obj, String propertyName) {
        Object object = obj;
        for (String name : StrUtils.split(propertyName, ".")) {
            String getterMethodName = GETTER_PREFIX + StrUtils.capitalize(name);
            object = invoke(object, getterMethodName);
        }
        return (E) object;
    }

}
