package pt.cmolhao.web.redetrabalho;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.FotosValencia;
import pt.cmolhao.entity.RedeTrabalho;
import pt.cmolhao.web.fotosvalencia.FotosValenciaEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_RedeTrabalho.browse")
@UiDescriptor("rede-trabalho-browse.xml")
@LookupComponent("redeTrabalhoesTable")
@LoadDataBeforeShow
public class RedeTrabalhoBrowse extends StandardLookup<RedeTrabalho> {
    @Inject
    protected LookupField linhasRedeTrabalho;
    @Inject
    protected CollectionContainer<RedeTrabalho> redeTrabalhoesDc;
    @Inject
    protected CollectionLoader<RedeTrabalho> redeTrabalhoesDl;
    @Inject
    protected TextField<String> nomeRedeTrabalho;
    @Named("redeTrabalhoesTable.remove")
    protected RemoveAction<RedeTrabalho> redeTrabalhoesTableRemove;
    @Inject
    protected GroupTable<RedeTrabalho> redeTrabalhoesTable;

    @Inject
    private Dialogs dialogs;

    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Redes de Trabalho");
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
        linhasRedeTrabalho.setOptionsList(list);

        redeTrabalhoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(redeTrabalhoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (nomeRedeTrabalho.getValue() != null)
                            {
                                customer.setNome(nomeRedeTrabalho.getValue());
                            }
                        })
                        .withScreenClass(RedeTrabalhoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe("linhasRedeTrabalho")
    protected void onLinhasRedeTrabalhoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            redeTrabalhoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            redeTrabalhoesDl.setMaxResults(0);
        }
        redeTrabalhoesDl.load();
    }

    @Subscribe("search_rede_trabalho")
    protected void onSearch_rede_trabalhoClick(Button.ClickEvent event) {
        if (nomeRedeTrabalho.getValue() != null) {
            redeTrabalhoesDl.setParameter("nome",  "(?i)%" + nomeRedeTrabalho.getValue() + "%");
        } else {
            redeTrabalhoesDl.removeParameter("nome");
        }


        redeTrabalhoesDl.load();
    }

    @Subscribe("reset_search_rede_trabalho")
    protected void onReset_search_rede_trabalhoClick(Button.ClickEvent event) {
        nomeRedeTrabalho.setValue(null);
        redeTrabalhoesDl.removeParameter("nome");
        redeTrabalhoesDl.load();
    }

    @Subscribe("redeTrabalhoesTable.remove")
    protected void onRedeTrabalhoesTableRemove(Action.ActionPerformedEvent event) {
        redeTrabalhoesTableRemove.setConfirmation(false);
        if (redeTrabalhoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção das redes de trabalho")
                    .withMessage("Deve seleccionar pelo um das redes de trabalho")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            RedeTrabalho user = redeTrabalhoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da rede de trabalho número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da rede de trabalho número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        redeTrabalhoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }




}