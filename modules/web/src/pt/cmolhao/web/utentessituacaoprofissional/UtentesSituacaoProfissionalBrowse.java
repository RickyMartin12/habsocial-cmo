package pt.cmolhao.web.utentessituacaoprofissional;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Utente;
import pt.cmolhao.entity.UtentesSituacaoProfissional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_UtentesSituacaoProfissional.browse")
@UiDescriptor("utentes-situacao-profissional-browse.xml")
@LookupComponent("utentesSituacaoProfissionalsTable")
@LoadDataBeforeShow
public class UtentesSituacaoProfissionalBrowse extends StandardLookup<UtentesSituacaoProfissional> {
    @Inject
    protected CollectionLoader<UtentesSituacaoProfissional> utentesSituacaoProfissionalsDl;
    @Inject
    protected LookupPickerField<Utente> utenteField;
    @Inject
    protected LookupField linhasUtentesSituacaoProfissao;
    @Inject
    protected LookupField situacaoProfissionalField;
    @Inject
    protected GroupTable<UtentesSituacaoProfissional> utentesSituacaoProfissionalsTable;
    @Named("utentesSituacaoProfissionalsTable.remove")
    protected RemoveAction<UtentesSituacaoProfissional> utentesSituacaoProfissionalsTableRemove;
    @Inject
    private ScreenBuilders screenBuilders;
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
        linhasUtentesSituacaoProfissao.setOptionsList(list);

        List<String> list_situacao_profissional = new ArrayList<>();
        list_situacao_profissional.add("Trabalhador por conta de outrém");
        list_situacao_profissional.add("Bolseiro");
        list_situacao_profissional.add("Desempregado");
        list_situacao_profissional.add("Estagiário");
        list_situacao_profissional.add("Trabalhador por conta própria (Com trabalhadores a cargo)");
        list_situacao_profissional.add("Trabalhador por conta própria (Sem trabalhadores a cargo)");
        situacaoProfissionalField.setOptionsList(list_situacao_profissional);


        utentesSituacaoProfissionalsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(utentesSituacaoProfissionalsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setUtente(utenteField.getValue());
                            if (situacaoProfissionalField.getValue() != null)
                            {
                                customer.setSituacaoProfissional(situacaoProfissionalField.getValue().toString());
                            }
                        })
                        .withScreenClass(UtentesSituacaoProfissionalEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("reset_utentes_situacao_profissao")
    protected void onReset_utentes_situacao_profissaoClick(Button.ClickEvent event) {
        utenteField.setValue(null);
        situacaoProfissionalField.setValue(null);
        utentesSituacaoProfissionalsDl.removeParameter("situacaoProfissional");
        utentesSituacaoProfissionalsDl.removeParameter("utente");
        utentesSituacaoProfissionalsDl.load();
    }

    @Subscribe("search_utentes_situacao_profissao")
    protected void onSearch_utentes_situacao_profissaoClick(Button.ClickEvent event) {
        if (utenteField.getValue() != null)
        {
            utentesSituacaoProfissionalsDl.setParameter("utente", utenteField.getValue().getId());
        }
        else
        {
            utentesSituacaoProfissionalsDl.removeParameter("utente");
        }

        if (situacaoProfissionalField.getValue() != null)
        {
            utentesSituacaoProfissionalsDl.setParameter("situacaoProfissional", situacaoProfissionalField.getValue().toString());
        }
        else
        {
            utentesSituacaoProfissionalsDl.removeParameter("situacaoProfissional");
        }
        utentesSituacaoProfissionalsDl.load();
    }

    @Subscribe("linhasUtentesSituacaoProfissao")
    protected void onLinhasUtentesSituacaoProfissaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            utentesSituacaoProfissionalsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            utentesSituacaoProfissionalsDl.setMaxResults(0);
        }
        utentesSituacaoProfissionalsDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Utentes de Situações Profissionais");
    }

    @Subscribe("utentesSituacaoProfissionalsTable.remove")
    protected void onUtentesSituacaoProfissionalsTableRemove(Action.ActionPerformedEvent event) {
        utentesSituacaoProfissionalsTableRemove.setConfirmation(false);
        if (utentesSituacaoProfissionalsTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos utentes de situação profissional")
                    .withMessage("Deve seleccionar pelo um dos utentes de situação profissional")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            UtentesSituacaoProfissional user = utentesSituacaoProfissionalsTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela dos utentes de situação profissional número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela dos utentes de situação profissional número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        utentesSituacaoProfissionalsTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}