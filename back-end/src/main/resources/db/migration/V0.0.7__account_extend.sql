alter table account
    add `create_time` date comment '创建时间';
alter table account
    add `update_time` datetime comment '创建时间';
update account
set account.create_time = now(),
    account.update_time = now()
where account.create_time is null
  and account.update_time is null;
alter table account
    modify create_time date not null;
alter table account
    modify update_time datetime not null;