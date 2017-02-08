/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : hms_dev

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2016-09-20 15:46:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hms_staff
-- ----------------------------
DROP TABLE IF EXISTS `hms_staff`;
CREATE TABLE `hms_staff` (
  `user_id` varchar(240) NOT NULL DEFAULT '',
  `user_name` varchar(240) DEFAULT NULL,
  `account_number` varchar(240) NOT NULL,
  `position` varchar(300) DEFAULT NULL,
  `GENDER_ID` bigint(20) DEFAULT NULL,
  `DEPARTMENT_ID` bigint(20) DEFAULT NULL,
  `MOBILE` varchar(240) DEFAULT NULL,
  `EMAIL` varchar(240) DEFAULT NULL,
  `AVATAR` varchar(300) DEFAULT NULL,
  `WX_NUMBER` varchar(240) DEFAULT NULL,
  `ENABLE_FLAG` varchar(1) DEFAULT 'Y',
  `OBJECT_VERSION_NUMBER` bigint(20) NOT NULL DEFAULT '1',
  `HOME_TOWN` varchar(240) DEFAULT NULL,
  `BASE_NAME` varchar(240) DEFAULT NULL,
  `EMP_STATUS` varchar(240) DEFAULT NULL,
  `UNIT_NAME` varchar(240) DEFAULT NULL,
  `ROOT_UNIT_NAME` varchar(240) DEFAULT NULL,
  `NAME_PINYIN_CAPITAL` varchar(240) DEFAULT NULL,
  `NAME_PINYIN` varchar(240) DEFAULT NULL,
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
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `account_number` (`account_number`),
  KEY `uuid_index` (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
