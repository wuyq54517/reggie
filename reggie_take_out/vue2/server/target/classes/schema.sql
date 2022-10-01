drop table if exists student;
create table student (
    id int primary key auto_increment,
    name varchar(32) not null,
    sex char(1),
    age tinyint
);
