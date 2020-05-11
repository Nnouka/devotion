
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    full_name VARCHAR(256) NULL ,
    email VARCHAR(256) NOT NULL ,
    password VARCHAR(256) NOT NULL ,
    phone VARCHAR(100) NOT NULL ,
    enabled boolean not null default '0',
    token_expired boolean not null default '0',
    password_reset_code VARCHAR(100) null ,
    email_verified_at timestamp null default null,
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roles (
     id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
     name VARCHAR(256) NOT NULL ,
     created_at timestamp default CURRENT_TIMESTAMP,
     updated_at timestamp default CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `privileges` (
     id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
     name VARCHAR(256) NOT NULL ,
     icon VARCHAR(100) NULL ,
     uri VARCHAR(256) NOT NULL ,
     description VARCHAR(256) NULL ,
     collapsible boolean not null default '0',
     collapse_to VARCHAR(100) NULL ,
     displayed boolean not null default '0',
     created_at timestamp default CURRENT_TIMESTAMP,
     updated_at timestamp default CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_role (
    user_id BIGINT NOT NULL ,
    role_id INT(11) NOT NULL ,
    primary key (user_id, role_id),
    constraint role_id_join_u_k foreign key (role_id) references roles(id),
    constraint user_id_join_k foreign key (user_id) references users(id)
);

CREATE TABLE IF NOT EXISTS role_privilege (
  role_id INT(11) NOT NULL ,
  privilege_id INT(11) NOT NULL ,
  primary key (role_id, privilege_id),
  constraint role_id_join_k foreign key (role_id) references roles(id),
  constraint privilege_id_join_k foreign key (privilege_id) references privileges(id)
);

