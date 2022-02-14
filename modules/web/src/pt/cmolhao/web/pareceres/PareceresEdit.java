package pt.cmolhao.web.pareceres;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Documentacao;
import pt.cmolhao.entity.Pareceres;
import pt.cmolhao.entity.PareceresInstituicoes;
import pt.cmolhao.entity.TipoPareceres;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_Pareceres.edit")
@UiDescriptor("pareceres-edit.xml")
@EditedEntityContainer("pareceresDc")
@LoadDataBeforeShow
public class PareceresEdit extends StandardEditor<Pareceres> {
    @Inject
    protected TextField<UUID> idParecerField;
    @Inject
    protected Table<TipoPareceres> tipoPareceresTable;
    @Inject
    protected Table<PareceresInstituicoes> pareceresInstituicoesTable;
    @Named("tipoPareceresTable.remove")
    protected RemoveAction<TipoPareceres> tipoPareceresTableRemove;
    @Named("pareceresInstituicoesTable.remove")
    protected RemoveAction<PareceresInstituicoes> pareceresInstituicoesTableRemove;

    @Inject
    protected UiComponents uiComponents;
    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Pareces - " + idParecerField.getValue());
    }


    @Subscribe("tipoPareceresTable.remove")
    protected void onTipoPareceresTableRemove(Action.ActionPerformedEvent event) {
        tipoPareceresTableRemove.setConfirmation(false);
        if (tipoPareceresTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de tipo de pareceres")
                    .withMessage("Deve seleccionar pelo um dos tipos de pareceres")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TipoPareceres user = tipoPareceresTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do tipo de pareceres número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do tipo de pareceres número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tipoPareceresTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

    @Subscribe("pareceresInstituicoesTable.remove")
    protected void onPareceresInstituicoesTableRemove(Action.ActionPerformedEvent event) {
        pareceresInstituicoesTableRemove.setConfirmation(false);
        if (pareceresInstituicoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos pareceres das instituições")
                    .withMessage("Deve seleccionar pelo um dos pareceres das instituições")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            PareceresInstituicoes user = pareceresInstituicoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do parecer da instituição número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do parecer da instituição número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        pareceresInstituicoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}