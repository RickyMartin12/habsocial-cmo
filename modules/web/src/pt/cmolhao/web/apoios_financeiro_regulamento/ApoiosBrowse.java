package pt.cmolhao.web.apoios_financeiro_regulamento;

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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@UiController("cmolhao_Apoios.browseApoiosFinanceirosRegulamentos")
@UiDescriptor("apoios-browse.xml")
@LookupComponent("apoiosesTable")
@LoadDataBeforeShow
public class ApoiosBrowse extends StandardLookup<Apoios> {
    @Inject
    protected CollectionLoader<Apoios> apoiosesDl;
    @Inject
    protected CollectionContainer<Apoios> apoiosesDc;
    @Inject
    protected CollectionContainer<Tiposvalencia> TipoValenciaDc;
    @Inject
    protected CollectionContainer<RespostaSocial> respostaSocialsDc;
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected TextField<String> numProcessoField;
    @Inject
    protected LookupPickerField<Estados> idEstadosField;
    @Inject
    protected TextField<String> tipoApoio;
    @Inject
    protected TextField<String> tipoDocumentoApoio;
    @Inject
    protected LookupPickerField<Utente> utenteField;
    @Inject
    protected LookupPickerField<TipoCartao> idTipoCartaoField;
    @Inject
    protected LookupPickerField<HabilitacoesLiterarias> habilitacoesField;
    @Inject
    protected LookupPickerField<Tecnico> idTecnicoField;
    @Inject
    protected LookupPickerField<Valencias> idvalenciaField;
    @Inject
    protected LookupPickerField<Tiposvalencia> idtipovalenciaField;
    @Inject
    protected GroupTable<Apoios> apoiosesTable;
    @Inject
    protected LookupPickerField<RespostaSocial> idResSocialField;
    @Inject
    protected CollectionContainer<Valencias> valenciasDc;

    @Inject
    private DataService dataService;
    @Inject
    protected ReportGuiManager reportGuiManager = AppBeans.get(ReportGuiManager.class);


    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    private Notifications notifications;

    @Inject
    private Dialogs dialogs;

    @Inject
    private DataManager dataManager;

    @Inject
    protected UiComponents uiComponents;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("List Rel do Apoio 'Apoio Financeiro ás Entidades' do documento 'Regulamentos'");
        Map<String, Valencias> map_valencias = new HashMap<>();
        Collection<Valencias> customers_valencias = valenciasDc.getItems();
        for (Valencias item : customers_valencias) {
            map_valencias.put(item.getDescricaotecnica() + " ", item);
        }
        idvalenciaField.setOptionsMap(map_valencias);

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



    @Subscribe
    public void onInit(InitEvent event) {

        apoiosesTable.addGeneratedColumn("imprimir_apoios", entity -> {

            Button btn = uiComponents.create(Button.class);
            btn.setStyleName("button_middle_pedido_habitacao");
            //btn.setCaption("imprimir");
            btn.setDescription("Imprimir Apoios: " + entity.getId());
            btn.setIcon("font-icon:FILE_PDF_O");
            btn.setAction(new BaseAction("imprimir") {
                @Override
                public void actionPerform(Component component) {

                    runReportApoio(component);
                }
            });
            return btn;
        });

        apoiosesTable.addGeneratedColumn("file", entity -> {
            if(entity.getFile() != null) {
                Button btn = uiComponents.create(Button.class);
                btn.setCaption(entity.getFile().getName());
                btn.addStyleName("download_documentos");
                String ext = entity.getFile().getExtension();
                btn.setIcon("font-icon:FILE_O");

                if (ext.equals("pdf")) {
                    btn.setIcon("font-icon:FILE_PDF_O");
                }
                if (ext.equals("docx") || ext.equals("doc")) {
                    btn.setIcon("font-icon:FILE_WORD_O");
                }
                if (ext.equals("webm") || ext.equals("mp4") || ext.equals("mpg") || ext.equals("ogg") || ext.equals("avi") || ext.equals("mov")) {
                    btn.setIcon("font-icon:FILE_VIDEO_O");
                }
                if (ext.equals("wav") || ext.equals("mp3")) {
                    btn.setIcon("font-icon:FILE_SOUND_O");
                }
                if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("gif")) {
                    btn.setIcon("font-icon:FILE_PICTURE_O");
                }
                if (ext.equals("rar") || ext.equals("zip")) {
                    btn.setIcon("font-icon:FILE_ZIP_O");
                }
                if (ext.equals("xlsx") || ext.equals("xls") || ext.equals("csv")) {
                    btn.setIcon("font-icon:FILE_EXCEL_O");
                }
                if (ext.equals("pptx") || ext.equals("ppt")) {
                    btn.setIcon("font-icon:FILE_POWERPOINT_O");
                }
                btn.setAction(new BaseAction("download") {
                    @Override
                    public void actionPerform(Component component) {
                        FileDescriptor imageFile = entity.getFile();
                        exportDisplay.show(imageFile, ExportFormat.OCTET_STREAM);

                    }
                });
                return btn;
            }
            else
            {
                return null;
            }
        });

    }

    private void runReportApoio(Component component) {
        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("Apoios", apoiosesTable.getSingleSelected());
        LoadContext<Report> lContext = new LoadContext<>(Report.class);
        lContext.setQueryString("select r from report$Report r where r.id = 'ae3893e1-adf4-9284-1fcd-d9910190de33' ");
        Report report = dataService.load(lContext);

        String templateCode = "DEFAULT";

        reportGuiManager.printReport(report, reportParams, templateCode, null);
    }

    @Subscribe("reset_apoios")
    protected void onReset_apoiosClick(Button.ClickEvent event) {
        idInstituicaoField.setValue(null);
        numProcessoField.setValue(null);
        idEstadosField.setValue(null);
        utenteField.setValue(null);
        idTipoCartaoField.setValue(null);
        habilitacoesField.setValue(null);
        idTecnicoField.setValue(null);
        idvalenciaField.setValue(null);
        idtipovalenciaField.setValue(null);
        idResSocialField.setValue(null);
        idResSocialField.setEditable(false);

        apoiosesDl.removeParameter("idInstituicao");
        apoiosesDl.removeParameter("numProcesso");
        apoiosesDl.removeParameter("idEstado");
        apoiosesDl.removeParameter("idUtente");
        apoiosesDl.removeParameter("idTipoCartao");
        apoiosesDl.removeParameter("habilitacoes");
        apoiosesDl.removeParameter("idTecnico");
        apoiosesDl.removeParameter("idvalencia");
        apoiosesDl.removeParameter("idtipovalencia");
        apoiosesDl.removeParameter("idResSocial");

        apoiosesDl.load();
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe("search_apoios")
    protected void onSearch_apoiosClick(Button.ClickEvent event) {
        // Apoio


        // ID do Instituição
        if (idInstituicaoField.getValue() != null) {
            apoiosesDl.setParameter("idInstituicao",  idInstituicaoField.getValue().getId());
        } else {
            apoiosesDl.removeParameter("idInstituicao");
        }

        // Numero de Processo
        if (numProcessoField.getValue() != null) {
            if (isNumeric(numProcessoField.getValue()))
            {
                apoiosesDl.setParameter("numProcesso", "(?i)%" + Integer.valueOf(numProcessoField.getValue()) + "%");
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
            apoiosesDl.removeParameter("numProcesso");
        }


        // Estado

        if (idEstadosField.getValue() != null)
        {
            apoiosesDl.setParameter("idEstado", idEstadosField.getValue().getId());
        }
        else
        {
            apoiosesDl.removeParameter("idEstado");
        }


        // Utentes

        // Id utente

        if (utenteField.getValue() != null)
        {
            apoiosesDl.setParameter("idUtente", utenteField.getValue().getId());
        }
        else
        {
            apoiosesDl.removeParameter("idUtente");
        }


        // Tipo de Cartão

        if (idTipoCartaoField.getValue() != null)
        {
            apoiosesDl.setParameter("idTipoCartao", idTipoCartaoField.getValue().getId());
        }
        else
        {
            apoiosesDl.removeParameter("idTipoCartao");
        }

        // Habilitações Literárias

        if (habilitacoesField.getValue() != null)
        {
            apoiosesDl.setParameter("habilitacoes", habilitacoesField.getValue().getId());
        }
        else
        {
            apoiosesDl.removeParameter("habilitacoes");
        }


        // Tecnico

        // Id Tecnico

        if (idTecnicoField.getValue() != null)
        {
            apoiosesDl.setParameter("idTecnico", idTecnicoField.getValue().getId());
        }
        else
        {
            apoiosesDl.removeParameter("idTecnico");
        }



        // Valencia


        // Descrição da Valencia

        if (idvalenciaField.getValue() != null)
        {
            apoiosesDl.setParameter("idvalencia", idvalenciaField.getValue().getId());
        }
        else
        {
            apoiosesDl.removeParameter("idvalencia");
        }

        // Area de Intervenção

        if (idtipovalenciaField.getValue() != null)
        {
            apoiosesDl.setParameter("idtipovalencia", idtipovalenciaField.getValue().getId());
        }
        else
        {
            apoiosesDl.removeParameter("idtipovalencia");
        }

        // Resposta Social

        if (idResSocialField.getValue() != null)
        {
            apoiosesDl.setParameter("idResSocial", idResSocialField.getValue().getId());
        }
        else
        {
            apoiosesDl.removeParameter("idResSocial");
        }



        apoiosesDl.load();
    }

    public Component generateNomeTecnicos(Apoios entity) {

        // idInstituicao.tecnicos

        Label label = (Label) uiComponents.create(Label.NAME);


        String tecnicos = "";
        String nome = "";
        String email = "";

        if (entity.getIdInstituicao() != null) {
            if (entity.getIdInstituicao().getValencias() != null) {


                Set<Tecnico> set = entity.getIdInstituicao().getTecnicos();

                tecnicos += "\n";

                for (Tecnico a : set) {

                    if (a.getNome() != null && a.getEmail() != null)
                    {
                        nome = a.getNome();
                        email = a.getEmail();
                        tecnicos += nome + " ( " + email + " ) "+ "\n\n";
                    }
                    if (a.getEmail() != null && a.getNome() == null)
                    {
                        email = a.getEmail();
                        tecnicos += email + "\n\n";
                    }
                    if (a.getEmail() == null && a.getNome() != null)
                    {
                        nome = a.getNome();
                        tecnicos += nome + "\n\n";
                    }
                    if (a.getEmail() == null && a.getNome() == null)
                    {
                        tecnicos += "" + "\n\n";
                    }
                }

                label.setValue(tecnicos);
                return label;

            }
        }



        return null;
    }

    public Component generateDescricaoTecnicaValencias(Apoios entity) {
        Label label = (Label) uiComponents.create(Label.NAME);

        //idInstituicao.valencias.descricaotecnica
        String descricao_tecnica = "";
        if (entity.getIdInstituicao() != null)
        {
            if (entity.getIdInstituicao().getValencias() != null)
            {
                Set<Valencias> set = entity.getIdInstituicao().getValencias();

                descricao_tecnica += "\n";

                for (Valencias a : set) {

                    if (a.getDescricaotecnica() != null)
                    {
                        descricao_tecnica += a.getDescricaotecnica() + "\n\n";
                    }


                }

                label.setValue(descricao_tecnica);
                return label;


            }

        }
        return null;
    }

    public Component generateAreaIntervencaoValenias(Apoios entity) {
        Label label = (Label) uiComponents.create(Label.NAME);

        //idInstituicao.valencias.idtipovalencia
        String areas_intervencao = "";
        if (entity.getIdInstituicao() != null)
        {
            if (entity.getIdInstituicao().getValencias() != null)
            {
                Set<Valencias> set = entity.getIdInstituicao().getValencias();

                areas_intervencao += "\n";

                for (Valencias a : set) {

                    if (a.getIdtipovalencia() != null)
                    {
                        areas_intervencao += a.getIdtipovalencia().getNome().replaceAll("[\\r\\n]+", "") + "\n\n";
                    }


                }

                label.setValue(areas_intervencao);
                return label;


            }

        }
        return null;
    }

    public Component generateRespostaSocialValencias(Apoios entity) {
        Label label = (Label) uiComponents.create(Label.NAME);

        //idInstituicao.valencias.idResSocial
        String resposta_social = "";
        if (entity.getIdInstituicao() != null)
        {
            if (entity.getIdInstituicao().getValencias() != null)
            {
                Set<Valencias> set = entity.getIdInstituicao().getValencias();

                resposta_social += "\n";

                for (Valencias a : set) {

                    if (a.getIdResSocial() != null)
                    {
                        resposta_social += a.getIdResSocial().getNome().replaceAll("[\\r\\n]+", "") + "\n\n";
                    }


                }

                label.setValue(resposta_social);
                return label;


            }

        }
        return null;
    }

    public Component generateCapacidadeValencias(Apoios entity) {
        //idInstituicao.valencias.acordocapacidade
        Label label = (Label) uiComponents.create(Label.NAME);


        String capacidade = "";

        if (entity.getIdInstituicao() != null)
        {
            if (entity.getIdInstituicao().getValencias() != null)
            {
                Set<Valencias> set = entity.getIdInstituicao().getValencias();

                capacidade += "\n";

                for (Valencias a : set) {

                    if (a.getAcordocapacidade() != null)
                    {
                        capacidade += a.getAcordocapacidade() + "\n\n";
                    }


                }

                label.setValue(capacidade);
                return label;


            }

        }




        return null;
    }

    @Subscribe("report")
    protected void onReportClick(Button.ClickEvent event) {
        runExcelDataApoios();
    }

    private void runExcelDataApoios() {
        Map<String, Object> reportParams = new HashMap<>();

        // Parametros

        // --- Apoios ---

        // Instituição

        if (idInstituicaoField.getValue() != null)
        {
            reportParams.put("ins_id", idInstituicaoField.getValue().getId());
        }

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
        reportParams.put("tipo_apoio", 702);

        // Tipo de Documento de Apoio

        reportParams.put("tipo_doc_apoio", 201);

        // Estado

        if (idEstadosField.getValue() != null)
        {
            reportParams.put("estado", idEstadosField.getValue().getId());
        }

        // --- Utentes ---

        // ID do Utente

        if (utenteField.getValue() != null)
        {
            reportParams.put("utente_id", utenteField.getValue().getId());
        }

        // Tipo de Cartão

        if (idTipoCartaoField.getValue() != null)
        {
            reportParams.put("tipo_cartao", idTipoCartaoField.getValue().getId());
        }

        // Habilitações Literárias

        if (habilitacoesField.getValue() != null)
        {
            reportParams.put("habilitacoes", habilitacoesField.getValue().getId());
        }

        // -- Tecnicos ---

        // ID do Tecnico

        if (idTecnicoField.getValue() != null)
        {
            reportParams.put("tecnico_id", idTecnicoField.getValue().getId());
        }

        // --- Valencia ---

        // ID da valencia

        if (idvalenciaField.getValue() != null)
        {
            reportParams.put("valencia_id", idvalenciaField.getValue().getId());
        }

        // Area de Intervencao

        if (idtipovalenciaField.getValue() != null)
        {
            reportParams.put("area_intervencao_id", idtipovalenciaField.getValue().getId());
        }

        // Resposta Social

        if (idResSocialField.getValue() != null)
        {
            reportParams.put("resposta_social_id", idResSocialField.getValue().getId());
        }


        // Tipo de Apoio e Documento de Apoio

        if (tipoApoio.getValue() != null)
        {
            reportParams.put("tipo_apoio", Integer.valueOf(tipoApoio.getValue()));
        }

        if (tipoDocumentoApoio.getValue() != null)
        {
            reportParams.put("tipo_doc_apoio", Integer.valueOf(tipoDocumentoApoio.getValue() ));
        }


        LoadContext<Report> lContext = new LoadContext<>(Report.class);
        lContext.setQueryString("select r from report$Report r where r.id = 'e998ed5f-9250-81e3-c133-dbdfc4724c58' ");
        Report report = dataService.load(lContext);

        String templateCode = "DEFAULT";

        reportGuiManager.printReport(report, reportParams, templateCode, null);

    }

    @Subscribe("popupButton1.popupAction1")
    protected void onPopupButton1PopupAction1(Action.ActionPerformedEvent event) {
        serachTipoApoioQuantidadePessoas_AtendimentoUtentes();
    }

    private void serachTipoApoioQuantidadePessoas_AtendimentoUtentes() {




        dialogs.createInputDialog(this)
                .withCaption("Dados da capacidade de pessoas de uma instituicao")
                .withActions(
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();
                                    dialog.closeWithDefaultAction();


                                    reportDataCapacidadeInstituicao();



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

    private void reportDataCapacidadeInstituicao() {


        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        reportParams.put("tipo_apoio_id", 702);

        reportParams.put("tipo_doc_apoio_id", 201);

        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Dados_capacidade_pessoas_instituicao";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);


            outputFile = "Contagem da Capacidade de Elementos do Tipo de Apoio 'Apoio Financeiro ás entidades' e do tipo de documento 'Regulamentos'";



        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);


    }


    @Subscribe("popupButton1.popupAction2")
    protected void onPopupButton1PopupAction2(Action.ActionPerformedEvent event) {


        dialogs.createInputDialog(this)
                .withCaption("Dados da capacidade de pessoas de uma dada área de intervenção")
                .withActions(
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();
                                    dialog.closeWithDefaultAction();


                                    reportDataCapacidadeAreaIntervencao();



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

    private void reportDataCapacidadeAreaIntervencao() {

        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        reportParams.put("tipo_apoio_id", 702);

        reportParams.put("tipo_doc_apoio_id", 201);

        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Dados_capacidade_pessoas_area_intervencao";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);

        outputFile = "Contagem da Capacidade de Elementos do Tipo de Apoio 'Apoio Financeiro ás entidades' e do tipo de documento 'Regulamentos'";

        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);

    }


    @Subscribe("popupButton1.popupAction3")
    protected void onPopupButton1PopupAction3(Action.ActionPerformedEvent event) {


        dialogs.createInputDialog(this)
                .withCaption("Dados dos utentes em relação ao número de atendimentos")
                .withActions(
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();
                                    dialog.closeWithDefaultAction();
                                    reportDataNomeUtentesAtendimentos();



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



    private void reportDataNomeUtentesAtendimentos() {

        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        reportParams.put("tipo_apoio_id", 702);

        reportParams.put("tipo_doc_apoio_id", 201);

        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Dados_capacidade_pessoas_atendimento_utentes";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);

        outputFile = "Contagem de numero de atendimentos dos utentes do Tipo de Apoio  ''Apoio Financeiro ás entidades' e do tipo de documento 'Regulamentos'";


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);


    }


    @Subscribe("popupButton1.popupAction4")
    protected void onPopupButton1PopupAction4(Action.ActionPerformedEvent event) {



        dialogs.createInputDialog(this)
                .withCaption("Dados das instituições em relação ao número de atendimentos")
                .withActions(
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();
                                    dialog.closeWithDefaultAction();


                                    reportDataInstituicaoAtendimentos();



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

    private void reportDataInstituicaoAtendimentos() {

        String outputFile = "";


        Map<String, Object> reportParams = new HashMap<>();

        reportParams.put("tipo_apoio_id", 702);

        reportParams.put("tipo_doc_apoio_id", 201);

        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "Dados_atendimentos_instituicoes";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);

        outputFile = "Contagem de numero de atendimentos das instituições do Tipo de Apoio 'Apoio Financeiro ás entidades' e do tipo de documento 'Regulamentos'";

        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);

    }




}