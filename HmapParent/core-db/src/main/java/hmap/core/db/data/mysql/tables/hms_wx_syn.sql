/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : hms_dev

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2016-09-26 09:17:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hms_wx_syn
-- ----------------------------
DROP TABLE IF EXISTS `hms_wx_syn`;
CREATE TABLE `hms_wx_syn` (
  `id` varchar(240) NOT NULL DEFAULT '',
  `type` varchar(240) DEFAULT NULL,
  `source_id` varchar(240) NOT NULL,
  `operation` varchar(300) DEFAULT NULL,
  `error_message` varchar(240) DEFAULT NULL,
  `status` varchar(240) DEFAULT NULL,
  `try_time` bigint(20) DEFAULT NULL,
  `OBJECT_VERSION_NUMBER` bigint(20) NOT NULL DEFAULT '1',
  `CREATION_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `CREATED_BY` bigint(20) DEFAULT '-1',
  `LAST_UPDATED_BY` bigint(20) DEFAULT '-1',
  `LAST_UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_LOGIN` bigint(20) DEFAULT NULL,
  `request_id` bigint(20) DEFAULT NULL,
  `program_id` bigint(20) DEFAULT NULL,
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
  `uuid` varchar(240) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account_number` (`source_id`),
  KEY `uuid_index` (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
