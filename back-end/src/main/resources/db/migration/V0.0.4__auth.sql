create table if not exists `role`
(
    `id`          int          not null auto_increment,
    `name`        varchar(255) not null comment '角色名',
    `enable`      boolean      not null comment '已启用(非零即真)',
    `create_time` datetime     not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset utf8;

insert into `role`
values (1, '超级管理员', 1, sysdate());
insert into `role`
values (2, '用户', 1, sysdate());
insert into `role`
values (3, '普通管理员', 1, sysdate());

create table if not exists `permission`
(
    `id`          int                 not null auto_increment,
    `name`        varchar(255) unique not null comment '权限名',
    `create_time` datetime            not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset utf8;

insert into `permission`
values (1, 'all', sysdate());
insert into `permission`
values (2, 'upload', sysdate());
insert into `permission`
values (3, 'manage', sysdate());
insert into `permission`
values (4, 'null', sysdate());

create table if not exists `role_permission`
(
    `id`            int      not null auto_increment,
    `role_id`       int      not null comment '角色id',
    `permission_id` int      not null comment '权限id',
    `create_time`   datetime not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset utf8;

insert into `role_permission`
values (1, 1, 1, sysdate());
insert into `role_permission`
values (2, 2, 2, sysdate());
insert into `role_permission`
values (3, 3, 3, sysdate());

alter table `account`
    add `role_id` int comment '角色id';

update `account`
set `role_id` = 1
where `id` = 1;

create table if not exists `app_key`
(
    `id`          int          not null auto_increment,
    `value`       varchar(255) not null comment 'appKey',
    `create_time` datetime     not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset utf8;

create table if not exists `menu_permission`
(
    `id`            int      not null auto_increment,
    `menu_id`       int      not null comment '菜单id',
    `permission_id` int      not null comment '权限id',
    `create_time`   datetime not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset utf8;

insert into `menu_permission`
values (1, 1, 3, sysdate());
insert into `menu_permission`
values (2, 2, 4, sysdate());
insert into `menu_permission`
values (3, 3, 3, sysdate());
insert into `menu_permission`
values (4, 4, 3, sysdate());
insert into `menu_permission`
values (5, 5, 4, sysdate());
insert into `menu_permission`
values (6, 6, 3, sysdate());
insert into `menu_permission`
values (7, 7, 3, sysdate());
insert into `menu_permission`
values (8, 8, 3, sysdate());
insert into `menu_permission`
values (9, 9, 3, sysdate());
insert into `menu_permission`
values (10, 10, 3, sysdate());