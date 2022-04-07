## 审计日志模块

通过Spring AOP切面来实现审计日志模块，面向切面编程能够降低与系统主题业务逻辑之间的耦合度， 从而实现更加精练的系统架构。

### 最终使用示例
```java

/**
 * ExampleController
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/2/16 22:53
 */
@Controller
public class ExampleController {

    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    /**
     * 审计日志注解示例
     *
     * @param params {@link JSONObject}
     * @param bh     {@link String}
     * @return 操作结果
     */
    @PostMapping({"/audit"})
    @ResponseBody
    @AuditLog(description = "审计日志", module = AuditLogModuleEnum.USER, extension = "单位名称：#{[0].get('name')},编号：#{[1]}")
    public Object updateCorp(JSONObject params, String bh) {
        logger.info("单位名称：{}", params.get("name"));
        logger.info("编号：{}", bh);
        return "ok";
    }

    /**
     * 审计日志注解示例
     *
     * @param bh     {@link String}
     * @return 操作结果
     */
    @GetMapping({"/audit1"})
    @ResponseBody
    @AuditLog(description = "审计日志", module = AuditLogModuleEnum.USER, extension = "编号：#{[0]}")
    public Object test1( String bh) {
        logger.info("编号：{}", bh);
        return "ok";
    }
}
```

### 依赖

引入Spring AOP模块依赖

```xml
<!--AOP-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### 定义审计日志Pojo

审计日志Pojo需要包含以下数据：操作人、操作时间、操作对象，作为基础的审计日志数据，可以自定义拓展实现更多的数据记录

```java
package com.example.springboot.audit.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * AuditLogRecord
 * 审计日志信息
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/2/10 16:17
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class AuditLogRecord implements Serializable {

    private static final long serialVersionUID = 7915910801199728820L;

    /**
     * 日志编号
     */
    private String bh;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 日志内容
     */
    private String description;

    /**
     * 用户登录名
     */
    private String userLoginId;

    /**
     * 操作类型
     */
    private Integer operationType;

    /**
     * 操作类型名称
     */
    private String operationName;

    /**
     * 日志模块类型
     */
    private Integer moduleCode;

    /**
     * 日志模块名称
     */
    private String moduleName;

    /**
     * 操作日志时间
     */
    private LocalDateTime operationTime;

    /**
     * IP
     */
    private String ip;

    /**
     * userAgent
     */
    private String userAgent;

    /**
     * 拓展字段
     */
    private String extension;
}

```

继承 `AuditLogRecord `可以自定义拓展字段

```java
public class AuditLogRecordExtend extends AuditLogRecord {
    /**
     * 拓展字段
     */
    private String data;
}
```

### 定义审计日志模块

根据系统中的数据类型和应用模块，定义枚举类存放日志模块，主要分为登录模块、用户模块、业务数据模块、权限模块

```java
package com.example.springboot.audit.enums;

import lombok.Getter;

/**
 * AuditLogModuleEnum
 * 审计日志模块枚举
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/2/10 15:38
 */
public enum AuditLogModuleEnum {

    /**
     * 审计日志模块
     */
    LOGIN(0, "登录"),
    USER(1, "用户"),
    DATA(2, "业务数据"),
    RIGHT(3, "权限"),
    ;

    AuditLogModuleEnum(int moduleCode, String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    @Getter
    private final int moduleCode;

    @Getter
    private final String moduleName;
}

```

### 定义审计日志操作

根据用户的操作类型，定义日志操作枚举类，主要有登录、登出、新增、修改、删除、查询、资源下载、资源上传等操作

```java
package com.example.springboot.audit.enums;

import lombok.Getter;

/**
 * AuditLogOperationTypeEnum
 * 审计日志操作类型枚举
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/2/10 15:41
 */
public enum AuditLogOperationTypeEnum {
    /**
     * 审计日志模块
     */
    login(0, "登录"),
    logout(1, "登出"),
    insert(2, "新增"),
    update(3, "修改"),
    delete(4, "删除"),
    query(5, "查询"),
    download(6, "资源下载"),
    upload(7, "资源上传"),
    ;

    AuditLogOperationTypeEnum(int typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    @Getter
    private final int typeCode;

    @Getter
    private final String typeName;
}

```

### 定义注解

定义审计日志注解，其中包含一些需要处理记录的信息，审计日志主要记录的是数据的变动，在查询上优先级较低，所以默认为业务数据+修改操作， 提供一个字段`description`记录概要内容，支持`SpEL`语法，字段`extension`
记录拓展信息，也支持支持`SpEL`语法

```java
package com.example.springboot.audit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.springboot.audit.enums.AuditLogModuleEnum;
import com.example.springboot.audit.enums.AuditLogOperationTypeEnum;


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

```

### 声明切面处理类

切面处理类，通过切面注解实现对连接点，即注解定位的方法内容进行处理，记录审计日志

```java
package com.example.springboot.audit.aspect;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.example.springboot.audit.annotation.AuditLog;
import com.example.springboot.audit.pojo.AuditLogRecord;
import com.example.springboot.audit.server.IAuditLogServer;
import com.example.springboot.audit.util.RequestUtils;


/**
 * AuditLogAspect
 * 审计日志注解切面
 * <p>
 * AOP
 * <p>
 * * 开始 {@link org.aspectj.lang.annotation.Around}连接点前后开始，将切入点包起来执行 👉   {@link Before} 在连接点（方法）之前执行
 * <p>
 * 👉   进入 Method连接点    👉   执行是否异常
 * <p>
 *  👉   异常 {@link AfterThrowing} 在连接点（方法）抛出异常后执行    👉   {@link After} 在连接点（方法）之后执行，无论异常与否  👉   {@link org.aspectj.lang.annotation.Around}
 * <p>
 * 👉   正常 {@link AfterReturning} 在连接点（方法）成功执行后执行   👉   {@link After} 在连接点（方法）之后执行，无论异常与否  👉   {@link org.aspectj.lang.annotation.Around}
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/2/10 16:25
 */
@Aspect
@Component
public class AuditLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogAspect.class);

    @Resource
    private IAuditLogServer auditLogServer;

    @Autowired
    public AuditLogAspect() {

    }

    /**
     * 定义注解切点，注解拦截
     *
     * @param auditLog {@link AuditLog}
     */
    @Pointcut(value = "@annotation(auditLog)")
    public void logPoint(AuditLog auditLog) {

    }

    /**
     * 定义注解切点，注解拦截
     */
    @Pointcut(value = "@annotation(com.qingyan.audit.annotation.AuditLog)")
    public void auditLogPoint() {

    }

    /**
     * 环绕增强
     * 先执行 pjp.proceed() 然后进入 @Before，然后执行主方法，回到 @Around 的 pjp.proceed() 后
     *
     * @param point {@link ProceedingJoinPoint}
     * @return Object
     */
    @Around(value = "logPoint(auditLog)", argNames = "point,auditLog")
    public Object doAround(ProceedingJoinPoint point, AuditLog auditLog) {
        LOGGER.info("进入 @Around ......");
        Object result = null;
        try {

            // 操作日志
            AuditLogRecord auditLogRecord = new AuditLogRecord();

            // 从切入点通过反射获取切入点方法
            MethodSignature signature = (MethodSignature) point.getSignature();
            // 获取执行方法
            Method method = signature.getMethod();
            // 获取方法参数
            Object[] args = point.getArgs();

            // 获取 RequestAttributes
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (null != requestAttributes) {
                // 获取 HttpServletRequest
                HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
                // 获取IP
                String ip = RequestUtils.getIpAddress(request);
                auditLogRecord.setIp(ip);
                if (null != request) {
                    // 获取 user-agent
                    String userAgent = request.getHeader("user-agent");
                    auditLogRecord.setUserAgent(userAgent);
                }
            }

            LOGGER.info("args：{}", args);
            String expression = parseExtensionExpression(auditLog.extension(), args);
            LOGGER.info("expression：{}", expression);
            String description = parseExtensionExpression(auditLog.description(), args);
            LOGGER.info("description：{}", description);

            auditLogRecord.setModuleCode(auditLog.module().getModuleCode())
                    .setModuleName(auditLog.module().getModuleName())
                    .setOperationType(auditLog.operationType().getTypeCode())
                    .setOperationName(auditLog.operationType().getTypeName())
                    .setOperationTime(LocalDateTime.now())
                    .setDescription(description)
                    .setExtension(expression);

            auditLogServer.insertLog(auditLogRecord);

            result = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        LOGGER.info("退出 @Around ......");
        return result;
    }

    /**
     * 前置通知，方法调用前被调用
     * 除非抛出一个异常，否则这个通知不能阻止连接点之前的执行流程
     */
    @Before(value = "auditLogPoint()")
    public void doBefore() {
        LOGGER.info("进入 @Before......");

    }

    /**
     * 后置通知，如果切入点抛出异常，则不会执行
     */
    @AfterReturning(value = "auditLogPoint()")
    public void doAfterReturning() {
        LOGGER.info("进入 @AfterReturning......");

    }

    /**
     * 异常返回通知，切入点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常
     */
    @AfterThrowing(pointcut = "auditLogPoint()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        LOGGER.error("进入 @AfterThrowing......错误：" + e.getMessage());

    }

    @After(value = "auditLogPoint()")
    public void doAfter() {
        LOGGER.info("进入 @After......");

    }


    /**
     * 解析SpEL表达式，从参数中获取数据并生成描述
     *
     * @param extensionExpression 描述表达式（SpEL）
     * @param args                参数
     * @return 拼接结果
     */
    private String parseExtensionExpression(String extensionExpression, Object[] args) {
        // SpEL解析器
        SpelExpressionParser parser = new SpelExpressionParser();
        // 解析参数名称和参数值
        Expression expression = parser.parseExpression(extensionExpression, new TemplateParserContext());

        return expression.getValue(new StandardEvaluationContext(args), String.class);
    }

}

```
