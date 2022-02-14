package pt.cmolhao.web.tipo_cartao;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.TipoCartao;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoCartao.browse")
@UiDescriptor("tipo-cartao-browse.xml")
@LookupComponent("tipoCartaosTable")
@LoadDataBeforeShow
public class TipoCartaoBrowse extends StandardLookup<TipoCartao> {
    @Inject
    protected CollectionContainer<TipoCartao> tipoCartaosDc;
    @Inject
    protected CollectionLoader<TipoCartao> tipoCartaosDl;
    @Inject
    protected TextField<String> tipoField;
    @Inject
    protected GroupTable<TipoCartao> tipoCartaosTable;
    @Named("tipoCartaosTable.remove")
    protected RemoveAction<TipoCartao> tipoCartaosTableRemove;
    @Inject
    protected LookupField linhasTipoCartao;

    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private DataManager dataManager;
    @Inject
    private Notifications notifications;


    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Cartão de Utentes");

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
        linhasTipoCartao.setOptionsList(list);

        tipoCartaosTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoCartaosTable)
                        .newEntity()
                        .withInitializer(customer -> {

                            if(tipoField.getValue() != null)
                            {
                                customer.setTipo(tipoField.getValue());
                            }
                        })
                        .withScreenClass(TipoCartaoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );



    }

    @Subscribe("reset_tipo_cartao")
    protected void onReset_tipo_cartaoClick(Button.ClickEvent event) {
        tipoField.setValue(null);
        tipoCartaosDl.removeParameter("tipo");
        tipoCartaosDl.load();
    }

    @Subscribe("search_tipo_cartao")
    protected void onSearch_tipo_cartaoClick(Button.ClickEvent event) {
        if (tipoField.getValue() != null) {
            tipoCartaosDl.setParameter("tipo", "(?i)%" + tipoField.getValue() + "%");
        } else {
            tipoCartaosDl.removeParameter("tipo");
        }
    }

    @Subscribe("linhasTipoCartao")
    protected void onLinhasTipoCartaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoCartaosDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoCartaosDl.setMaxResults(0);
        }
        tipoCartaosDl.load();
    }

    @Subscribe("tipoCartaosTable.remove")
    protected void onTipoCartaosTableRemove(Action.ActionPerformedEvent event) {
        tipoCartaosTableRemove.setConfirmation(false);
        if (tipoCartaosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do tipo de cartão")
                    .withMessage("Deve seleccionar pelo um dos tipos de cartão")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TipoCartao user = tipoCartaosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do tipo de cartão número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do tipo de cartão número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tipoCartaosTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}