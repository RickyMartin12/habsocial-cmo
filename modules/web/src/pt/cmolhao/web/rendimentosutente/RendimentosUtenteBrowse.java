package pt.cmolhao.web.rendimentosutente;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.RendimentosUtente;
import pt.cmolhao.entity.TipoRendimento;
import pt.cmolhao.entity.Utente;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_RendimentosUtente.browse")
@UiDescriptor("rendimentos-utente-browse.xml")
@LookupComponent("rendimentosUtentesTable")
@LoadDataBeforeShow
public class RendimentosUtenteBrowse extends StandardLookup<RendimentosUtente> {
    @Inject
    protected LookupField linhasRendimentosUtente;
    @Inject
    protected TextField<String> anoField;
    @Inject
    protected LookupPickerField<TipoRendimento> idTipoRendimentoField;
    @Inject
    protected LookupPickerField<Utente> idUtenteField;
    @Inject
    protected CollectionLoader<RendimentosUtente> rendimentosUtentesDl;
    @Inject
    protected GroupTable<RendimentosUtente> rendimentosUtentesTable;
    @Named("rendimentosUtentesTable.remove")
    protected RemoveAction<RendimentosUtente> rendimentosUtentesTableRemove;
    @Inject
    private Notifications notifications;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    protected UiComponents uiComponents;


    @Inject
    private Dialogs dialogs;

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    public Component generateValorRendimento(RendimentosUtente entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getValorRendimento() != null)
        {
            label.setValue(entity.getValorRendimento() + " ???");
            return label;
        }
        return null;
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
        linhasRendimentosUtente.setOptionsList(list);

        rendimentosUtentesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(rendimentosUtentesTable)
                        .newEntity()
                        .withInitializer(customer -> {

                            customer.setIdUtente(idUtenteField.getValue());
                            customer.setIdTipoRendimento(idTipoRendimentoField.getValue());
                            if (anoField.getValue() != null)
                            {
                                customer.setAno(anoField.getValue());
                            }
                        })
                        .withScreenClass(RendimentosUtenteEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe("reset_rendimentos_utentes")
    protected void onReset_rendimentos_utentesClick(Button.ClickEvent event) {
        anoField.setValue(null);
        idUtenteField.setValue(null);
        idTipoRendimentoField.setValue(null);
        rendimentosUtentesDl.removeParameter("ano");
        rendimentosUtentesDl.removeParameter("idUtente");
        rendimentosUtentesDl.removeParameter("idTipoRendimento");
        rendimentosUtentesDl.load();
    }

    @Subscribe("search_rendimentos_utentes")
    protected void onSearch_rendimentos_utentesClick(Button.ClickEvent event) {
        if (anoField.getValue() != null) {
            if (isNumeric(anoField.getValue()))
            {
                rendimentosUtentesDl.setParameter("ano", anoField.getValue() );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do ano de rendimento do utente</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }

        } else {
            rendimentosUtentesDl.removeParameter("ano");
        }

        if (idUtenteField.getValue() != null)
        {
            rendimentosUtentesDl.setParameter("idUtente",  idUtenteField.getValue().getId());
        }
        else {
            rendimentosUtentesDl.removeParameter("idUtente");
        }


        if (idTipoRendimentoField.getValue() != null)
        {
            rendimentosUtentesDl.setParameter("idTipoRendimento",  idTipoRendimentoField.getValue().getId());
        }
        else {
            rendimentosUtentesDl.removeParameter("idTipoRendimento");
        }


        rendimentosUtentesDl.load();
    }

    @Subscribe("linhasRendimentosUtente")
    protected void onLinhasRendimentosUtenteValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            rendimentosUtentesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            rendimentosUtentesDl.setMaxResults(0);
        }
        rendimentosUtentesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Rendimentos dos Utentes");
    }

    @Subscribe("rendimentosUtentesTable.remove")
    protected void onRendimentosUtentesTableRemove(Action.ActionPerformedEvent event) {
        rendimentosUtentesTableRemove.setConfirmation(false);
        if (rendimentosUtentesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selec????o de rendimentos de utentes")
                    .withMessage("Deve seleccionar pelo um dos rendimentos dos utentes")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            RendimentosUtente user = rendimentosUtentesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do rendimento do utente n??mero '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do rendimento do utente n??mero '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        rendimentosUtentesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}