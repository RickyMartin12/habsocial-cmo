package pt.cmolhao.web.atendimento;

import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.*;
import pt.cmolhao.web.moradores.MoradoresEdit;

import javax.inject.Inject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Atendimento.browse")
@UiDescriptor("atendimento-browse.xml")
@LookupComponent("atendimentoesTable")
@LoadDataBeforeShow
public class AtendimentoBrowse extends StandardLookup<Atendimento> {
    @Inject
    protected CollectionLoader<Atendimento> atendimentoesDl;
    @Inject
    protected GroupTable<Atendimento> atendimentoesTable;
    @Inject
    protected LookupField linhasAtendimento;
    @Inject
    protected LookupField<Utente> utenteField;
    @Inject
    protected LookupField<Tecnico> idTecnicoField;
    @Inject
    protected LookupField<TipoAtendimento> idTipoAtendimentoField;
    @Inject
    protected LookupField<AtendimentoObjetivos> idAtendimentoObjetivoField;
    @Inject
    protected LookupField<AtendimentoEncaminhamento> idAtendimentoEncaminhamentoField;
    @Inject
    protected DateField<Date> dataAtendimentoField;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Atendimento");
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
        linhasAtendimento.setOptionsList(list);

        atendimentoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(atendimentoesTable)
                        .newEntity()
                        .withInitializer(customer -> {

                            customer.setIdUtente(utenteField.getValue());
                            customer.setIdTecnico(idTecnicoField.getValue());
                            customer.setIdTipoAtendimento(idTipoAtendimentoField.getValue());
                            customer.setIdAtendimentoEncaminhamento(idAtendimentoEncaminhamentoField.getValue());
                            customer.setIdAtendimentoObjetivo(idAtendimentoObjetivoField.getValue());
                            if (dataAtendimentoField.getValue() != null)
                            {
                                customer.setDataAtendimento(dataAtendimentoField.getValue());
                            }

                        })
                        .withScreenClass(AtendimentoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("linhasAtendimento")
    protected void onLinhasAtendimentoValueChange(HasValue.ValueChangeEvent event) {
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

    @Subscribe("reset_atendimento")
    protected void onReset_atendimentoClick(Button.ClickEvent event) {
        utenteField.setValue(null);
        idTecnicoField.setValue(null);
        idTipoAtendimentoField.setValue(null);
        idAtendimentoObjetivoField.setValue(null);
        idAtendimentoEncaminhamentoField.setValue(null);
        dataAtendimentoField.setValue(null);
        linhasAtendimento.setValue(null);
        atendimentoesDl.removeParameter("idUtente");
        atendimentoesDl.removeParameter("idTecnico");
        atendimentoesDl.removeParameter("idTipoAtendimento");
        atendimentoesDl.removeParameter("idAtendimentoObjetivo");
        atendimentoesDl.removeParameter("idAtendimentoEncaminhamento");
        atendimentoesDl.removeParameter("dataAtendimento");
        atendimentoesDl.setMaxResults(0);
        atendimentoesDl.load();
    }

    @Subscribe("search_atendimento")
    protected void onSearch_atendimentoClick(Button.ClickEvent event) {

        if (utenteField.getValue() != null)
        {
            atendimentoesDl.setParameter("idUtente", utenteField.getValue().getId());
        }
        else
        {
            atendimentoesDl.removeParameter("idUtente");
        }

        if (idTecnicoField.getValue() != null)
        {
            atendimentoesDl.setParameter("idTecnico", idTecnicoField.getValue().getId());
        }
        else
        {
            atendimentoesDl.removeParameter("idTecnico");
        }

        if (idTipoAtendimentoField.getValue() != null)
        {
            atendimentoesDl.setParameter("idTipoAtendimento", idTipoAtendimentoField.getValue().getId());
        }
        else
        {
            atendimentoesDl.removeParameter("idTipoAtendimento");
        }

        if (idAtendimentoObjetivoField.getValue() != null)
        {
            atendimentoesDl.setParameter("idAtendimentoObjetivo", idAtendimentoObjetivoField.getValue().getId());
        }
        else
        {
            atendimentoesDl.removeParameter("idAtendimentoObjetivo");
        }


        if (idAtendimentoEncaminhamentoField.getValue() != null)
        {
            atendimentoesDl.setParameter("idAtendimentoEncaminhamento", idAtendimentoEncaminhamentoField.getValue().getId());
        }
        else
        {
            atendimentoesDl.removeParameter("idAtendimentoEncaminhamento");
        }

        if (dataAtendimentoField.getValue() != null)
        {
            atendimentoesDl.setParameter("dataAtendimento", dataAtendimentoField.getValue());
        }
        else
        {
            atendimentoesDl.removeParameter("dataAtendimento");
        }

        atendimentoesDl.load();


    }
}