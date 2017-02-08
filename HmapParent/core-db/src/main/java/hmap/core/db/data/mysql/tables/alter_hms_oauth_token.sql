--
--  Oauth sql  -- MYSQL
--

Alter table `hms_oauth_token` add unique(app_id,user_name);
