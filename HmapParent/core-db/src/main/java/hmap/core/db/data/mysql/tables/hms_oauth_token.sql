/*
Navicat MySQL Data Transfer

Source Server         : 172.20.0.165
Source Server Version : 50713
Source Host           : 172.20.0.165:3306
Source Database       : hms_hrms_sit

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2016-08-19 15:20:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hms_oauth_token
-- ----------------------------
DROP TABLE IF EXISTS `hms_oauth_token`;
CREATE TABLE `hms_oauth_token` (
  `ID` varchar(240),
  `APP_ID` varchar(240) DEFAULT NULL,
  `USERNAME` varchar(240) DEFAULT NULL,
  `ACCESS_TOKEN` varchar(240) DEFAULT NULL,
  `REFRESH_TOKEN` varchar(240) DEFAULT NULL,
  `EXPIRE_DATE` datetime DEFAULT NULL,
  `OBJECT_VERSION_NUMBER` bigint(20) DEFAULT '1',
  `CREATION_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CREATED_BY` bigint(20) NOT NULL DEFAULT '-1',
  `LAST_UPDATED_BY` bigint(20) NOT NULL DEFAULT '-1',
  `LAST_UPDATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_LOGIN` bigint(20) DEFAULT NULL,
  `ATTRIBUTE_CATEGORY` varchar(30) DEFAULT NULL,
  `REQUEST_ID` bigint(20) DEFAULT NULL,
  `PROGRAM_ID` bigint(20) DEFAULT NULL,
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
  PRIMARY KEY (`id`)
);
