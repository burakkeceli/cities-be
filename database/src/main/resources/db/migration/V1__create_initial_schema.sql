drop table if exists cities.city cascade;
drop table if exists cities.country cascade;
drop table if exists cities.friendship cascade;
drop table if exists cities.user cascade;
drop table if exists cities.userRole cascade;
drop table if exists cities.user_userRole cascade;

create table cities.city (ci_id serial not null,
                          ci_name varchar(255) not null,
                          ci_country_id int4 not null,
                          ci_latitude float8,
                          ci_longitude float8,
                          ci_wiki_url varchar(255),
                          primary key (ci_id));


create table cities.country (co_id serial not null,
                             co_name varchar(255) not null,
                             co_capital varchar(255) not null,
                             co_population int4,
                             co_big_flag varchar(255),
                             co_icon_flag varchar(255),
                             co_small_flag varchar(255),
                             primary key (co_id));

create table cities.friendship (id  serial not null,
                                status varchar(255),
                                user_from_id int4,
                                user_to_id int4,
                                primary key (id));

create table cities.user (us_id serial not null,
                          us_user_name varchar(255) not null,
                          us_email varchar(255),
                          us_first_name varchar(255),
                          us_last_name varchar(255),
                          us_password varchar(255) not null,
                          us_country varchar(255),
                          primary key (us_id));

create table cities.userRole (userrole_id serial not null,
                              role varchar(255) not null,
                              primary key (userrole_id));

create table cities.user_userRole (user_id int4 not null,
                                   userrole_id int4 not null,
                                   primary key (user_id, userrole_id));

alter table cities.city add constraint UK_ci_name unique (ci_name);

alter table cities.country add constraint UK_co_big_flag unique (co_big_flag);
alter table cities.country add constraint UK_co_capital unique (co_capital);
alter table cities.country add constraint UK_co_icon_flag unique (co_icon_flag);
alter table cities.country add constraint UK_co_name unique (co_name);
alter table cities.country add constraint UK_co_small_flag unique (co_small_flag);

alter table cities.user add constraint UK_user_name unique (us_user_name);

alter table cities.friendship add constraint UK_user_from_id_user_to_id unique (user_from_id, user_to_id);
alter table cities.friendship add constraint FK_user_from_id foreign key (user_from_id) references cities.user;
alter table cities.friendship add constraint FK_user_to_id foreign key (user_to_id) references cities.user;

alter table cities.userRole add constraint UK_role unique (role);
alter table cities.user_userRole add constraint FK_userrole_id foreign key (userrole_id) references cities.userRole;
alter table cities.user_userRole add constraint FK_user_id foreign key (user_id) references cities.user;

insert into cities.userRole (role) values ('ROLE_ADMIN');
insert into cities.userRole (role) values ('ROLE_USER');

insert into cities.country (co_name, co_capital) values ('TURKEY', 'ANKARA');
insert into cities.city (ci_name, ci_country_id) SELECT 'ISTANBUL', co_id FROM cities.country WHERE co_name='TURKEY';
insert into cities.city (ci_name, ci_country_id) SELECT 'ANKARA', co_id FROM cities.country WHERE co_name='TURKEY';

insert into cities.country (co_name, co_capital) values ('GERMANY', 'BERLIN');
insert into cities.city (ci_name, ci_country_id) SELECT 'BERLIN', co_id FROM cities.country WHERE co_name='GERMANY';
insert into cities.city (ci_name, ci_country_id) SELECT 'BONN', co_id FROM cities.country WHERE co_name='GERMANY';