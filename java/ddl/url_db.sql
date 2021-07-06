

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `url_record`;
CREATE TABLE `url_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '物理主键',
  `long_url` varchar(255) NOT NULL COMMENT '长链接',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
