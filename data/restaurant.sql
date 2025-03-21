-- IMPORTANT: DO NOT CHANGE THE GIVEN SCHEMA UNLESS YOU HAVE A GOOD REASON
-- IF YOU DO CHANGE IT WRITE THE JUSTIFICATION IN A COMMENT ABOVE THE CHANGE

drop database if exists restaurant;

create database restaurant;

use restaurant;

create table customers (
  username varchar(64) not null,
  password varchar(128) not null,
  -- added a primary key constraint so that the username will be unique and can be referenced as a foreign key in the place_orders table
  constraint pk_username PRIMARY KEY(username) 
);

insert into customers(username, password) values
  ('fred', sha2('fred', 224)),
  ('barney', sha2('barney', 224)),
  ('wilma', sha2('wilma', 224)),
  ('betty', sha2('betty', 224)),
  ('pebbles', sha2('pebbles', 224));

-- TODO: Task 1.2
-- Write your task 1.2 below

create table place_orders (
  order_id char(8) not null, 
  payment_id varchar(128) unique not null, 
  order_date Date not null, 
  total decimal(10,2) not null, 
  username varchar(64) not null, 
  constraint pk_order_id PRIMARY KEY(order_id),
  constraint fk_username FOREIGN KEY(username) references customers(username),
  constraint chk_total CHECK (total <= 999999.99)
)


