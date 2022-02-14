package pt.cmolhao.web.screens.atendimento;

import com.google.common.base.Strings;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.LoadContext;
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
import java.util.*;

@UiController("cmolhao_Atendimento.browseRelatorios")
@UiDescriptor("atendimento-browse-relatorios.xml")
@LookupComponent("atendimentoesTable")
@LoadDataBeforeShow
public class AtendimentoBrowseRelatorios extends StandardLookup<Atendimento> {

    @Inject
    protected ReportGuiManager reportGuiManager = AppBeans.get(ReportGuiManager.class);
    @Inject
    protected GroupTable<Atendimento> atendimentoesTable;
    @Inject
    protected CollectionContainer<Atendimento> atendimentoesDc;
    @Inject
    protected CollectionContainer<AtendimentoObjetivos> atendimentoObjetivosDc;
    @Inject
    protected LookupPickerField<AtendimentoObjetivos> atendimentoObjectivosField;
    @Inject
    protected LookupField linhasAtendimentoRelatorios;
    @Inject
    protected CollectionLoader<Atendimento> atendimentoesDl;
    @Inject
    protected DateField<Date> dataInicioField;
    @Inject
    protected DateField<Date> dataFimField;
    @Inject
    protected LookupPickerField<AtendimentoEncaminhamento> idAtendimentoEncaminhamentoField;
    @Inject
    protected LookupPickerField<Tecnico> idTecnicoField;
    @Inject
    protected PopupButton popupButton1;
    @Inject
    protected TextField<String> anoField;
    @Inject
    protected TextField<String> mesField;
    @Inject
    protected LookupPickerField<HabilitacoesLiterarias> habilitacoesField;
    @Inject
    protected LookupPickerField<Utente> utenteField;
    @Inject
    protected LookupPickerField<TipoAtendimento> idTipoAtendimentoField;
    @Inject
    protected LookupPickerField<TipoCartao> idTipoCartaoField;
    @Inject
    protected LookupPickerField<TipoCivil> idTipoCivilField;
    @Inject
    private DataService dataService;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;


    @Inject
    private Dialogs dialogs;

    @Inject
    private UiComponents uiComponents;


    private void runReport()
    {
        Map<String, Object> reportParams = new HashMap<>();


        // Atendimentos Objectivos
        if (atendimentoObjectivosField.getValue() != null) {
            reportParams.put("at_id", atendimentoObjectivosField.getValue().getId());
        }

        // Datas de Atendimento



        if (dataInicioField.getValue() != null && dataFimField.getValue() == null)
        {
            reportParams.put("data_inicio", dataInicioField.getValue());
            reportParams.put("data_fim", dataInicioField.getValue());
        }
        if (dataInicioField.getValue() != null && dataFimField.getValue() != null)
        {
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


        // Atendimento de Encaminhamento

        if (idAtendimentoEncaminhamentoField.getValue() != null)
        {
            reportParams.put("atendimento_encaminhamento_id", idAtendimentoEncaminhamentoField.getValue().getId());
        }

        // Tecnico

        if (idTecnicoField.getValue() != null)
        {
            reportParams.put("tecnico_id", idTecnicoField.getValue().getId());
        }

        // Utente

        if (utenteField.getValue() != null)
        {
            reportParams.put("utente_id", utenteField.getValue().getId());
        }

        // tipo de atendimento


        if (idTipoAtendimentoField.getValue() != null)
        {
            reportParams.put("tipo_atendimento", idTipoAtendimentoField.getValue().getId());
        }

        if (habilitacoesField.getValue() != null)
        {
            reportParams.put("habilitacoes", habilitacoesField.getValue().getId());
        }

        if (idTipoCartaoField.getValue() != null)
        {
            reportParams.put("tipo_cartao", idTipoCartaoField.getValue().getId());
        }

        if (idTipoCivilField.getValue() != null)
        {
            reportParams.put("tipo_civil", idTipoCivilField.getValue().getId());
        }


        LoadContext<Report> lContext = new LoadContext<>(Report.class);
        //lContext.setQueryString("select r from report$Report r where r.id = '77f0f2de-cbe2-0490-0d12-ca427e4b2a4b' ");
        lContext.setQueryString("select r from report$Report r where r.id = '22c57146-ccdb-d33c-1212-8e0f1b917495' ");
        Report report = dataService.load(lContext);

        String templateCode = "DEFAULT";

        reportGuiManager.printReport(report, reportParams, templateCode, null);



    }

    @Subscribe("report")
    protected void onReportClick(Button.ClickEvent event) {
        runReport();
    }


    @Subscribe
    protected void onInit(InitEvent event) {

     

        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(50);
        list.add(100);
        list.add(200);
        list.add(500);
        list.add(1000);
        list.add(2000);
        list.add(5000);
        list.add(10000);
        linhasAtendimentoRelatorios.setOptionsList(list);
    }

    private void saveAsDoc() {
    }

    @Subscribe("reset_atendimentos_relatorios")
    protected void onReset_atendimentos_relatoriosClick(Button.ClickEvent event) {
        atendimentoObjectivosField.setValue(null);
        atendimentoesDl.removeParameter("atendimentoObjetivos");
        if (dataInicioField.getValue() != null)
        {
            dataInicioField.setValue(null);
            atendimentoesDl.removeParameter("dataInicio");
            atendimentoesDl.setQuery("select e from cmolhao_Atendimento e");
        }
        if (dataFimField.getValue() != null)
        {
            dataFimField.setValue(null);
            atendimentoesDl.removeParameter("dateFim");
            atendimentoesDl.setQuery("select e from cmolhao_Atendimento e");
            dataFimField.setEnabled(false);
        }

        anoField.setValue(null);
        atendimentoesDl.removeParameter("ano");

        idAtendimentoEncaminhamentoField.setValue(null);
        atendimentoesDl.removeParameter("idAtendimentoEncaminhamento");

        idTecnicoField.setValue(null);
        atendimentoesDl.removeParameter("idTecnico");

        habilitacoesField.setValue(null);
        atendimentoesDl.removeParameter("habilitacoes");

        utenteField.setValue(null);
        atendimentoesDl.removeParameter("idUtente");

        idTipoAtendimentoField.setValue(null);
        atendimentoesDl.removeParameter("idTipoAtendimento");

        idTipoCartaoField.setValue(null);
        atendimentoesDl.removeParameter("idTipoCartao");

        idTipoCivilField.setValue(null);
        atendimentoesDl.removeParameter("idTipoCivil");

        mesField.setValue(null);
        atendimentoesDl.removeParameter("mes");





        atendimentoesDl.load();
    }

    @Subscribe("search_atendimentos_relatorios")
    protected void onSearch_atendimentos_relatoriosClick(Button.ClickEvent event) {

        if (dataInicioField.getValue() != null && dataFimField.getValue() == null)
        {
            atendimentoesDl.setQuery("select e from cmolhao_Atendimento e where e.dataAtendimento = :dataInicio");
            atendimentoesDl.setParameter("dataInicio",  dataInicioField.getValue());
        }
        if (dataInicioField.getValue() != null && dataFimField.getValue() != null)
        {
            atendimentoesDl.setQuery("select e from cmolhao_Atendimento e where e.dataAtendimento >= :dataInicio and e.dataAtendimento <= :dateFim");
            atendimentoesDl.setParameter("dataInicio",  dataInicioField.getValue());
            atendimentoesDl.setParameter("dateFim",  dataFimField.getValue());
        }


        if (atendimentoObjectivosField.getValue() != null)
        {
            atendimentoesDl.setParameter("atendimentoObjetivos",  atendimentoObjectivosField.getValue().getId());
        } else {
            atendimentoesDl.removeParameter("atendimentoObjetivos");
        }

        if (idAtendimentoEncaminhamentoField.getValue() != null)
        {
            atendimentoesDl.setParameter("idAtendimentoEncaminhamento",  idAtendimentoEncaminhamentoField.getValue().getId());
        } else {
            atendimentoesDl.removeParameter("idAtendimentoEncaminhamento");
        }

        if (idTecnicoField.getValue() != null)
        {
            atendimentoesDl.setParameter("idTecnico",  idTecnicoField.getValue().getId());
        } else {
            atendimentoesDl.removeParameter("idTecnico");
        }

        if (habilitacoesField.getValue() != null)
        {
            atendimentoesDl.setParameter("habilitacoes",  habilitacoesField.getValue().getId());
        } else {
            atendimentoesDl.removeParameter("habilitacoes");
        }

        if (utenteField.getValue() != null)
        {
            atendimentoesDl.setParameter("idUtente",  utenteField.getValue().getId());
        } else {
            atendimentoesDl.removeParameter("idUtente");
        }

        if (idTipoAtendimentoField.getValue() != null)
        {
            atendimentoesDl.setParameter("idTipoAtendimento", idTipoAtendimentoField.getValue().getId());
        }
        else
        {
            atendimentoesDl.removeParameter("idTipoAtendimento");
        }

        if (idTipoCartaoField.getValue() != null)
        {
            atendimentoesDl.setParameter("idTipoCartao", idTipoCartaoField.getValue().getId());
        }
        else
        {
            atendimentoesDl.removeParameter("idTipoCartao");
        }

        if (idTipoCivilField.getValue() != null)
        {
            atendimentoesDl.setParameter("idTipoCivil", idTipoCivilField.getValue().getId());
        }
        else
        {
            atendimentoesDl.removeParameter("idTipoCivil");
        }


        atendimentoesDl.load();
    }

    @Subscribe("linhasAtendimentoRelatorios")
    protected void onLinhasAtendimentoRelatoriosValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            atendimentoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            atendimentoesDl.setMaxResults(0);
        }


        atendimentoesDl.load();
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



    @Subscribe("popupButton1.popupAction1")
    protected void onPopupButton1PopupAction1(Action.ActionPerformedEvent event) {
        SearchByYear();
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }


    private void SearchByYear() {
        dialogs.createInputDialog(this)
                .withCaption("Valor do ano de Atendimento para a pesquisa/impressao de dados no relatorio de atendimento")
                .withParameters(
                        InputParameter.stringParameter("ano").withCaption("Ano do Atendimento: ")
                )
                .withValidator(context -> {
                    String name = context.getValue("ano");
                    if (Strings.isNullOrEmpty(name)) {
                        return ValidationErrors.of("Introduza o ano de atendimento para a pesquisa/impressao de dados no atendimentos por relatorio");
                    }
                    else if (!isNumeric(name))
                    {
                        return ValidationErrors.of("o ano de atendimento deve ser um número inteiro");
                    }
                    else if (isNumeric(name) && name.length() != 4)
                    {
                        return ValidationErrors.of("o ano de atendimento deve possuir pelo menos 4 caracteres");

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
                                    dialog.closeWithDefaultAction();

                                    anoField.setValue(name);

                                    if (name != null)
                                    {
                                        atendimentoesDl.setParameter("ano",  Integer.valueOf(name));
                                    } else {
                                        atendimentoesDl.removeParameter("ano");
                                    }

                                    atendimentoesDl.load();


                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();
                                    String name = dialog.getValue("ano");
                                    dialog.closeWithDefaultAction();

                                    // funcao de imprimir os meses do atendimento por ano

                                    runReportAtendimentobyYear(name);



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

    private void runReportAtendimentobyYear(String name) {
        Map<String, Object> reportParams = new HashMap<>();

        reportParams.put("ano", name);

        LoadContext<Report> lContext = new LoadContext<>(Report.class);
        //lContext.setQueryString("select r from report$Report r where r.id = '77f0f2de-cbe2-0490-0d12-ca427e4b2a4b' ");
        lContext.setQueryString("select r from report$Report r where r.id = '0a2a1546-0c1c-2343-9e6a-3eeaeb8646a2' ");
        Report report = dataService.load(lContext);

        String templateCode = "mes_atendimento";

        String outputFile = "Mes de Atendimento no ano " + name;

        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);




    }

    private void runReportAtendimentobyMonth(String name) {
        Map<String, Object> reportParams = new HashMap<>();

        reportParams.put("mes", name);


        LoadContext<Report> lContext = new LoadContext<>(Report.class);
        //lContext.setQueryString("select r from report$Report r where r.id = '77f0f2de-cbe2-0490-0d12-ca427e4b2a4b' ");
        lContext.setQueryString("select r from report$Report r where r.id = '0ba7a735-c0ab-09d0-ec7f-4c5cd6bd7d32' ");
        Report report = dataService.load(lContext);

        String templateCode = "DEFAULT";

        String nome_mes = "";

        if (name.equals("01"))
        {
            nome_mes = "Janeiro";
        }
        if (name.equals("02"))
        {
            nome_mes = "Fevereiro";
        }
        if (name.equals("03"))
        {
            nome_mes = "Marco";
        }
        if (name.equals("04"))
        {
            nome_mes = "Abril";
        }
        if (name.equals("05"))
        {
            nome_mes = "Maio";
        }
        if (name.equals("06"))
        {
            nome_mes = "Junho";
        }
        if (name.equals("07"))
        {
            nome_mes = "Julho";
        }
        if (name.equals("08"))
        {
            nome_mes = "Agosto";
        }
        if (name.equals("09"))
        {
            nome_mes = "Setembro";
        }
        if (name.equals("10"))
        {
            nome_mes = "Outubro";
        }
        if (name.equals("11"))
        {
            nome_mes = "Novembro";
        }
        if (name.equals("12"))
        {
            nome_mes = "Dezembro";
        }

        String outputFile = "Ano de Atendimento no mes " + nome_mes;

        reportGuiManager.printReport(report, reportParams, templateCode, outputFile);




    }

    @Subscribe("popupButton1.popupAction2")
    protected void onPopupButton1PopupAction2(Action.ActionPerformedEvent event) {
        SearchByMonth();
    }

    private void SearchByMonth() {
        dialogs.createInputDialog(this)
                .withCaption("Valor do mes de Atendimento para a pesquisa/impressao de dados no relatorio de atendimento")
                .withParameters(
                        InputParameter.parameter("mes")
                                .withField(() -> {
                                    LookupField<String> field = uiComponents.create(LookupField.TYPE_STRING);
                                    field.setInputPrompt("Seleccione o mes do atendimento");


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
                    String name = context.getValue("mes");
                    if (Strings.isNullOrEmpty(name)) {
                        return ValidationErrors.of("Introduza o mes de atendimento para a pesquisa/impressao de dados no atendimentos por relatorio");
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
                                    String mes = dialog.getValue("mes");
                                    dialog.closeWithDefaultAction();

                                    mesField.setValue(mes);

                                    if (mes != null)
                                    {
                                        atendimentoesDl.setParameter("mes",  Integer.valueOf(mes));
                                    } else {
                                        atendimentoesDl.removeParameter("mes");
                                    }

                                    atendimentoesDl.load();









                                }),
                        InputDialogAction.action("imprimir")
                                .withCaption("Imprimir")
                                .withPrimary(false)
                                .withIcon("font-icon:PRINT")
                                .withHandler(actionEvent -> {
                                    InputDialog dialog = actionEvent.getInputDialog();
                                    String mes = dialog.getValue("mes");
                                    dialog.closeWithDefaultAction();

                                    runReportAtendimentobyMonth(mes);



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
}