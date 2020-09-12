package pt.cmolhao.web.situacaoutente;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.ProjectosIntervencao;
import pt.cmolhao.entity.SituacaoUtente;
import pt.cmolhao.entity.TiposSituacoesUtentes;
import pt.cmolhao.entity.Utente;
import pt.cmolhao.web.rendimentosutente.RendimentosUtenteEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_SituacaoUtente.browse")
@UiDescriptor("situacao-utente-browse.xml")
@LookupComponent("situacaoUtentesTable")
@LoadDataBeforeShow
public class SituacaoUtenteBrowse extends StandardLookup<SituacaoUtente> {

    @Inject
    protected CollectionLoader<SituacaoUtente> situacaoUtentesDl;

    @Inject
    protected DateField<Date> dataInicioField;
    @Inject
    protected DateField<Date> dataFimField;
    @Named("situacaoUtentesTable.remove")
    protected RemoveAction<SituacaoUtente> situacaoUtentesTableRemove;

    @Inject
    private Notifications notifications;

    @Inject
    protected LookupPickerField<Utente> idUtenteField;
    @Inject
    protected LookupPickerField<TiposSituacoesUtentes> idTiposSituacaoUtentesField;

    @Inject
    protected GroupTable<SituacaoUtente> situacaoUtentesTable;
    @Inject
    protected LookupField linhasSituacaoUtente;

    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Situações dos Utentes");
    }


    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) throws ParseException {

        java.util.Date date = null;
        java.util.Date date2 = null;
        String dataInicio = "0000-01-01";
        String dataFim = "9999-12-31";
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.parse(dataInicio);

            DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            date2 = formatter2.parse(dataFim);

            situacaoUtentesDl.setParameter("dataInicio", date);
            situacaoUtentesDl.setParameter("dataFim", date2);

        } catch (ParseException e) {
            throw e;
        }
        situacaoUtentesDl.load();
    }

    @Subscribe("search_situacao_utente")
    protected void onSearch_situacao_utenteClick(Button.ClickEvent event) throws ParseException {

        if (idUtenteField.getValue() != null)
        {
            situacaoUtentesDl.setParameter("idUtente",  idUtenteField.getValue().getId());
        } else {
            situacaoUtentesDl.removeParameter("idUtente");
        }

        if (idTiposSituacaoUtentesField.getValue() != null)
        {
            situacaoUtentesDl.setParameter("idTiposSituacaoUtentes",  idTiposSituacaoUtentesField.getValue().getId());
        } else {
            situacaoUtentesDl.removeParameter("idTiposSituacaoUtentes");
        }


        if (dataInicioField.getValue() != null)
        {
            situacaoUtentesDl.setParameter("dataInicio", dataInicioField.getValue());
        }
        else
        {
            java.util.Date date2 = null;
            String dataInicio = "0000-01-01";
            try {
                DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                date2 = formatter2.parse(dataInicio);
                situacaoUtentesDl.setParameter("dataInicio", date2);
            } catch (ParseException e) {
                throw e;
            }
            //situacaoUtentesDl.removeParameter("dataInicio");
        }
        if (dataFimField.getValue() != null)
        {
            situacaoUtentesDl.setParameter("dataFim", dataFimField.getValue());
        }
        else
        {
            java.util.Date date2 = null;
            String dataFim = "9999-12-31";
            try {
                DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                date2 = formatter2.parse(dataFim);
                situacaoUtentesDl.setParameter("dataFim", date2);
            } catch (ParseException e) {
                throw e;
            }
            //situacaoUtentesDl.removeParameter("dataFim");
        }

        situacaoUtentesDl.load();
    }

    @Subscribe("reset_situacao_utente")
    protected void onReset_situacao_utenteClick(Button.ClickEvent event) throws ParseException {
        idUtenteField.setValue(null);
        idTiposSituacaoUtentesField.setValue(null);
        dataInicioField.setValue(null);
        dataFimField.setValue(null);
        situacaoUtentesDl.removeParameter("idUtente");
        situacaoUtentesDl.removeParameter("idTiposSituacaoUtentes");
        if (dataInicioField.getValue() == null)
        {
            java.util.Date date2 = null;
            String dataInicio = "0000-01-01";
            try {
                DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                date2 = formatter2.parse(dataInicio);
                situacaoUtentesDl.setParameter("dataInicio", date2);
            } catch (ParseException e) {
                throw e;
            }
        }
        if (dataFimField.getValue() == null)
        {
            java.util.Date date2 = null;
            String dataFim = "9999-12-31";
            try {
                DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                date2 = formatter2.parse(dataFim);
                situacaoUtentesDl.setParameter("dataFim", date2);
            } catch (ParseException e) {
                throw e;
            }

        }
        situacaoUtentesDl.load();
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
        linhasSituacaoUtente.setOptionsList(list);

        situacaoUtentesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(situacaoUtentesTable)
                        .newEntity()
                        .withInitializer(customer -> {

                            customer.setIdUtente(idUtenteField.getValue());
                            customer.setIdTiposSituacaoUtentes(idTiposSituacaoUtentesField.getValue());
                            if (dataInicioField.getValue() != null)
                            {
                                customer.setDataInicio(dataInicioField.getValue());
                            }
                            if (dataFimField.getValue() != null)
                            {
                                customer.setDataFim(dataFimField.getValue());
                            }
                        })
                        .withScreenClass(SituacaoUtenteEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }


    @Subscribe("dataInicioField")
    protected void onDataInicioFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
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

    @Subscribe("linhasSituacaoUtente")
    protected void onLinhasSituacaoUtenteValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            situacaoUtentesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            situacaoUtentesDl.setMaxResults(0);
        }
        situacaoUtentesDl.load();
    }

    @Subscribe("situacaoUtentesTable.remove")
    protected void onSituacaoUtentesTableRemove(Action.ActionPerformedEvent event) {
        situacaoUtentesTableRemove.setConfirmation(false);
        if (situacaoUtentesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção da Situação dos utente")
                    .withMessage("Deve seleccionar pelo a situação do utente")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            SituacaoUtente user = situacaoUtentesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da situação do utente número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da situação do utente número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        situacaoUtentesTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}