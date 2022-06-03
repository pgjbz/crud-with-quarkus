create table if not exists product (id serial primary key,name text,price real);

insert into product (name, price) values ('Vassoura', 10.99);