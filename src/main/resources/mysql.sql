create table t_job
(
    id         bigint(20)   not null,
    name       varchar(128) not null,
    group_job  varchar(128) not null,
    invoke     text         null,
    cron       varchar(128) not null,
    misfire    int(4)       not null DEFAULT 0,
    concurrent int(4)       not null,
    status     int(4)       not null,
    primary key (id),
    unique uq_name_group (name, group_job)
);

-- 其他sql 请直接搜索文件名  tables_mysql_innodb.sql