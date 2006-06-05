drop database if exists sample_dao;
create database sample_dao character set utf8;
use sample_dao;

create table FoodItem (
	id integer auto_increment primary key,
	name varchar(75) null,
	points integer null
);