create table user (
id bigint primary key auto_increment,
user_name varchar(30) not null
);

create table file_user (
id bigint primary key auto_increment,
file_name varchar(100) not null,
file_path varchar(200) not null,
user_id bigint,
foreign key (user_id) references user (id)
);

create table event (
id bigint primary key auto_increment,
event varchar(300) not null
);
