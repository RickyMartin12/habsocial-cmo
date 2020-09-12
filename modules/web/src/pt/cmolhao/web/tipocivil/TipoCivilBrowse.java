package pt.cmolhao.web.tipocivil;

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
import pt.cmolhao.entity.TipoAtendimento;
import pt.cmolhao.entity.TipoCivil;
import pt.cmolhao.web.profissao.ProfissaoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoCivil.browse")
@UiDescriptor("tipo-civil-browse.xml")
@LookupComponent("tipoCivilsTable")
@LoadDataBeforeShow
public class TipoCivilBrowse extends StandardLookup<TipoCivil> {
    @Inject
    protected CollectionLoader<TipoCivil> tipoCivilsDl;
    @Inject
    protected Table<TipoCivil> tipoCivilsTable;
    @Inject
    protected LookupField linhasTipoCivil;
    @Inject
    protected TextField<String> idTipoCivilField;
    @Named("tipoCivilsTable.remove")
    protected RemoveAction<TipoCivil> tipoCivilsTableRemove;
    @Inject
    private DataManager dataManager;
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
        linhasTipoCivil.setOptionsList(list);

        tipoCivilsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoCivilsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (idTipoCivilField.getValue() != null)
                            {
                                customer.setTipo(idTipoCivilField.getValue());
                            }
                        })
                        .withScreenClass(TipoCivilEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Civil");
    }

    @Subscribe("reset_tipo_civil")
    protected void onReset_tipo_civilClick(Button.ClickEvent event) {
        idTipoCivilField.setValue(null);
        tipoCivilsDl.removeParameter("tipo");
        tipoCivilsDl.load();
    }

    @Subscribe("search_tipo_civil")
    protected void onSearch_tipo_civilClick(Button.ClickEvent event) {
        if (idTipoCivilField.getValue() != null) {
            tipoCivilsDl.setParameter("tipo",  "(?i)%" + idTipoCivilField.getValue() + "%");
        } else {
            tipoCivilsDl.removeParameter("tipo");
        }

        tipoCivilsDl.load();
    }

    @Subscribe("linhasTipoCivil")
    protected void onLinhasTipoCivilValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoCivilsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoCivilsDl.setMaxResults(0);
        }
        tipoCivilsDl.load();
    }

    @Subscribe("tipoCivilsTable.remove")
    protected void onTipoCivilsTableRemove(Action.ActionPerformedEvent event) {

        tipoCivilsTableRemove.setConfirmation(false);
        if (tipoCivilsTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do tipo de civil")
                    .withMessage("Deve seleccionar pelo uns dos tipos de civil")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TipoCivil user = tipoCivilsTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do tipo de civil número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do tipo de civil número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tipoCivilsTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }

    }
}