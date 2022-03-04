package com.qingyan.audit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qingyan.audit.enums.AuditLogModuleEnum;
import com.qingyan.audit.enums.AuditLogOperationTypeEnum;


/**
 * AuditLog
 * 审计日志注解
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/2/10 15:35
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditLog {

    /**
     * 日志描述
     * 支持SpEL语法
     *
     * @return 描述内容
     */
    String description() default "";

    /**
     * 审计日志模块
     *
     * @return {@link AuditLogModuleEnum}
     */
    AuditLogModuleEnum module() default AuditLogModuleEnum.DATA;

    /**
     * 审计日志操作类型
     *
     * @return {@link AuditLogOperationTypeEnum}
     */
    AuditLogOperationTypeEnum operationType() default AuditLogOperationTypeEnum.update;

    /**
     * 拓展信息
     * 支持SpEL语法
     *
     * @return {@link String}
     */
    String extension() default "";
}
