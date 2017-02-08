USE hms_dev;

CREATE TABLE hms_interface_header_b (
  HEADER_ID varchar(255) NOT NULL,
  INTERFACE_CODE varchar(30) NOT NULL,
  INTERFACE_TYPE varchar(10) NOT NULL,
  BODY_HEADER varchar(2000) DEFAULT NULL,
  BODY_TAIL varchar(2000) DEFAULT NULL,
  NAMESPACE varchar(30) DEFAULT NULL,
  DOMAIN_URL varchar(200) NOT NULL,
  REQUEST_METHOD varchar(10) NOT NULL,
  REQUEST_FORMAT varchar(30) NOT NULL,
  REQUEST_CONTENTTYPE varchar(80) DEFAULT NULL,
  REQUEST_ACCEPT varchar(80) DEFAULT NULL,
  AUTH_FLAG varchar(1) NOT NULL,
  SYSTEM_TYPE varchar(240) DEFAULT NULL,
  AUTH_TYPE varchar(240) DEFAULT NULL,
  GRANT_TYPE varchar(240) DEFAULT NULL,
  ACCESS_TOKEN_URL varchar(240) DEFAULT NULL,
  APP_KEY varchar(240) DEFAULT NULL,
  APP_SECRET varchar(240) DEFAULT NULL,
  AUTH_USERNAME varchar(80) DEFAULT NULL,
  AUTH_PASSWORD varchar(200) DEFAULT NULL,
  ENABLE_FLAG varchar(1) NOT NULL,
  CREATION_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY decimal(20, 0) DEFAULT - 1,
  LAST_UPDATED_BY decimal(20, 0) DEFAULT - 1,
  LAST_UPDATE_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE_LOGIN decimal(20, 0) DEFAULT NULL,
  ATTRIBUTE_CATEGORY varchar(30) DEFAULT NULL,
  ATTRIBUTE1 varchar(240) DEFAULT NULL,
  ATTRIBUTE2 varchar(240) DEFAULT NULL,
  ATTRIBUTE3 varchar(240) DEFAULT NULL,
  ATTRIBUTE4 varchar(240) DEFAULT NULL,
  ATTRIBUTE5 varchar(240) DEFAULT NULL,
  ATTRIBUTE6 varchar(240) DEFAULT NULL,
  ATTRIBUTE7 varchar(240) DEFAULT NULL,
  ATTRIBUTE8 varchar(240) DEFAULT NULL,
  ATTRIBUTE9 varchar(240) DEFAULT NULL,
  ATTRIBUTE10 varchar(240) DEFAULT NULL,
  ATTRIBUTE11 varchar(240) DEFAULT NULL,
  ATTRIBUTE12 varchar(240) DEFAULT NULL,
  ATTRIBUTE13 varchar(240) DEFAULT NULL,
  ATTRIBUTE14 varchar(240) DEFAULT NULL,
  ATTRIBUTE15 varchar(240) DEFAULT NULL,
  MAPPER_CLASS varchar(255) DEFAULT NULL,
  NAME varchar(200) NOT NULL,
  DESCRIPTION varchar(255) NOT NULL,
  REQUEST_ID bigint(20) DEFAULT - 1,
  PROGRAM_ID bigint(20) DEFAULT - 1,
  OBJECT_VERSION_NUMBER decimal(20, 0) DEFAULT - 1,
  PRIMARY KEY (HEADER_ID),
  UNIQUE INDEX ITF_INTERFACE_HEADER_U1 (INTERFACE_CODE)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;


CREATE TABLE hms_interface_header_tl (
  HEADER_ID varchar(255) NOT NULL,
  LANG varchar(10) NOT NULL,
  NAME varchar(200) NOT NULL,
  DESCRIPTION varchar(240) DEFAULT NULL,
  CREATION_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY decimal(20, 0) DEFAULT - 1,
  LAST_UPDATED_BY decimal(20, 0) DEFAULT - 1,
  LAST_UPDATE_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE_LOGIN decimal(20, 0) DEFAULT NULL,
  ATTRIBUTE_CATEGORY varchar(30) DEFAULT NULL,
  ATTRIBUTE1 varchar(240) DEFAULT NULL,
  ATTRIBUTE2 varchar(240) DEFAULT NULL,
  ATTRIBUTE3 varchar(240) DEFAULT NULL,
  ATTRIBUTE4 varchar(240) DEFAULT NULL,
  ATTRIBUTE5 varchar(240) DEFAULT NULL,
  ATTRIBUTE6 varchar(240) DEFAULT NULL,
  ATTRIBUTE7 varchar(240) DEFAULT NULL,
  ATTRIBUTE8 varchar(240) DEFAULT NULL,
  ATTRIBUTE9 varchar(240) DEFAULT NULL,
  ATTRIBUTE10 varchar(240) DEFAULT NULL,
  ATTRIBUTE11 varchar(240) DEFAULT NULL,
  ATTRIBUTE12 varchar(240) DEFAULT NULL,
  ATTRIBUTE13 varchar(240) DEFAULT NULL,
  ATTRIBUTE14 varchar(240) DEFAULT NULL,
  ATTRIBUTE15 varchar(240) DEFAULT NULL,
  REQUEST_ID bigint(20) DEFAULT - 1,
  PROGRAM_ID bigint(20) DEFAULT - 1,
  OBJECT_VERSION_NUMBER decimal(20, 0) DEFAULT - 1,
  PRIMARY KEY (LANG, HEADER_ID),
  INDEX ITF_INTERFACE_HEADER_TL_U1 (NAME, LANG)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;


CREATE TABLE hms_interface_line_b (
  LINE_ID varchar(255) NOT NULL,
  HEADER_ID varchar(255) NOT NULL,
  LINE_CODE varchar(30) NOT NULL,
  IFT_URL varchar(200) NOT NULL,
  ENABLE_FLAG varchar(1) NOT NULL,
  HEADER_PARA1 varchar(200) DEFAULT NULL,
  HEADER_PARA2 varchar(200) DEFAULT NULL,
  HEADER_PARA3 varchar(200) DEFAULT NULL,
  HEADER_PARA4 varchar(200) DEFAULT NULL,
  HEADER_PARA5 varchar(200) DEFAULT NULL,
  HEADER_PARA6 varchar(200) DEFAULT NULL,
  HEADER_PARA7 varchar(200) DEFAULT NULL,
  HEADER_PARA8 varchar(200) DEFAULT NULL,
  HEADER_PARA9 varchar(200) DEFAULT NULL,
  HEADER_PARA10 varchar(200) DEFAULT NULL,
  HEADER_PARA11 varchar(200) DEFAULT NULL,
  HEADER_PARA12 varchar(200) DEFAULT NULL,
  HEADER_PARA13 varchar(200) DEFAULT NULL,
  HEADER_PARA14 varchar(200) DEFAULT NULL,
  HEADER_PARA15 varchar(200) DEFAULT NULL,
  PARA1 varchar(200) DEFAULT NULL,
  PARA2 varchar(200) DEFAULT NULL,
  PARA3 varchar(200) DEFAULT NULL,
  PARA4 varchar(200) DEFAULT NULL,
  PARA5 varchar(200) DEFAULT NULL,
  PARA6 varchar(200) DEFAULT NULL,
  PARA7 varchar(200) DEFAULT NULL,
  PARA8 varchar(200) DEFAULT NULL,
  PARA9 varchar(200) DEFAULT NULL,
  PARA10 varchar(200) DEFAULT NULL,
  PARA11 varchar(200) DEFAULT NULL,
  PARA12 varchar(200) DEFAULT NULL,
  PARA13 varchar(200) DEFAULT NULL,
  PARA14 varchar(200) DEFAULT NULL,
  PARA15 varchar(200) DEFAULT NULL,
  PARA16 varchar(200) DEFAULT NULL,
  PARA17 varchar(200) DEFAULT NULL,
  PARA18 varchar(200) DEFAULT NULL,
  PARA19 varchar(200) DEFAULT NULL,
  PARA20 varchar(200) DEFAULT NULL,
  PARA21 varchar(200) DEFAULT NULL,
  PARA22 varchar(200) DEFAULT NULL,
  PARA23 varchar(200) DEFAULT NULL,
  PARA24 varchar(200) DEFAULT NULL,
  PARA25 varchar(200) DEFAULT NULL,
  CREATION_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY decimal(20, 0) DEFAULT - 1,
  LAST_UPDATED_BY decimal(20, 0) DEFAULT - 1,
  LAST_UPDATE_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE_LOGIN decimal(20, 0) DEFAULT NULL,
  ATTRIBUTE_CATEGORY varchar(30) DEFAULT NULL,
  ATTRIBUTE1 varchar(240) DEFAULT NULL,
  ATTRIBUTE2 varchar(240) DEFAULT NULL,
  ATTRIBUTE3 varchar(240) DEFAULT NULL,
  ATTRIBUTE4 varchar(240) DEFAULT NULL,
  ATTRIBUTE5 varchar(240) DEFAULT NULL,
  ATTRIBUTE6 varchar(240) DEFAULT NULL,
  ATTRIBUTE7 varchar(240) DEFAULT NULL,
  ATTRIBUTE8 varchar(240) DEFAULT NULL,
  ATTRIBUTE9 varchar(240) DEFAULT NULL,
  ATTRIBUTE10 varchar(240) DEFAULT NULL,
  ATTRIBUTE11 varchar(240) DEFAULT NULL,
  ATTRIBUTE12 varchar(240) DEFAULT NULL,
  ATTRIBUTE13 varchar(240) DEFAULT NULL,
  ATTRIBUTE14 varchar(240) DEFAULT NULL,
  ATTRIBUTE15 varchar(240) DEFAULT NULL,
  LINE_NAME varchar(50) DEFAULT NULL,
  LINE_DESCRIPTION varchar(255) DEFAULT NULL,
  REQUEST_ID bigint(20) DEFAULT - 1,
  PROGRAM_ID bigint(20) DEFAULT - 1,
  OBJECT_VERSION_NUMBER varchar(255) DEFAULT '-1',
  PRIMARY KEY (LINE_ID),
  UNIQUE INDEX ITF_INTERFACE_LINE_U1 (HEADER_ID, LINE_CODE)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;


CREATE TABLE hms_interface_line_tl (
  LINE_ID varchar(255) NOT NULL,
  LANG varchar(10) NOT NULL,
  LINE_NAME varchar(200) NOT NULL,
  LINE_DESCRIPTION varchar(240) DEFAULT NULL,
  CREATION_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  CREATED_BY decimal(20, 0) DEFAULT - 1,
  LAST_UPDATED_BY decimal(20, 0) DEFAULT - 1,
  LAST_UPDATE_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE_LOGIN decimal(20, 0) DEFAULT NULL,
  ATTRIBUTE_CATEGORY varchar(30) DEFAULT NULL,
  ATTRIBUTE1 varchar(240) DEFAULT NULL,
  ATTRIBUTE2 varchar(240) DEFAULT NULL,
  ATTRIBUTE3 varchar(240) DEFAULT NULL,
  ATTRIBUTE4 varchar(240) DEFAULT NULL,
  ATTRIBUTE5 varchar(240) DEFAULT NULL,
  ATTRIBUTE6 varchar(240) DEFAULT NULL,
  ATTRIBUTE7 varchar(240) DEFAULT NULL,
  ATTRIBUTE8 varchar(240) DEFAULT NULL,
  ATTRIBUTE9 varchar(240) DEFAULT NULL,
  ATTRIBUTE10 varchar(240) DEFAULT NULL,
  ATTRIBUTE11 varchar(240) DEFAULT NULL,
  ATTRIBUTE12 varchar(240) DEFAULT NULL,
  ATTRIBUTE13 varchar(240) DEFAULT NULL,
  ATTRIBUTE14 varchar(240) DEFAULT NULL,
  ATTRIBUTE15 varchar(240) DEFAULT NULL,
  REQUEST_ID bigint(20) DEFAULT - 1,
  PROGRAM_ID bigint(20) DEFAULT - 1,
  OBJECT_VERSION_NUMBER varchar(255) DEFAULT '-1',
  PRIMARY KEY (LINE_ID, LANG),
  INDEX ITF_INTERFACE_LINE_TL_U1 (LINE_NAME, LANG)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci
ROW_FORMAT = DYNAMIC;

