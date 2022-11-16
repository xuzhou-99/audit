-- ----------------------------
-- Table structure for sys_audit_log
-- ----------------------------
DROP TABLE IF EXISTS "sys_audit_log";
CREATE TABLE "sys_audit_log" (
  "bh" varchar(300) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(3000) COLLATE "pg_catalog"."default",
  "module_code" int4,
  "module_name" varchar(300) COLLATE "pg_catalog"."default",
  "operation_type" int4,
  "operation_name" varchar(300) COLLATE "pg_catalog"."default",
  "operation_time" timestamp(6),
  "user_id" varchar(50) COLLATE "pg_catalog"."default",
  "user_name" varchar(300) COLLATE "pg_catalog"."default",
  "user_login_name" varchar(300) COLLATE "pg_catalog"."default",
  "user_agent" varchar(3000) COLLATE "pg_catalog"."default",
  "ip" varchar(300) COLLATE "pg_catalog"."default",
  "extension" varchar(3000) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "sys_audit_log"."bh" IS '日志编号';
COMMENT ON COLUMN "sys_audit_log"."description" IS '日志内容';
COMMENT ON COLUMN "sys_audit_log"."module_code" IS '日志模块类型';
COMMENT ON COLUMN "sys_audit_log"."module_name" IS '日志模块名称';
COMMENT ON COLUMN "sys_audit_log"."operation_type" IS '操作类型';
COMMENT ON COLUMN "sys_audit_log"."operation_name" IS '操作类型名称';
COMMENT ON COLUMN "sys_audit_log"."operation_time" IS '操作日志时间';
COMMENT ON COLUMN "sys_audit_log"."user_id" IS '操作用户ID';
COMMENT ON COLUMN "sys_audit_log"."user_name" IS '操作用户姓名';
COMMENT ON COLUMN "sys_audit_log"."user_login_name" IS '用户登录名';
COMMENT ON COLUMN "sys_audit_log"."user_agent" IS 'userAgent';
COMMENT ON COLUMN "sys_audit_log"."ip" IS 'IP';
COMMENT ON COLUMN "sys_audit_log"."extension" IS '拓展字段';
COMMENT ON TABLE "sys_audit_log" IS '审计日志表';

-- ----------------------------
-- Primary Key structure for table sys_audit_log
-- ----------------------------
ALTER TABLE "sys_audit_log" ADD CONSTRAINT "sys_audit_log_pkey" PRIMARY KEY ("bh");
