package cn.altaria.audit.handler;

import cn.altaria.audit.pojo.AuditLogRecord;

/**
 * @author xuzhou
 * @since 2022/12/12
 */
public interface IAuditLogHandler {

    /**
     * 插入审计日志
     *
     * @param auditLogRecord {@link AuditLogRecord}
     */
    void handleRecord(AuditLogRecord auditLogRecord);
}
