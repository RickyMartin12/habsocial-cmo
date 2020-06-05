alter table fotos_valencia add constraint FK_FOTOS_VALENCIA_ON_IDVALENCIA foreign key (IDVALENCIA) references valencias(idvalencias);
create index IDX_FOTOS_VALENCIA_ON_IDVALENCIA on fotos_valencia (IDVALENCIA);
