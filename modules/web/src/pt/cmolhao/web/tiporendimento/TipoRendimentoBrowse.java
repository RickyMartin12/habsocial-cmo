package pt.cmolhao.web.tiporendimento;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.TipoRelacionamentoUtentes;
import pt.cmolhao.entity.TipoRendimento;
import pt.cmolhao.web.tiporelacionamentoutentes.TipoRelacionamentoUtentesEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoRendimento.browse")
@UiDescriptor("tipo-rendimento-browse.xml")
@LookupComponent("tipoRendimentoesTable")
@LoadDataBeforeShow
public class TipoRendimentoBrowse extends StandardLookup<TipoRendimento> {
    @Inject
    protected LookupField linhastipoRendimento;
    @Inject
    protected CollectionLoader<TipoRendimento> tipoRendimentoesDl;
    @Inject
    protected Table<TipoRendimento> tipoRendimentoesTable;
    @Inject
    protected TextField<String> tipoRendimentoField;
    @Named("tipoRendimentoesTable.remove")
    protected RemoveAction<TipoRendimento> tipoRendimentoesTableRemove;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private Dialogs dialogs;

    @Subscribe("linhastipoRendimento")
    protected void onLinhastipoRendimentoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoRendimentoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoRendimentoesDl.setMaxResults(0);
        }
        tipoRendimentoesDl.load();
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
        linhastipoRendimento.setOptionsList(list);

        tipoRendimentoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoRendimentoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (tipoRendimentoField.getValue() != null)
                            {
                                customer.setTipoRendimento(tipoRendimentoField.getValue());
                            }
                        })
                        .withScreenClass(TipoRendimentoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("reset_tipo_rendimento_utentes")
    protected void onReset_tipo_rendimento_utentesClick(Button.ClickEvent event) {
        tipoRendimentoField.setValue(null);
        tipoRendimentoesDl.removeParameter("tipoRendimento");
        tipoRendimentoesDl.load();
    }

    @Subscribe("search_tipo_rendimento_utentes")
    protected void onSearch_tipo_rendimento_utentesClick(Button.ClickEvent event) {
        if (tipoRendimentoField.getValue() != null)
        {
            tipoRendimentoesDl.setParameter("tipoRendimento",  "(?i)%" + tipoRendimentoField.getValue() + "%");
        }
        else
        {
            tipoRendimentoesDl.removeParameter("tipoRendimento");
        }
        tipoRendimentoesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Rendimentos");
    }

    @Subscribe("tipoRendimentoesTable.remove")
    protected void onTipoRendimentoesTableRemove(Action.ActionPerformedEvent event) {
        tipoRendimentoesTableRemove.setConfirmation(false);
        if (tipoRendimentoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do tipo de rendimento")
                    .withMessage("Deve seleccionar pelo um dos tipos de rendimento")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TipoRendimento user = tipoRendimentoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do tipo de rendimento número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do tipo de rendimento número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tipoRendimentoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

}