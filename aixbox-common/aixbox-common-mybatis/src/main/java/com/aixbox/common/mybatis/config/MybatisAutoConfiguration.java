package com.aixbox.common.mybatis.config;


import cn.hutool.core.net.NetUtil;
import com.aixbox.common.core.utils.spring.SpringUtils;
import com.aixbox.common.datePermission.aspect.DataPermissionAspect;
import com.aixbox.common.datePermission.interceptor.DataPermissionInterceptor;
import com.aixbox.common.mybatis.core.handler.DefaultDBFieldHandler;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Mybatis 配置类
 */
@AutoConfiguration(before = MybatisPlusAutoConfiguration.class) // 目的：先于 MyBatis Plus 自动配置，避免 @MapperScan 可能扫描不到 Mapper 打印 warn 日志
@MapperScan(value = "${aixbox.info.base-package}.**.mapper", annotationClass = Mapper.class,
        lazyInitialization = "${mybatis.lazy-initialization:false}") // Mapper 懒加载，目前仅用于单元测试
public class MybatisAutoConfiguration {

    //todo 添加本地缓存优化
    //static {
    //    // 动态 SQL 智能优化支持本地缓存加速解析，更完善的租户复杂 XML 动态 SQL 支持，静态注入缓存
    //    JsqlParserGlobal.setJsqlParseCache(new JdkSerialCaffeineJsqlParseCache(
    //            (cache) -> cache.maximumSize(1024)
    //                            .expireAfterWrite(5, TimeUnit.SECONDS))
    //    );
    //}

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 数据权限处理
        mybatisPlusInterceptor.addInnerInterceptor(dataPermissionInterceptor());
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件
        mybatisPlusInterceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    /**
     * 数据权限切面处理器
     */
    @Bean
    public DataPermissionAspect dataPermissionAspect() {
        return new DataPermissionAspect();
    }


    /**
     * 数据权限拦截器
     */
    public DataPermissionInterceptor dataPermissionInterceptor() {
        String property = SpringUtils.getProperty("aixbox.info.base-package");
        return new DataPermissionInterceptor( property + ".**.mapper.*Mapper");
    }


    /**
     * 分页插件，自动识别数据库类型
     */
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 分页合理化
        paginationInnerInterceptor.setOverflow(true);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁插件
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 元对象字段填充控制器
     */
    @Bean
    public MetaObjectHandler defaultMetaObjectHandler() {
        return new DefaultDBFieldHandler(); // 自动填充参数类
    }

    /**
     * 使用网卡信息绑定雪花生成器
     * 防止集群雪花ID重复
     */
    @Bean
    public IdentifierGenerator idGenerator() {
        return new DefaultIdentifierGenerator(NetUtil.getLocalhost());
    }

}
