--
--  Oauth sql  -- MYSQL
--

alter table `hms_staff` drop column SOURCE_CODE;
alter table `hms_staff` drop column SOURCE_LINE_ID;
alter table `hms_staff` drop column SOURCE_REFERENCE;
Alter table `hms_staff` add unique(account_number);
Alter table `hms_dept` add unique(DEPT_NUMBER);
alter table `hms_staff` modify column user_id VARCHAR(240);
