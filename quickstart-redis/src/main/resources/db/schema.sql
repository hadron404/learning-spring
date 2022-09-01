drop table if exists t_user;
create table t_user
(
    `id`     int primary key auto_increment,
    `name`   varchar(255) not null,
    `password`  varchar(1000) not null,
    `age`    int          not null,
    `gender` int          not null
);
