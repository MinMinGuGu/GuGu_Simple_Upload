create table if not exists `menu`
(
    `id`               int          not null auto_increment,
    `path`             varchar(255) not null comment '请求路径',
    `name`             varchar(255) not null comment '菜单名称',
    `parent_menu_id` int          not null comment '父菜单id',
    `enable`           boolean       not null comment '已启用(非零即真)',
    `create_time`      datetime     not null comment '创建时间',
    `update_time`      datetime     not null comment '修改时间',
    primary key (`id`)
) engine = InnoDB
  default charset = utf8;

insert into `menu`
values (1, '/', '概览', 0, 1, sysdate(), sysdate());

insert into `menu`
values (2, '/file', '文件管理', 0, 1, sysdate(), sysdate());

insert into `menu`
values (3, '/file/upload', '文件上传', 2, 1, sysdate(), sysdate());

insert into `menu`
values (4, '/file/list', '文件列表', 2, 1, sysdate(), sysdate());

insert into `menu`
values (5, '/system', '系统管理', 0, 1, sysdate(), sysdate());

insert into `menu`
values (6, '/system/user', '用户管理', 5, 1, sysdate(), sysdate());

insert into `menu`
values (7, '/system/role', '角色管理', 5, 1, sysdate(), sysdate());

insert into `menu`
values (8, '/system/role', '权限管理', 5, 1, sysdate(), sysdate());

insert into `menu`
values (9, '/system/manage', '系统设置', 5, 1, sysdate(), sysdate());

insert into `menu`
values (10, '/system/appKey', 'AppKey设置', 5, 1, sysdate(), sysdate());