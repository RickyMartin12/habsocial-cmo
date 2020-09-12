package pt.cmolhao.web.nucleo_executivo_elementos_membros;

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
import pt.cmolhao.entity.MembrosRedesTrabalhoClaso;
import pt.cmolhao.entity.MembrosRedesTrabalhoNuceloExecutivo;
import pt.cmolhao.web.claso.MembrosRedesTrabalhoClasoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UiController("cmolhao_MembrosRedesTrabalhoNuceloExecutivo.browse")
@UiDescriptor("membros-redes-trabalho-nucelo-executivo-browse.xml")
@LookupComponent("membrosRedesTrabalhoNuceloExecutivoesTable")
@LoadDataBeforeShow
public class MembrosRedesTrabalhoNuceloExecutivoBrowse extends StandardLookup<MembrosRedesTrabalhoNuceloExecutivo> {
    @Inject
    protected CollectionLoader<MembrosRedesTrabalhoNuceloExecutivo> membrosRedesTrabalhoNuceloExecutivoesDl;
    @Inject
    protected GroupTable<MembrosRedesTrabalhoNuceloExecutivo> membrosRedesTrabalhoNuceloExecutivoesTable;
    @Inject
    protected CollectionContainer<Instituicoes> instituicoesDc;
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected LookupField linhasMembrosRedeTrabalho;
    @Inject
    protected UiComponents uiComponents;

    @Inject
    protected ScreenBuilders screenBuilders;
    @Named("membrosRedesTrabalhoNuceloExecutivoesTable.remove")
    protected RemoveAction<MembrosRedesTrabalhoNuceloExecutivo> membrosRedesTrabalhoNuceloExecutivoesTableRemove;

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


        membrosRedesTrabalhoNuceloExecutivoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(membrosRedesTrabalhoNuceloExecutivoesTable)
                        .newEntity()
                        .withInitializer(customer -> {

                            customer.setIdInsituicao(idInstituicaoField.getValue());

                        })
                        .withScreenClass(MembrosRedesTrabalhoNuceloExecutivoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }




    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Membros de Rede de Trabalho do Núcleo Executivo");

    }

    @Subscribe("reset_membros_rede_trabalho_nucleo_executivo")
    protected void onReset_membros_rede_trabalho_nucleo_executivoClick(Button.ClickEvent event) {
        idInstituicaoField.setValue(null);
        membrosRedesTrabalhoNuceloExecutivoesDl.removeParameter("idInsituicao");
        membrosRedesTrabalhoNuceloExecutivoesDl.load();
    }

    @Subscribe("search_membros_rede_trabalho_nucleo_executivo")
    protected void onSearch_membros_rede_trabalho_nucleo_executivoClick(Button.ClickEvent event) {

        if (idInstituicaoField.getValue() != null) {
            membrosRedesTrabalhoNuceloExecutivoesDl.setParameter("idInsituicao",  idInstituicaoField.getValue().getId());
        } else {
            membrosRedesTrabalhoNuceloExecutivoesDl.removeParameter("idInsituicao");
        }

        membrosRedesTrabalhoNuceloExecutivoesDl.load();
    }

    @Subscribe("linhasMembrosRedeTrabalho")
    protected void onLinhasMembrosRedeTrabalhoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            membrosRedesTrabalhoNuceloExecutivoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            membrosRedesTrabalhoNuceloExecutivoesDl.setMaxResults(0);
        }
        membrosRedesTrabalhoNuceloExecutivoesDl.load();
    }

    @Subscribe("membrosRedesTrabalhoNuceloExecutivoesTable.remove")
    protected void onMembrosRedesTrabalhoNuceloExecutivoesTableRemove(Action.ActionPerformedEvent event) {
        membrosRedesTrabalhoNuceloExecutivoesTableRemove.setConfirmation(false);
        if (membrosRedesTrabalhoNuceloExecutivoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de membros de rede do nucleo executivo")
                    .withMessage("Deve seleccionar pelo um dos membros de rede do nucleo executivo")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            MembrosRedesTrabalhoNuceloExecutivo user = membrosRedesTrabalhoNuceloExecutivoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela membro de rede do nucleo executivo número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela membro de rede do nucleo executivo número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        membrosRedesTrabalhoNuceloExecutivoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}