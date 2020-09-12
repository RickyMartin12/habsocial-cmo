package pt.cmolhao.web.subredetrabalho;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.RedeTrabalho;
import pt.cmolhao.entity.SubRedeTrabalho;
import pt.cmolhao.web.redetrabalho.RedeTrabalhoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_SubRedeTrabalho.browse")
@UiDescriptor("sub-rede-trabalho-browse.xml")
@LookupComponent("subRedeTrabalhoesTable")
@LoadDataBeforeShow
public class SubRedeTrabalhoBrowse extends StandardLookup<SubRedeTrabalho> {


    @Inject
    protected CollectionContainer<RedeTrabalho> redeTrabalhoDc;
    @Inject
    protected CollectionLoader<SubRedeTrabalho> subRedeTrabalhoesDl;
    @Named("subRedeTrabalhoesTable.remove")
    protected RemoveAction<SubRedeTrabalho> subRedeTrabalhoesTableRemove;
    @Inject
    protected GroupTable<SubRedeTrabalho> subRedeTrabalhoesTable;
    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;
    @Inject
    protected TextField<String> nomeField;
    @Inject
    protected LookupField linhasSubRedeTrabalho;

    @Inject
    protected ScreenBuilders screenBuilders;

    @Inject
    private Notifications notifications;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onInit(InitEvent event) {

        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(20);
        list.add(50);
        list.add(100);
        list.add(200);
        list.add(500);
        list.add(1000);
        list.add(2000);
        list.add(5000);
        list.add(10000);
        linhasSubRedeTrabalho.setOptionsList(list);

        subRedeTrabalhoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(subRedeTrabalhoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (nomeField.getValue() != null)
                            {
                                customer.setNome(nomeField.getValue());
                            }
                            customer.setIdRedeTrabalho(idRedeTrabalhoField.getValue());
                        })
                        .withScreenClass(SubRedeTrabalhoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe("reset_sub_rede_trabalho")
    protected void onReset_sub_rede_trabalhoClick(Button.ClickEvent event) {
        nomeField.setValue(null);
        idRedeTrabalhoField.setValue(null);
        subRedeTrabalhoesDl.removeParameter("idRedeTrabalho");
        subRedeTrabalhoesDl.removeParameter("nome");
        subRedeTrabalhoesDl.load();
    }

    @Subscribe("search_sub_rede_trabalho")
    protected void onSearch_sub_rede_trabalhoClick(Button.ClickEvent event) {
        if (nomeField.getValue() != null) {
            subRedeTrabalhoesDl.setParameter("nome",  "(?i)%" + nomeField.getValue() + "%");
        } else {
            subRedeTrabalhoesDl.removeParameter("nome");
        }

        if (idRedeTrabalhoField.getValue() != null) {
            subRedeTrabalhoesDl.setParameter("idRedeTrabalho",  idRedeTrabalhoField.getValue().getId());
        } else {
            subRedeTrabalhoesDl.removeParameter("idRedeTrabalho");
        }


        subRedeTrabalhoesDl.load();
    }

    @Subscribe("subRedeTrabalhoesTable.remove")
    protected void onSubRedeTrabalhoesTableRemove(Action.ActionPerformedEvent event) {
        subRedeTrabalhoesTableRemove.setConfirmation(false);
        if (subRedeTrabalhoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção das sub redes de trabalho")
                    .withMessage("Deve seleccionar pelo um das sub redes de trabalho")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            SubRedeTrabalho user = subRedeTrabalhoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela sub rede de trabalho número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela sub rede de trabalho número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        subRedeTrabalhoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

    @Subscribe("linhasSubRedeTrabalho")
    protected void onLinhasSubRedeTrabalhoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            subRedeTrabalhoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            subRedeTrabalhoesDl.setMaxResults(0);
        }
        subRedeTrabalhoesDl.load();
    }
}