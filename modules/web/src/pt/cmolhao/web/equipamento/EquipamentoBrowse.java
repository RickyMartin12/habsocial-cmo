package pt.cmolhao.web.equipamento;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Equipamento;
import pt.cmolhao.web.apoios.ApoiosEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Equipamento.browse")
@UiDescriptor("equipamento-browse.xml")
@LookupComponent("equipamentoesTable")
@LoadDataBeforeShow
public class EquipamentoBrowse extends StandardLookup<Equipamento> {

    @Inject
    protected LookupField linhasEquipamento;
    @Inject
    protected CollectionLoader<Equipamento> equipamentoesDl;
    @Inject
    protected Table<Equipamento> equipamentoesTable;
    @Inject
    protected TextField<String> desc_equipamento_id;
    @Named("equipamentoesTable.remove")
    protected RemoveAction<Equipamento> equipamentoesTableRemove;
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
        linhasEquipamento.setOptionsList(list);

        equipamentoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(equipamentoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (desc_equipamento_id.getValue() != null)
                            {
                                customer.setDescricao(desc_equipamento_id.getValue());
                            }
                        })
                        .withScreenClass(EquipamentoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Equipamento");

    }

    @Subscribe("search_equipamento")
    protected void onSearch_equipamentoClick(Button.ClickEvent event) {
        // Descrição do Equipamento
        if (desc_equipamento_id.getValue() != null) {
            equipamentoesDl.setParameter("descricao",  "(?i)%" + desc_equipamento_id.getValue() + "%");
        } else {
            equipamentoesDl.removeParameter("descricao");
        }

        equipamentoesDl.load();
    }

    @Subscribe("reset_equipamento")
    protected void onReset_equipamentoClick(Button.ClickEvent event) {
        desc_equipamento_id.setValue(null);
        equipamentoesDl.removeParameter("descricao");
        equipamentoesDl.load();
    }

    @Subscribe("linhasEquipamento")
    protected void onLinhasEquipamentoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            equipamentoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            equipamentoesDl.setMaxResults(0);
        }
        equipamentoesDl.load();
    }

    @Subscribe("equipamentoesTable.remove")
    protected void onEquipamentoesTableRemove(Action.ActionPerformedEvent event) {
        equipamentoesTableRemove.setConfirmation(false);
        if (equipamentoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de equipamentos")
                    .withMessage("Deve seleccionar pelo um dos equipamentos")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Equipamento user = equipamentoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do equipamento número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha do equipamento número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        equipamentoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }

    }
}