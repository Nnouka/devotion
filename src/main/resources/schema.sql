
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

CREATE TABLE IF NOT EXISTS congregations (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    `name` varchar(256) null ,
    country varchar (256) not null,
    region varchar (256) not null,
    city varchar (256) not null,
    address varchar (256) not null
);

CREATE TABLE IF NOT EXISTS congregation_user (
    user_id BIGINT NOT NULL,
    congregation_id BIGINT NOT NULL,
    is_minister BOOLEAN NOT NULL default '0',
    primary key (user_id, congregation_id),
    constraint user_id_con_join_k foreign key (user_id) references users(id),
    constraint congregation_id_con_join_k foreign key (congregation_id) references congregations(id)
);

CREATE TABLE IF NOT EXISTS prayer_points (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    issue VARCHAR(256) NOT NULL,
    answer VARCHAR(256) NULL
);

CREATE TABLE IF NOT EXISTS bible_references (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    book VARCHAR(256) NOT NULL ,
    chapter INT(11) NOT NULL ,
    verse INT(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS pulses (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  title VARCHAR(256) NOT NULL ,
  exhortation LONGTEXT NULL
);

CREATE TABLE IF NOT EXISTS devotionals (
   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
   title VARCHAR(256) NOT NULL ,
   duration_in_days INT(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS devotion_reference (
    devotion_id BIGINT NOT NULL,
    reference_id BIGINT NOT NULL,
    primary key (devotion_id, reference_id),
    constraint devotional_id_dev_join_k foreign key (devotion_id) references devotionals(id),
    constraint reference_id_dev_join_k foreign key (reference_id) references bible_references(id)
);

CREATE TABLE IF NOT EXISTS devotion_pulse (
    devotion_id BIGINT NOT NULL,
    pulse_id BIGINT NOT NULL,
    primary key (devotion_id, pulse_id),
    constraint devotional_id_dep_join_k foreign key (devotion_id) references devotionals(id),
    constraint pulse_id_dep_join_k foreign key (pulse_id) references pulses(id)
);

CREATE TABLE IF NOT EXISTS pulse_reference (
  pulse_id BIGINT NOT NULL,
  reference_id BIGINT NOT NULL,
  primary key (pulse_id, reference_id),
  constraint pulse_id_pu_join_k foreign key (pulse_id) references pulses(id),
  constraint reference_id_pu_join_k foreign key (reference_id) references bible_references(id)
);

CREATE TABLE IF NOT EXISTS pulse_prayer_point (
   pulse_id BIGINT NOT NULL,
   prayer_point_id BIGINT NOT NULL,
   primary key (pulse_id, prayer_point_id),
   constraint pulse_id_pp_join_k foreign key (pulse_id) references pulses(id),
   constraint reference_id_pp_join_k foreign key (prayer_point_id) references prayer_points(id)
);
