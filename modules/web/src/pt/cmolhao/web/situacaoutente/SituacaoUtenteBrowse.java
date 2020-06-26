package pt.cmolhao.web.situacaoutente;

import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.SituacaoUtente;
import pt.cmolhao.entity.TiposSituacoesUtentes;
import pt.cmolhao.entity.Utente;
import pt.cmolhao.web.rendimentosutente.RendimentosUtenteEdit;

import javax.inject.Inject;
import java.sql.Date;
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
    protected GroupTable<SituacaoUtente> situacaoUtentesTable;
    @Inject
    protected LookupField linhasSituacaoUtente;
    @Inject
    protected LookupField<Utente> idUtenteField;
    @Inject
    protected LookupField<TiposSituacoesUtentes> idTiposSituacaoUtentesField;
    @Inject
    protected DateField<Date> dataInicioField;
    @Inject
    protected DateField<Date> dataFimField;
    @Inject
    private ScreenBuilders screenBuilders;

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

    @Subscribe("reset_situacao_utente")
    protected void onReset_situacao_utenteClick(Button.ClickEvent event) {
        linhasSituacaoUtente.setValue(null);
        idUtenteField.setValue(null);
        idTiposSituacaoUtentesField.setValue(null);
        dataInicioField.setValue(null);
        dataFimField.setValue(null);
        situacaoUtentesDl.removeParameter("idUtente");
        situacaoUtentesDl.removeParameter("idTiposSituacaoUtentes");
        situacaoUtentesDl.removeParameter("dataInicio");
        situacaoUtentesDl.removeParameter("dataFim");
        situacaoUtentesDl.load();
    }

    @Subscribe("search_situacao_utente")
    protected void onSearch_situacao_utenteClick(Button.ClickEvent event) {
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
            situacaoUtentesDl.removeParameter("dataInicio");
        }

        if (dataFimField.getValue() != null)
        {
            situacaoUtentesDl.setParameter("dataFim", dataFimField.getValue());
        }
        else
        {
            situacaoUtentesDl.removeParameter("dataFim");
        }

        situacaoUtentesDl.load();

    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Situações dos Utentes");
    }
}