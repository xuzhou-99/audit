# 审计日志模块

支持以注解的形式记录用户操作，审计日志模块组件，方便使用

通过**Spring AOP**切面来实现审计日志模块，面向切面编程能够降低与系统主题业务逻辑之间的**耦合**， 从而实现更加精练的系统架构。

## 项目使用

### 项目引用

**GitHub仓库**

项目目前依托于**GitHub**建立的仓库，需要先添加**仓库配置**

```yml
<repositories>
    <!-- github -->
    <repository>
        <id>github</id>
        <url>https://raw.github.com/xuzhou-99/mvn-repo/main</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```

**Maven依赖**

```yml
<!--audit 审计日志-->
<dependency>
    <groupId>cn.altaria</groupId>
    <artifactId>audit</artifactId>
    <version>1.0.1</version>
</dependency>
```



### 使用示例

#### 1、使用sql生产审计日志表结构

* mysql：[sys_audit_log_mysql.sql](https://github.com/xuzhou-99/audit/tree/main/sql/sys_audit_log_mysql.sql)
* pgsql：[sys_audit_log_pgsql.sql](https://github.com/xuzhou-99/audit/blob/main/sql/sys_audit_log_pgsql.sql)

#### 2、实现审计日志处理接口：IAuditLogHandler

自定义实现 `handleRecord()` 方法，进行入库操作，或者对审计日志记录进行二次操作

```java
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
```

#### 3、使用注解：@AuditLog

`AuditLog` 支持方法层级切入，记录操作描述、日志模块、日志操作、拓展信息

支持`SpEL`语法，可以从方法中解析获取指定参数

```java

/**
 * ExampleController
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/2/16 22:53
 */
@Controller
public class ExampleController {

    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    /**
     * 审计日志注解示例
     *
     * @param params {@link JSONObject}
     * @param bh     {@link String}
     * @return 操作结果
     */
    @PostMapping({"/audit"})
    @ResponseBody
    @AuditLog(description = "审计日志", module = AuditLogModuleEnum.USER, extension = "单位名称：#{[0].get('name')},编号：#{[1]}")
    public Object updateCorp(JSONObject params, String bh) {
        logger.info("单位名称：{}", params.get("name"));
        logger.info("编号：{}", bh);
        return "ok";
    }

    /**
     * 审计日志注解示例
     *
     * @param bh     {@link String}
     * @return 操作结果
     */
    @GetMapping({"/audit1"})
    @ResponseBody
    @AuditLog(description = "审计日志", module = AuditLogModuleEnum.USER, extension = "编号：#{[0]}")
    public Object test1( String bh) {
        logger.info("编号：{}", bh);
        return "ok";
    }
}
```

## 项目介绍

### 项目结构

![image-20221212192603641](C:\Users\p'v\AppData\Roaming\Typora\typora-user-images\image-20221212192603641.png)

### 项目核心类

#### 审计日志Pojo

审计日志Pojo需要包含以下数据：操作人、操作时间、操作对象，作为基础的审计日志数据，可以自定义拓展实现更多的数据记录

```java
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

    private static final long serialVersionUID = 7915910801199728820L;

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

```

#### 审计日志模块

根据系统中的数据类型和应用模块，定义枚举类存放日志模块

主要分为登录模块、用户模块、业务数据模块、权限模块

```java
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

    AuditLogModuleEnum(int moduleCode, String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    @Getter
    private final int moduleCode;

    @Getter
    private final String moduleName;
}

```

#### 审计日志操作

根据用户的操作类型，定义日志操作枚举类，主要有登录、登出、新增、修改、删除、查询、资源下载、资源上传等操作

```java
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

    AuditLogOperationTypeEnum(int typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    @Getter
    private final int typeCode;

    @Getter
    private final String typeName;
}

```

#### 审计日志注解

定义审计日志注解，其中包含一些需要处理记录的信息，审计日志主要记录的是数据的变动，在查询上优先级较低，所以默认为业务数据+修改操作， 

* `description`：操作内容，支持`SpEL`语法，
* `module`：日志模块，参见`AuditLogModuleEnum`
* `operationType`：操作类型，参见`AuditLogOperationTypeEnum`
* `extension`：拓展信息，也支持支持`SpEL`语法

```java
/**
 * AuditLog
 * 审计日志注解
 *
 * @author xuzhou
 * @version v1.0.0
 * @date 2022/2/10 15:35
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditLog {

    /**
     * 日志描述
     * 支持SpEL语法
     *
     * @return 描述内容
     */
    String description() default "";

    /**
     * 审计日志模块
     *
     * @return {@link AuditLogModuleEnum}
     */
    AuditLogModuleEnum module() default AuditLogModuleEnum.DATA;

    /**
     * 审计日志操作类型
     *
     * @return {@link AuditLogOperationTypeEnum}
     */
    AuditLogOperationTypeEnum operationType() default AuditLogOperationTypeEnum.update;

    /**
     * 拓展信息
     * 支持SpEL语法
     *
     * @return {@link String}
     */
    String extension() default "";
}

```

