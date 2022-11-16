package cn.altaria.audit.enums;

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

    private final int moduleCode;
    private final String moduleName;

    AuditLogModuleEnum(int moduleCode, String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    public int getModuleCode() {
        return moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }
}
