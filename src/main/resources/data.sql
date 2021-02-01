-- Countries
insert into country
values(1001,'Colombia');

insert into country
values(1002,'Uruguay');

insert into country
values(1003,'United States');

insert into country
values(1004,'Argentina');

-- Country Taxes
insert into country_tax
values(1001,'BUSINESS',7.0);

insert into country_tax
values(1001,'CUSTOMER',3.0);

insert into country_tax
values(1002,'BUSINESS',5.5);

insert into country_tax
values(1002,'CUSTOMER',3.5);

-- Customers
insert into customer
values('123456789','german.ubillos@globant.com','German','Ubillos',1002);

insert into customer
values('987654321','m.goyeneche@globant.com','Mauricio','Goyeneche',1001);

insert into customer
values('1094901480','hector.hurtado@globant.com','Hector','Hurtado',1001);

-- Credit Cards
insert into credit_card
values(1001,true,'1234567890','987654321','12/05','Mauricio Ubillos');

insert into credit_card
values(1002,true,'0123456789','123456789','10/10','German Ubillos');

insert into credit_card
values(1003,true,'1122334455','1094901480','19/11','Hector Hurtado');

-- Products
insert into product
values('fd9975c8-f09f-4648-bd0b-3f49c9de8b1e','Play Station','A Play Station',299,10000);

insert into product
values('fd9975c8-f09f-4648-bd0b-3f49c9de8b2e','Xbox One','A Xbox One',300,1000);

insert into product
values('fd9975c8-f09f-4648-bd0b-3f49c9de8b3e','Nintendo Switch','A Nintendo Switch',303,1000);

-- Carts
insert into cart
values('fd9975c8-f09f-4648-bd0b-3f49c9de8b1e','123456789');

insert into cart
values('fd9975c8-f09f-4648-bd0b-3f49c9de8b2e','987654321');

-- Cart Items
insert into cart_item
values(10001,'fd9975c8-f09f-4648-bd0b-3f49c9de8b2e',303,'fd9975c8-f09f-4648-bd0b-3f49c9de8b3e',2,9.09);

insert into cart_item
values(10002,'fd9975c8-f09f-4648-bd0b-3f49c9de8b2e',300,'fd9975c8-f09f-4648-bd0b-3f49c9de8b2e',2,9);