package pt.cmolhao.web.tipoajuda;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.TipoAjuda;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoAjuda.browse")
@UiDescriptor("tipo-ajuda-browse.xml")
@LookupComponent("tipoAjudasTable")
@LoadDataBeforeShow
public class TipoAjudaBrowse extends StandardLookup<TipoAjuda> {

    @Inject
    protected LookupField linhasTipoAjuda;
    @Inject
    protected Table<TipoAjuda> tipoAjudasTable;
    @Inject
    protected CollectionLoader<TipoAjuda> tipoAjudasDl;
    @Inject
    protected TextField<String> desc_equipamento_id;
    @Named("tipoAjudasTable.remove")
    protected RemoveAction<TipoAjuda> tipoAjudasTableRemove;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Dialogs dialogs;


    @Subscribe
    public void onInit(InitEvent event) {
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
        linhasTipoAjuda.setOptionsList(list);

        tipoAjudasTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoAjudasTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (desc_equipamento_id.getValue() != null)
                            {
                                customer.setDescricaoTipoAjuda(desc_equipamento_id.getValue());
                            }
                        })
                        .withScreenClass(TipoAjudaEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }



    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Tipos de Apoio");
    }

    @Subscribe("linhasTipoAjuda")
    protected void onLinhasTipoAjudaValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoAjudasDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoAjudasDl.setMaxResults(0);
        }
        tipoAjudasDl.load();
    }

    @Subscribe("reset_tipo_ajuda")
    protected void onReset_tipo_ajudaClick(Button.ClickEvent event) {
        desc_equipamento_id.setValue(null);
        tipoAjudasDl.removeParameter("descricaoTipoAjuda");
        tipoAjudasDl.load();
    }

    @Subscribe("search_tipo_ajuda")
    protected void onSearch_tipo_ajudaClick(Button.ClickEvent event) {
        // Descrição de Tipo Ajuda

        if (desc_equipamento_id.getValue() != null) {
            tipoAjudasDl.setParameter("descricaoTipoAjuda",  "(?i)%" + desc_equipamento_id.getValue() + "%");
        } else {
            tipoAjudasDl.removeParameter("descricaoTipoAjuda");
        }

        tipoAjudasDl.load();
    }

    @Subscribe("tipoAjudasTable.remove")
    protected void onTipoAjudasTableRemove(Action.ActionPerformedEvent event) {
        tipoAjudasTableRemove.setConfirmation(false);
        if (tipoAjudasTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do tipo de apoio")
                    .withMessage("Deve seleccionar pelo uns dos tipos de apoio")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TipoAjuda user = tipoAjudasTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do tipo de apoio número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do tipo de apoio número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tipoAjudasTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }











}