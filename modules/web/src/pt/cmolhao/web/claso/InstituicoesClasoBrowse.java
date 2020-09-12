package pt.cmolhao.web.claso;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.InstituicoesClaso;
import pt.cmolhao.web.instituicoes.InstituicoesEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UiController("cmolhao_InstituicoesClaso.browse")
@UiDescriptor("instituicoes-claso-browse.xml")
@LookupComponent("instituicoesClasoesTable")
@LoadDataBeforeShow
public class InstituicoesClasoBrowse extends StandardLookup<InstituicoesClaso> {

    @Inject
    protected TextField<String> numRegistoField;
    @Inject
    protected TextField<String> nissField;
    @Inject
    protected CollectionLoader<InstituicoesClaso> instituicoesClasoesDl;
    @Inject
    protected Table<InstituicoesClaso> instituicoesClasoesTable;
    @Named("instituicoesClasoesTable.remove")
    protected RemoveAction<InstituicoesClaso> instituicoesClasoesTableRemove;
    @Inject
    protected TextField<String> anoAdesao;
    @Inject
    private LookupPickerField<InstituicoesClaso> desricaoField;

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



        instituicoesClasoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(instituicoesClasoesTable)
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
                            if (anoAdesao.getValue() != null)
                            {
                                String dia = "01";
                                String mes = "01";
                                String ano = anoAdesao.getValue();
                                String data = dia +"/"+mes+"/"+ano;
                                try
                                {
                                    Date date_ano = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                                    customer.setDataAdesao(date_ano);
                                }
                                catch (ParseException e)
                                {
                                    notifications.create()
                                            .withCaption("Mensagem de erro: " + e.toString())
                                            .withType(Notifications.NotificationType.TRAY)
                                            .show();
                                }
                            }

                        })
                        .withScreenClass(InstituicoesClasoEdit.class)    // specific editor screen
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
        anoAdesao.setValue(null);
        instituicoesClasoesDl.removeParameter("descricao");
        instituicoesClasoesDl.removeParameter("nrregistosegsocial");
        instituicoesClasoesDl.removeParameter("niss");
        instituicoesClasoesDl.removeParameter("anoAdesao");
        instituicoesClasoesDl.load();
    }

    @Subscribe("search_instituicoes")
    public void onSearch_instituicoesClick(Button.ClickEvent event) {
        // Descrição da Instituição
        if (desricaoField.getValue() != null) {
            instituicoesClasoesDl.setParameter("descricao",  desricaoField.getValue().getDescricao());
        } else {
            instituicoesClasoesDl.removeParameter("descricao");
        }

        if (numRegistoField.getValue() != null)
        {
            if (isNumeric(numRegistoField.getValue()))
            {
                instituicoesClasoesDl.setParameter("nrregistosegsocial", "(?i)%" + numRegistoField.getValue() + "%");
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
            instituicoesClasoesDl.removeParameter("nrregistosegsocial");
        }

        if (nissField.getValue() != null)
        {
            if (isNumeric(nissField.getValue()))
            {
                instituicoesClasoesDl.setParameter("niss", "(?i)%" + nissField.getValue() + "%");
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
            instituicoesClasoesDl.removeParameter("niss");
        }

        if (anoAdesao.getValue() != null) {
            if (isNumeric(anoAdesao.getValue()))
            {
                instituicoesClasoesDl.setParameter("anoAdesao", Integer.valueOf(anoAdesao.getValue()) );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do ano de adesão</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }

        } else {
            instituicoesClasoesDl.removeParameter("anoAdesao");
        }

        instituicoesClasoesDl.load();
    }

    @Subscribe("linhasInstituicoes")
    public void onLinhasInstituicoesValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            instituicoesClasoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            instituicoesClasoesDl.setMaxResults(0);
        }
        instituicoesClasoesDl.load();

    }


    @Subscribe("instituicoesClasoesTable.remove")
    protected void onInstituicoesClasoesTableRemove(Action.ActionPerformedEvent event) {
        instituicoesClasoesTableRemove.setConfirmation(false);
        if (instituicoesClasoesTable.getSelected().isEmpty())
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
            InstituicoesClaso user = instituicoesClasoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da instituição número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da instituição número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        instituicoesClasoesTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}