alter table `file_info`
    add `account_id` int not null comment '用户id';
-- 不需要精确到时分秒
alter table `file_info`
    modify `create_time` date;
alter table `file_info`
    modify `update_time` date;