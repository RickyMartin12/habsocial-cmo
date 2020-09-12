package pt.cmolhao.web.claso;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.MembrosRedeTrabalho;
import pt.cmolhao.entity.MembrosRedesTrabalhoClaso;
import pt.cmolhao.web.membros_rede_trabalho.MembrosRedeTrabalhoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UiController("cmolhao_MembrosRedesTrabalhoClaso.browse")
@UiDescriptor("membros-redes-trabalho-claso-browse.xml")
@LookupComponent("membrosRedesTrabalhoClasoesTable")
@LoadDataBeforeShow
public class MembrosRedesTrabalhoClasoBrowse extends StandardLookup<MembrosRedesTrabalhoClaso> {
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected TextField<String> anoAdesao;
    @Inject
    protected CollectionContainer<Instituicoes> instituicoesDc;
    @Inject
    protected LookupField linhasMembrosRedeTrabalho;
    @Inject
    protected GroupTable<MembrosRedesTrabalhoClaso> membrosRedesTrabalhoClasoesTable;
    @Inject
    protected CollectionLoader<MembrosRedesTrabalhoClaso> membrosRedesTrabalhoClasoesDl;
    @Inject
    protected UiComponents uiComponents;

    @Inject
    protected ScreenBuilders screenBuilders;

    @Inject
    private Notifications notifications;

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
        linhasMembrosRedeTrabalho.setOptionsList(list);


        membrosRedesTrabalhoClasoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(membrosRedesTrabalhoClasoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
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

                            customer.setIdInsituicao(idInstituicaoField.getValue());

                        })
                        .withScreenClass(MembrosRedesTrabalhoClasoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Membros de Rede de Trabalho do Claso");

    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }


    @Named("membrosRedesTrabalhoClasoesTable.remove")
    protected RemoveAction<MembrosRedesTrabalhoClaso> membrosRedesTrabalhoClasoesTableRemove;

    @Subscribe("search_membros_rede_trabalho_claso")
    protected void onSearch_membros_rede_trabalho_clasoClick(Button.ClickEvent event) {
        if (anoAdesao.getValue() != null)
        {
            if (isNumeric(anoAdesao.getValue()))
            {
                membrosRedesTrabalhoClasoesDl.setParameter("anoAdesao", Integer.valueOf(anoAdesao.getValue()));
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir o Ano da Adesão</code>")
                        .withDescription("<u>Devera introduzir o Ano da Adesão</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }
        }
        else
        {
            membrosRedesTrabalhoClasoesDl.removeParameter("anoAdesao");
        }

        if (idInstituicaoField.getValue() != null) {
            membrosRedesTrabalhoClasoesDl.setParameter("idInsituicao",  idInstituicaoField.getValue().getId());
        } else {
            membrosRedesTrabalhoClasoesDl.removeParameter("idInsituicao");
        }

        membrosRedesTrabalhoClasoesDl.load();
    }

    @Subscribe("reset_membros_rede_trabalho_claso")
    protected void onReset_membros_rede_trabalho_clasoClick(Button.ClickEvent event) {
        anoAdesao.setValue(null);
        idInstituicaoField.setValue(null);
        membrosRedesTrabalhoClasoesDl.removeParameter("anoAdesao");
        membrosRedesTrabalhoClasoesDl.removeParameter("idInsituicao");
        membrosRedesTrabalhoClasoesDl.load();
    }

    @Subscribe("linhasMembrosRedeTrabalho")
    protected void onLinhasMembrosRedeTrabalhoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            membrosRedesTrabalhoClasoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            membrosRedesTrabalhoClasoesDl.setMaxResults(0);
        }
        membrosRedesTrabalhoClasoesDl.load();
    }

    @Subscribe("membrosRedesTrabalhoClasoesTable.remove")
    protected void onMembrosRedesTrabalhoClasoesTableRemove(Action.ActionPerformedEvent event) {
        membrosRedesTrabalhoClasoesTableRemove.setConfirmation(false);
        if (membrosRedesTrabalhoClasoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de membros de rede de trabalho do claso")
                    .withMessage("Deve seleccionar pelo um dos membros de rede de trabalho do claso")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            MembrosRedesTrabalhoClaso user = membrosRedesTrabalhoClasoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela membro de rede de trabalho do claso número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela membro de rede de trabalho do claso número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        membrosRedesTrabalhoClasoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}