package pt.cmolhao.web.instituicoes;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Instituicoes;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Instituicoes.browse")
@UiDescriptor("instituicoes-browse.xml")
@LookupComponent("instituicoesesTable")
@LoadDataBeforeShow
public class InstituicoesBrowse extends StandardLookup<Instituicoes> {

    @Inject
    protected TextField<String> numRegistoField;
    @Inject
    protected TextField<String> nissField;
    @Named("instituicoesesTable.remove")
    protected RemoveAction<Instituicoes> instituicoesesTableRemove;
    @Inject
    private LookupPickerField<Instituicoes> desricaoField;
    @Inject
    private CollectionLoader<Instituicoes> instituicoesesDl;

    @Inject
    private Notifications notifications;
    @Inject
    private LookupField linhasInstituicoes;

    @Inject
    private Table<Instituicoes> instituicoesesTable;
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
        linhasInstituicoes.setOptionsList(list);

        

        instituicoesesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(instituicoesesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (desricaoField.getValue() != null)
                            {
                                customer.setDescricao(desricaoField.getValue().getDescricao());
                            }
                            if(numRegistoField.getValue() != null)
                            {
                                customer.setNrregistosegsocial(numRegistoField.getValue());
                            }
                            if(nissField.getValue() != null)
                            {
                                customer.setNiss(nissField.getValue());
                            }

                        })
                        .withScreenClass(InstituicoesEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Instituições");
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe("reset_search_instituicoes")
    public void onReset_search_instituicoesClick(Button.ClickEvent event) {
        desricaoField.setValue(null);
        numRegistoField.setValue(null);
        nissField.setValue(null);
        instituicoesesDl.removeParameter("descricao");
        instituicoesesDl.removeParameter("nrregistosegsocial");
        instituicoesesDl.removeParameter("niss");
        instituicoesesDl.load();
    }

    @Subscribe("search_instituicoes")
    public void onSearch_instituicoesClick(Button.ClickEvent event) {
        // Descrição da Instituição
        if (desricaoField.getValue() != null) {
            instituicoesesDl.setParameter("descricao",  desricaoField.getValue().getDescricao());
        } else {
            instituicoesesDl.removeParameter("descricao");
        }

        if (numRegistoField.getValue() != null)
        {
            if (isNumeric(numRegistoField.getValue()))
            {
                instituicoesesDl.setParameter("nrregistosegsocial", "(?i)%" + numRegistoField.getValue() + "%");
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir o Numero Registo de Segurança Social</code>")
                        .withDescription("<u>Devera introduzir o número registo da segurança social</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }
        }
        else
        {
            instituicoesesDl.removeParameter("nrregistosegsocial");
        }

        if (nissField.getValue() != null)
        {
            if (isNumeric(nissField.getValue()))
            {
                instituicoesesDl.setParameter("niss", "(?i)%" + nissField.getValue() + "%");
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir o NISS</code>")
                        .withDescription("<u>Devera introduzir o niss</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }
        }
        else
        {
            instituicoesesDl.removeParameter("niss");
        }

        instituicoesesDl.load();
    }

    @Subscribe("linhasInstituicoes")
    public void onLinhasInstituicoesValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            instituicoesesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            instituicoesesDl.setMaxResults(0);
        }
        instituicoesesDl.load();

    }

    @Subscribe("instituicoesesTable.remove")
    protected void onInstituicoesesTableRemove(Action.ActionPerformedEvent event) {
        instituicoesesTableRemove.setConfirmation(false);
        if (instituicoesesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção da instituições")
                    .withMessage("Deve seleccionar pelo uma das instituições")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Instituicoes user = instituicoesesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da instituição número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da instituição número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        instituicoesesTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

}