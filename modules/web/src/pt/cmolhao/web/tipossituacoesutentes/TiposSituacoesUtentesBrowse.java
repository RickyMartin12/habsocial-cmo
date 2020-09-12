package pt.cmolhao.web.tipossituacoesutentes;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.TipoRendimento;
import pt.cmolhao.entity.TiposSituacoesUtentes;
import pt.cmolhao.web.tiporendimento.TipoRendimentoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TiposSituacoesUtentes.browse")
@UiDescriptor("tipos-situacoes-utentes-browse.xml")
@LookupComponent("tiposSituacoesUtentesesTable")
@LoadDataBeforeShow
public class TiposSituacoesUtentesBrowse extends StandardLookup<TiposSituacoesUtentes> {
    @Inject
    protected CollectionLoader<TiposSituacoesUtentes> tiposSituacoesUtentesesDl;
    @Inject
    protected LookupField linhasTiposSituacoesUtentes;
    @Inject
    protected TextField<String> tipoSituacaoUtentesField;
    @Inject
    protected Table<TiposSituacoesUtentes> tiposSituacoesUtentesesTable;
    @Named("tiposSituacoesUtentesesTable.remove")
    protected RemoveAction<TiposSituacoesUtentes> tiposSituacoesUtentesesTableRemove;
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
        linhasTiposSituacoesUtentes.setOptionsList(list);

        tiposSituacoesUtentesesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tiposSituacoesUtentesesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (tipoSituacaoUtentesField.getValue() != null)
                            {
                                customer.setDescricaoSituacao(tipoSituacaoUtentesField.getValue());
                            }
                        })
                        .withScreenClass(TiposSituacoesUtentesEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("linhasTiposSituacoesUtentes")
    protected void onLinhasTiposSituacoesUtentesValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tiposSituacoesUtentesesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tiposSituacoesUtentesesDl.setMaxResults(0);
        }
        tiposSituacoesUtentesesDl.load();
    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Situações dos Utentes");
    }

    @Subscribe("search_tipo_situacao_utentes")
    protected void onSearch_tipo_situacao_utentesClick(Button.ClickEvent event) {
        if (tipoSituacaoUtentesField.getValue() != null)
        {
            tiposSituacoesUtentesesDl.setParameter("descricaoSituacao",  "(?i)%" + tipoSituacaoUtentesField.getValue() + "%");
        }
        else
        {
            tiposSituacoesUtentesesDl.removeParameter("descricaoSituacao");
        }
        tiposSituacoesUtentesesDl.load();
    }

    @Subscribe("reset_tipo_situacao_utentes")
    protected void onReset_tipo_situacao_utentesClick(Button.ClickEvent event) {
        tipoSituacaoUtentesField.setValue(null);
        tiposSituacoesUtentesesDl.removeParameter("descricaoSituacao");
        tiposSituacoesUtentesesDl.load();
    }

    @Subscribe("tiposSituacoesUtentesesTable.remove")
    protected void onTiposSituacoesUtentesesTableRemove(Action.ActionPerformedEvent event) {
        tiposSituacoesUtentesesTableRemove.setConfirmation(false);
        if (tiposSituacoesUtentesesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do tipo de situação de utentes")
                    .withMessage("Deve seleccionar pelo um dos tipos de situações de utentes")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TiposSituacoesUtentes user = tiposSituacoesUtentesesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do tipo de situação de utente número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do tipo de situação de utente número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tiposSituacoesUtentesesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}