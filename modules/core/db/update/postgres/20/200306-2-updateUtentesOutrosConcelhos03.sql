alter table utentes_outros_concelhos add constraint FK_UTENTES_OUTROS_CONCELHOS_ON_IDVALENCIA foreign key (IDVALENCIA) references valencias(idvalencias);
create index IDX_UTENTES_OUTROS_CONCELHOS_ON_IDVALENCIA on utentes_outros_concelhos (IDVALENCIA);
