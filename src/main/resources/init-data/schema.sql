-- drop database ConcertCalendar;
-- create database ConcertCalendar;
--
-- create table user_table(
--     id bigint not null auto_increment,
--     name varchar(11)
-- );
-- create table post_table;
-- create table board_table;
drop table if exists test_table;

;;

CREATE TABLE test_table (
    id INTEGER,
    name varchar(255),
    primary key (id)
);

;;

DROP TABLE IF EXISTS country;

;;

CREATE TABLE country (
                         id   INTEGER      NOT NULL AUTO_INCREMENT,
                         name VARCHAR(128) NOT NULL,
                         PRIMARY KEY (id)
);
;;

drop table if exists test_table;;
drop table if exists country;;

