-- ----------------------------
-- Table structure for UserInfo
-- ----------------------------

DROP TABLE IF EXISTS `UserInfo`;
-- 创建一个表，指定了4个属性：id、年龄、身高、体重。最后指定了id是唯一不能重复的键值
CREATE TABLE IF NOT EXISTS `UserInfo` (  `id` varchar(20) NOT NULL,  `age` int DEFAULT NULL,  `height` int DEFAULT NULL,  `weight` int DEFAULT NULL,  PRIMARY KEY (`id`) );

DROP TABLE IF EXISTS `URL_INFO`;
-- CREATE TABLE URL_INFO(ID INT AUTO_INCREMENT PRIMARY KEY, LONG_URL VARCHAR(255), SHORT_URL VARCHAR(255), SIGN VARCHAR(255), EXPIRE_TIME TIMESTAMP)

create table URL_INFO
(
    ID          VARCHAR2 auto_increment,
    LONG_URL    VARCHAR2,
    SHORT_URL   VARCHAR2,
    SIGN        VARCHAR2,
    EXPIRE_TIME DATETIME,
    constraint URL_INFO_PK
        primary key (ID)
);

comment on table URL_INFO is 'URL映射表';

comment on column URL_INFO.LONG_URL is '长链接';

comment on column URL_INFO.SHORT_URL is '短链接';

comment on column URL_INFO.SIGN is '签名';

comment on column URL_INFO.EXPIRE_TIME is '过期时间';

create unique index URL_INFO_ID_UINDEX
    on URL_INFO (ID);