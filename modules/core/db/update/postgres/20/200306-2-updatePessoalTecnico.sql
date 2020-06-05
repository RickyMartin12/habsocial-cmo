alter table pessoal_tecnico rename column idvalencia to idvalencia__u17434 ;
alter table pessoal_tecnico drop constraint PESSOAL_TECNICO_FK ;
drop index IDX_PESSOAL_TECNICO_ON_IDVALENCIA ;
alter table pessoal_tecnico add column IDVALENCIA uuid ;
