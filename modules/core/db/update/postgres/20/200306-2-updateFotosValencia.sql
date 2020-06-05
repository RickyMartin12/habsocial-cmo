alter table fotos_valencia rename column idvalencia to idvalencia__u85774 ;
alter table fotos_valencia drop constraint FOTOS_VALENCIA_FK ;
drop index IDX_FOTOS_VALENCIA_ON_IDVALENCIA ;
alter table fotos_valencia add column IDVALENCIA uuid ;
