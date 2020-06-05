alter table ajudas_tecnicas rename column idvalencia to idvalencia__u30946 ;
alter table ajudas_tecnicas drop constraint AJUDAS_TECNICAS_FK ;
drop index IDX_AJUDAS_TECNICAS_ON_IDVALENCIA ;
alter table ajudas_tecnicas add column IDVALENCIA uuid ;
