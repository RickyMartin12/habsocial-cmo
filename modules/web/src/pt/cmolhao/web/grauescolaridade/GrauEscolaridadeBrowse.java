package pt.cmolhao.web.grauescolaridade;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.GrauEscolaridade;
import pt.cmolhao.web.tipoajuda.TipoAjudaEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_GrauEscolaridade.browse")
@UiDescriptor("grau-escolaridade-browse.xml")
@LookupComponent("grauEscolaridadesTable")
@LoadDataBeforeShow
public class GrauEscolaridadeBrowse extends StandardLookup<GrauEscolaridade> {
    @Inject
    protected Table<GrauEscolaridade> grauEscolaridadesTable;
    @Inject
    protected LookupField linhasGrauEscolraidade;

    @Inject
    protected CollectionLoader<GrauEscolaridade> grauEscolaridadesDl;
    @Inject
    protected LookupField desc_grau_escolaridade_id;
    @Named("grauEscolaridadesTable.remove")
    protected RemoveAction<GrauEscolaridade> grauEscolaridadesTableRemove;

    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private Dialogs dialogs;



    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Grau Escolaridade");

    }

    @Subscribe("reset_grau_escolaridade")
    protected void onReset_grau_escolaridadeClick(Button.ClickEvent event) {
        desc_grau_escolaridade_id.setValue(null);
        grauEscolaridadesDl.removeParameter("descricao");
        grauEscolaridadesDl.load();
    }

    @Subscribe("search_grau_escolaridade")
    protected void onSearch_grau_escolaridadeClick(Button.ClickEvent event) {
        // Descri????o de Tipo Ajuda

        if (desc_grau_escolaridade_id.getValue() != null) {
            grauEscolaridadesDl.setParameter("descricao",  desc_grau_escolaridade_id.getValue().toString() );
        } else {
            grauEscolaridadesDl.removeParameter("descricao");
        }

        grauEscolaridadesDl.load();
    }

    @Subscribe("linhasGrauEscolraidade")
    protected void onLinhasGrauEscolraidadeValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            grauEscolaridadesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            grauEscolaridadesDl.setMaxResults(0);
        }
        grauEscolaridadesDl.load();
    }

    @Subscribe
    public void onInit(InitEvent event) {
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
        linhasGrauEscolraidade.setOptionsList(list);

        List<String> list_escolaridade = new ArrayList<>();
        list_escolaridade.add("Sem Escolaridade");
        list_escolaridade.add("n??vel 1 - 2?? ciclo do ensino b??sico (6?? ano)");
        list_escolaridade.add("n??vel 2 - 3?? ciclo do ensino b??sico (9?? ano)");
        list_escolaridade.add("n??vel 3 - Ensino Secund??rio");
        list_escolaridade.add("n??vel 4 - Ensino Secund??rio acrescentando um est??gio profissional com dura????o de seis meses");
        list_escolaridade.add("n??vel 5 - Qualifica????o de n??vel p??s-secund??rio e n??o superior");
        list_escolaridade.add("n??vel 6 - Ensino Superior (Licenciatura)");
        list_escolaridade.add("n??vel 7 - Mestrado");
        list_escolaridade.add("n??vel 8 - Doutoramento");
        desc_grau_escolaridade_id.setOptionsList(list_escolaridade);

        grauEscolaridadesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(grauEscolaridadesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (desc_grau_escolaridade_id.getValue() != null)
                            {
                                customer.setDescricao(desc_grau_escolaridade_id.getValue().toString());
                            }
                        })
                        .withScreenClass(GrauEscolaridadeEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe("grauEscolaridadesTable.remove")
    protected void onGrauEscolaridadesTableRemove(Action.ActionPerformedEvent event) {
        grauEscolaridadesTableRemove.setConfirmation(false);
        if (grauEscolaridadesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selec????o do grau de escolaridade")
                    .withMessage("Deve seleccionar pelo um dos graus de escolaridade")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            GrauEscolaridade user = grauEscolaridadesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do grau de escolaridade n??mero '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do grau de escolaridade n??mero '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        grauEscolaridadesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}