-- 2个库2张表 伸缩至 4个库4个表

# 添加两个数据源
ADD RESOURCE ds2 (
    URL="jdbc:mysql://127.0.0.1:3306/ds2?serverTimezone=UTC&useSSL=false",
    USER=root,
    PASSWORD=123,
    PROPERTIES("maximumPoolSize"=10,"idleTimeout"="30000")
), ds3 (
    URL="jdbc:mysql://127.0.0.1:3306/ds3?serverTimezone=UTC&useSSL=false",
    USER=root,
    PASSWORD=123,
    PROPERTIES("maximumPoolSize"=10,"idleTimeout"="30000")
);

# 调整分片规则
ALTER SHARDING ALGORITHM database_inline (
TYPE(NAME=INLINE,PROPERTIES("algorithm-expression"="ds${user_id % 4}"))
);

ALTER SHARDING ALGORITHM user_inline (
TYPE(NAME=INLINE,PROPERTIES("algorithm-expression"="user_{id % 4}"))
);

ALTER SHARDING TABLE RULE user (
DATANODES("ds${0..3}.t_order_${0..3}"),
DATABASE_STRATEGY(TYPE=standard,SHARDING_COLUMN=user_id,SHARDING_ALGORITHM=database_inline),
TABLE_STRATEGY(TYPE=standard,SHARDING_COLUMN=id,SHARDING_ALGORITHM=user_inline),
KEY_GENERATE_STRATEGY(COLUMN=id,TYPE(NAME=snowflake))
);

# 查询所有迁移任务
show scaling list;

# 查询迁移任务进度
show scaling status {jobId};

# 预览分片是否生效
preview select count(1) from user;
