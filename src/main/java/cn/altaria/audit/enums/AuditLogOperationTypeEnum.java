package cn.altaria.audit.enums;

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

    private final int typeCode;
    private final String typeName;

    AuditLogOperationTypeEnum(int typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }
}
