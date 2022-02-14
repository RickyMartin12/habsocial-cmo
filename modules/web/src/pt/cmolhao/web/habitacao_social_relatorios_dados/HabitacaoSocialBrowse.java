package pt.cmolhao.web.habitacao_social_relatorios_dados;

import com.google.common.base.Strings;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.app.core.inputdialog.DialogActions;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.components.inputdialog.InputDialogAction;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.gui.ReportGuiManager;
import pt.cmolhao.entity.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@UiController("cmolhao_HabitacaoSocial.browseRelatorios")
@UiDescriptor("habitacao-social-browse.xml")
@LookupComponent("habitacaoSocialsTable")
@LoadDataBeforeShow
public class HabitacaoSocialBrowse extends StandardLookup<HabitacaoSocial> {
    @Inject
    protected CollectionContainer<HabitacaoSocial> habitacaoSocialsDc;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected DateField<Date> dataInicioField;
    @Inject
    protected CollectionLoader<HabitacaoSocial> habitacaoSocialsDl;
    @Inject
    protected DateField<Date> dataFimField;
    @Inject
    protected LookupPickerField<TipoAtendimento> idTipoAtendimentoField;
    @Inject
    protected LookupPickerField<AtendimentoEncaminhamento> idAtendimentoEncaminhamentoField;
    @Inject
    protected LookupPickerField<BlocosHabitacaoSocial> blocField;
    @Inject
    protected TextField<String> localidadeField;
    @Inject
    protected LookupField tipoArrendamentoField;
    @Inject
    protected LookupPickerField<Utente> utenteField;
    @Inject
    protected LookupPickerField<HabilitacoesLiterarias> habilitacoesField;
    @Inject
    protected LookupPickerField<Tecnico> idTecnicoField;
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected GroupTable<HabitacaoSocial> habitacaoSocialsTable;
    @Inject
    protected LookupPickerField<TipoCartao> idTipoCartaoField;
    @Inject
    protected LookupPickerField<AtendimentoObjetivos> atendimentoObjectivosField;
    @Named("popupButton1.popupAction1")
    protected BaseAction popupButton1PopupAction1;
    @Inject
    protected TextField<String> anoField;
    @Inject
    protected TextField<String> mesField;
    @Inject
    protected LookupField linhasHabSocial;

    @Inject
    private Notifications notifications;

    @Inject
    private DataService dataService;
    @Inject
    protected ReportGuiManager reportGuiManager = AppBeans.get(ReportGuiManager.class);

    @Inject
    private Dialogs dialogs;

    @Inject
    private DataManager dataManager;

    @Inject
    private MetadataTools metadataTools;


    @Subscribe
    public void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Reside no Concelho");
        list.add("Não reside no Concelho");
        tipoArrendamentoField.setOptionsList(list);

        habitacaoSocialsTable.addGeneratedColumn("imprimir_pedido_habitacao", entity -> {

            Button btn = uiComponents.create(Button.class);
            btn.setStyleName("button_middle_pedido_habitacao");
            //btn.setCaption("imprimir");
            btn.setDescription("Imprimir Habitação Social: " + entity.getId());
            btn.setIcon("font-icon:FILE_PDF_O");
            btn.setAction(new BaseAction("imprimir") {
                @Override
                public void actionPerform(Component component) {
                    runReportHabitacaoSocial(component);
                }
            });
            return btn;
        });


        List<Integer> list_hab_social = new ArrayList<>();
        list_hab_social.add(10);
        list_hab_social.add(20);
        list_hab_social.add(50);
        list_hab_social.add(100);
        list_hab_social.add(200);
        list_hab_social.add(500);
        list_hab_social.add(1000);
        list_hab_social.add(2000);
        list_hab_social.add(5000);
        list_hab_social.add(10000);
        linhasHabSocial.setOptionsList(list_hab_social);



    }

    @Subscribe("linhasHabSocial")
    public void onLinhasHabSocialValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            habitacaoSocialsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            habitacaoSocialsDl.setMaxResults(0);
        }
        habitacaoSocialsDl.load();
    }

    private void runReportHabitacaoSocial(Component component) {
        Map<String, Object> reportParams = new HashMap<>();
        reportParams.put("HabitacaoSocial", habitacaoSocialsTable.getSingleSelected());
        LoadContext<Report> lContext = new LoadContext<>(Report.class);
        lContext.setQueryString("select r from report$Report r where r.id = '41868232-9abc-5a2e-e052-e90d85f8cc78' ");
        Report report = dataService.load(lContext);

        String templateCode = "DEFAULT";

        reportGuiManager.printReport(report, reportParams, templateCode, null);
    }





        public Component generateAtendimentosEncaminhamentos(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        String atendimentos_encaminhamentos = "";
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                atendimentos_encaminhamentos += "\n";

                for (Atendimento a : set) {

                    if (a.getIdAtendimentoEncaminhamento() != null)
                    {
                        atendimentos_encaminhamentos += a.getIdAtendimentoEncaminhamento().getAtendimentoEncaminhamento() + "\n\n";
                    }

                }

                label.setValue(atendimentos_encaminhamentos);
                return label;


            }

        }
        return null;
    }

    public Component generateAtendimentosTecnicoNome(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        String tipos_atendimentos = "";
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                tipos_atendimentos += "\n";

                for (Atendimento a : set) {

                    if (a.getIdTecnico() != null)
                    {
                        tipos_atendimentos += a.getIdTecnico().getNome() + "\n\n";
                    }

                }

                label.setValue(tipos_atendimentos);
                return label;


            }

        }
        return null;
    }


    public Component generateAtendimentoTecnicos(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        String tipos_atendimentos = "";
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                tipos_atendimentos += "\n";

                for (Atendimento a : set) {

                    if (a.getIdTecnico() != null)
                    {
                        if (a.getIdTecnico().getIdInstituicao() != null)
                        {
                            tipos_atendimentos += a.getIdTecnico().getIdInstituicao().getDescricao() + "\n\n";
                        }

                    }

                }

                label.setValue(tipos_atendimentos);
                return label;


            }

        }
        return null;
    }

    public Component generateTiposAtendimentos(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        String tipos_atendimentos = "";
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                tipos_atendimentos += "\n";

                for (Atendimento a : set) {

                    if (a.getIdTipoAtendimento() != null)
                    {
                        tipos_atendimentos += a.getIdTipoAtendimento().getTipoAtendimento() + "\n\n";
                    }

                }

                    label.setValue(tipos_atendimentos);
                    return label;


            }

        }
        return null;
    }


    public Component generateTiposAtendimentosObjectivos(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        String atendimentos_objectivos = "";
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                atendimentos_objectivos += "\n";

                for (Atendimento a : set) {

                        List<AtendimentoObjetivos> ao = a.getAtendimentoObjetivos();

                        for (int i = 0; i < ao.size(); i++) {
                            atendimentos_objectivos += ao.get(i).getAtendimentoObjetivoGeral() + "\n\n";
                        }



                }

                label.setValue(atendimentos_objectivos);
                return label;


            }

        }
        return null;
    }


    public Component generateDataAtendimento(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        StringBuilder data_atendimento = new StringBuilder();
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                data_atendimento.append("\n");

                for (Atendimento a : set) {

                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                    String data_atend = formatter.format(a.getDataAtendimento());

                    data_atendimento.append(data_atend).append("\n\n");


                }

                label.setValue(data_atendimento.toString());
                return label;


            }

        }
        return null;
    }

    @Subscribe("dataInicioField")
    protected void onDataInicioFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        dataFimField.setEnabled(true);
        if (event.isUserOriginated())
        {
            if (event.getValue() != null)
            {
                dataFimField.setRangeStart(event.getValue());
            }
        }
    }

    @Subscribe("dataFimField")
    protected void onDataFimFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                dataInicioField.setRangeEnd(event.getValue());
            }
        }
    }

    @Subscribe("search_habitacao_social")
    protected void onSearch_habitacao_socialClick(Button.ClickEvent event) {
        if (dataInicioField.getValue() != null && dataFimField.getValue() == null)
        {
            habitacaoSocialsDl.setQuery("select e from cmolhao_HabitacaoSocial e join e.idUtente.atendimentos l where l.dataAtendimento = :dataInicio");
            habitacaoSocialsDl.setParameter("dataInicio",  dataInicioField.getValue());
        }
        if (dataInicioField.getValue() != null && dataFimField.getValue() != null)
        {
            habitacaoSocialsDl.setQuery("select e from cmolhao_HabitacaoSocial e join e.idUtente.atendimentos l where l.dataAtendimento >= :dataInicio and l.dataAtendimento <= :dateFim");
            habitacaoSocialsDl.setParameter("dataInicio",  dataInicioField.getValue());
            habitacaoSocialsDl.setParameter("dateFim",  dataFimField.getValue());
        }

        if (idTipoAtendimentoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idTipoAtendimento", idTipoAtendimentoField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("idTipoAtendimento");
        }

        if (idAtendimentoEncaminhamentoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idAtendimentoEncaminhamento", idAtendimentoEncaminhamentoField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("idAtendimentoEncaminhamento");
        }


        if (blocField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("bloc", blocField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("bloc");
        }

        if (localidadeField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("localidade",  "(?i)%" + localidadeField.getValue() + "%");
        }
        else
        {
            habitacaoSocialsDl.removeParameter("localidade");
        }

        if (tipoArrendamentoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("tipoArrendamento", tipoArrendamentoField.getValue());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("tipoArrendamento");
        }

        if(utenteField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idUtente", utenteField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("idUtente");
        }

        if (habilitacoesField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("habilitacoes", habilitacoesField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("habilitacoes");
        }


        if(idTecnicoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idTecnico", idTecnicoField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("idTecnico");
        }

        if(idInstituicaoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idInstituicao", idInstituicaoField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("idInstituicao");
        }

        if (idTipoCartaoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idTipoCartao", idTipoCartaoField.getValue().getId());
        }
        else {
            habitacaoSocialsDl.removeParameter("idTipoCartao");
        }

        if (atendimentoObjectivosField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("atendimentoObjetivos",  atendimentoObjectivosField.getValue().getId());
        } else {
            habitacaoSocialsDl.removeParameter("atendimentoObjetivos");
        }




        habitacaoSocialsDl.load();
    }

    @Subscribe("reset_habitacao_social")
    protected void onReset_habitacao_socialClick(Button.ClickEvent event) {

        if (dataInicioField.getValue() != null)
        {
            dataInicioField.setValue(null);
            habitacaoSocialsDl.removeParameter("dataInicio");
            habitacaoSocialsDl.setQuery("select e from cmolhao_HabitacaoSocial e");
        }
        if (dataFimField.getValue() != null)
        {
            dataFimField.setValue(null);
            habitacaoSocialsDl.removeParameter("dateFim");
            habitacaoSocialsDl.setQuery("select e from cmolhao_HabitacaoSocial e");
            dataFimField.setEnabled(false);
        }


        idTipoAtendimentoField.setValue(null);
        habitacaoSocialsDl.removeParameter("idTipoAtendimento");

        idAtendimentoEncaminhamentoField.setValue(null);
        habitacaoSocialsDl.removeParameter("idAtendimentoEncaminhamento");

        blocField.setValue(null);
        habitacaoSocialsDl.removeParameter("bloc");

        localidadeField.setValue(null);
        habitacaoSocialsDl.removeParameter("localidade");

        tipoArrendamentoField.setValue(null);
        habitacaoSocialsDl.removeParameter("tipoArrendamento");

        utenteField.setValue(null);
        habitacaoSocialsDl.removeParameter("idUtente");

        habilitacoesField.setValue(null);
        habitacaoSocialsDl.removeParameter("habilitacoes");


        idTecnicoField.setValue(null);
        habitacaoSocialsDl.removeParameter("idTecnico");

        idInstituicaoField.setValue(null);
        habitacaoSocialsDl.removeParameter("idInstituicao");

        idTipoCartaoField.setValue(null);
        habitacaoSocialsDl.removeParameter("idTipoCartao");

        atendimentoObjectivosField.setValue(null);
        habitacaoSocialsDl.removeParameter("atendimentoObjetivos");

        anoField.setValue(null);
        habitacaoSocialsDl.removeParameter("ano");

        mesField.setValue(null);
        habitacaoSocialsDl.removeParameter("mes");





        habitacaoSocialsDl.load();

    }



    @Subscribe("report")
    protected void onReportClick(Button.ClickEvent event) {
            runExcelDataHabitacaoSocial();
    }

    private void runExcelDataHabitacaoSocial() {
        Map<String, Object> reportParams = new HashMap<>();

        // Parametros

        // --- Habitação Social ---

        // Bloco da Habitação Social

        if (blocField.getValue() != null)
        {
            reportParams.put("bloc_hab_social", blocField.getValue().getId());
        }

        // Localidade

        if (localidadeField.getValue() != null)
        {
            reportParams.put("local", localidadeField.getValue());
        }
        else
        {
            reportParams.put("local", "-/-");
        }


        // Tipo de Arrendamento

        if (tipoArrendamentoField.getValue() != null)
        {
            reportParams.put("tipo_arrend", tipoArrendamentoField.getValue());
        }
        else
        {
            reportParams.put("tipo_arrend", "-/-");
        }

        // --- Utente ---

        // ID utente

        if (utenteField.getValue() != null)
        {
            reportParams.put("ut_id", utenteField.getValue().getId());
        }

        // Habilitações Literarias

        if(habilitacoesField.getValue() != null)
        {
            reportParams.put("hl_id", habilitacoesField.getValue().getId());
        }

        // Tipo de Cartão

        if (idTipoCartaoField.getValue() != null)
        {
            reportParams.put("tc_id", idTipoCartaoField.getValue().getId());
        }

        // --- Tecnico ---

        // Nome e Email do Técnico

        if (idTecnicoField.getValue() != null)
        {
            reportParams.put("t_id", idTecnicoField.getValue().getId());
        }

        // Instituição

        if (idInstituicaoField.getValue() != null)
        {
            reportParams.put("ins_id", idInstituicaoField.getValue().getId());
        }

        // --- Atendimento ---

        // Tipo de Atendimento

        if (idTipoAtendimentoField.getValue() != null)
        {
            reportParams.put("tipo_atend", idTipoAtendimentoField.getValue().getId());
        }

        // Atendimento por Encaminhamento

        if (idAtendimentoEncaminhamentoField.getValue() != null)
        {
            reportParams.put("atend_enc", idAtendimentoEncaminhamentoField.getValue().getId());
        }


        // Atendimentos por Objectivos

        if (atendimentoObjectivosField.getValue() != null)
        {
            reportParams.put("at_id", atendimentoObjectivosField.getValue().getId());
        }

        // Datas de Atendimento

        if (dataInicioField.getValue() != null && dataFimField.getValue() == null)
        {
            reportParams.put("data_inicio", dataInicioField.getValue());
            reportParams.put("data_fim", dataInicioField.getValue());
        }
        if (dataInicioField.getValue() != null && dataFimField.getValue() != null) {
            reportParams.put("data_inicio", dataInicioField.getValue());
            reportParams.put("data_fim", dataFimField.getValue());
        }


        // Ano do Atendimento

        if (anoField.getValue() != null)
        {
            reportParams.put("ano", anoField.getValue());
        }
        else
        {
            reportParams.put("ano", "-/-");
        }

        // Mes de Atendimento

        if (mesField.getValue() != null)
        {
            reportParams.put("mes", mesField.getValue());
        }
        else
        {
            reportParams.put("mes", "-/-");
        }

        LoadContext<Report> lContext = new LoadContext<>(Report.class);
        lContext.setQueryString("select r from report$Report r where r.id = 'a6cb1a3b-3e6e-8211-fa15-4a3fde6fd740' ");
        Report report = dataService.load(lContext);

        String templateCode = "DEFAULT";

        reportGuiManager.printReport(report, reportParams, templateCode, null);
    }

    @Subscribe("popupButton1.popupAction1")
    protected void onPopupButton1PopupAction1(Action.ActionPerformedEvent event) {
        searchByYearAndMonthHabSocial();
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    private void searchByYearAndMonthHabSocial() {

        dialogs.createInputDialog(this)
                .withCaption("Valor do ano e do mes de Atendimento para a pesquisa/impressao de dados no relatorio de atendimento")
                .withParameters(
                        InputParameter.stringParameter("ano").withCaption("Ano do Pedido da Habitacao Social: "),
                        InputParameter.parameter("mes")
                                .withField(() -> {
                                    LookupField<String> field = uiComponents.create(LookupField.TYPE_STRING);
                                    field.setInputPrompt("Seleccione o mes do atendimento");
                                    field.setCaption("Mes do Pedido da Habitacao Social: ");


                                    Map<String, String> map = new LinkedHashMap<>();
                                    map.put("Janeiro", "01");
                                    map.put("Fevereiro", "02");
                                    map.put("Marco", "03");
                                    map.put("Abril", "04");
                                    map.put("Maio", "05");
                                    map.put("Junho", "06");
                                    map.put("Julho", "07");
                                    map.put("Agosto", "08");
                                    map.put("Setembro", "09");
                                    map.put("Outubro", "10");
                                    map.put("Novembro", "11");
                                    map.put("Dezembro", "12");

                                    field.setOptionsMap(map);
                                    field.setWidthFull();
                                    return field;
                                })
                )
                .withValidator(context -> {
                    String name = context.getValue("ano");
                    String mes = context.getValue("mes");
                    if (!isNumeric(name) && name != null)
                    {
                        return ValidationErrors.of("o ano de atendimento deve ser um número inteiro");
                    }
                    else if (isNumeric(name) && name.length() != 4)
                    {
                        return ValidationErrors.of("o ano de atendimento deve possuir pelo menos 4 caracteres");

                    }
                    else if (name == null && mes == null)
                    {
                        return ValidationErrors.of("Deve introduzir pelo menos um dos dados de pedidos de habitação");
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
                                    String name = dialog.getValue("ano");
                                    String mes = dialog.getValue("mes");
                                    dialog.closeWithDefaultAction();

                                    anoField.setValue(name);
                                    mesField.setValue(mes);


                                    // ano de atendimento
                                    if (name != null)
                                    {
                                        habitacaoSocialsDl.setParameter("ano",  Integer.valueOf(name));
                                    } else {
                                        habitacaoSocialsDl.removeParameter("ano");
                                    }

                                    // mes de atendimento

                                    if (mes != null)
                                    {
                                        habitacaoSocialsDl.setParameter("mes",  Integer.valueOf(mes));
                                    } else {
                                        habitacaoSocialsDl.removeParameter("mes");
                                    }

                                    habitacaoSocialsDl.load();


                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();
                                    String name = dialog.getValue("ano");
                                    String mes = dialog.getValue("mes");
                                    dialog.closeWithDefaultAction();

                                    // funcao de imprimir os meses do atendimento por ano e por mes

                                    // ano e mes do pedido do atendimento da habitação social

                                    reportsByMonthandYears(name, mes);

                                    //runReportAtendimentobyYear(name);



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

    private void reportsByMonthandYears(String ano, String mes) {


        Map<String, Object> reportParams = new HashMap<>();

        if (ano != null && mes == null)
        {
            reportParams.put("ano", ano);

            reportParams.put("mes", "-/-");

            LoadContext<Report> lContext = new LoadContext<>(Report.class);

            String templateCode = "mes_cont_pedido_hab";

            String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
            lContext.setQueryString(query_report_code_ano);
            Report report = dataService.load(lContext);



            String outputFile = "Pedido de Habitação Social no ano " + ano;

            reportGuiManager.printReport(report, reportParams, templateCode, outputFile);

        }

        if (ano == null && mes != null)
        {
            reportParams.put("ano", "-/-");

            reportParams.put("mes", mes);

            LoadContext<Report> lContext = new LoadContext<>(Report.class);

            String templateCode = "ano_cont_pedido_hab";

            String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
            lContext.setQueryString(query_report_code_ano);
            Report report = dataService.load(lContext);

            String nome_mes = "";

            if (mes.equals("01"))
            {
                nome_mes = "Janeiro";
            }
            if (mes.equals("02"))
            {
                nome_mes = "Fevereiro";
            }
            if (mes.equals("03"))
            {
                nome_mes = "Marco";
            }
            if (mes.equals("04"))
            {
                nome_mes = "Abril";
            }
            if (mes.equals("05"))
            {
                nome_mes = "Maio";
            }
            if (mes.equals("06"))
            {
                nome_mes = "Junho";
            }
            if (mes.equals("07"))
            {
                nome_mes = "Julho";
            }
            if (mes.equals("08"))
            {
                nome_mes = "Agosto";
            }
            if (mes.equals("09"))
            {
                nome_mes = "Setembro";
            }
            if (mes.equals("10"))
            {
                nome_mes = "Outubro";
            }
            if (mes.equals("11"))
            {
                nome_mes = "Novembro";
            }
            if (mes.equals("12"))
            {
                nome_mes = "Dezembro";
            }



            String outputFile = "Pedido de Habitação Social no mês " + nome_mes;

            reportGuiManager.printReport(report, reportParams, templateCode, outputFile);

        }



        if (ano != null && mes != null)
        {
            reportParams.put("ano", ano);

            reportParams.put("mes", mes);

            LoadContext<Report> lContext = new LoadContext<>(Report.class);

            String templateCode = "mes_ano_cont_pedido_hab";

            String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
            lContext.setQueryString(query_report_code_ano);
            Report report = dataService.load(lContext);

            String nome_mes = "";

            if (mes.equals("01"))
            {
                nome_mes = "Janeiro";
            }
            if (mes.equals("02"))
            {
                nome_mes = "Fevereiro";
            }
            if (mes.equals("03"))
            {
                nome_mes = "Marco";
            }
            if (mes.equals("04"))
            {
                nome_mes = "Abril";
            }
            if (mes.equals("05"))
            {
                nome_mes = "Maio";
            }
            if (mes.equals("06"))
            {
                nome_mes = "Junho";
            }
            if (mes.equals("07"))
            {
                nome_mes = "Julho";
            }
            if (mes.equals("08"))
            {
                nome_mes = "Agosto";
            }
            if (mes.equals("09"))
            {
                nome_mes = "Setembro";
            }
            if (mes.equals("10"))
            {
                nome_mes = "Outubro";
            }
            if (mes.equals("11"))
            {
                nome_mes = "Novembro";
            }
            if (mes.equals("12"))
            {
                nome_mes = "Dezembro";
            }
            String outputFile = "Pedido de Habitação Social no mês " + nome_mes + " no ano " + ano;

            reportGuiManager.printReport(report, reportParams, templateCode, outputFile);
        }

    }

    @Subscribe("popupButton1.popupAction2")
    protected void onPopupButton1PopupAction2(Action.ActionPerformedEvent event) {
        searchByNameHabSocial();
    }

    private void searchByNameHabSocial() {
        dialogs.createInputDialog(this)
                .withCaption("Pedido da Morada de Habitacao Social")
                .withParameters(
                        InputParameter.parameter("bloc")
                                .withField(() -> {
                                    LookupField<BlocosHabitacaoSocial> field = uiComponents.create(
                                            LookupField.of(BlocosHabitacaoSocial.class));
                                    field.setOptionsList(dataManager.load(BlocosHabitacaoSocial.class).list());
                                    field.setCaption("Habitação Social: ");
                                    field.setWidthFull();
                                    return field;
                                })
                )
                .withValidator(context -> {
                    BlocosHabitacaoSocial bloc = context.getValue("bloc");

                    if (bloc == null)
                    {
                        return ValidationErrors.of("Deve introduzir a morada da habitação social");
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
                                    BlocosHabitacaoSocial bloc = dialog.getValue("bloc");
                                    dialog.closeWithDefaultAction();



                                    if (bloc != null)
                                    {
                                        habitacaoSocialsDl.setParameter("bloc", bloc.getId());
                                    }
                                    else
                                    {
                                        habitacaoSocialsDl.removeParameter("bloc");
                                    }

                                    habitacaoSocialsDl.load();


                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();
                                    BlocosHabitacaoSocial bloc = dialog.getValue("bloc");
                                    dialog.closeWithDefaultAction();


                                    reportByHabSocial(bloc);



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

    private void reportByHabSocial(BlocosHabitacaoSocial bloc) {

        Map<String, Object> reportParams = new HashMap<>();

        reportParams.put("bloc", bloc.getId());


        LoadContext<Report> lContext = new LoadContext<>(Report.class);

        String templateCode = "bloc_hab_social_cont";

        String query_report_code_ano = "select r from report$Report r join r.defaultTemplate p where p.code = '"+templateCode+"'  ";
        lContext.setQueryString(query_report_code_ano);

        Report report = dataService.load(lContext);

        String outputFile = "Atendimento na Habitacao Social - " + bloc.getDesignacao();

        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);



    }


}