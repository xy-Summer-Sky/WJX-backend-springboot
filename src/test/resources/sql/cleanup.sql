SET FOREIGN_KEY_CHECKS = 0;

-- 删除 `questions` 表上的外键约束


-- 删除 `options` 表上的外键约束


-- 清空相关表
TRUNCATE TABLE users;
TRUNCATE TABLE surveys;
TRUNCATE TABLE questions;
TRUNCATE TABLE options;
TRUNCATE TABLE responses;

SET FOREIGN_KEY_CHECKS = 1;


