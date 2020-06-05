-- begin PESSOAL_TECNICO
alter table pessoal_tecnico add constraint FK_PESSOAL_TECNICO_ON_IDVALENCIA foreign key (IDVALENCIA) references valencias(idvalencias)^
create index IDX_PESSOAL_TECNICO_ON_IDVALENCIA on pessoal_tecnico (IDVALENCIA)^
-- end PESSOAL_TECNICO
-- begin UTENTES_OUTROS_CONCELHOS
alter table utentes_outros_concelhos add constraint FK_UTENTES_OUTROS_CONCELHOS_ON_IDVALENCIA foreign key (IDVALENCIA) references valencias(idvalencias)^
create index IDX_UTENTES_OUTROS_CONCELHOS_ON_IDVALENCIA on utentes_outros_concelhos (IDVALENCIA)^
-- end UTENTES_OUTROS_CONCELHOS
-- begin PROJECTOS_INTERVENCAO
alter table projectos_intervencao add constraint FK_PROJECTOS_INTERVENCAO_ON_IDINSTITUICAO foreign key (IDINSTITUICAO) references instituicoes(idinstituicao)^
create index IDX_PROJECTOS_INTERVENCAO_ON_IDINSTITUICAO on projectos_intervencao (IDINSTITUICAO)^
-- end PROJECTOS_INTERVENCAO
-- begin FOTOS_VALENCIA
alter table fotos_valencia add constraint FK_FOTOS_VALENCIA_ON_IDVALENCIA foreign key (IDVALENCIA) references valencias(idvalencias)^
create index IDX_FOTOS_VALENCIA_ON_IDVALENCIA on fotos_valencia (IDVALENCIA)^
-- end FOTOS_VALENCIA
-- begin PROJECTOS_EM_APROVACAO
alter table projectos_em_aprovacao add constraint FK_PROJECTOS_EM_APROVACAO_ON_IDPROJECTOSINTERVENCAO foreign key (IDPROJECTOSINTERVENCAO) references projectos_intervencao(idprojectosintervencao)^
create index IDX_PROJECTOS_EM_APROVACAO_ON_IDPROJECTOSINTERVENCAO on projectos_em_aprovacao (IDPROJECTOSINTERVENCAO)^
-- end PROJECTOS_EM_APROVACAO
-- begin LOGIN
alter table login add constraint FK_LOGIN_ON_INSTITUICAO foreign key (INSTITUICAO_ID) references instituicoes(idinstituicao)^
create index IDX_LOGIN_ON_INSTITUICAO on login (INSTITUICAO_ID)^
-- end LOGIN
-- begin AJUDAS_TECNICAS
alter table ajudas_tecnicas add constraint FK_AJUDAS_TECNICAS_ON_IDVALENCIA foreign key (IDVALENCIA) references valencias(idvalencias)^
create index IDX_AJUDAS_TECNICAS_ON_IDVALENCIA on ajudas_tecnicas (IDVALENCIA)^
-- end AJUDAS_TECNICAS
-- begin PESSOAL_AUXILIAR
alter table pessoal_auxiliar add constraint FK_PESSOAL_AUXILIAR_ON_IDVALENCIA foreign key (IDVALENCIA) references valencias(idvalencias)^
create index IDX_PESSOAL_AUXILIAR_ON_IDVALENCIA on pessoal_auxiliar (IDVALENCIA)^
-- end PESSOAL_AUXILIAR
-- begin VALENCIAS
alter table valencias add constraint FK_VALENCIAS_ON_IDINSTITUICAO foreign key (IDINSTITUICAO) references instituicoes(idinstituicao)^
alter table valencias add constraint FK_VALENCIAS_ON_IDTIPOVALENCIA foreign key (IDTIPOVALENCIA) references tiposvalencia(idtiposvalencia)^
create index IDX_VALENCIAS_ON_IDINSTITUICAO on valencias (IDINSTITUICAO)^
create index IDX_VALENCIAS_ON_IDTIPOVALENCIA on valencias (IDTIPOVALENCIA)^
-- end VALENCIAS
