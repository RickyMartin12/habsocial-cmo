package pt.cmolhao.web.tipoatendimento;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.TipoAjuda;
import pt.cmolhao.entity.TipoAtendimento;
import pt.cmolhao.web.tipologiafamiliar.TipologiaFamiliarEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoAtendimento.browse")
@UiDescriptor("tipo-atendimento-browse.xml")
@LookupComponent("tipoAtendimentoesTable")
@LoadDataBeforeShow
public class TipoAtendimentoBrowse extends StandardLookup<TipoAtendimento> {
    @Inject
    protected CollectionLoader<TipoAtendimento> tipoAtendimentoesDl;
    @Inject
    protected Table<TipoAtendimento> tipoAtendimentoesTable;
    @Inject
    protected LookupField linhasTipoAtendimento;
    @Inject
    protected LookupField tipoAtendimentoField;
    @Named("tipoAtendimentoesTable.remove")
    protected RemoveAction<TipoAtendimento> tipoAtendimentoesTableRemove;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Dialogs dialogs;

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
        linhasTipoAtendimento.setOptionsList(list);

        List<String> list_tipo_atendimento = new ArrayList<>();
        list_tipo_atendimento.add("Pedidos de Habitação por email");
        list_tipo_atendimento.add("Pedidos de Habitação por sinalização por outra instituição");
        list_tipo_atendimento.add("Plataforma eAA");
        list_tipo_atendimento.add("Atendimento técnico presencial");
        tipoAtendimentoField.setOptionsList(list_tipo_atendimento);

        tipoAtendimentoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoAtendimentoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (tipoAtendimentoField.getValue() != null)
                            {
                                customer.setTipoAtendimento(tipoAtendimentoField.getValue().toString());
                            }
                        })
                        .withScreenClass(TipoAtendimentoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe("linhasTipoAtendimento")
    protected void onLinhasTipoAtendimentoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoAtendimentoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoAtendimentoesDl.setMaxResults(0);
        }
        tipoAtendimentoesDl.load();
    }

    @Subscribe("reset_tipo_atendimento")
    protected void onReset_tipo_atendimentoClick(Button.ClickEvent event) {
        tipoAtendimentoField.setValue(null);
        tipoAtendimentoesDl.removeParameter("tipoAtendimento");
        tipoAtendimentoesDl.load();
    }

    @Subscribe("search_tipo_atendimento")
    protected void onSearch_tipo_atendimentoClick(Button.ClickEvent event) {
        if (tipoAtendimentoField.getValue() != null)
        {
            tipoAtendimentoesDl.setParameter("tipoAtendimento",  tipoAtendimentoField.getValue().toString());
        }
        else
        {
            tipoAtendimentoesDl.removeParameter("tipoAtendimento");
        }
        tipoAtendimentoesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Atendimento");
    }

    @Subscribe("tipoAtendimentoesTable.remove")
    protected void onTipoAtendimentoesTableRemove(Action.ActionPerformedEvent event) {
        tipoAtendimentoesTableRemove.setConfirmation(false);
        if (tipoAtendimentoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do tipo de atendimento")
                    .withMessage("Deve seleccionar pelo uns dos tipos de atendimento")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TipoAtendimento user = tipoAtendimentoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do tipo de atendimento número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do tipo de atendimento número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tipoAtendimentoesTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}