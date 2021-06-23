CREATE TABLE `short_url` (
  `id` INTEGER NOT NULL AUTO_INCREMENT COMMENT 'id',
  `base_url` varchar(15)  COMMENT '短链域名',
  `short_code` varchar(8)  COMMENT '短链接',
  `full_url` varchar(255) DEFAULT NULL COMMENT '长链接',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `expiration_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ;