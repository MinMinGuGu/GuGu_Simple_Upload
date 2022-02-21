alter table account
    add `enable` tinyint(1) after system_default;
update account
set account.enable = 1
where account.enable is null;
alter table account
    modify `enable` tinyint(1) not null comment '启用状态';