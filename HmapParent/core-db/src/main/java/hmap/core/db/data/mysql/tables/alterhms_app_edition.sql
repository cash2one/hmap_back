DROP TABLE IF EXISTS `hms_app_edition`;
CREATE TABLE `hms_app_edition` (
  `ID` varchar(240) NOT NULL,
  `APP_ID` varchar(200) DEFAULT NULL,
  `APP_EQUIPMENT` varchar(255) DEFAULT NULL,
  `UPDATE_MESSAGE` varchar(1000) DEFAULT NULL,
  `ENABLE_FLAG` varchar(1) DEFAULT NULL,
  `APP_HOMEPAGE` varchar(200) DEFAULT NULL,
  `DOWNLOAD_URL` varchar(200) DEFAULT NULL COMMENT 'APP应用分发链接',
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `CREATED_BY` decimal(20,0) DEFAULT '-1',
  `LAST_UPDATED_BY` decimal(20,0) DEFAULT '-1',
  `LAST_UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_LOGIN` decimal(20,0) DEFAULT NULL,
  `OBJECT_VERSION_NUMBER` decimal(20,0) DEFAULT '1',
  `REQUEST_ID` bigint(20) DEFAULT NULL,
  `PROGRAM_ID` bigint(20) DEFAULT NULL,
  `ATTRIBUTE_CATEGORY` varchar(30) DEFAULT NULL,
  `ATTRIBUTE1` varchar(240) DEFAULT NULL,
  `ATTRIBUTE2` varchar(240) DEFAULT NULL,
  `ATTRIBUTE3` varchar(240) DEFAULT NULL,
  `ATTRIBUTE4` varchar(240) DEFAULT NULL,
  `ATTRIBUTE5` varchar(240) DEFAULT NULL,
  `ATTRIBUTE6` varchar(240) DEFAULT NULL,
  `ATTRIBUTE7` varchar(240) DEFAULT NULL,
  `ATTRIBUTE8` varchar(240) DEFAULT NULL,
  `ATTRIBUTE9` varchar(240) DEFAULT NULL,
  `ATTRIBUTE10` varchar(240) DEFAULT NULL,
  `ATTRIBUTE11` varchar(240) DEFAULT NULL,
  `ATTRIBUTE12` varchar(240) DEFAULT NULL,
  `ATTRIBUTE13` varchar(240) DEFAULT NULL,
  `ATTRIBUTE14` varchar(240) DEFAULT NULL,
  `ATTRIBUTE15` varchar(240) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `hms_app_edition_line`;
CREATE TABLE `hms_app_edition_line` (
  `ID` varchar(40) NOT NULL,
  `APP_EDITION_ID` varchar(200) DEFAULT NULL,
  `ORDER_NUM` decimal(20,0) DEFAULT 0 COMMENT '序号，用于排序',
  `UPDATE_TYPE` varchar(200) DEFAULT NULL COMMENT '更新类型',
  `EDITION_CODE` varchar(200) DEFAULT NULL COMMENT '版本编号',
  `DOWNLOAD_URL` varchar(200) DEFAULT NULL COMMENT '下载地址',
  `IS_MINIMUM_EDITION` varchar(1) DEFAULT NULL COMMENT '是否是最小版本',
  `IS_LATEST_EDITION` varchar(1) DEFAULT NULL COMMENT '是否是最新版本',
  `UPDATE_MESSAGE` LONGTEXT DEFAULT NULL COMMENT '是否是最新版本',
  `ENABLE_FLAG` varchar(1) DEFAULT NULL COMMENT '是否有效',
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `CREATED_BY` decimal(20,0) DEFAULT '-1',
  `LAST_UPDATED_BY` decimal(20,0) DEFAULT '-1',
  `LAST_UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_LOGIN` decimal(20,0) DEFAULT NULL,
  `OBJECT_VERSION_NUMBER` decimal(20,0) DEFAULT '1',
  `REQUEST_ID` bigint(20) DEFAULT NULL,
  `PROGRAM_ID` bigint(20) DEFAULT NULL,
  `ATTRIBUTE_CATEGORY` varchar(30) DEFAULT NULL,
  `ATTRIBUTE1` varchar(240) DEFAULT NULL,
  `ATTRIBUTE2` varchar(240) DEFAULT NULL,
  `ATTRIBUTE3` varchar(240) DEFAULT NULL,
  `ATTRIBUTE4` varchar(240) DEFAULT NULL,
  `ATTRIBUTE5` varchar(240) DEFAULT NULL,
  `ATTRIBUTE6` varchar(240) DEFAULT NULL,
  `ATTRIBUTE7` varchar(240) DEFAULT NULL,
  `ATTRIBUTE8` varchar(240) DEFAULT NULL,
  `ATTRIBUTE9` varchar(240) DEFAULT NULL,
  `ATTRIBUTE10` varchar(240) DEFAULT NULL,
  `ATTRIBUTE11` varchar(240) DEFAULT NULL,
  `ATTRIBUTE12` varchar(240) DEFAULT NULL,
  `ATTRIBUTE13` varchar(240) DEFAULT NULL,
  `ATTRIBUTE14` varchar(240) DEFAULT NULL,
  `ATTRIBUTE15` varchar(240) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
ALTER TABLE `hms_app_auth`
ADD INDEX `APP_ID_INDEX` (`APP_ID`(191)) USING BTREE ;
ALTER TABLE `hms_app_edition`
ADD INDEX `APP_ID_INDEX` (`APP_ID`(191)) USING BTREE ;
ALTER TABLE `hms_app_edition_line`
ADD INDEX `APP_EDITION_ID_INDEX` (`APP_EDITION_ID`(191)) USING BTREE ;