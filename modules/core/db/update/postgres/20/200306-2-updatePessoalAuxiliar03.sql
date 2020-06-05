alter table pessoal_auxiliar add constraint FK_PESSOAL_AUXILIAR_ON_IDVALENCIA foreign key (IDVALENCIA) references valencias(idvalencias);
create index IDX_PESSOAL_AUXILIAR_ON_IDVALENCIA on pessoal_auxiliar (IDVALENCIA);
