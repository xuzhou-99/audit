package cn.altaria.audit.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;

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
     * 日志编号
     */
    @Id
    @Column(name = "C_BH")
    private String bh;

    /**
     * 日志内容
     */
    @Column(name = "C_DESCRIPTION")
    private String description;

    /**
     * 操作类型
     */
    @Column(name = "n_operation_type")
    private Integer operationType;

    /**
     * 操作类型名称
     */
    @Column(name = "c_operation_name")
    private String operationName;

    /**
     * 日志模块类型
     */
    @Column(name = "n_module_code")
    private Integer moduleCode;

    /**
     * 日志模块名称
     */
    @Column(name = "c_module_name")
    private String moduleName;

    /**
     * 操作日志时间
     */
    @Column(name = "dt_operation_time")
    private LocalDateTime operationTime;

    /**
     * 用户Id
     */
    @Column(name = "c_user_id")
    private String userId;

    /**
     * 用户名称
     */
    @Column(name = "c_user_name")
    private String userName;

    /**
     * 用户登录名
     */
    @Column(name = "c_user_loginid")
    private String userLoginId;

    /**
     * userAgent
     */
    @Column(name = "c_user_agent")
    private String userAgent;

    /**
     * IP
     */
    @Column(name = "C_IP")
    private String ip;

    /**
     * 拓展字段
     */
    @Column(name = "C_EXTENSION")
    private String extension;


}
