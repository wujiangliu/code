# MySQL触发器
```sql
-- 删除触发器 --
DROP TRIGGER IF EXISTS byjy_core.test;
-- 创建触发器 --
delimiter $
CREATE TRIGGER test AFTER UPDATE ON xlc_course
FOR EACH ROW
BEGIN
    SELECT concat('正在更改状态:',OLD.`condition`) INTO msg from xlc_course where courseid = OLD.courseid and OLD.`condition`='900' and NEW.`condition` = '100';
END;
delimiter;
-- 查看触发器 --
SHOW TRIGGERS;
```
# 字符串分割/拼接
```sql
left/right(colname,length)

-- 从左边开始切割12个字符长度 --
select left(chapterid,12) as newchapterid from xlc_chapter;

-- 从右边开始切割12个字符长度 --
select right(chapterid,12) as newchapterid from xlc_chapter;
concat(s1,s2,...)

-- 将chapterid字段的值从左切割12个字符后，再与"1010"拼接 --
select concat(left(chapterid,12),"1010") as newchapterid from xlc_chapter;
```
