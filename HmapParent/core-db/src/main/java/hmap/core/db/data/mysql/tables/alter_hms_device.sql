alter table hms_device change OPENING_SYSTEM OPERATION_SYSTEM VARCHAR(200) DEFAULT NULL;
alter table hms_device change OPENING_SYSTEM_VERSION OPERATION_SYSTEM_VERSION VARCHAR(200) DEFAULT NULL;
alter table hms_device add WIDTH  VARCHAR(200) default null ;
alter table hms_device add HEIGHT  VARCHAR(200) default null ;
alter table hms_device add PIXEL_RATIO  VARCHAR(200) default null ;