
insert into users (`id`, `full_name`, `email`, `password`, `phone`, `enabled`, `token_expired`)
values (1, 'normal user', 'user@devotion.com', '{bcrypt}$2a$10$Cyt9Sq8ubL90hVJHqIP0TelSBGBn5sf5nG7p/V0mfD9TTcc8q1kRi', '67882251', true , false ),
(2, 'admin user', 'admin@devotion.com', '{bcrypt}$2a$10$Cyt9Sq8ubL90hVJHqIP0TelSBGBn5sf5nG7p/V0mfD9TTcc8q1kRi', '65412310', true , false );

insert into roles (`id`, `name`)
values (1, 'USER'), (2, 'ADMIN');

insert into privileges (`id`, `name`, `icon`, `displayed`, `uri`)
values (1, 'USER', 'account', true, '/user' ), (2, 'ADMIN', 'key', true, '/admin' );

insert into user_role (`user_id`, `role_id`) values (1, 1), (2, 2);
insert into role_privilege (`role_id`, `privilege_id`) values (1, 1), (2, 1), (2, 2);
/**/
