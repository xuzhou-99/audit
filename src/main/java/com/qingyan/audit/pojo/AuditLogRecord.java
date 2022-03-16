package com.qingyan.audit.pojo;

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

    /**
     * serialVersionUID
     */
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
