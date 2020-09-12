package pt.cmolhao.web.profissao;

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
import pt.cmolhao.entity.Profissao;
import pt.cmolhao.web.habilitacoesliterarias.HabilitacoesLiterariasEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Profissao.browse")
@UiDescriptor("profissao-browse.xml")
@LookupComponent("profissaosTable")
@LoadDataBeforeShow
public class ProfissaoBrowse extends StandardLookup<Profissao> {
    @Inject
    protected LookupField linhasProfissao;
    @Inject
    protected Table<Profissao> profissaosTable;
    @Inject
    protected CollectionLoader<Profissao> profissaosDl;
    @Inject
    protected TextField<String> nome_prof_id;
    @Named("profissaosTable.remove")
    protected RemoveAction<Profissao> profissaosTableRemove;
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
        linhasProfissao.setOptionsList(list);

        profissaosTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(profissaosTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (nome_prof_id.getValue() != null)
                            {
                                customer.setNome(nome_prof_id.getValue());
                            }
                        })
                        .withScreenClass(ProfissaoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Profissões");

    }

    @Subscribe("linhasProfissao")
    protected void onLinhasProfissaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            profissaosDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            profissaosDl.setMaxResults(0);
        }
        profissaosDl.load();
    }

    @Subscribe("reset_profissao")
    protected void onReset_profissaoClick(Button.ClickEvent event) {
        nome_prof_id.setValue(null);
        profissaosDl.removeParameter("nome");
        profissaosDl.load();
    }

    @Subscribe("search_profissao")
    protected void onSearch_profissaoClick(Button.ClickEvent event) {
        if (nome_prof_id.getValue() != null) {
            profissaosDl.setParameter("nome",  "(?i)%" + nome_prof_id.getValue() + "%");
        } else {
            profissaosDl.removeParameter("nome");
        }

        profissaosDl.load();
    }

    @Subscribe("profissaosTable.remove")
    protected void onProfissaosTableRemove(Action.ActionPerformedEvent event) {
        profissaosTableRemove.setConfirmation(false);
        if (profissaosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção das profissões")
                    .withMessage("Deve seleccionar pelo um das profissões")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Profissao user = profissaosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da profissão número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da profissão número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        profissaosTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }

    }
}