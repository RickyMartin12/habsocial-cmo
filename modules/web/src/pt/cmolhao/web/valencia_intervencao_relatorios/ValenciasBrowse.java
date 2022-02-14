package pt.cmolhao.web.valencia_intervencao_relatorios;

import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.components.inputdialog.InputDialogAction;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.gui.ReportGuiManager;
import pt.cmolhao.entity.*;

import javax.inject.Inject;
import java.util.*;

@UiController("cmolhao_Valencias.browseRelatoriosIntervencao")
@UiDescriptor("valencias-browse.xml")
@LookupComponent("valenciasesTable")
@LoadDataBeforeShow
public class ValenciasBrowse extends StandardLookup<Valencias> {

    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected GroupTable<Valencias> valenciasesTable;
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected TextField<String> descricaotecnicaField;
    @Inject
    protected TextField<String> moradaField;
    @Inject
    protected LookupPickerField<Tiposvalencia> idtipovalenciaField;
    @Inject
    protected LookupPickerField<RespostaSocial> idResSocialField;
    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;
    @Inject
    protected LookupPickerField<Tecnico> idTecnicoField;
    @Inject
    protected TextField<String> numProcessoField;
    @Inject
    protected LookupPickerField<TipoAjuda> idTipoapoioField;
    @Inject
    protected LookupPickerField<TipoDocApoio> idTipoDocApoioField;
    @Inject
    protected TextField<String> tipoApoio;
    @Inject
    protected TextField<String> tipoDocumentoApoio;
    @Inject
    protected LookupPickerField<ProjectosIntervencao> idprojectosintervencaoField;
    @Inject
    protected CollectionLoader<Valencias> valenciasesDl;
    @Inject
    protected CollectionContainer<RespostaSocial> respostaSocialsDc;
    @Inject
    protected CollectionContainer<TipoDocApoio> tipoDocApoioDc;
    @Inject
    protected LookupPickerField<Utente> utenteField;
    @Inject
    protected TextField<String> inst;
    @Inject
    protected CollectionContainer<ProjectosIntervencao> ProjectosIntervencaoDc;
    @Inject
    protected TextField<String> rede_trabalho;
    @Inject
    protected TextField<String> utenteId;
    @Inject
    protected TextField<String> projectosId;
    @Inject
    protected TextField<String> tecnicoId;
    @Inject
    protected CollectionContainer<TipoAjuda> tipoAjudasDc;

    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    private Notifications notifications;

    @Inject
    private Dialogs dialogs;

    @Inject
    private DataManager dataManager;

    @Inject
    private DataService dataService;
    @Inject
    protected ReportGuiManager reportGuiManager = AppBeans.get(ReportGuiManager.class);





    @Subscribe
    public void onInit(InitEvent event) {

        valenciasesTable.addGeneratedColumn("imprimir_dados", entity -> {

            Button btn = uiComponents.create(Button.class);
            btn.setStyleName("button_middle_pedido_habitacao");
            //btn.setCaption("imprimir");
            btn.setDescription("Imprimir Valencias: " + entity.getId());
            btn.setIcon("font-icon:FILE_PDF_O");
            btn.setAction(new BaseAction("imprimir") {
                @Override
                public void actionPerform(Component component) {

                    runReportValencias(component);
                }
            });
            return btn;
        });
    }

    private void runReportValencias(Component component) {

        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("Valencia", valenciasesTable.getSingleSelected());
        LoadContext<Report> lContext = new LoadContext<>(Report.class);
        lContext.setQueryString("select r from report$Report r where r.id = 'f2d3ae7e-1bdd-4675-22b2-3b54b67b3195' ");
        Report report = dataService.load(lContext);

        String templateCode = "DEFAULT";

        reportGuiManager.printReport(report, reportParams, templateCode, null);



    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Relatórios de Valências");
        Map<String, ProjectosIntervencao> map = new HashMap<>();
        Collection<ProjectosIntervencao> customers = ProjectosIntervencaoDc.getItems();
        for (ProjectosIntervencao item : customers) {
            map.put(item.getNomeProjecto() + " ", item);
        }
        idprojectosintervencaoField.setOptionsMap(map);
    }


    public Component generateNomeMembrosdeRede(Valencias entity) {
        Label label = (Label) uiComponents.create(Label.NAME);

        String descricao_tecnica = "";
        if (entity.getIdinstituicao() != null)
        {
            if (entity.getIdinstituicao().getMembrosRedeTrabalhos() != null)
            {
                Set<MembrosRedeTrabalho> set = entity.getIdinstituicao().getMembrosRedeTrabalhos();

                descricao_tecnica += "\n";

                for (MembrosRedeTrabalho a : set) {

                    if (a.getIdRedeTrabalho() != null)
                    {
                        descricao_tecnica += a.getIdRedeTrabalho().getNome() + "\n\n";
                    }


                }

                label.setValue(descricao_tecnica);
                return label;


            }

        }
        return null;
    }

    public Component generateNomesTecnicos(Valencias entity) {
        Label label = (Label) uiComponents.create(Label.NAME);

        String nomes_tecnicos = "";

        if (entity.getIdinstituicao() != null)
        {
            if (entity.getIdinstituicao().getTecnicos() != null)
            {
                Set<Tecnico> tecnicos = entity.getIdinstituicao().getTecnicos();

                nomes_tecnicos += "\n";

                for (Tecnico a : tecnicos) {

                    if (a.getNome() != null && a.getEmail() != null)
                    {
                        nomes_tecnicos += a.getNome() + " (" + a.getEmail() +") " + "\n\n";
                    }
                    if (a.getNome() == null && a.getEmail() != null)
                    {
                        nomes_tecnicos += a.getEmail() + "\n\n";
                    }
                    if (a.getNome() != null && a.getEmail() == null)
                    {
                        nomes_tecnicos += a.getNome() + "\n\n";
                    }


                }

                label.setValue(nomes_tecnicos);
                return label;




            }
        }
        return null;
    }

    public Component generateApoiosNumProcesso(Valencias entity) {
        Label label = (Label) uiComponents.create(Label.NAME);

        String num_processo = "";

        if (entity.getIdinstituicao() != null)
        {
            if (entity.getIdinstituicao().getApoios() != null)
            {
                Set<Apoios> apoios = entity.getIdinstituicao().getApoios();

                num_processo += "\n";

                for (Apoios a : apoios) {
                    if (a.getNumProcesso() != null)
                    {
                        num_processo += a.getNumProcesso() + "\n\n";
                    }
                }

                label.setValue(num_processo);
                return label;
            }

        }

        return null;
    }

    public Component generateApoiosFicheiros(Valencias entity) {
        Label label = (Label) uiComponents.create(Label.NAME);

        String num_processo = "";

        if (entity.getIdinstituicao() != null)
        {
            if (entity.getIdinstituicao().getApoios() != null)
            {
                Set<Apoios> apoios = entity.getIdinstituicao().getApoios();

                num_processo += "\n";

                for (Apoios a : apoios) {
                    if (a.getFile() != null)
                    {
                        num_processo += a.getFile().getName() + "\n\n";
                    }
                }

                label.setValue(num_processo);
                return label;
            }

        }

        return null;
    }

    public Component generateTiposApoios(Valencias entity) {
        Label label = (Label) uiComponents.create(Label.NAME);

        String tipos_apoios = "";

        if (entity.getIdinstituicao() != null)
        {
            if (entity.getIdinstituicao().getApoios() != null)
            {
                Set<Apoios> apoios = entity.getIdinstituicao().getApoios();

                tipos_apoios += "\n";

                for (Apoios a : apoios) {
                    if (a.getIdTipoapoio() != null)
                    {
                        tipos_apoios += a.getIdTipoapoio().getDescricaoTipoAjuda() + "\n\n";
                    }
                }

                label.setValue(tipos_apoios);
                return label;
            }

        }

        return null;
    }

    public Component generateTiposDocumentosApoios(Valencias entity) {

        Label label = (Label) uiComponents.create(Label.NAME);

        String tipos_doc_apoios = "";

        if (entity.getIdinstituicao() != null)
        {
            if (entity.getIdinstituicao().getApoios() != null)
            {
                Set<Apoios> apoios = entity.getIdinstituicao().getApoios();

                tipos_doc_apoios += "\n";

                for (Apoios a : apoios) {
                    if (a.getIdTipoDocApoio() != null)
                    {
                        tipos_doc_apoios += a.getIdTipoDocApoio().getDescricao() + "\n\n";
                    }
                }

                label.setValue(tipos_doc_apoios);
                return label;
            }

        }




        return null;
    }

    public Component generateUtentes(Valencias entity) {

        Label label = (Label) uiComponents.create(Label.NAME);

        String utentes = "";

        if (entity.getIdinstituicao() != null)
        {
            if (entity.getIdinstituicao().getApoios() != null)
            {
               Set<Apoios> apoios = entity.getIdinstituicao().getApoios();

                utentes += "\n";

                for (Apoios a : apoios) {
                    if (a.getIdUtente() != null)
                    {
                        if (a.getIdUtente().getNome() != null && a.getIdUtente().getNumContribuinte() == null)
                        {
                            utentes += a.getIdUtente().getNome() + "\n\n";
                        }
                        if (a.getIdUtente().getNome() != null && a.getIdUtente().getNumContribuinte() != null)
                        {
                            utentes += "(" + a.getIdUtente().getNumContribuinte() +  ") " +a.getIdUtente().getNome() + "\n\n";
                        }
                        if (a.getIdUtente().getNome() == null && a.getIdUtente().getNumContribuinte() != null)
                        {
                            utentes += a.getIdUtente().getNumContribuinte() + "\n\n";
                        }
                    }
                }

                label.setValue(utentes);
                return label;


            }

        }
        return null;
    }

    public Component generateProjectos(Valencias entity) {
        Label label = (Label) uiComponents.create(Label.NAME);

        String projectos = "";

        if (entity.getIdinstituicao() != null) {
            if (entity.getIdinstituicao().getProjectosIntervencaos() != null) {

                Set<ProjectosIntervencao> projetos = entity.getIdinstituicao().getProjectosIntervencaos();

                projectos += "\n";

                for (ProjectosIntervencao a : projetos) {
                    if (a.getNomeProjecto() != null) {

                        projectos += a.getNomeProjecto() + "\n\n";

                    }
                }

                label.setValue(projectos);
                return label;

            }

        }

        return null;
    }

    @Subscribe("reset_valencia")
    protected void onReset_valenciaClick(Button.ClickEvent event) {

        // Valencia

        // ID instituição

        idInstituicaoField.setValue(null);
        valenciasesDl.removeParameter("idinstituicao");

        // Descrição da Valencia

        descricaotecnicaField.setValue(null);
        valenciasesDl.removeParameter("descricaotecnica");

        // Morada da Valencia

        moradaField.setValue(null);
        valenciasesDl.removeParameter("morada");

        // Area de Intervencao

        idtipovalenciaField.setValue(null);
        valenciasesDl.removeParameter("idtipovalencia");

        // Resposta Social

        idResSocialField.setValue(null);
        valenciasesDl.removeParameter("idResSocial");

        // Membros de Rede

        // ID Rede de Trabalho

        idRedeTrabalhoField.setValue(null);
        valenciasesDl.removeParameter("idRedeTrabalho");

        // Tecnicos

        // ID Tecnico

        idTecnicoField.setValue(null);
        valenciasesDl.removeParameter("idTecnico");

        // Apoios

        // Num processo

        // Numero de Processo

        numProcessoField.setValue(null);
        valenciasesDl.removeParameter("numProcesso");

        // Tipo de Apoio

        idTipoapoioField.setValue(null);
        valenciasesDl.removeParameter("idTipoapoio");

        // Tipo de Documento de Apoio

        idTipoDocApoioField.setValue(null);
        valenciasesDl.removeParameter("idTipoDocApoio");

        // Utente

        // Id do Utente

        utenteField.setValue(null);
        valenciasesDl.removeParameter("idUtente");

        // Projectos

        // ID projectos de intervencao

        idprojectosintervencaoField.setValue(null);
        valenciasesDl.removeParameter("idprojectosintervencao");


        inst.setValue(null);
        rede_trabalho.setValue(null);

        tipoApoio.setValue(null);
        tipoDocumentoApoio.setValue(null);

        utenteId.setValue(null);
        projectosId.setValue(null);

        tecnicoId.setValue(null);


        valenciasesDl.load();

        
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }


    @Subscribe("search_valencia")
    protected void onSearch_valenciaClick(Button.ClickEvent event) {

        // Apoio




        // ID do Instituição
        if (idInstituicaoField.getValue() != null) {
            valenciasesDl.setParameter("idinstituicao",  idInstituicaoField.getValue().getId());
        } else {
            valenciasesDl.removeParameter("idinstituicao");
        }

        // Descricao da Valencia
        if (descricaotecnicaField.getValue() != null) {
            valenciasesDl.setParameter("descricaotecnica", "(?i)%" + descricaotecnicaField.getValue() + "%");
        } else {
            valenciasesDl.removeParameter("descricaotecnica");
        }

        // Morada da Valencia
        if (moradaField.getValue() != null) {
            valenciasesDl.setParameter("morada", "(?i)%" + moradaField.getValue() + "%");
        } else {
            valenciasesDl.removeParameter("morada");
        }

        // Área de Intervenção

        if (idtipovalenciaField.getValue() != null)
        {
            valenciasesDl.setParameter("idtipovalencia", idtipovalenciaField.getValue().getId());
        }
        else
        {
            valenciasesDl.removeParameter("idtipovalencia");
        }

        // Resposta Social

        if (idResSocialField.getValue() != null)
        {
            valenciasesDl.setParameter("idResSocial", idResSocialField.getValue().getId());
        }
        else
        {
            valenciasesDl.removeParameter("idResSocial");
        }

        // Membros de Rede

        // ID Rede de Trabalho

        if (idRedeTrabalhoField.getValue() != null)
        {
            valenciasesDl.setParameter("idRedeTrabalho", idRedeTrabalhoField.getValue().getId());
        }
        else
        {
            valenciasesDl.removeParameter("idRedeTrabalho");
        }

        // Tecnicos

        // ID Tecnico

        if (idTecnicoField.getValue() != null)
        {
            valenciasesDl.setParameter("idTecnico", idTecnicoField.getValue().getId());
        }
        else
        {
            valenciasesDl.removeParameter("idTecnico");
        }

        // Apoios

        // Num processo

        // Numero de Processo
        if (numProcessoField.getValue() != null) {
            if (isNumeric(numProcessoField.getValue()))
            {
                valenciasesDl.setParameter("numProcesso", "(?i)%" + Integer.valueOf(numProcessoField.getValue()) + "%");
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do numero de processo</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }

        } else {
            valenciasesDl.removeParameter("numProcesso");
        }

        // Tipo de Apoio

        if (idTipoapoioField.getValue() != null)
        {
            valenciasesDl.setParameter("idTipoapoio", idTipoapoioField.getValue().getId());
        }
        else
        {
            valenciasesDl.removeParameter("idTipoapoio");
        }

        // Tipo de Documento de Apoio

        if (idTipoDocApoioField.getValue() != null)
        {
            valenciasesDl.setParameter("idTipoDocApoio", idTipoDocApoioField.getValue().getId());
        }
        else
        {
            valenciasesDl.removeParameter("idTipoDocApoio");
        }

        // Utente

        // Id do Utente

        if (utenteField.getValue() != null)
        {
            valenciasesDl.setParameter("idUtente", utenteField.getValue().getId());
        }
        else
        {
            valenciasesDl.removeParameter("idUtente");
        }

        // Projectos

        // ID projectos de intervencao

        if (idprojectosintervencaoField.getValue() != null)
        {
            valenciasesDl.setParameter("idprojectosintervencao", idprojectosintervencaoField.getValue().getId());
        }
        else
        {
            valenciasesDl.removeParameter("idprojectosintervencao");
        }






        valenciasesDl.load();
        
    }

    @Subscribe("idTipoapoioField")
    protected void onIdTipoapoioFieldValueChange(HasValue.ValueChangeEvent<TipoAjuda> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                Map<String, TipoDocApoio> map = new HashMap<>();
                Collection<TipoDocApoio> customers = tipoDocApoioDc.getItems();
                for (TipoDocApoio item : customers) {
                    if (item.getIdTipoApoio().getId().equals(event.getValue().getId()))
                    {
                        map.put(item.getDescricao(), item);
                    }
                }

                idTipoDocApoioField.setEditable(true);
                idTipoDocApoioField.setValue(null);
                idTipoDocApoioField.setOptionsMap(map);


            }
        }
    }

    @Subscribe("idtipovalenciaField")
    protected void onIdtipovalenciaFieldValueChange(HasValue.ValueChangeEvent<Tiposvalencia> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                Map<String, RespostaSocial> map = new HashMap<>();
                Collection<RespostaSocial> customers = respostaSocialsDc.getItems();
                for (RespostaSocial item : customers) {
                    if (item.getIdTipoValencia().getId().equals(event.getValue().getId()))
                    {
                        map.put(item.getNome(), item);
                    }
                }
                idResSocialField.setEditable(true);
                idResSocialField.setValue(null);
                idResSocialField.setOptionsMap(map);

            }
        }
    }

    @Subscribe("report")
    protected void onReportClick(Button.ClickEvent event) {
        runExcelDataValencias();
    }

    private void runExcelDataValencias() {
        Map<String, Object> reportParams = new HashMap<>();

        // Parametros

        // --- Valencias ---

        // Descrição da Valênçia

        if (descricaotecnicaField.getValue() != null)
        {
            reportParams.put("descr", descricaotecnicaField.getValue());
        }
        else
        {
            reportParams.put("descr", "-/-");
        }

        // Instituição

        if (idInstituicaoField.getValue() != null)
        {
            reportParams.put("id_instituicao", idInstituicaoField.getValue().getId());
        }



        // Morada da Valência

        if (moradaField.getValue() != null)
        {
            reportParams.put("morada", moradaField.getValue());
        }
        else
        {
            reportParams.put("morada", "-/-");
        }

        // Área de Intervenção

        if (idtipovalenciaField.getValue() != null)
        {
            reportParams.put("area_intervencao", idtipovalenciaField.getValue().getId());
        }

        // Resposta Social

        if (idResSocialField.getValue() != null)
        {
            reportParams.put("resposta_social", idResSocialField.getValue().getId());
        }

        // --- Membros de Rede ---

        // Rede de Trabalho

        if (idRedeTrabalhoField.getValue() != null)
        {
            reportParams.put("id_membro_rede", idRedeTrabalhoField.getValue().getId());
        }

        // --- Tecnicos ---

        // Nome do Tecnico

        if (idTecnicoField.getValue() != null)
        {
            reportParams.put("tecnico_id", idTecnicoField.getValue().getId());
        }

        // --- Apoios ---

        // Numero de Processo de Apoio

        if (numProcessoField.getValue() != null)
        {
            reportParams.put("num_proc", numProcessoField.getValue());
        }
        else
        {
            reportParams.put("num_proc", "-/-");
        }

        // Tipo de Apoio

        if (idTipoapoioField.getValue() != null)
        {
            reportParams.put("tipo_apoio",idTipoapoioField.getValue().getId());
        }

        // Tipo Documento de Apoio

        if (idTipoDocApoioField.getValue() != null)
        {
            reportParams.put("tipo_documento_apoio",idTipoDocApoioField.getValue().getId());
        }

        // --- Utente ---

        // ID do Utente

        if (utenteField.getValue() != null)
        {
            reportParams.put("utente_id",utenteField.getValue().getId());
        }

        // --- Projectos ---

        // ID do Projecto

        if (idprojectosintervencaoField.getValue() != null)
        {
            reportParams.put("projetos",idprojectosintervencaoField.getValue().getId());
        }


        if (inst.getValue() != null)
        {
            reportParams.put("id_instituicao", Integer.valueOf(inst.getValue() ));
        }

        if (rede_trabalho.getValue() != null)
        {
            reportParams.put("id_membro_rede", Integer.valueOf(rede_trabalho.getValue() ));
        }

        if (tipoApoio.getValue() != null)
        {
            reportParams.put("tipo_apoio",Integer.valueOf(tipoApoio.getValue() ));
        }

        if (tipoDocumentoApoio.getValue() != null)
        {
            reportParams.put("tipo_documento_apoio",Integer.valueOf(tipoDocumentoApoio.getValue() ));
        }

        if (utenteId.getValue() != null)
        {
            reportParams.put("utente_id",Integer.valueOf(utenteId.getValue() ));
        }

        if(projectosId.getValue() != null)
        {
            reportParams.put("projetos",Integer.valueOf(projectosId.getValue() ));
        }

        if(tecnicoId.getValue() != null)
        {
            reportParams.put("tecnico_id",Integer.valueOf(tecnicoId.getValue() ));
        }




        LoadContext<Report> lContext = new LoadContext<>(Report.class);
        lContext.setQueryString("select r from report$Report r where r.id = '97d6f9e3-b13e-7417-2d5b-a43140ec32dc' ");
        Report report = dataService.load(lContext);

        String templateCode = "valencias_dados";

        reportGuiManager.printReport(report, reportParams, templateCode, null);








    }

    @Subscribe("popupButton1.popupAction1")
    protected void onPopupButton1PopupAction1(Action.ActionPerformedEvent event) {
        searchCapacidadeInstituicao();
    }


    private void searchCapacidadeInstituicao() {
        LookupField<Instituicoes> institucoes = uiComponents.create(
                LookupField.of(Instituicoes.class));
        dialogs.createInputDialog(this)
                .withCaption("Dados da capacidade de pessoas de uma instituicao")
                .withParameters(
                        InputParameter.parameter("idinstituicao")
                                .withField(() -> {

                                    institucoes.setOptionsList(dataManager.load(Instituicoes.class).list());
                                    institucoes.setCaption("Instituição: ");
                                    institucoes.setId("idInstituicaoField");
                                    institucoes.setWidthFull();

                                    return institucoes;

                                })
                )
                .withValidator(context -> {
                    Instituicoes instituicoes = context.getValue("idinstituicao");
                    if (instituicoes == null)
                    {
                        return ValidationErrors.of("Deve introduzir a descrição da instituição");
                    }
                    return ValidationErrors.none();
                })
                .withActions(
                        InputDialogAction.action("pesquisar")
                                .withCaption("Pesquisar")
                                .withPrimary(false)
                                .withIcon("font-icon:SEARCH")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();


                                    Instituicoes instituicoes = dialog.getValue("idinstituicao");

                                    if (instituicoes != null)
                                    {
                                        valenciasesDl.setParameter("idinstituicao", instituicoes.getId());
                                        inst.setValue(Integer.toString(instituicoes.getId()));
                                    }
                                    else
                                    {
                                        valenciasesDl.removeParameter("idinstituicao");
                                    }


                                    valenciasesDl.load();







                                    //apoiosesDl.load();


                                    dialog.closeWithDefaultAction();




                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();

                                    Instituicoes instituicoes = dialog.getValue("idinstituicao");


                                    dialog.closeWithDefaultAction();


                                    reportDataCapacidadeInstituicao(instituicoes);



                                }),
                        InputDialogAction.action("cancelar")
                                .withCaption("Cancelar")
                                .withIcon("font-icon:TIMES_CIRCLE")
                                .withValidationRequired(false)
                                .withHandler(actionEvent ->
                                        actionEvent.getInputDialog().closeWithDefaultAction())
                )
                .show();
    }

    private void reportDataCapacidadeInstituicao(Instituicoes instituicoes) {

        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        if (instituicoes != null)
        {
            reportParams.put("instituicao_id", instituicoes.getId());
        }


        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Estatistica_utente_instituicao_valencia";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);


        if (instituicoes != null)
        {
            outputFile = "Contagem da Capacidade de Elementos da Instituição  '"+instituicoes.getDescricao()+"'";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);

    }

    @Subscribe("popupButton1.popupAction2")
    protected void onPopupButton1PopupAction2(Action.ActionPerformedEvent event) {
        searchCapacidadeInstituicaoAreaIntervencao();
    }

    private void searchCapacidadeInstituicaoAreaIntervencao() {
        LookupField<Instituicoes> institucoes = uiComponents.create(
                LookupField.of(Instituicoes.class));
        dialogs.createInputDialog(this)
                .withCaption("Dados da capacidade de pessoas de uma instituicao numa dada área de intervenção")
                .withParameters(
                        InputParameter.parameter("idinstituicao")
                                .withField(() -> {

                                    institucoes.setOptionsList(dataManager.load(Instituicoes.class).list());
                                    institucoes.setCaption("Instituição: ");
                                    institucoes.setId("idInstituicaoField");
                                    institucoes.setWidthFull();

                                    return institucoes;

                                })
                )
                .withValidator(context -> {
                    Instituicoes instituicoes = context.getValue("idinstituicao");
                    if (instituicoes == null)
                    {
                        return ValidationErrors.of("Deve introduzir a descrição da instituição");
                    }
                    return ValidationErrors.none();
                })
                .withActions(
                        InputDialogAction.action("pesquisar")
                                .withCaption("Pesquisar")
                                .withPrimary(false)
                                .withIcon("font-icon:SEARCH")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();


                                    Instituicoes instituicoes = dialog.getValue("idinstituicao");

                                    if (instituicoes != null)
                                    {
                                        valenciasesDl.setParameter("idinstituicao", instituicoes.getId());
                                        inst.setValue(Integer.toString(instituicoes.getId()));
                                    }
                                    else
                                    {
                                        valenciasesDl.removeParameter("idinstituicao");
                                    }


                                    valenciasesDl.load();







                                    //apoiosesDl.load();


                                    dialog.closeWithDefaultAction();




                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();

                                    Instituicoes instituicoes = dialog.getValue("idinstituicao");


                                    dialog.closeWithDefaultAction();


                                    reportDataCapacidadeInstituicaoAreaIntervencao(instituicoes);



                                }),
                        InputDialogAction.action("cancelar")
                                .withCaption("Cancelar")
                                .withIcon("font-icon:TIMES_CIRCLE")
                                .withValidationRequired(false)
                                .withHandler(actionEvent ->
                                        actionEvent.getInputDialog().closeWithDefaultAction())
                )
                .show();
    }

    private void reportDataCapacidadeInstituicaoAreaIntervencao(Instituicoes instituicoes) {
        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        if (instituicoes != null)
        {
            reportParams.put("instituicao_id", instituicoes.getId());
        }


        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Estatistica_Valencia_utentes_capacidade_utentes";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);


        if (instituicoes != null)
        {
            outputFile = "Contagem da Capacidade de Elementos da Instituição  '"+instituicoes.getDescricao()+"' nas áreas de intervenção";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);
    }


    private void searchCapacidadeRedeTrabalhoValencia() {
        LookupField<RedeTrabalho> rede_trabalho_id = uiComponents.create(
                LookupField.of(RedeTrabalho.class));

        dialogs.createInputDialog(this)
                .withCaption("Dados da capacidade de pessoas da rede de trabalho numa dada descrição da valênçia")
                .withParameters(
                        InputParameter.parameter("idRedeTrabalho")
                                .withField(() -> {

                                    rede_trabalho_id.setOptionsList(dataManager.load(RedeTrabalho.class).list());
                                    rede_trabalho_id.setCaption("Rede de Trabalho: ");
                                    rede_trabalho_id.setId("idRedeTrabalhoField");
                                    rede_trabalho_id.setWidthFull();

                                    return rede_trabalho_id;

                                })
                )
                .withValidator(context -> {
                    RedeTrabalho redetrabalho = context.getValue("idRedeTrabalho");
                    if (redetrabalho == null)
                    {
                        return ValidationErrors.of("Deve introduzir o nome da rede de trabalho");
                    }
                    return ValidationErrors.none();
                })
                .withActions(
                        InputDialogAction.action("pesquisar")
                                .withCaption("Pesquisar")
                                .withPrimary(false)
                                .withIcon("font-icon:SEARCH")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();

                                    RedeTrabalho redetrabalho = dialog.getValue("idRedeTrabalho");


                                    if (redetrabalho != null)
                                    {
                                        valenciasesDl.setParameter("idRedeTrabalho", redetrabalho.getId());
                                        rede_trabalho.setValue(Integer.toString(redetrabalho.getId()));
                                    }
                                    else
                                    {
                                        valenciasesDl.removeParameter("idRedeTrabalho");
                                    }


                                    valenciasesDl.load();







                                    //apoiosesDl.load();


                                    dialog.closeWithDefaultAction();




                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();

                                    RedeTrabalho redetrabalho = dialog.getValue("idRedeTrabalho");


                                    dialog.closeWithDefaultAction();


                                    reportDataCapacidadeValenciasPorMembrosRede(redetrabalho);



                                }),
                        InputDialogAction.action("cancelar")
                                .withCaption("Cancelar")
                                .withIcon("font-icon:TIMES_CIRCLE")
                                .withValidationRequired(false)
                                .withHandler(actionEvent ->
                                        actionEvent.getInputDialog().closeWithDefaultAction())
                )
                .show();
    }

    private void reportDataCapacidadeValenciasPorMembrosRede(RedeTrabalho redetrabalho) {
        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        if (redetrabalho != null)
        {
            reportParams.put("rede_trabalho", redetrabalho.getId());
        }


        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Estatistica_valencia_rede_trabalho_instituicao";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);


        if (redetrabalho != null)
        {
            outputFile = "Contagem da Capacidade de Elementos da Rede de Trabalho '"+redetrabalho.getNome()+"' nas descrições das valências";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);
    }

    @Subscribe("popupButton1.popupAction3")
    protected void onPopupButton1PopupAction3(Action.ActionPerformedEvent event) {

        searchCapacidadeRedeTrabalhoValencia();
        
    }

    @Subscribe("popupButton1.popupAction4")
    protected void onPopupButton1PopupAction4(Action.ActionPerformedEvent event) {
        searchValenciasUtentesporApoio();
    }

    private void searchValenciasUtentesporApoio() {
        LookupField<TipoDocApoio> field2 = uiComponents.create(
                LookupField.of(TipoDocApoio.class));
        LookupField<TipoAjuda> field = uiComponents.create(
                LookupField.of(TipoAjuda.class));

        dialogs.createInputDialog(this)
                .withCaption("Dados do apoio em relacao ao numero de utentes")
                .withParameters(
                        InputParameter.parameter("idTipoapoio")
                                .withField(() -> {

                                    field.setOptionsList(dataManager.load(TipoAjuda.class).list());
                                    field.setCaption("Tipo de Apoio: ");
                                    field.setId("idTipoapoioField");
                                    field.setWidthFull();
                                    field.addValueChangeListener(e -> {

                                        if (e.isUserOriginated()) {
                                            if (e.getValue() != null) {
                                                Map<String, TipoDocApoio> map = new HashMap<>();
                                                Collection<TipoDocApoio> customers = tipoDocApoioDc.getItems();
                                                for (TipoDocApoio item : customers) {
                                                    if (item.getIdTipoApoio().getId().equals(e.getValue().getId()))
                                                    {
                                                        map.put(item.getDescricao(), item);
                                                    }
                                                }

                                                field2.setEditable(true);
                                                field2.setValue(null);
                                                field2.setOptionsMap(map);


                                            }
                                            else
                                            {
                                                field2.setEditable(false);
                                            }
                                        }
                                    });
                                    return field;

                                }),
                        InputParameter.parameter("idTipoDocApoio")
                                .withField(() -> {

                                    field2.setOptionsList(dataManager.load(TipoDocApoio.class).list());
                                    field2.setCaption("Tipo de Documento de Apoio: ");
                                    field2.setWidthFull();
                                    field2.setId("idTipoDocApoioField");
                                    field2.setEditable(false);
                                    return field2;

                                })
                )
                .withValidator(context -> {
                    TipoAjuda tipo_apoio = context.getValue("idTipoapoio");
                    TipoDocApoio tipo_doc_apoio =  context.getValue("idTipoDocApoio");
                    if (tipo_apoio == null)
                    {
                        return ValidationErrors.of("Deve introduzir o tipo de apoio");
                    }
                    return ValidationErrors.none();
                })
                .withActions(
                        InputDialogAction.action("pesquisar")
                                .withCaption("Pesquisar")
                                .withPrimary(false)
                                .withIcon("font-icon:SEARCH")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();


                                    TipoAjuda tipo_apoio = dialog.getValue("idTipoapoio");
                                    TipoDocApoio tipo_doc_apoio =  dialog.getValue("idTipoDocApoio");

                                    if (tipo_apoio != null)
                                    {
                                        valenciasesDl.setParameter("idTipoapoio", tipo_apoio.getId());
                                        tipoApoio.setValue(Integer.toString(tipo_apoio.getId()));
                                    }
                                    else
                                    {
                                        valenciasesDl.removeParameter("idTipoapoio");
                                    }

                                    //Tipo de Documento de Apoio

                                    if (tipo_doc_apoio != null)
                                    {
                                        valenciasesDl.setParameter("idTipoDocApoio", tipo_doc_apoio.getId());
                                        tipoDocumentoApoio.setValue(Integer.toString(tipo_doc_apoio.getId()));
                                    }
                                    else
                                    {
                                        valenciasesDl.removeParameter("idTipoDocApoio");
                                    }

                                    valenciasesDl.load();







                                    //apoiosesDl.load();


                                    dialog.closeWithDefaultAction();




                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();
                                    TipoAjuda tipo_apoio = dialog.getValue("idTipoapoio");
                                    TipoDocApoio tipo_doc_apoio =  dialog.getValue("idTipoDocApoio");
                                    dialog.closeWithDefaultAction();


                                    reportDataUtentesApoios(tipo_apoio, tipo_doc_apoio);



                                }),
                        InputDialogAction.action("cancelar")
                                .withCaption("Cancelar")
                                .withIcon("font-icon:TIMES_CIRCLE")
                                .withValidationRequired(false)
                                .withHandler(actionEvent ->
                                        actionEvent.getInputDialog().closeWithDefaultAction())
                )
                .show();
    }

    private void reportDataUtentesApoios(TipoAjuda tipo_apoio, TipoDocApoio tipo_doc_apoio) {

        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        if (tipo_apoio != null)
        {
            reportParams.put("tipo_apoio_id", tipo_apoio.getId());
        }

        if (tipo_doc_apoio != null)
        {
            reportParams.put("tipo_doc_apoio_id", tipo_doc_apoio.getId());
        }

        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Estatistica_Valencia_Apoios_Utentes";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);



        if (tipo_apoio != null && tipo_doc_apoio == null)
        {
            outputFile = "Contagem do numero de utentes do Tipo de Apoio  '"+tipo_apoio.getDescricaoTipoAjuda()+"'";
        }
        if (tipo_apoio != null && tipo_doc_apoio != null)
        {
            outputFile = "Contagem do numero de utentes do Tipo de Apoio  '"+tipo_apoio.getDescricaoTipoAjuda()+"' e do tipo de documento '"+tipo_doc_apoio.getDescricao()+"'";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);

    }

    @Subscribe("popupButton1.popupAction5")
    protected void onPopupButton1PopupAction5(Action.ActionPerformedEvent event) {
        searchValenciasApoiosUtente();
    }

    private void searchValenciasApoiosUtente() {
        LookupField<Utente> utente_id = uiComponents.create(
                LookupField.of(Utente.class));

        dialogs.createInputDialog(this)
                .withCaption("Dados do utente em relacao ao numero de apoios")
                .withParameters(
                        InputParameter.parameter("idUtente")
                                .withField(() -> {

                                    utente_id.setOptionsList(dataManager.load(Utente.class).list());
                                    utente_id.setCaption("Utente: ");
                                    utente_id.setId("utenteField");
                                    utente_id.setWidthFull();

                                    return utente_id;

                                })
                )
                .withValidator(context -> {
                    Utente utente = context.getValue("idUtente");
                    if (utente == null)
                    {
                        return ValidationErrors.of("Deve introduzir o nome do utente");
                    }
                    return ValidationErrors.none();
                })
                .withActions(
                        InputDialogAction.action("pesquisar")
                                .withCaption("Pesquisar")
                                .withPrimary(false)
                                .withIcon("font-icon:SEARCH")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();

                                    Utente utente = dialog.getValue("idUtente");


                                    if (utente != null)
                                    {
                                        valenciasesDl.setParameter("idUtente", utente.getId());
                                        utenteId.setValue(Integer.toString(utente.getId()));
                                    }
                                    else
                                    {
                                        valenciasesDl.removeParameter("idUtente");
                                    }


                                    valenciasesDl.load();







                                    //apoiosesDl.load();


                                    dialog.closeWithDefaultAction();




                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();

                                    Utente utente = dialog.getValue("idUtente");


                                    dialog.closeWithDefaultAction();


                                    reportDataApoiosporUtentes(utente);



                                }),
                        InputDialogAction.action("cancelar")
                                .withCaption("Cancelar")
                                .withIcon("font-icon:TIMES_CIRCLE")
                                .withValidationRequired(false)
                                .withHandler(actionEvent ->
                                        actionEvent.getInputDialog().closeWithDefaultAction())
                )
                .show();
    }

    private void reportDataApoiosporUtentes(Utente utente) {

        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        if (utente != null)
        {
            reportParams.put("utente_id", utente.getId());
        }


        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Estatistica_Utentes_Apoio";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);


        if (utente != null)
        {
            outputFile = "Contagem dos tipos de apoio do utente '"+utente.getNome()+"' ";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);


    }

    @Subscribe("popupButton1.popupAction6")
    protected void onPopupButton1PopupAction6(Action.ActionPerformedEvent event) {
        searchProjetosValencias();
    }

    private void searchProjetosValencias() {
        LookupField<ProjectosIntervencao> projectos_int_id = uiComponents.create(
                LookupField.of(ProjectosIntervencao.class));



        dialogs.createInputDialog(this)
                .withCaption("Dados dos projectos de intervenção de uma dada valênçia")
                .withParameters(
                        InputParameter.parameter("idprojectosintervencao")
                                .withField(() -> {

                                    projectos_int_id.setOptionsList(dataManager.load(ProjectosIntervencao.class).list());
                                    projectos_int_id.setCaption("Projetos de Intervenção: ");
                                    projectos_int_id.setId("idprojectosintervencaoField");
                                    projectos_int_id.setWidthFull();
                                    Map<String, ProjectosIntervencao> map2 = new HashMap<>();
                                    Collection<ProjectosIntervencao> customers2 = ProjectosIntervencaoDc.getItems();
                                    for (ProjectosIntervencao item2 : customers2) {
                                        if (item2 != null && item2.getNomeProjecto() != null )
                                        {
                                            map2.put(item2.getNomeProjecto() + " ", item2);
                                        }

                                    }
                                    projectos_int_id.setOptionsMap(map2);



                                    return projectos_int_id;

                                })
                )
                .withValidator(context -> {
                    ProjectosIntervencao projetosintid = context.getValue("idprojectosintervencao");
                    if (projetosintid == null)
                    {
                        return ValidationErrors.of("Deve seleccionador um dos projetos de intervenção");
                    }
                    return ValidationErrors.none();
                })
                .withActions(
                        InputDialogAction.action("pesquisar")
                                .withCaption("Pesquisar")
                                .withPrimary(false)
                                .withIcon("font-icon:SEARCH")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();

                                    ProjectosIntervencao projetosintid = dialog.getValue("idprojectosintervencao");


                                    if (projetosintid != null)
                                    {
                                        valenciasesDl.setParameter("idprojectosintervencao", projetosintid.getId());
                                        projectosId.setValue(Integer.toString(projetosintid.getId()));
                                    }
                                    else
                                    {
                                        valenciasesDl.removeParameter("idprojectosintervencao");
                                    }


                                    valenciasesDl.load();







                                    //apoiosesDl.load();


                                    dialog.closeWithDefaultAction();




                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();

                                    ProjectosIntervencao projetosintid = dialog.getValue("idprojectosintervencao");


                                    dialog.closeWithDefaultAction();


                                    reportDataProjetosValencias(projetosintid);



                                }),
                        InputDialogAction.action("cancelar")
                                .withCaption("Cancelar")
                                .withIcon("font-icon:TIMES_CIRCLE")
                                .withValidationRequired(false)
                                .withHandler(actionEvent ->
                                        actionEvent.getInputDialog().closeWithDefaultAction())
                )
                .show();
    }

    private void reportDataProjetosValencias(ProjectosIntervencao projetosintid) {

        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        if (projetosintid != null)
        {
            reportParams.put("projeto_id", projetosintid.getId());
        }


        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Estatistica_Projectos_Valencia";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);


        if (projetosintid != null)
        {
            outputFile = "Contagem das valênçias do projecto de intervencao '"+projetosintid.getNomeProjecto()+"' ";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);

    }

    @Subscribe("popupButton1.popupAction7")
    protected void onPopupButton1PopupAction7(Action.ActionPerformedEvent event) {
        searchTecnicosDescricaoValencias();
    }

    private void searchTecnicosDescricaoValencias() {
        LookupField<Tecnico> tecnico_id = uiComponents.create(
                LookupField.of(Tecnico.class));



        dialogs.createInputDialog(this)
                .withCaption("Dados dos projectos de intervenção de uma dada valênçia")
                .withParameters(
                        InputParameter.parameter("idTecnico")
                                .withField(() -> {

                                    tecnico_id.setOptionsList(dataManager.load(Tecnico.class).list());
                                    tecnico_id.setCaption("Técnico: ");
                                    tecnico_id.setId("idTecnicoField");
                                    tecnico_id.setWidthFull();




                                    return tecnico_id;

                                })
                )
                .withValidator(context -> {
                    Tecnico tecnicoid = context.getValue("idTecnico");
                    if (tecnicoid == null)
                    {
                        return ValidationErrors.of("Deve seleccionador um dos Técnicos");
                    }
                    return ValidationErrors.none();
                })
                .withActions(
                        InputDialogAction.action("pesquisar")
                                .withCaption("Pesquisar")
                                .withPrimary(false)
                                .withIcon("font-icon:SEARCH")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();

                                    Tecnico tecnicoid  = dialog.getValue("idTecnico");


                                    if (tecnicoid != null)
                                    {
                                        valenciasesDl.setParameter("idTecnico", tecnicoid.getId());
                                        tecnicoId.setValue(Integer.toString(tecnicoid.getId()));
                                    }
                                    else
                                    {
                                        valenciasesDl.removeParameter("idTecnico");
                                    }


                                    valenciasesDl.load();







                                    //apoiosesDl.load();


                                    dialog.closeWithDefaultAction();




                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();

                                    Tecnico tecnicoid  = dialog.getValue("idTecnico");


                                    dialog.closeWithDefaultAction();


                                    reportDataTecnicosValencias(tecnicoid);



                                }),
                        InputDialogAction.action("cancelar")
                                .withCaption("Cancelar")
                                .withIcon("font-icon:TIMES_CIRCLE")
                                .withValidationRequired(false)
                                .withHandler(actionEvent ->
                                        actionEvent.getInputDialog().closeWithDefaultAction())
                )
                .show();
    }

    private void reportDataTecnicosValencias(Tecnico tecnicoid) {
        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        if (tecnicoid != null)
        {
            reportParams.put("tecnico_id", tecnicoid.getId());
        }


        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Estatistica_Tecnicos_Valencias";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);


        if (tecnicoid != null)
        {
            outputFile = "Contagem das valênçias do tecnico '"+ tecnicoid.getNome()+ "'";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);
    }


}