alter table `account`
    add `system_default` tinyint(1) not null default 0 comment '系统默认账户';
update `account`
set `system_default` = 1
where id = 1;