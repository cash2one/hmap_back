/*
Navicat MySQL Data Transfer

Source Server         : 172.20.0.165
Source Server Version : 50713
Source Host           : 172.20.0.165:3306
Source Database       : hms_dev101

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2016-11-17 11:01:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hms_active_trace
-- ----------------------------
DROP TABLE IF EXISTS `hms_active_trace`;
CREATE TABLE `hms_active_trace` (
  `id` varchar(240) NOT NULL,
  `user_name` varchar(240) NOT NULL,
  `last_active` datetime DEFAULT CURRENT_TIMESTAMP,
  `day_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `day_count` bigint(20) DEFAULT NULL,
  `week_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `week_count` bigint(20) DEFAULT NULL,
  `month_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `month_count` bigint(20) DEFAULT NULL,
  `OBJECT_VERSION_NUMBER` bigint(20) DEFAULT '1',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
