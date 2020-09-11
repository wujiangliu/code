# MySQL知识点
在文件系统中，MySQL 会把每个数据库保存为数据目录下的一个子目录
当创建一个表时，MySQL 会在和表同名，以.frm为后缀的文件中存储表的定义
因为 MySQL 是使用文件系统来存储库名和表定义，所以大小写敏感取决于平台，在window系统中是大小写不敏感的，在linux系统中是大小写敏感的
表的信息存储在 `information_schema`
```sql
select * from information_schema.TABLES;
```
# 字符集
```sql
-- 查看 --

select * from information_schema.TABLES;
-- 修改 --

alter table table_name convert to character set utf8mb4 collate utf8mb4_0900_ai_ci;

-- 生成批量修改语句 --
SELECT 
CONCAT("ALTER TABLE `", TABLE_SCHEMA,"`.`" ,TABLE_NAME,"` CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;") 
AS target_tables
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_TYPE="BASE TABLE" and TABLE_COLLATION = "utf8mb4_0900_ai_ci";
```
