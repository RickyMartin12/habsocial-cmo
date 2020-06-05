-- begin PESSOAL_TECNICO
create table pessoal_tecnico (
    idpessoaltecnico integer,
    --
    anosservicoinstituicao integer,
    categoriaprofissional varchar(250),
    formacaoprofissional boolean,
    habilitacoesliterarias varchar(250),
    idade integer,
    idvalencia uuid,
    sexomasculino boolean,
    tipovinculo varchar(250),
    --
    primary key (idpessoaltecnico)
)^
-- end PESSOAL_TECNICO
-- begin UTENTES_OUTROS_CONCELHOS
create table utentes_outros_concelhos (
    idutentesoutrosconcelhos integer,
    --
    concelho varchar(45),
    freguesia varchar(45),
    idvalencia uuid,
    utilizadores integer,
    --
    primary key (idutentesoutrosconcelhos)
)^
-- end UTENTES_OUTROS_CONCELHOS
-- begin BLOCOS_HABITACAO_SOCIAL
create table blocos_habitacao_social (
    codigo integer,
    --
    ano_de_construcao date,
    designacao text,
    morada text,
    --
    primary key (codigo)
)^
-- end BLOCOS_HABITACAO_SOCIAL
-- begin PROJECTOS_INTERVENCAO
create table projectos_intervencao (
    idprojectosintervencao integer,
    --
    adultos boolean,
    comunidade boolean,
    criancas boolean,
    deficientes boolean,
    deficientesapoiodomiciliario boolean,
    deficientescao boolean,
    deficienteslarresidencial boolean,
    idinstituicao integer,
    idosos boolean,
    idososapoiodomiciliario boolean,
    idososcat boolean,
    idososcentrodia boolean,
    idososlar boolean,
    idosospequenolar boolean,
    jovens boolean,
    jovensatl boolean,
    jovenscat boolean,
    jovenscreche boolean,
    jovensjardiminfancia boolean,
    outrosgrupos varchar(250),
    pretendealargarservicos boolean,
    projectosemaprovacao boolean,
    --
    primary key (idprojectosintervencao)
)^
-- end PROJECTOS_INTERVENCAO
-- begin TIPOSVALENCIA
create table tiposvalencia (
    idtiposvalencia integer,
    --
    nome varchar(250),
    --
    primary key (idtiposvalencia)
)^
-- end TIPOSVALENCIA
-- begin FOTOS_VALENCIA
create table fotos_valencia (
    idfotosvalencia integer,
    --
    daequipacolaboradores boolean,
    descricao varchar(1000),
    doequipamento boolean,
    foto varchar(250),
    idvalencia uuid,
    --
    primary key (idfotosvalencia)
)^
-- end FOTOS_VALENCIA
-- begin PROJECTOS_EM_APROVACAO
create table projectos_em_aprovacao (
    idprojectosemaprovacao integer,
    --
    etapaprocesso varchar(45),
    idprojectosintervencao integer,
    tipologia varchar(45),
    --
    primary key (idprojectosemaprovacao)
)^
-- end PROJECTOS_EM_APROVACAO
-- begin LOGIN
create table login (
    ID integer,
    --
    instituicao_id integer,
    is_enabled integer not null,
    password varchar(20),
    username varchar(20) not null,
    --
    primary key (ID)
)^
-- end LOGIN
-- begin AJUDAS_TECNICAS
create table ajudas_tecnicas (
    idajudastecnicas integer,
    --
    datadisponivel timestamp,
    idvalencia uuid,
    localizacao varchar(250),
    tipoajuda varchar(250),
    --
    primary key (idajudastecnicas)
)^
-- end AJUDAS_TECNICAS
-- begin INSTITUICOES
create table instituicoes (
    idinstituicao integer,
    --
    cae varchar(45),
    clasolhao boolean,
    contactopresidentedireccao varchar(250),
    contactoresponsavelservico varchar(250),
    coordenadasgps varchar(45),
    cpcj boolean,
    descricao varchar(250),
    email varchar(250),
    fax varchar(45),
    moradacompleta varchar(250),
    naturezajuridica varchar(45),
    niss varchar(11),
    nrregistosegsocial varchar(45),
    outraredelocal boolean,
    plataformatematica boolean,
    presidentedireccao varchar(250),
    projectoscomunitarios boolean,
    quaisprojectoscomunitarios varchar(250),
    qualoutraredelocal varchar(250),
    responsavelservico varchar(250),
    rsi boolean,
    telefone varchar(45),
    url varchar(250),
    --
    primary key (idinstituicao)
)^
-- end INSTITUICOES
-- begin PESSOAL_AUXILIAR
create table pessoal_auxiliar (
    idpessoalauxiliar integer,
    --
    anosservicoinstituicao integer,
    categoriaprofissional varchar(250),
    formacaoprofissional boolean,
    habilitacoesliterarias varchar(250),
    idade integer,
    idvalencia uuid,
    sexomasculino boolean,
    tipovinculo varchar(250),
    --
    primary key (idpessoalauxiliar)
)^
-- end PESSOAL_AUXILIAR
-- begin HABITACAO_SOCIAL
create table habitacao_social (
    bloc_id integer,
    --
    arrend integer,
    bl text,
    cod integer,
    codbr integer,
    coord varchar(255),
    eqsocial integer,
    t0 integer,
    t1 integer,
    t2 integer,
    t3 integer,
    t4 integer,
    t5 integer,
    vend integer,
    --
    primary key (bloc_id)
)^
-- end HABITACAO_SOCIAL
-- begin LOCALIZACOES
create table localizacoes (
    idvalencias integer,
    --
    coord varchar(255),
    --
    primary key (idvalencias)
)^
-- end LOCALIZACOES
-- begin VALENCIAS
create table valencias (
    idvalencias uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    acordocapacidade integer,
    acordocomparticipacao decimal(12, 2),
    acordodatacelebracao timestamp,
    acordolistaespera integer,
    acordoutentesextrafeminino integer,
    acordoutentesextramasculino integer,
    acordoutentesfeminino integer,
    acordoutentesmasculino integer,
    acordovagasfeminino integer,
    acordovagasmasculino integer,
    actividadescultural boolean,
    actividadesdesportiva boolean,
    actividadeslazer boolean,
    actividadesoutras varchar(1000),
    actividadesrecreativas boolean,
    actividadessocial boolean,
    certauditoriaexternacurso boolean,
    certauditoriasinternascurso boolean,
    certdatainicio timestamp,
    certdataprevista timestamp,
    certificacaoqualidade boolean,
    contactodirectortecnico varchar(250),
    contratacaoexternajardinagem boolean,
    contratacaoexternalimpeza boolean,
    contratacaoexternaoutras varchar(250),
    contratacaoexternarefeicoes boolean,
    coordenadagps varchar(45),
    descricaotecnica varchar(250),
    directortecnico varchar(250),
    emal varchar(250),
    fax varchar(45),
    formacaoprevia varchar(250),
    horariofimsemanafimmanha time,
    horariofimsemanafimtarde time,
    horariofimsemanainiciomanha time,
    horariofimsemanainiciotarde time,
    horariosemanafimmanha time,
    horariosemanafimtarde time,
    horariosemanainiciomanha time,
    horariosemanainiciotarde time,
    idinstituicao integer,
    idtipovalencia integer,
    morada varchar(1000),
    nregsegsocial varchar(45),
    recursostecnicoscomplementares varchar(1000),
    servicosespecializados varchar(1000),
    telefone varchar(45),
    tipoacordosegsocial varchar(45),
    transporte boolean,
    transportecapacidade varchar(45),
    transporteproprio boolean,
    url varchar(250),
    utentesfuzeta integer,
    utentesmoncarapacho integer,
    utentesolhao integer,
    utentespechao integer,
    utentesquelfes integer,
    --
    primary key (idvalencias)
)^
-- end VALENCIAS
