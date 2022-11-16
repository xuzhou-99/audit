package cn.altaria.audit.pojo;

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
 * @version v1.0.1
 * @date 2022/2/10 16:17
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class AuditLogRecord implements Serializable {

    /**
     * 日志编号
     */
    private String bh;

    /**
     * 日志内容
     */
    private String description;

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
     * 用户Id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户登录名
     */
    private String userLoginName;

    /**
     * userAgent
     */
    private String userAgent;

    /**
     * IP
     */
    private String ip;

    /**
     * 拓展字段
     */
    private String extension;


}
