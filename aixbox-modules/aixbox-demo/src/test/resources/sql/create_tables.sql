CREATE TABLE IF NOT EXISTS "test_demo" (
     "id" bigint NOT NULL,
     "user_id" bigint DEFAULT NULL,
     "test_key" varchar(255) DEFAULT NULL,
     "value" varchar(255) DEFAULT NULL,
     "creator" varchar(64) DEFAULT '',
     "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
     "updater" varchar(64) DEFAULT '',
     "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
     "deleted" bit NOT NULL DEFAULT FALSE,
     PRIMARY KEY ("id")
) COMMENT='测试单表';