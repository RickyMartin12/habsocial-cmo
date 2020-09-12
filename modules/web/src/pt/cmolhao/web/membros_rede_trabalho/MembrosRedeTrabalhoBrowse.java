package pt.cmolhao.web.membros_rede_trabalho;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Documentacao;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.MembrosRedeTrabalho;
import pt.cmolhao.entity.RedeTrabalho;
import pt.cmolhao.web.claso_regulamentos.DocumentacaoClasoRegulamentosEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UiController("cmolhao_MembrosRedeTrabalho.browse")
@UiDescriptor("membros-rede-trabalho-browse.xml")
@LookupComponent("membrosRedeTrabalhoesTable")
@LoadDataBeforeShow
public class MembrosRedeTrabalhoBrowse extends StandardLookup<MembrosRedeTrabalho> {
    @Inject
    protected LookupField linhasMembrosRedeTrabalho;
    @Inject
    protected TextField<String> anoAdesao;
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;
    @Inject
    protected CollectionLoader<MembrosRedeTrabalho> membrosRedeTrabalhoesDl;

    @Inject
    protected UiComponents uiComponents;
    @Named("membrosRedeTrabalhoesTable.remove")
    protected RemoveAction<MembrosRedeTrabalho> membrosRedeTrabalhoesTableRemove;
    @Inject
    protected GroupTable<MembrosRedeTrabalho> membrosRedeTrabalhoesTable;

    @Inject
    private ExportDisplay exportDisplay;

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


        membrosRedeTrabalhoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(membrosRedeTrabalhoesTable)
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
                            customer.setIdRedeTrabalho(idRedeTrabalhoField.getValue());

                        })
                        .withScreenClass(MembrosRedeTrabalhoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Membros de Rede de Trabalho");

    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe("reset_membros_rede_trabalho")
    protected void onReset_membros_rede_trabalhoClick(Button.ClickEvent event) {
        anoAdesao.setValue(null);
        idInstituicaoField.setValue(null);
        idRedeTrabalhoField.setValue(null);
        membrosRedeTrabalhoesDl.removeParameter("anoAdesao");
        membrosRedeTrabalhoesDl.removeParameter("idRedeTrabalho");
        membrosRedeTrabalhoesDl.removeParameter("idInsituicao");
        membrosRedeTrabalhoesDl.load();
    }

    @Subscribe("search_membros_rede_trabalho")
    protected void onSearch_membros_rede_trabalhoClick(Button.ClickEvent event) {
        if (anoAdesao.getValue() != null)
        {
            if (isNumeric(anoAdesao.getValue()))
            {
                membrosRedeTrabalhoesDl.setParameter("anoAdesao", Integer.valueOf(anoAdesao.getValue()));
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
            membrosRedeTrabalhoesDl.removeParameter("anoAdesao");
        }

        if (idRedeTrabalhoField.getValue() != null) {
            membrosRedeTrabalhoesDl.setParameter("idRedeTrabalho",  idRedeTrabalhoField.getValue().getId());
        } else {
            membrosRedeTrabalhoesDl.removeParameter("idRedeTrabalho");
        }

        if (idInstituicaoField.getValue() != null) {
            membrosRedeTrabalhoesDl.setParameter("idInsituicao",  idInstituicaoField.getValue().getId());
        } else {
            membrosRedeTrabalhoesDl.removeParameter("idInsituicao");
        }

        membrosRedeTrabalhoesDl.load();
    }

    @Subscribe("linhasMembrosRedeTrabalho")
    protected void onLinhasMembrosRedeTrabalhoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            membrosRedeTrabalhoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            membrosRedeTrabalhoesDl.setMaxResults(0);
        }
        membrosRedeTrabalhoesDl.load();
    }

    @Subscribe("membrosRedeTrabalhoesTable.remove")
    protected void onMembrosRedeTrabalhoesTableRemove(Action.ActionPerformedEvent event) {
        membrosRedeTrabalhoesTableRemove.setConfirmation(false);
        if (membrosRedeTrabalhoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de membros de rede de trabalho")
                    .withMessage("Deve seleccionar pelo um dos membros de rede de trabalho")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            MembrosRedeTrabalho user = membrosRedeTrabalhoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela membro de rede de trabalho número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela membro de rede de trabalho número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        membrosRedeTrabalhoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}