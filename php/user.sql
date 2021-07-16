SET NAMES utf8mb4;
-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改（更新）时间',
  PRIMARY KEY (`id`) ,
  UNIQUE KEY `key_username` (`username`) COMMENT '用户名唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';