create table event (
id bigint primary key auto_increment,
file_name varchar(100) not null,
file_path varchar(200) not null,
date_upload datetime not null,
date_last_modified datetime,
user_id bigint,
foreign key (user_id) references user (id)
);
