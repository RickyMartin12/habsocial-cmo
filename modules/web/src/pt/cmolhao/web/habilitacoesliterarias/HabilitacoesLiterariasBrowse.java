package pt.cmolhao.web.habilitacoesliterarias;

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
import pt.cmolhao.entity.HabilitacoesLiterarias;
import pt.cmolhao.web.grauescolaridade.GrauEscolaridadeEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_HabilitacoesLiterarias.browse")
@UiDescriptor("habilitacoes-literarias-browse.xml")
@LookupComponent("habilitacoesLiterariasesTable")
@LoadDataBeforeShow
public class HabilitacoesLiterariasBrowse extends StandardLookup<HabilitacoesLiterarias> {

    @Inject
    protected LookupField linhasHabilitacoesLiterarias;
    @Inject
    protected CollectionLoader<HabilitacoesLiterarias> habilitacoesLiterariasesDl;
    @Inject
    protected Table<HabilitacoesLiterarias> habilitacoesLiterariasesTable;
    @Inject
    protected LookupField desc_habilitacoes_literarias_id;
    @Named("habilitacoesLiterariasesTable.remove")
    protected RemoveAction<HabilitacoesLiterarias> habilitacoesLiterariasesTableRemove;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Habilitações Literárias");
    }

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
        linhasHabilitacoesLiterarias.setOptionsList(list);

        List<String> list_hab = new ArrayList<>();
        list_hab.add("Sem Habilitação");
        list_hab.add("Menos de 4 Anos de Escolaridade");
        list_hab.add("4 Anos de Escolaridade (1.º ciclo do ensino básico)");
        list_hab.add("6 Anos de escolaridade (2.º ciclo do ensino básico)");
        list_hab.add("9.º ano (3.º ciclo do ensino básico)");
        list_hab.add("11.º ano");
        list_hab.add("12.º ano (ensino secundário)");
        list_hab.add("Curso tecnológico/profissional/outros (nível III)*");
        list_hab.add("Bacharelato");
        list_hab.add("Licenciatura");
        list_hab.add("Pós-Graduação");
        list_hab.add("Mestrado");
        list_hab.add("Doutoramento");
        list_hab.add("Curso de especialização tecnológica");
        desc_habilitacoes_literarias_id.setOptionsList(list_hab);



        habilitacoesLiterariasesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(habilitacoesLiterariasesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (desc_habilitacoes_literarias_id.getValue() != null)
                            {
                                customer.setDescricao(desc_habilitacoes_literarias_id.getValue().toString());
                            }
                        })
                        .withScreenClass(HabilitacoesLiterariasEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe("reset_grau_escolaridade")
    protected void onReset_grau_escolaridadeClick(Button.ClickEvent event) {
        desc_habilitacoes_literarias_id.setValue(null);
        habilitacoesLiterariasesDl.removeParameter("descricao");
        habilitacoesLiterariasesDl.load();
    }

    @Subscribe("search_grau_escolaridade")
    protected void onSearch_grau_escolaridadeClick(Button.ClickEvent event) {
        if (desc_habilitacoes_literarias_id.getValue() != null) {
            habilitacoesLiterariasesDl.setParameter("descricao",  desc_habilitacoes_literarias_id.getValue().toString());
        } else {
            habilitacoesLiterariasesDl.removeParameter("descricao");
        }

        habilitacoesLiterariasesDl.load();
    }

    @Subscribe("linhasHabilitacoesLiterarias")
    protected void onLinhasHabilitacoesLiterariasValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            habilitacoesLiterariasesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            habilitacoesLiterariasesDl.setMaxResults(0);
        }
        habilitacoesLiterariasesDl.load();
    }

    @Subscribe("habilitacoesLiterariasesTable.remove")
    protected void onHabilitacoesLiterariasesTableRemove(Action.ActionPerformedEvent event) {
        habilitacoesLiterariasesTableRemove.setConfirmation(false);
        if (habilitacoesLiterariasesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de habilitações literárias")
                    .withMessage("Deve seleccionar pelo um das habilitações literárias")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            HabilitacoesLiterarias user = habilitacoesLiterariasesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da habilitação literária número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da habilitação literária número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        habilitacoesLiterariasesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}