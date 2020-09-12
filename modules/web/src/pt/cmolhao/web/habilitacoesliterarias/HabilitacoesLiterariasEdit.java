package pt.cmolhao.web.habilitacoesliterarias;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.HabilitacoesLiterarias;
import pt.cmolhao.entity.Utente;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UiController("cmolhao_HabilitacoesLiterarias.edit")
@UiDescriptor("habilitacoes-literarias-edit.xml")
@EditedEntityContainer("habilitacoesLiterariasDc")
@LoadDataBeforeShow
public class HabilitacoesLiterariasEdit extends StandardEditor<HabilitacoesLiterarias> {
    @Inject
    protected LookupField<String> descricaoField;
    @Inject
    protected TextField<UUID> idHabilitacoesLiterariasField;
    @Inject
    protected Table<Utente> utentesTable;
    @Named("utentesTable.remove")
    protected RemoveAction<Utente> utentesTableRemove;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    public void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Sem Habilitação");
        list.add("Menos de 4 Anos de Escolaridade");
        list.add("4 Anos de Escolaridade (1.º ciclo do ensino básico)");
        list.add("6 Anos de escolaridade (2.º ciclo do ensino básico)");
        list.add("9.º ano (3.º ciclo do ensino básico)");
        list.add("11.º ano");
        list.add("12.º ano (ensino secundário)");
        list.add("Curso tecnológico/profissional/outros (nível III)*");
        list.add("Bacharelato");
        list.add("Licenciatura");
        list.add("Pós-Graduação");
        list.add("Mestrado");
        list.add("Doutoramento");
        list.add("Curso de especialização tecnológica");
        descricaoField.setOptionsList(list);

    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Atendimento Encaminhamento - " + idHabilitacoesLiterariasField.getValue());
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