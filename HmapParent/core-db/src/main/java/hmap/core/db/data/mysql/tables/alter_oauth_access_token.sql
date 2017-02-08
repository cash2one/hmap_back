--
--  Oauth sql  -- MYSQL
--

Alter table `oauth_access_token` add unique(user_name,client_id);

Alter table `oauth_access_token` add unique(token_id);

