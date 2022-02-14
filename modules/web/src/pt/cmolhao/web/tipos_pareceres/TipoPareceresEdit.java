package pt.cmolhao.web.tipos_pareceres;

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
import pt.cmolhao.entity.PareceresInstituicoes;
import pt.cmolhao.entity.TipoPareceres;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_TipoPareceres.edit")
@UiDescriptor("tipo-pareceres-edit.xml")
@EditedEntityContainer("tipoPareceresDc")
@LoadDataBeforeShow
public class TipoPareceresEdit extends StandardEditor<TipoPareceres> {
    @Inject
    protected TextField<UUID> idTipoParecerField;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected Table<PareceresInstituicoes> pareceresInstituicoesTable;
    @Named("pareceresInstituicoesTable.remove")
    protected RemoveAction<PareceresInstituicoes> pareceresInstituicoesTableRemove;
    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    private Dialogs dialogs;



    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Parecer - " + idTipoParecerField.getValue());
    }

    @Subscribe("pareceresInstituicoesTable.remove")
    protected void onPareceresInstituicoesTableRemove(Action.ActionPerformedEvent event) {
        pareceresInstituicoesTableRemove.setConfirmation(false);
        if (pareceresInstituicoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de pareceres das instituições")
                    .withMessage("Deve seleccionar pelo um dos paraceres das instituições")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            PareceresInstituicoes user = pareceresInstituicoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do paracer da instituição número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do paracer da instituição número '"+user.getId()+"'?")
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