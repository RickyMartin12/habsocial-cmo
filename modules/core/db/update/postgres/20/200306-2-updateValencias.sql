alter table valencias add column DELETED_BY varchar(50) ;
alter table valencias add column UPDATE_TS timestamp ;
alter table valencias add column DELETE_TS timestamp ;
alter table valencias add column UPDATED_BY varchar(50) ;
alter table valencias add column CREATED_BY varchar(50) ;
alter table valencias add column CREATE_TS timestamp ;
alter table valencias add column VERSION integer ^
update valencias set VERSION = 0 where VERSION is null ;
alter table valencias alter column VERSION set not null ;
