package pt.cmolhao.web.atendimento;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.*;
import pt.cmolhao.web.moradores.MoradoresEdit;

import javax.inject.Inject;
import javax.inject.Named;
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
    protected LookupPickerField<Utente> utenteField;
    @Inject
    protected LookupPickerField<Tecnico> idTecnicoField;
    @Inject
    protected LookupPickerField<TipoAtendimento> idTipoAtendimentoField;
    @Named("atendimentoesTable.remove")
    protected RemoveAction<Atendimento> atendimentoesTableRemove;
    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private Dialogs dialogs;

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
        atendimentoesDl.removeParameter("idUtente");
        atendimentoesDl.removeParameter("idTecnico");
        atendimentoesDl.removeParameter("idTipoAtendimento");
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

        atendimentoesDl.load();


    }

    @Subscribe("atendimentoesTable.remove")
    protected void onAtendimentoesTableRemove(Action.ActionPerformedEvent event) {
        atendimentoesTableRemove.setConfirmation(false);
        if (atendimentoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos atendimentos")
                    .withMessage("Deve seleccionar pelo um dos atendimentos")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Atendimento user = atendimentoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do atendimento número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do atendimento número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        atendimentoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}