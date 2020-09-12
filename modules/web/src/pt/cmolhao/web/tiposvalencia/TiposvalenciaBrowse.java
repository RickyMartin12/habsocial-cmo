package pt.cmolhao.web.tiposvalencia;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.TiposSituacoesUtentes;
import pt.cmolhao.entity.Tiposvalencia;
import pt.cmolhao.web.tipossituacoesutentes.TiposSituacoesUtentesEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Tiposvalencia.browse")
@UiDescriptor("tiposvalencia-browse.xml")
@LookupComponent("tiposvalenciasTable")
@LoadDataBeforeShow
public class TiposvalenciaBrowse extends StandardLookup<Tiposvalencia> {
    @Inject
    protected TextField<String> descricaoField;
    @Named("tiposvalenciasTable.remove")
    protected RemoveAction<Tiposvalencia> tiposvalenciasTableRemove;
    @Inject
    protected Table<Tiposvalencia> tiposvalenciasTable;
    @Inject
    private CollectionLoader<Tiposvalencia> tiposvalenciasDl;
    @Inject
    private LookupField linhasTiposValencias;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Dialogs dialogs;


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
        linhasTiposValencias.setOptionsList(list);

        tiposvalenciasTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tiposvalenciasTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (descricaoField.getValue() != null)
                            {
                                customer.setNome(descricaoField.getValue());
                            }
                        })
                        .withScreenClass(TiposvalenciaEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe("linhasTiposValencias")
    public void onLinhasTiposValenciasValueChange(HasValue.ValueChangeEvent event) {

        if (event.getValue() != null)
        {
            tiposvalenciasDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tiposvalenciasDl.setMaxResults(0);
        }
        tiposvalenciasDl.load();

    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Áreas de Intervenção");
    }

    @Subscribe("search_tipos_valencia")
    protected void onSearch_tipos_valenciaClick(Button.ClickEvent event) {
        if (descricaoField.getValue() != null) {
            tiposvalenciasDl.setParameter("nome",  "(?i)%" + descricaoField.getValue() + "%");
        } else {
            tiposvalenciasDl.removeParameter("nome");
        }

        tiposvalenciasDl.load();
    }

    @Subscribe("reset_tipos_valencia")
    protected void onReset_tipos_valenciaClick(Button.ClickEvent event) {
        descricaoField.setValue(null);
        tiposvalenciasDl.removeParameter("nome");
        tiposvalenciasDl.load();
    }

    @Subscribe("tiposvalenciasTable.remove")
    protected void onTiposvalenciasTableRemove(Action.ActionPerformedEvent event) {
        tiposvalenciasTableRemove.setConfirmation(false);
        if (tiposvalenciasTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção das áreas de intervenção")
                    .withMessage("Deve seleccionar pelo um das áreas de intervenção")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Tiposvalencia user = tiposvalenciasTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da área de intervenção número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da área de intervenção número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tiposvalenciasTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}