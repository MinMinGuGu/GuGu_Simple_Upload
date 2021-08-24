create database if not exists `visit`(
    `id` int auto_increment not null comment 'id',
    `ip_address` varchar(100) not null comment 'ip地址',
    `path` varchar(255) not null comment '访问路径',
    `create_time` datetime not null comment '创建时间',
    primary key(`id`)
)engine=InnoDB default charset=utf8;