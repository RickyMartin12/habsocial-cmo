alter table utentes_outros_concelhos rename column idvalencia to idvalencia__u57101 ;
alter table utentes_outros_concelhos drop constraint UTENTES_OUTROS_CONCELHOS_FK ;
drop index IDX_UTENTES_OUTROS_CONCELHOS_ON_IDVALENCIA ;
alter table utentes_outros_concelhos add column IDVALENCIA uuid ;
