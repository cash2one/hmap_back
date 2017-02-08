CREATE TABLE `HMS_CONTACT_TAG_MEMBER` (
  `ID` VARCHAR(240) NOT NULL ,
  `TAG_ID` VARCHAR(200) NOT NULL,
  `MEMBER_NAME` VARCHAR(2000) DEFAULT NULL,
  `MEMBER_ID` VARCHAR(200) DEFAULT NULL,  
  `MEMBER_TYPE` VARCHAR(200) DEFAULT NULL,
  `CREATION_DATE`         DATETIME       DEFAULT CURRENT_TIMESTAMP,
  `CREATED_BY`            DECIMAL(20, 0) DEFAULT -1,
  `LAST_UPDATED_BY`       DECIMAL(20, 0) DEFAULT -1,
  `LAST_UPDATE_DATE`      DATETIME       DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_LOGIN`     DECIMAL(20, 0),
  `OBJECT_VERSION_NUMBER` DECIMAL(20, 0) DEFAULT 1,
  `REQUEST_ID` BIGINT(20) DEFAULT NULL,
  `PROGRAM_ID` BIGINT(20) DEFAULT NULL,
  `ATTRIBUTE_CATEGORY` VARCHAR(30) DEFAULT NULL,
  `ATTRIBUTE1` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE2` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE3` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE4` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE5` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE6` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE7` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE8` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE9` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE10` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE11` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE12` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE13` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE14` VARCHAR(240) DEFAULT NULL,
  `ATTRIBUTE15` VARCHAR(240) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=INNODB AUTO_INCREMENT=18101 DEFAULT CHARSET=UTF8;
