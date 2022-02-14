package pt.cmolhao.web.pareceres_instituicao;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.*;
import pt.cmolhao.web.pareceres.PareceresEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@UiController("cmolhao_PareceresInstituicoes.browse")
@UiDescriptor("pareceres-instituicoes-browse.xml")
@LookupComponent("pareceresInstituicoesesTable")
@LoadDataBeforeShow
public class PareceresInstituicoesBrowse extends StandardLookup<PareceresInstituicoes> {
    @Inject
    protected LookupField linhasTipoParaceres;
    @Inject
    protected LookupPickerField<Pareceres> idParecerField;
    @Inject
    protected LookupPickerField<TipoPareceres> idTipoParecerField;
    @Inject
    protected CollectionContainer<Pareceres> pareceresesDc;
    @Inject
    protected CollectionContainer<TipoPareceres> tipoPareceresesDc;
    @Inject
    protected CollectionContainer<Instituicoes> InstituicaoDc;
    @Inject
    protected GroupTable<PareceresInstituicoes> pareceresInstituicoesesTable;
    @Named("pareceresInstituicoesesTable.remove")
    protected RemoveAction<PareceresInstituicoes> pareceresInstituicoesesTableRemove;
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected CollectionLoader<PareceresInstituicoes> pareceresInstituicoesesDl;
    @Inject
    private Notifications notifications;

    @Inject
    private Dialogs dialogs;
    @Inject
    protected ScreenBuilders screenBuilders;


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
        linhasTipoParaceres.setOptionsList(list);

        pareceresInstituicoesesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(pareceresInstituicoesesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdInstituicao(idInstituicaoField.getValue());
                            customer.setIdParecer(idParecerField.getValue());
                            customer.setIdTipoParecer(idTipoParecerField.getValue());
                        })
                        .withScreenClass(PareceresInstituicoesEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }


    @Subscribe("idParecerField")
    protected void onIdParecerFieldValueChange(HasValue.ValueChangeEvent<Pareceres> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                Map<String, TipoPareceres> map = new HashMap<>();
                Collection<TipoPareceres> customers = tipoPareceresesDc.getItems();
                for (TipoPareceres item : customers) {
                    if (item.getIdTipoPareceres().getId().equals(event.getValue().getId()))
                    {
                        map.put(item.getNome(), item);
                    }
                }
                idTipoParecerField.setEditable(true);
                idTipoParecerField.setOptionsMap(map);
            }
        }
    }

    @Subscribe("linhasTipoParaceres")
    protected void onLinhasTipoParaceresValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            pareceresInstituicoesesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            pareceresInstituicoesesDl.setMaxResults(0);
        }
        pareceresInstituicoesesDl.load();
    }

    @Subscribe("reset_tipo_pareceres")
    protected void onReset_tipo_pareceresClick(Button.ClickEvent event) {
        idTipoParecerField.setValue(null);
        idParecerField.setValue(null);
        idInstituicaoField.setValue(null);
        pareceresInstituicoesesDl.removeParameter("idTipoParecer");
        pareceresInstituicoesesDl.removeParameter("idParecer");
        pareceresInstituicoesesDl.removeParameter("idInstituicao");
        pareceresInstituicoesesDl.load();
    }

    @Subscribe("search_tipo_pareceres")
    protected void onSearch_tipo_pareceresClick(Button.ClickEvent event) {
        if (idInstituicaoField.getValue() != null) {
            pareceresInstituicoesesDl.setParameter("idInstituicao",  idInstituicaoField.getValue().getId());
        } else {
            pareceresInstituicoesesDl.removeParameter("idInstituicao");
        }

        if (idParecerField.getValue() != null) {
            pareceresInstituicoesesDl.setParameter("idParecer",  idParecerField.getValue().getId());
        } else {
            pareceresInstituicoesesDl.removeParameter("idParecer");
        }

        if (idTipoParecerField.getValue() != null) {
            pareceresInstituicoesesDl.setParameter("idTipoParecer",  idTipoParecerField.getValue().getId());
        } else {
            pareceresInstituicoesesDl.removeParameter("idTipoParecer");
        }


        pareceresInstituicoesesDl.load();
    }

    @Subscribe("pareceresInstituicoesesTable.remove")
    protected void onPareceresInstituicoesesTableRemove(Action.ActionPerformedEvent event) {
        pareceresInstituicoesesTableRemove.setConfirmation(false);
        if (pareceresInstituicoesesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos Pareceres das Instituições")
                    .withMessage("Deve seleccionar pelo um dos pareceres das instituições")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            PareceresInstituicoes user = pareceresInstituicoesesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela dos pareceres das instituições número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do dos pareceres das instituições número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        pareceresInstituicoesesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}