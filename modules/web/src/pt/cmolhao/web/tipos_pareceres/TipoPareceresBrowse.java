package pt.cmolhao.web.tipos_pareceres;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Pareceres;
import pt.cmolhao.entity.SubRedeTrabalho;
import pt.cmolhao.entity.TipoPareceres;
import pt.cmolhao.web.subredetrabalho.SubRedeTrabalhoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoPareceres.browse")
@UiDescriptor("tipo-pareceres-browse.xml")
@LookupComponent("tipoPareceresesTable")
@LoadDataBeforeShow
public class TipoPareceresBrowse extends StandardLookup<TipoPareceres> {
    @Inject
    protected CollectionLoader<TipoPareceres> tipoPareceresesDl;
    @Inject
    protected LookupPickerField<Pareceres> idParaceresField;
    @Inject
    protected TextField<String> nomeField;
    @Inject
    protected LookupField linhasTipoParaceres;
    @Inject
    protected GroupTable<TipoPareceres> tipoPareceresesTable;
    @Named("tipoPareceresesTable.remove")
    protected RemoveAction<TipoPareceres> tipoPareceresesTableRemove;

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
        linhasTipoParaceres.setOptionsList(list);

        tipoPareceresesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoPareceresesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (nomeField.getValue() != null)
                            {
                                customer.setNome(nomeField.getValue());
                            }
                            customer.setIdTipoPareceres(idParaceresField.getValue());
                        })
                        .withScreenClass(TipoPareceresEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe("search_tipo_pareceres")
    protected void onSearch_tipo_pareceresClick(Button.ClickEvent event) {
        if (nomeField.getValue() != null) {
            tipoPareceresesDl.setParameter("nome",  "(?i)%" + nomeField.getValue() + "%");
        } else {
            tipoPareceresesDl.removeParameter("nome");
        }

        if (idParaceresField.getValue() != null) {
            tipoPareceresesDl.setParameter("idTipoPareceres",  idParaceresField.getValue().getId());
        } else {
            tipoPareceresesDl.removeParameter("idTipoPareceres");
        }


        tipoPareceresesDl.load();
    }

    @Subscribe("reset_tipo_pareceres")
    protected void onReset_tipo_pareceresClick(Button.ClickEvent event) {
        nomeField.setValue(null);
        idParaceresField.setValue(null);
        tipoPareceresesDl.removeParameter("nome");
        tipoPareceresesDl.removeParameter("idTipoPareceres");
        tipoPareceresesDl.load();
    }

    @Subscribe("linhasTipoParaceres")
    protected void onLinhasTipoParaceresValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoPareceresesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoPareceresesDl.setMaxResults(0);
        }
        tipoPareceresesDl.load();
    }

    @Subscribe("tipoPareceresesTable.remove")
    protected void onTipoPareceresesTableRemove(Action.ActionPerformedEvent event) {
        tipoPareceresesTableRemove.setConfirmation(false);
        if (tipoPareceresesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do Tipo de Parecer")
                    .withMessage("Deve seleccionar pelo um dos tipos de paraceres")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TipoPareceres user = tipoPareceresesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do tipo de parecer número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do tipo de parecer número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tipoPareceresesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}