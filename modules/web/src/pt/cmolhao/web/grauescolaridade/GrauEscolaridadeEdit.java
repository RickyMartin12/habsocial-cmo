package pt.cmolhao.web.grauescolaridade;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.GrauEscolaridade;
import pt.cmolhao.entity.Utente;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UiController("cmolhao_GrauEscolaridade.edit")
@UiDescriptor("grau-escolaridade-edit.xml")
@EditedEntityContainer("grauEscolaridadeDc")
@LoadDataBeforeShow
public class GrauEscolaridadeEdit extends StandardEditor<GrauEscolaridade> {

    @Inject
    protected TextField<UUID> idGrauEscolaridadeField;
    @Inject
    protected LookupField<String> descricaoField;
    @Inject
    protected Table<Utente> utentesTable;
    @Named("utentesTable.remove")
    protected RemoveAction<Utente> utentesTableRemove;

    @Inject
    private Dialogs dialogs;


    @Subscribe
    protected void onInit(InitEvent event) {

        List<String> list = new ArrayList<>();
        list.add("Sem Escolaridade");
        list.add("nível 1 - 2º ciclo do ensino básico (6º ano)");
        list.add("nível 2 - 3º ciclo do ensino básico (9º ano)");
        list.add("nível 3 - Ensino Secundário");
        list.add("nível 4 - Ensino Secundário acrescentando um estágio profissional com duração de seis meses");
        list.add("nível 5 - Qualificação de nível pós-secundário e não superior");
        list.add("nível 6 - Ensino Superior (Licenciatura)");
        list.add("nível 7 - Mestrado");
        list.add("nível 8 - Doutoramento");
        descricaoField.setOptionsList(list);
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Grau Escolaridade - " + idGrauEscolaridadeField.getValue());
    }

    @Subscribe("utentesTable.remove")
    protected void onUtentesTableRemove(Action.ActionPerformedEvent event) {
        utentesTableRemove.setConfirmation(false);
        if (utentesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos utentes")
                    .withMessage("Deve seleccionar pelo um dos utentes")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Utente user = utentesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do utente número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do utente número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        utentesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}