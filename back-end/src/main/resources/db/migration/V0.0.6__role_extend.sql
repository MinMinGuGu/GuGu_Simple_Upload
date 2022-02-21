alter table `role`
    add `system_default` tinyint(1) not null comment '系统默认角色' after `enable`;
update `role`
set role.`system_default` = 1
where role.system_default is null
   or role.system_default = 0;