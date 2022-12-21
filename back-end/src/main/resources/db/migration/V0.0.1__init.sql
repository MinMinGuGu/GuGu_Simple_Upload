create table if not exists `file_info`
(
    `id`            int auto_increment not null,
    `account_id`    int                not null comment '用户id',
    `file_hash`     varchar(255)       not null comment '文件哈希值',
    `file_desc`     varchar(255) comment '文件描述',
    `file_path`     varchar(255)       not null comment '存放于临时路径的文件名(绝对路径)',
    `file_original` varchar(255)       not null comment '上传时的文件原名',
    `file_size`     varchar(100)       not null comment '文件大小',
    `status`        int                not null comment '文件状态 0:删除 1:存在',
    `uploader`      varchar(255)       not null comment '上传者ip',
    `deleted`       int default 0 comment '逻辑删除',
    `create_time`   date               not null comment '创建时间',
    `update_time`   datetime comment '修改时间',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8mb4;

create table if not exists `account`
(
    `id`             int auto_increment not null,
    `role_id`        int comment '角色id',
    `user_name`      varchar(255)       not null,
    `user_password`  varchar(255)       not null,
    `system_default` tinyint default 0 comment '系统默认账户',
    `enable`         tinyint default 1 comment '启用状态',
    `create_time`    date               not null comment '创建时间',
    `update_time`    datetime comment '修改时间',
    primary key (`id`),
    unique key (`user_name`)
) engine = InnoDB
  default charset = utf8mb4;
insert into `account`(`role_id`, `user_name`, `user_password`, `system_default`, `enable`, `create_time`)
values (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 1, 1, sysdate());

create table if not exists `operation_log`
(
    `id`             int auto_increment not null,
    `ip_address`     varchar(100)       not null comment 'ip地址',
    `operation_type` tinyint            not null comment '操作类型',
    `user_name`      varchar(255)       not null comment '用户名',
    `context`        varchar(255) comment '内容',
    `create_time`    datetime           not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8mb4;

create table if not exists `role`
(
    `id`             int          not null auto_increment,
    `name`           varchar(255) not null comment '角色名',
    `enable`         boolean      not null comment '已启用(非零即真)',
    `system_default` tinyint default 0 comment '系统默认角色',
    `create_time`    datetime     not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset utf8mb4;
insert into `role`(`id`, `name`, `enable`, `system_default`, `create_time`)
values (1, '超级管理员', 1, 1, sysdate());
insert into `role`(`id`, `name`, `enable`, `create_time`)
values (2, '用户', 1, sysdate());
insert into `role`(`id`, `name`, `enable`, `create_time`)
values (3, '普通管理员', 1, sysdate());

create table if not exists `permission`
(
    `id`          int                 not null auto_increment,
    `name`        varchar(255) unique not null comment '权限名',
    `description` varchar(100),
    `create_time` datetime            not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset utf8mb4;
insert into `permission`
values (1, 'all', '所有权限', sysdate());
insert into `permission`
values (2, 'upload', '上传权限', sysdate());
insert into `permission`
values (3, 'manage', '管理权限', sysdate());

create table if not exists `role_permission`
(
    `id`            int      not null auto_increment,
    `role_id`       int      not null comment '角色id',
    `permission_id` int      not null comment '权限id',
    `create_time`   datetime not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset utf8mb4;
insert into `role_permission`
values (1, 1, 1, sysdate());
insert into `role_permission`
values (2, 2, 2, sysdate());
insert into `role_permission`
values (3, 3, 3, sysdate());

create table if not exists `app_key`
(
    `id`          int          not null auto_increment,
    `user_name`   varchar(255) not null comment '用户名',
    `value`       varchar(255) not null comment 'appKey',
    `description` varchar(100),
    `create_time` datetime     not null comment '创建时间',
    primary key (`id`),
    index `index_value` (`value`)
) engine = InnoDB
  default charset utf8mb4;

create table if not exists `menu`
(
    `id`          int         not null auto_increment,
    `name`        varchar(50) not null comment '菜单名',
    `create_time` datetime    not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset utf8mb4;
insert into menu(`name`, `create_time`)
values ('概览', sysdate());
insert into menu(`name`, `create_time`)
values ('文件上传', sysdate());
insert into menu(`name`, `create_time`)
values ('文件列表', sysdate());
insert into menu(`name`, `create_time`)
values ('用户管理', sysdate());
insert into menu(`name`, `create_time`)
values ('角色管理', sysdate());
insert into menu(`name`, `create_time`)
values ('AppKey设置', sysdate());
insert into menu(`name`, `create_time`)
values ('系统日志', sysdate());

create table if not exists `menu_permission`
(
    `id`            int      not null auto_increment,
    `menu_id`       int      not null comment '菜单id',
    `permission_id` int      not null comment '权限id',
    `create_time`   datetime not null comment '创建时间',
    primary key (`id`)
) engine = InnoDB
  default charset utf8mb4;
insert into `menu_permission`
values (1, 1, 2, sysdate());
insert into `menu_permission`
values (2, 2, 2, sysdate());
insert into `menu_permission`
values (3, 3, 2, sysdate());
insert into `menu_permission`
values (4, 4, 3, sysdate());
insert into `menu_permission`
values (5, 5, 3, sysdate());
insert into `menu_permission`
values (6, 6, 3, sysdate());
insert into `menu_permission`
values (7, 7, 3, sysdate());