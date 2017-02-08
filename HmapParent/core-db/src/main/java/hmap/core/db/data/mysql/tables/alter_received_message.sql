--
--  Oauth sql  -- MYSQL
--


ALTER TABLE hms_received_message ADD (AUDIENCE_TYPE VARCHAR(240));
ALTER TABLE hms_received_message ADD (RECEIVER_LIST VARCHAR(240));
ALTER TABLE hms_received_message ADD (APNS_PRODUCTION VARCHAR(40));
