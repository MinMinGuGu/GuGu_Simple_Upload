create table if not exists `file_info`(
                                          `id`            int auto_increment not null,
                                          `file_hash`     varchar(255)       not null comment '文件哈希值',
                                          `file_desc`     varchar(255) comment '文件描述',
                                          `file_path`     varchar(255)       not null comment '存放于临时路径的文件名(绝对路径)',
                                          `file_original` varchar(255)       not null comment '上传时的文件原名',
                                          `file_size`     varchar(100)       not null comment '文件大小',
                                          `status`        int(1)             not null comment '文件状态 0:删除 1:存在',
                                          `uploader`      varchar(255)       not null comment '上传者ip',
                                          `deleted`       int(1)             not null default 0 comment '逻辑删除',
                                          `create_time`   date               not null comment '创建时间',
                                          `update_time`   datetime           not null comment '修改时间',
                                          primary key (`id`)
)engine=InnoDB default charset=utf8;

create table if not exists `account`(
    `id` int auto_increment not null,
    `user_name` varchar(255) not null,
    `user_password` varchar(255) not null,
    primary key (`id`)
)engine=InnoDB default charset=utf8;

insert into `account`(`user_name`, `user_password`) values('admin', '21232f297a57a5a743894a0e4a801fc3');