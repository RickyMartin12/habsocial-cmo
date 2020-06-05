alter table pessoal_auxiliar rename column idvalencia to idvalencia__u80657 ;
alter table pessoal_auxiliar drop constraint PESSOAL_AUXILIAR_FK ;
drop index IDX_PESSOAL_AUXILIAR_ON_IDVALENCIA ;
alter table pessoal_auxiliar add column IDVALENCIA uuid ;
