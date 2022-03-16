package com.qingyan.audit.handler;

import com.qingyan.audit.pojo.AuditLogRecord;

/**
 * IAuditLogHandler 审计日志服务层，通过实现该接口来具体处理审计日志逻辑
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/3/4 14:43
 */
public interface IAuditLogHandler {

    /**
     * 插入审计日志
     *
     * @param auditLogRecord {@link AuditLogRecord}
     */
    void handleRecord(AuditLogRecord auditLogRecord);

}
