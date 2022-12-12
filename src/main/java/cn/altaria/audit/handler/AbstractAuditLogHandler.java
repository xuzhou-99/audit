package cn.altaria.audit.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.altaria.audit.pojo.AuditLogRecord;

/**
 * AbstractAuditLogHandler 审计日志服务层，通过实现该接口来具体处理审计日志逻辑
 *
 * @author xuzhou
 * @version v1.0.1
 * @since 2022/3/4 14:43
 */
public abstract class AbstractAuditLogHandler implements IAuditLogHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractAuditLogHandler.class);

    /**
     * 插入审计日志
     *
     * @param auditLogRecord {@link AuditLogRecord}
     */
    @Override
    public void handleRecord(AuditLogRecord auditLogRecord) {
        log.info("【审计日志】编号：{}，用户：{}，模块：{}，操作：{}，操作时间：{}，内容：{}", auditLogRecord.getBh(),
                auditLogRecord.getUserId(), auditLogRecord.getModuleName(), auditLogRecord.getOperationName(),
                auditLogRecord.getOperationTime(), auditLogRecord.getDescription());
    }

}
