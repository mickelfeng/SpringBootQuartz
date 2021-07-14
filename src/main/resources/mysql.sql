create table t_job
(
    id           BIGINT(20)   not null,
    `name`       VARCHAR(128) not null,
    `group`      VARCHAR(128) not null,
    invoke       text         null,
    cron         VARCHAR(128) not null,
    misfire      int(4)       not null DEFAULT 0,
    `concurrent` int(4)       not null,
    `status`     int(4)       not null,
    PRIMARY KEY (id)
);

-- 其他sql 请直接搜索文件名  tables_mysql_innodb.sql