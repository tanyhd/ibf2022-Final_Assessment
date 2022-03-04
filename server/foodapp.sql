drop database if exists foodapp;

create database foodapp;

use foodapp;

create table user_profile (
	user_id int auto_increment not null,
    username varchar(64) not null,
    name varchar(64) not null,
    email varchar(64),
    password varchar(512) not null,
    primary key(user_id)
);

create table inventory_line_item (
	item_id int auto_increment not null,
    name varchar(64),
    quantity int,
    price float,
    user_id int,
    
    primary key(item_id),
    constraint fk_user_id
		foreign key(user_id)
        references user_profile (user_id)
);