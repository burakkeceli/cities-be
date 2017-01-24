create table cities.city (city_id  serial not null, latitude float8, longitude float8, name varchar(255) not null, country_id int4 not null, primary key (city_id))
create table cities.country (country_id  serial not null, name varchar(255) not null, primary key (country_id))
create table cities.friendship (id  serial not null, status varchar(255), user_from_id int4, user_to_id int4, primary key (id))
create table cities.user (user_id  serial not null, country varchar(255), email varchar(255), firstName varchar(255), lastName varchar(255), password varchar(255), username varchar(255), primary key (user_id))
create table cities.userRole (userrole_id  serial not null, role varchar(255) not null, primary key (userrole_id))
create table cities.user_userRole (user_id int4 not null, userrole_id int4 not null, primary key (user_id, userrole_id))
alter table cities.city add constraint UK_qsstlki7ni5ovaariyy9u8y79 unique (name)
alter table cities.country add constraint UK_llidyp77h6xkeokpbmoy710d4 unique (name)
alter table cities.friendship add constraint UK_49u44xxj581198a3rfpwilu1 unique (user_from_id, user_to_id)
alter table cities.user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username)
alter table cities.userRole add constraint UK_b0ku4xtcf1tk9nr8raoj9tvfw unique (role)
alter table cities.city add constraint FK_dqdsiek23hleiulhpmnf98hwj foreign key (country_id) references cities.country
alter table cities.friendship add constraint FK_sfi5islhfab2kv0fqijbntwxo foreign key (user_from_id) references cities.user
alter table cities.friendship add constraint FK_cygmwt31855cm39vv7myj5417 foreign key (user_to_id) references cities.user
alter table cities.user_userRole add constraint FK_mj33g72x8f3026ljra7ns2n7m foreign key (userrole_id) references cities.userRole
