package pt.cmolhao.web.respostasocial;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.ProjectosIntervencao;
import pt.cmolhao.entity.RespostaSocial;
import pt.cmolhao.entity.Tiposvalencia;
import pt.cmolhao.web.projectosintervencao.ProjectosIntervencaoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_RespostaSocial.browse")
@UiDescriptor("resposta-social-browse.xml")
@LookupComponent("respostaSocialsTable")
@LoadDataBeforeShow
public class RespostaSocialBrowse extends StandardLookup<RespostaSocial> {
    @Inject
    protected CollectionLoader<RespostaSocial> respostaSocialsDl;

    @Inject
    protected LookupField linhasRespostaSocial;
    @Inject
    protected GroupTable<RespostaSocial> respostaSocialsTable;
    @Inject
    protected LookupPickerField<Tiposvalencia> idtipovalenciaField;
    @Inject
    protected TextField<String> nomeField;
    @Named("respostaSocialsTable.remove")
    protected RemoveAction<RespostaSocial> respostaSocialsTableRemove;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Dialogs dialogs;

    @Subscribe
    public void onInit(InitEvent event) {

        getWindow().setCaption("Listar Respostas Sociais");

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
        linhasRespostaSocial.setOptionsList(list);

        respostaSocialsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(respostaSocialsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdTipoValencia(idtipovalenciaField.getValue());
                            if (nomeField.getValue() != null)
                            {
                                customer.setNome(nomeField.getValue());
                            }

                        })
                        .withScreenClass(RespostaSocialEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Respostas Sociais");
    }

    @Subscribe("linhasRespostaSocial")
    protected void onLinhasRespostaSocialValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            respostaSocialsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            respostaSocialsDl.setMaxResults(0);
        }
        respostaSocialsDl.load();
    }

    @Subscribe("search_resposta_social")
    protected void onSearch_resposta_socialClick(Button.ClickEvent event) {
        // ID de Instituição

        if (idtipovalenciaField.getValue() != null)
        {
            respostaSocialsDl.setParameter("idTipoValencia",  idtipovalenciaField.getValue().getId());
        } else {
            respostaSocialsDl.removeParameter("idTipoValencia");
        }

        if (nomeField.getValue() != null)
        {
            respostaSocialsDl.setParameter("nome",  "(?i)%" + nomeField.getValue() + "%");
        } else {
            respostaSocialsDl.removeParameter("nome");
        }
        respostaSocialsDl.load();
    }

    @Subscribe("reset_search_resposta_social")
    protected void onReset_search_resposta_socialClick(Button.ClickEvent event) {

        idtipovalenciaField.setValue(null);
        nomeField.setValue(null);
        respostaSocialsDl.removeParameter("idTipoValencia");
        respostaSocialsDl.removeParameter("nome");
        respostaSocialsDl.load();
        
    }

    @Subscribe("respostaSocialsTable.remove")
    protected void onRespostaSocialsTableRemove(Action.ActionPerformedEvent event) {
        respostaSocialsTableRemove.setConfirmation(false);
        if (respostaSocialsTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção da resposta social")
                    .withMessage("Deve seleccionar pelo uma das respostas sociais")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            RespostaSocial user = respostaSocialsTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da resposta social número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da resposta social número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        respostaSocialsTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}