CREATE DATABASE short_url CHARACTER SET utf8mb4;

use short_url;
CREATE TABLE `short_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `base_url` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '短链域名',
  `short_code` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '短链接',
  `full_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '长链接',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `expiration_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  KEY `idx_sc` (`short_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;