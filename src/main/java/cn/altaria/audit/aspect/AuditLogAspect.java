package cn.altaria.audit.aspect;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import javax.annotation.Resource;

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
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import cn.altaria.audit.annotation.AuditLog;
import cn.altaria.audit.handler.IAuditLogHandler;
import cn.altaria.audit.pojo.AuditLogRecord;


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
 * 👉   异常 {@link AfterThrowing} 在连接点（方法）抛出异常后执行    👉   {@link After} 在连接点（方法）之后执行，无论异常与否  👉   {@link org.aspectj.lang.annotation.Around}
 * <p>
 * 👉   正常 {@link AfterReturning} 在连接点（方法）成功执行后执行   👉   {@link After} 在连接点（方法）之后执行，无论异常与否  👉   {@link org.aspectj.lang.annotation.Around}
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/2/10 16:25
 */
@Aspect
public class AuditLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogAspect.class);

    @Resource
    private IAuditLogHandler auditLogHandler;


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
    @Pointcut(value = "@annotation(cn.altaria.audit.annotation.AuditLog)")
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

//            // 获取 RequestAttributes
//            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//            if (null != requestAttributes) {
//                // 获取 HttpServletRequest
//                HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//                // 获取IP
//                String ip = RequestUtils.getIpAddress(request);
//                auditLogRecord.setIp(ip);
//
//                // 获取 user-agent
//                String userAgent = RequestUtils.getUserAgent(request);
//                auditLogRecord.setUserAgent(userAgent);
//            }

            LOGGER.info("args：{}", args);
            String expression = parseExtensionExpression(auditLog.extension(), args);
            LOGGER.info("expression：{}", expression);
            String description = parseExtensionExpression(auditLog.description(), args);
            LOGGER.info("description：{}", description);

            auditLogRecord.setDescription(description)
                    .setModuleCode(auditLog.module().getModuleCode())
                    .setModuleName(auditLog.module().getModuleName())
                    .setOperationType(auditLog.operationType().getTypeCode())
                    .setOperationName(auditLog.operationType().getTypeName())
                    .setOperationTime(LocalDateTime.now())
                    .setExtension(expression);

            auditLogHandler.handleRecord(auditLogRecord);

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
