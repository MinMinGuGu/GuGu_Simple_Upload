alter table permission
    add `description` varchar(100) after `name`;
delete
from permission
where id = '4';
update permission
set `description` = '所有权限'
where id = '1';
update permission
set `description` = '上传权限'
where id = '2';
update permission
set `description` = '管理权限'
where id = '3';