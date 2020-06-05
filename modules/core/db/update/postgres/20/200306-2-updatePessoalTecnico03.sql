alter table pessoal_tecnico add constraint FK_PESSOAL_TECNICO_ON_IDVALENCIA foreign key (IDVALENCIA) references valencias(idvalencias);
create index IDX_PESSOAL_TECNICO_ON_IDVALENCIA on pessoal_tecnico (IDVALENCIA);
