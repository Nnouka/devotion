
CREATE TABLE IF NOT EXISTS `countries`
(
    `id`            INT(11)       NOT NULL AUTO_INCREMENT,
    `iso2`          char(2)      NOT NULL,
    `name`          varchar(100) NOT NULL,
    `iso3`          char(3)     DEFAULT NULL,
    `num_code`      smallint(6) DEFAULT NULL,
    `phone_code`    varchar(100) NOT NULL,
    `currency_name` varchar(80) DEFAULT NULL,
    `currency_code` varchar(80) DEFAULT NULL,
    `lang`          varchar(80) DEFAULT NULL,
    `locale`        varchar(3)  DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS regions (
   id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
   `name` varchar(100) NOT NULL ,
   country_id INT(11) NOT NULL ,
   constraint fk_country_reg_key foreign key (country_id) references countries(id)
);

CREATE TABLE IF NOT EXISTS cities (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  `name` varchar(100) NOT NULL ,
  region_id BIGINT NOT NULL ,
  constraint fk_region_city_key foreign key (region_id) references regions(id)
);

CREATE TABLE IF NOT EXISTS location_addresses (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  address varchar(256) NOT NULL ,
  latitude double NULL ,
  longitude double NULL ,
  city_id BIGINT NOT NULL ,
  constraint fk_city_addr_key foreign key (city_id) references cities(id)
);
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    full_name VARCHAR(256) NULL ,
    email VARCHAR(256) NOT NULL ,
    password VARCHAR(256) NOT NULL ,
    phone VARCHAR(100) NOT NULL ,
    enabled boolean not null default '0',
    token_expired boolean not null default '0',
    password_reset_code VARCHAR(100) null ,
    country_id INT NULL ,
    email_verified_at timestamp null default null,
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP,
    constraint fk_country_id foreign key (country_id) references countries(id)
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
    display_pic varchar(256) null ,
    location_address_id BIGINT NOT NULL,
    constraint fk_loc_addr_id foreign key (location_address_id) references location_addresses(id)
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


CREATE TABLE IF NOT EXISTS user_checked_pulse
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    user_id BIGINT NOT NULL,
    pulse_id BIGINT NOT NULL,
    checked_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint fk_usr_id_pc foreign key (user_id) references users(id),
    constraint fk_pulse_id_uc foreign key (pulse_id) references pulses(id)
);

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    text LONGTEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS user_commented_pulse
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    user_id BIGINT NOT NULL,
    pulse_id BIGINT NOT NULL,
    comment_id BIGINT NOT NULL ,
    commented_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    constraint fk_usr_id_cmt foreign key (user_id) references users(id),
    constraint fk_cmt_id_cmt foreign key (comment_id) references comments(id),
    constraint fk_pulse_id_cmt foreign key (pulse_id) references pulses(id)
);

CREATE TABLE IF NOT EXISTS relevance (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    type VARCHAR(100) NOT NULL ,
    featured_image VARCHAR(256) NULL
);

CREATE TABLE IF NOT EXISTS pulse_relevant_to_user (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  user_id BIGINT NOT NULL,
  pulse_id BIGINT NOT NULL,
  relevance_id BIGINT NOT NULL ,
  relevant_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  constraint fk_usr_id_rel foreign key (user_id) references users(id),
  constraint fk_rel_id_rel foreign key (relevance_id) references relevance(id),
  constraint fk_pulse_id_rel foreign key (pulse_id) references pulses(id)
);