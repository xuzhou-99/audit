-- ----------------------------
-- Table structure for t_audit_log
-- ----------------------------
DROP TABLE IF EXISTS "t_audit_log";
CREATE TABLE "t_audit_log" (
  "c_bh" varchar(300) COLLATE "pg_catalog"."default" NOT NULL,
  "c_description" varchar(3000) COLLATE "pg_catalog"."default",
  "n_operation_type" int4,
  "c_operation_name" varchar(300) COLLATE "pg_catalog"."default",
  "n_module_code" int4,
  "c_module_name" varchar(300) COLLATE "pg_catalog"."default",
  "dt_operation_time" timestamp(6),
  "c_user_id" varchar(50) COLLATE "pg_catalog"."default",
  "c_user_name" varchar(300) COLLATE "pg_catalog"."default",
  "c_user_loginid" varchar(300) COLLATE "pg_catalog"."default",
  "c_user_agent" varchar(3000) COLLATE "pg_catalog"."default",
  "c_ip" varchar(300) COLLATE "pg_catalog"."default",
  "c_extension" varchar(3000) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "t_audit_log"."c_bh" IS '日志编号';
COMMENT ON COLUMN "t_audit_log"."c_description" IS '日志内容';
COMMENT ON COLUMN "t_audit_log"."n_operation_type" IS '操作类型';
COMMENT ON COLUMN "t_audit_log"."c_operation_name" IS '操作类型名称';
COMMENT ON COLUMN "t_audit_log"."n_module_code" IS '日志模块类型';
COMMENT ON COLUMN "t_audit_log"."c_module_name" IS '日志模块名称';
COMMENT ON COLUMN "t_audit_log"."dt_operation_time" IS '操作日志时间';
COMMENT ON COLUMN "t_audit_log"."c_user_id" IS '操作用户ID';
COMMENT ON COLUMN "t_audit_log"."c_user_name" IS '操作用户姓名';
COMMENT ON COLUMN "t_audit_log"."c_user_loginid" IS '用户登录名';
COMMENT ON COLUMN "t_audit_log"."c_user_agent" IS 'userAgent';
COMMENT ON COLUMN "t_audit_log"."c_ip" IS 'IP';
COMMENT ON COLUMN "t_audit_log"."c_extension" IS '拓展字段';
COMMENT ON TABLE "t_audit_log" IS '审计日志表';

-- ----------------------------
-- Primary Key structure for table t_audit_log
-- ----------------------------
ALTER TABLE "t_audit_log" ADD CONSTRAINT "t_audit_log_pkey" PRIMARY KEY ("c_bh");
