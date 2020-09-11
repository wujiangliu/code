# 存储引擎汇总
![](cdbc75bd-4dee-41c4-8c86-e1789dc6ec1e)
# 查看存储引擎
-- 全部 --
show table status;
-- 条件 --
show table status like "%xlc_task%";
show table status where name like "%xlc_task%";
# 修改表的存储引擎
-- 方法1：alter table ,该方法在存储引擎之间转换会导致原有的引擎特性消失 --
alter table xlc_task engine = InnoDB;
-- 方法2：创建一个新表 --
create table new_table like old_table;
alter table new_table engine = InnoDB;
insert into new_table select * from old_table;
#
