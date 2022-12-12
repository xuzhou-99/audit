package cn.altaria.audit.handler;

import cn.altaria.audit.pojo.AuditLogRecord;

/**
 * AbstractAuditLogHandler 审计日志服务层，通过实现该接口来具体处理审计日志逻辑
 *
 * @author xuzhou
 * @version v1.0.1
 * @since 2022/3/4 14:43
 */
public abstract class AbstractAuditLogHandler {

    /**
     * 插入审计日志
     *
     * @param auditLogRecord {@link AuditLogRecord}
     */
    public void handleRecord(AuditLogRecord auditLogRecord) {

    }

}
