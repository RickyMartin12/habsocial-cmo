package pt.cmolhao.web.tipoatendimento;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Atendimento;
import pt.cmolhao.entity.TipoAtendimento;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UiController("cmolhao_TipoAtendimento.edit")
@UiDescriptor("tipo-atendimento-edit.xml")
@EditedEntityContainer("tipoAtendimentoDc")
@LoadDataBeforeShow
public class TipoAtendimentoEdit extends StandardEditor<TipoAtendimento> {
    @Inject
    protected LookupField<String> tipoAtendimentoField;
    @Inject
    protected TextField<UUID> idTipoAtendimentoField;
    @Inject
    protected Table<Atendimento> atendimentoesTable;
    @Named("atendimentoesTable.remove")
    protected RemoveAction<Atendimento> atendimentoesTableRemove;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list_tipo_atendimento = new ArrayList<>();
        list_tipo_atendimento.add("Pedidos de Habitação por email");
        list_tipo_atendimento.add("Pedidos de Habitação por sinalização por outra instituição");
        list_tipo_atendimento.add("Plataforma eAA");
        list_tipo_atendimento.add("Atendimento técnico presencial");
        tipoAtendimentoField.setOptionsList(list_tipo_atendimento);
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Atendimento - " + idTipoAtendimentoField.getValue());
    }

    @Subscribe("atendimentoesTable.remove")
    protected void onAtendimentoesTableRemove(Action.ActionPerformedEvent event) {
        atendimentoesTableRemove.setConfirmation(false);
        if (atendimentoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de atendimentos")
                    .withMessage("Deve seleccionar pelo um dos atendimentos")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Atendimento user = atendimentoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do atendimento número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do atendimento número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        atendimentoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}