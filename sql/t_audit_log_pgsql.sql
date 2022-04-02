-- ----------------------------
-- Table structure for t_audit_log
-- ----------------------------
DROP TABLE
IF
	EXISTS "t_audit_log";
CREATE TABLE "t_audit_log" (
	"c_bh" VARCHAR ( 300 ) COLLATE "pg_catalog"."default" NOT NULL "c_description" VARCHAR ( 3000 ) COLLATE "pg_catalog"."default",
	"n_operationType" int4,
	"c_operationName" VARCHAR ( 300 ) COLLATE "pg_catalog"."default",
	"n_moduleCode" int4,
	"c_moduleName" VARCHAR ( 300 ) COLLATE "pg_catalog"."default",
	"dt_operationTime" TIMESTAMP ( 6 ),
	"c_userid" VARCHAR ( 50 ) COLLATE "pg_catalog"."default",
	"c_username" VARCHAR ( 300 ) COLLATE "pg_catalog"."default",
	"c_userLoginId" VARCHAR ( 300 ) COLLATE "pg_catalog"."default",
	"c_ip" VARCHAR ( 300 ) COLLATE "pg_catalog"."default",
	"c_userAgent" VARCHAR ( 3000 ) COLLATE "pg_catalog"."default",
	"c_extension" VARCHAR ( 3000 ) COLLATE "pg_catalog"."default",
	
);
COMMENT ON COLUMN "t_audit_log"."c_userid" IS '操作用户ID';
COMMENT ON COLUMN "t_audit_log"."c_username" IS '操作用户姓名';
COMMENT ON COLUMN "t_audit_log"."c_description" IS '日志内容';
COMMENT ON COLUMN "t_audit_log"."c_userLoginId" IS '用户登录名';
COMMENT ON COLUMN "t_audit_log"."n_operationType" IS '操作类型';
COMMENT ON COLUMN "t_audit_log"."c_operationName" IS '操作类型名称';
COMMENT ON COLUMN "t_audit_log"."n_moduleCode" IS '日志模块类型';
COMMENT ON COLUMN "t_audit_log"."c_moduleName" IS '日志模块名称';
COMMENT ON COLUMN "t_audit_log"."dt_operationTime" IS '操作日志时间';
COMMENT ON COLUMN "t_audit_log"."c_ip" IS 'IP';
COMMENT ON COLUMN "t_audit_log"."c_userAgent" IS 'userAgent';
COMMENT ON COLUMN "t_audit_log"."c_extension" IS '拓展字段';
COMMENT ON COLUMN "t_audit_log"."c_bh" IS '日志编号';
COMMENT ON TABLE "t_audit_log" IS '审计日志表';
-- ----------------------------
-- Primary Key structure for table t_audit_log
-- ----------------------------
ALTER TABLE "t_audit_log" ADD CONSTRAINT "t_audit_log_pkey" PRIMARY KEY ( "c_bh" );