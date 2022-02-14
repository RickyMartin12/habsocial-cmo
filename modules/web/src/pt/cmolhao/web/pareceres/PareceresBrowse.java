package pt.cmolhao.web.pareceres;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Pareceres;
import pt.cmolhao.entity.TipoPareceres;
import pt.cmolhao.web.tipos_pareceres.TipoPareceresEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Pareceres.browse")
@UiDescriptor("pareceres-browse.xml")
@LookupComponent("pareceresesTable")
@LoadDataBeforeShow
public class PareceresBrowse extends StandardLookup<Pareceres> {
    @Inject
    protected TextField<String> nomeField;
    @Inject
    protected LookupField linhasTipoParaceres;
    @Inject
    protected GroupTable<Pareceres> pareceresesTable;

    @Inject
    protected ScreenBuilders screenBuilders;
    @Named("pareceresesTable.remove")
    protected RemoveAction<Pareceres> pareceresesTableRemove;
    @Inject
    protected CollectionLoader<Pareceres> pareceresesDl;

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

        pareceresesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(pareceresesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (nomeField.getValue() != null)
                            {
                                customer.setNome(nomeField.getValue());
                            }
                        })
                        .withScreenClass(PareceresEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe("reset_tipo_pareceres")
    protected void onReset_tipo_pareceresClick(Button.ClickEvent event) {
        nomeField.setValue(null);
        pareceresesDl.removeParameter("nome");
        pareceresesDl.load();
    }

    @Subscribe("search_tipo_pareceres")
    protected void onSearch_tipo_pareceresClick(Button.ClickEvent event) {
        if (nomeField.getValue() != null) {
            pareceresesDl.setParameter("nome",  "(?i)%" + nomeField.getValue() + "%");
        } else {
            pareceresesDl.removeParameter("nome");
        }


        pareceresesDl.load();
    }

    @Subscribe("linhasTipoParaceres")
    protected void onLinhasTipoParaceresValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            pareceresesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            pareceresesDl.setMaxResults(0);
        }
        pareceresesDl.load();
    }

    @Subscribe("pareceresesTable.remove")
    protected void onPareceresesTableRemove(Action.ActionPerformedEvent event) {
        pareceresesTableRemove.setConfirmation(false);
        if (pareceresesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do Parecer")
                    .withMessage("Deve seleccionar pelo um dos paraceres")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Pareceres user = pareceresesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do parecer número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do parecer número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        pareceresesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}