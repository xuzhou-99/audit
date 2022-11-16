-- auto-generated definition
drop table sys_audit_log;
create table sys_audit_log
(
    bh              varchar(50)                               not null comment '编号'
        primary key,
    description     varchar(3000)                             null comment '日志内容',
    module_code     varchar(10)                               null comment '日志模块类型',
    module_name     varchar(300)                              null comment '日志模块名称',
    operation_type  varchar(10)                               null comment '操作类型',
    operation_name  varchar(300)                              null comment '操作类型名称',
    operation_time  timestamp(6) default CURRENT_TIMESTAMP(6) not null on update CURRENT_TIMESTAMP(6) comment '操作日志时间',
    user_id         varchar(50)                               null comment '操作用户ID',
    user_name       varchar(300)                              null comment '操作用户姓名',
    user_login_name varchar(300)                              null comment '用户登录名',
    user_agent      varchar(3000)                             null comment 'userAgent',
    ip              varchar(300)                              null comment 'IP',
    extension       varchar(3000)                             null comment '拓展字段'
)
    comment '系统审计日志表';

