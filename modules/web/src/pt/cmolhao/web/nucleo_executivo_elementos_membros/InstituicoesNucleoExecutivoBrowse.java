package pt.cmolhao.web.nucleo_executivo_elementos_membros;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.InstituicoesNucleoExecutivo;
import pt.cmolhao.web.instituicoes.InstituicoesEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_InstituicoesNucleoExecutivo.browse")
@UiDescriptor("instituicoes-nucleo-executivo-browse.xml")
@LookupComponent("instituicoesNucleoExecutivoesTable")
@LoadDataBeforeShow
public class InstituicoesNucleoExecutivoBrowse extends StandardLookup<InstituicoesNucleoExecutivo> {
    @Inject
    protected Table<InstituicoesNucleoExecutivo> instituicoesNucleoExecutivoesTable;
    @Named("instituicoesNucleoExecutivoesTable.remove")
    protected RemoveAction<InstituicoesNucleoExecutivo> instituicoesNucleoExecutivoesTableRemove;

    @Inject
    protected TextField<String> numRegistoField;
    @Inject
    protected TextField<String> nissField;
    @Inject
    protected LookupPickerField<InstituicoesNucleoExecutivo> desricaoField;
    @Inject
    protected CollectionContainer<InstituicoesNucleoExecutivo> instituicoesNucleoExecutivoDc;
    @Inject
    protected CollectionLoader<InstituicoesNucleoExecutivo> instituicoesNucleoExecutivoesDl;

    @Inject
    private Notifications notifications;
    @Inject
    private LookupField linhasInstituicoes;

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



        instituicoesNucleoExecutivoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(instituicoesNucleoExecutivoesTable)
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
                        .withScreenClass(InstituicoesNucleoExecutivoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Instituições do Núcleo Executivo");
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }


    @Subscribe("instituicoesNucleoExecutivoesTable.remove")
    protected void onInstituicoesNucleoExecutivoesTableRemove(Action.ActionPerformedEvent event) {
        instituicoesNucleoExecutivoesTableRemove.setConfirmation(false);
        if (instituicoesNucleoExecutivoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção da instituições do núcleo executivo")
                    .withMessage("Deve seleccionar pelo uma das instituições do núcleo executivo")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            InstituicoesNucleoExecutivo user = instituicoesNucleoExecutivoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da instituição do núcleo executivo número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da instituição do núcleo executivo número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        instituicoesNucleoExecutivoesTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

    @Subscribe("linhasInstituicoes")
    protected void onLinhasInstituicoesValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            instituicoesNucleoExecutivoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            instituicoesNucleoExecutivoesDl.setMaxResults(0);
        }
        instituicoesNucleoExecutivoesDl.load();
    }

    @Subscribe("search_instituicoes")
    protected void onSearch_instituicoesClick(Button.ClickEvent event) {
        // Descrição da Instituição
        if (desricaoField.getValue() != null) {
            instituicoesNucleoExecutivoesDl.setParameter("descricao",  desricaoField.getValue().getDescricao());
        } else {
            instituicoesNucleoExecutivoesDl.removeParameter("descricao");
        }

        if (numRegistoField.getValue() != null)
        {
            if (isNumeric(numRegistoField.getValue()))
            {
                instituicoesNucleoExecutivoesDl.setParameter("nrregistosegsocial", "(?i)%" + numRegistoField.getValue() + "%");
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
            instituicoesNucleoExecutivoesDl.removeParameter("nrregistosegsocial");
        }

        if (nissField.getValue() != null)
        {
            if (isNumeric(nissField.getValue()))
            {
                instituicoesNucleoExecutivoesDl.setParameter("niss", "(?i)%" + nissField.getValue() + "%");
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
            instituicoesNucleoExecutivoesDl.removeParameter("niss");
        }

        instituicoesNucleoExecutivoesDl.load();
    }

    @Subscribe("reset_search_instituicoes")
    protected void onReset_search_instituicoesClick(Button.ClickEvent event) {
        desricaoField.setValue(null);
        numRegistoField.setValue(null);
        nissField.setValue(null);
        instituicoesNucleoExecutivoesDl.removeParameter("descricao");
        instituicoesNucleoExecutivoesDl.removeParameter("nrregistosegsocial");
        instituicoesNucleoExecutivoesDl.removeParameter("niss");
        instituicoesNucleoExecutivoesDl.load();
    }
}