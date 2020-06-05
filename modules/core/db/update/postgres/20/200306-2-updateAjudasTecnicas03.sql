alter table ajudas_tecnicas add constraint FK_AJUDAS_TECNICAS_ON_IDVALENCIA foreign key (IDVALENCIA) references valencias(idvalencias);
create index IDX_AJUDAS_TECNICAS_ON_IDVALENCIA on ajudas_tecnicas (IDVALENCIA);
