truncate  oauth_client_details;
insert into oauth_client_details
(client_id, resource_ids, client_secret, scope, authorized_grant_types,
web_server_redirect_uri,authorities, access_token_validity,
refresh_token_validity, additional_information, create_time, archived, trusted)
values
('client','api-resource', 'secret', 'read,write','authorization_code,password,refresh_token,implicit',
null,'ROLE_CLIENT',null,
null,null, now(), 0, 0);
