package pt.cmolhao.web.apoios_relatorios;

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


@UiController("cmolhao_Apoios.browseRelatorios")
@UiDescriptor("apoios-browse.xml")
@LookupComponent("apoiosesTable")
@LoadDataBeforeShow
public class ApoiosBrowse extends StandardLookup<Apoios> {
    @Inject
    protected UiComponents uiComponents;

    @Inject
    protected LookupPickerField<TipoAjuda> idTipoapoioField;
    @Inject
    protected LookupPickerField<TipoDocApoio> idTipoDocApoioField;
    @Inject
    protected LookupPickerField<Estados> idEstadosField;
    @Inject
    protected CollectionContainer<TipoDocApoio> tipoDocApoioDc;
    @Inject
    protected CollectionContainer<Valencias> valenciasDc;
    @Inject
    protected LookupPickerField<Valencias> idvalenciaField;
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected TextField<String> numProcessoField;
    @Inject
    protected LookupPickerField<Utente> utenteField;
    @Inject
    protected CollectionContainer<Tiposvalencia> TipoValenciaDc;
    @Inject
    protected LookupPickerField<Tiposvalencia> idtipovalenciaField;
    @Inject
    protected LookupPickerField<RespostaSocial> idResSocialField;
    @Inject
    protected LookupPickerField<Tecnico> idTecnicoField;
    @Inject
    protected LookupPickerField<HabilitacoesLiterarias> habilitacoesField;
    @Inject
    protected LookupPickerField<TipoCartao> idTipoCartaoField;
    @Inject
    protected CollectionContainer<RespostaSocial> respostaSocialsDc;
    @Inject
    protected CollectionLoader<Apoios> apoiosesDl;
    @Inject
    protected Table<Apoios> apoiosesTable;
    @Inject
    protected TextField<String> tipoApoio;
    @Inject
    protected TextField<String> tipoDocumentoApoio;
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

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Relatórios de Apoio");
        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            map.put(item.getDescricaotecnica() + " ", item);
        }
        idvalenciaField.setOptionsMap(map);

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

    @Subscribe("reset_apoios")
    protected void onReset_apoiosClick(Button.ClickEvent event) {
        idInstituicaoField.setValue(null);
        numProcessoField.setValue(null);
        idTipoapoioField.setValue(null);
        idTipoDocApoioField.setValue(null);
        idTipoDocApoioField.setEditable(false);
        idEstadosField.setValue(null);
        utenteField.setValue(null);
        idTipoCartaoField.setValue(null);
        habilitacoesField.setValue(null);
        idTecnicoField.setValue(null);
        idvalenciaField.setValue(null);
        idtipovalenciaField.setValue(null);
        idResSocialField.setValue(null);
        idResSocialField.setEditable(false);

        tipoApoio.setValue(null);
        tipoDocumentoApoio.setValue(null);


        apoiosesDl.removeParameter("idInstituicao");
        apoiosesDl.removeParameter("numProcesso");
        apoiosesDl.removeParameter("idTipoapoio");
        apoiosesDl.removeParameter("idTipoDocApoio");
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

        //Tipo de Apoio

        if (idTipoapoioField.getValue() != null)
        {
            apoiosesDl.setParameter("idTipoapoio", idTipoapoioField.getValue().getId());
        }
        else
        {
            apoiosesDl.removeParameter("idTipoapoio");
        }

        //Tipo de Documento de Apoio

        if (idTipoDocApoioField.getValue() != null)
        {
            apoiosesDl.setParameter("idTipoDocApoio", idTipoDocApoioField.getValue().getId());
        }
        else
        {
            apoiosesDl.removeParameter("idTipoDocApoio");
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

        if (idTipoapoioField.getValue() != null)
        {
            reportParams.put("tipo_apoio", idTipoapoioField.getValue().getId());
        }

        // Tipo de Documento de Apoio

        if (idTipoDocApoioField.getValue() != null)
        {
            reportParams.put("tipo_doc_apoio", idTipoDocApoioField.getValue().getId());
        }

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
        LookupField<TipoDocApoio> field2 = uiComponents.create(
                LookupField.of(TipoDocApoio.class));
        LookupField<TipoAjuda> field = uiComponents.create(
                LookupField.of(TipoAjuda.class));
        dialogs.createInputDialog(this)
                .withCaption("Dados da capacidade de pessoas de uma instituicao")
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
                                        apoiosesDl.setParameter("idTipoapoio", tipo_apoio.getId());
                                        tipoApoio.setValue(Integer.toString(tipo_apoio.getId()));
                                    }
                                    else
                                    {
                                        apoiosesDl.removeParameter("idTipoapoio");
                                    }

                                    //Tipo de Documento de Apoio

                                    if (tipo_doc_apoio != null)
                                    {
                                        apoiosesDl.setParameter("idTipoDocApoio", tipo_doc_apoio.getId());
                                        tipoDocumentoApoio.setValue(Integer.toString(tipo_doc_apoio.getId()));
                                    }
                                    else
                                    {
                                        apoiosesDl.removeParameter("idTipoDocApoio");
                                    }

                                    apoiosesDl.load();







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


                                    reportDataCapacidadeInstituicao(tipo_apoio, tipo_doc_apoio);



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

    private void reportDataCapacidadeInstituicao(TipoAjuda tipo_apoio, TipoDocApoio tipo_doc_apoio) {


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

        String templateCode = "Dados_capacidade_pessoas_instituicao";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);



        if (tipo_apoio != null && tipo_doc_apoio == null)
        {
            outputFile = "Contagem da Capacidade de Elementos do Tipo de Apoio  '"+tipo_apoio.getDescricaoTipoAjuda()+"'";
        }
        if (tipo_apoio != null && tipo_doc_apoio != null)
        {
            outputFile = "Contagem da Capacidade de Elementos do Tipo de Apoio  '"+tipo_apoio.getDescricaoTipoAjuda()+"' e do tipo de documento '"+tipo_doc_apoio.getDescricao()+"'";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);


    }

    @Subscribe("popupButton1.popupAction2")
    protected void onPopupButton1PopupAction2(Action.ActionPerformedEvent event) {
        LookupField<TipoDocApoio> field2 = uiComponents.create(
                LookupField.of(TipoDocApoio.class));
        LookupField<TipoAjuda> field = uiComponents.create(
                LookupField.of(TipoAjuda.class));
        dialogs.createInputDialog(this)
                .withCaption("Dados da capacidade de pessoas de uma dada área de intervenção")
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
                                        apoiosesDl.setParameter("idTipoapoio", tipo_apoio.getId());
                                        tipoApoio.setValue(Integer.toString(tipo_apoio.getId()));
                                    }
                                    else
                                    {
                                        apoiosesDl.removeParameter("idTipoapoio");
                                    }

                                    //Tipo de Documento de Apoio

                                    if (tipo_doc_apoio != null)
                                    {
                                        apoiosesDl.setParameter("idTipoDocApoio", tipo_doc_apoio.getId());
                                        tipoDocumentoApoio.setValue(Integer.toString(tipo_doc_apoio.getId()));
                                    }
                                    else
                                    {
                                        apoiosesDl.removeParameter("idTipoDocApoio");
                                    }

                                    apoiosesDl.load();







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


                                    reportDataCapacidadeAreaIntervencao(tipo_apoio, tipo_doc_apoio);



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

    private void reportDataCapacidadeAreaIntervencao(TipoAjuda tipo_apoio, TipoDocApoio tipo_doc_apoio) {

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

        String templateCode = "Dados_capacidade_pessoas_area_intervencao";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);



        if (tipo_apoio != null && tipo_doc_apoio == null)
        {
            outputFile = "Contagem da Capacidade de Elementos do Tipo de Apoio  '"+tipo_apoio.getDescricaoTipoAjuda()+"'";
        }
        if (tipo_apoio != null && tipo_doc_apoio != null)
        {
            outputFile = "Contagem da Capacidade de Elementos do Tipo de Apoio  '"+tipo_apoio.getDescricaoTipoAjuda()+"' e do tipo de documento '"+tipo_doc_apoio.getDescricao()+"'";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);

    }

    @Subscribe("popupButton1.popupAction3")
    protected void onPopupButton1PopupAction3(Action.ActionPerformedEvent event) {
        LookupField<TipoDocApoio> field2 = uiComponents.create(
                LookupField.of(TipoDocApoio.class));
        LookupField<TipoAjuda> field = uiComponents.create(
                LookupField.of(TipoAjuda.class));
        dialogs.createInputDialog(this)
                .withCaption("Dados dos utentes em relação ao número de atendimentos")
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
                                        apoiosesDl.setParameter("idTipoapoio", tipo_apoio.getId());
                                        tipoApoio.setValue(Integer.toString(tipo_apoio.getId()));
                                    }
                                    else
                                    {
                                        apoiosesDl.removeParameter("idTipoapoio");
                                    }

                                    //Tipo de Documento de Apoio

                                    if (tipo_doc_apoio != null)
                                    {
                                        apoiosesDl.setParameter("idTipoDocApoio", tipo_doc_apoio.getId());
                                        tipoDocumentoApoio.setValue(Integer.toString(tipo_doc_apoio.getId()));
                                    }
                                    else
                                    {
                                        apoiosesDl.removeParameter("idTipoDocApoio");
                                    }

                                    apoiosesDl.load();







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


                                    reportDataNomeUtentesAtendimentos(tipo_apoio, tipo_doc_apoio);



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

    @Subscribe("popupButton1.popupAction4")
    protected void onPopupButton1PopupAction4(Action.ActionPerformedEvent event) {
        LookupField<TipoDocApoio> field2 = uiComponents.create(
                LookupField.of(TipoDocApoio.class));
        LookupField<TipoAjuda> field = uiComponents.create(
                LookupField.of(TipoAjuda.class));
        dialogs.createInputDialog(this)
                .withCaption("Dados das instituições em relação ao número de atendimentos")
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
                                        apoiosesDl.setParameter("idTipoapoio", tipo_apoio.getId());
                                        tipoApoio.setValue(Integer.toString(tipo_apoio.getId()));
                                    }
                                    else
                                    {
                                        apoiosesDl.removeParameter("idTipoapoio");
                                    }

                                    //Tipo de Documento de Apoio

                                    if (tipo_doc_apoio != null)
                                    {
                                        apoiosesDl.setParameter("idTipoDocApoio", tipo_doc_apoio.getId());
                                        tipoDocumentoApoio.setValue(Integer.toString(tipo_doc_apoio.getId()));
                                    }
                                    else
                                    {
                                        apoiosesDl.removeParameter("idTipoDocApoio");
                                    }

                                    apoiosesDl.load();







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


                                    reportDataInstituicaoAtendimentos(tipo_apoio, tipo_doc_apoio);



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

    private void reportDataInstituicaoAtendimentos(TipoAjuda tipo_apoio, TipoDocApoio tipo_doc_apoio) {

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

        String templateCode = "Dados_atendimentos_instituicoes";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);



        if (tipo_apoio != null && tipo_doc_apoio == null)
        {
            outputFile = "Contagem de numero de atendimentos das instituições do Tipo de Apoio  '"+tipo_apoio.getDescricaoTipoAjuda()+"'";
        }
        if (tipo_apoio != null && tipo_doc_apoio != null)
        {
            outputFile = "Contagem de numero de atendimentos das instituições do Tipo de Apoio  '"+tipo_apoio.getDescricaoTipoAjuda()+"' e do tipo de documento '"+tipo_doc_apoio.getDescricao()+"'";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);

    }

    private void reportDataNomeUtentesAtendimentos(TipoAjuda tipo_apoio, TipoDocApoio tipo_doc_apoio) {

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

        String templateCode = "Dados_capacidade_pessoas_atendimento_utentes";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);



        if (tipo_apoio != null && tipo_doc_apoio == null)
        {
            outputFile = "Contagem de numero de atendimentos dos utentes do Tipo de Apoio  '"+tipo_apoio.getDescricaoTipoAjuda()+"'";
        }
        if (tipo_apoio != null && tipo_doc_apoio != null)
        {
            outputFile = "Contagem de numero de atendimentos dos utentes do Tipo de Apoio  '"+tipo_apoio.getDescricaoTipoAjuda()+"' e do tipo de documento '"+tipo_doc_apoio.getDescricao()+"'";
        }


        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);


    }
}