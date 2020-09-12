package pt.cmolhao.web.tiporelacionamentoutentes;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.TipoRelacionamentoUtentes;
import pt.cmolhao.entity.TipologiaFamiliar;
import pt.cmolhao.web.tipoatendimento.TipoAtendimentoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoRelacionamentoUtentes.browse")
@UiDescriptor("tipo-relacionamento-utentes-browse.xml")
@LookupComponent("tipoRelacionamentoUtentesesTable")
@LoadDataBeforeShow
public class TipoRelacionamentoUtentesBrowse extends StandardLookup<TipoRelacionamentoUtentes> {
    @Inject
    protected Table<TipoRelacionamentoUtentes> tipoRelacionamentoUtentesesTable;
    @Inject
    protected LookupField linhastipoRelacionamento;
    @Inject
    protected LookupField tipoRelacionamentoField;
    @Inject
    protected CollectionLoader<TipoRelacionamentoUtentes> tipoRelacionamentoUtentesesDl;
    @Inject
    protected LookupField tipoRelacionamentoInvField;
    @Named("tipoRelacionamentoUtentesesTable.remove")
    protected RemoveAction<TipoRelacionamentoUtentes> tipoRelacionamentoUtentesesTableRemove;
    @Inject
    private DataManager dataManager;
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
        linhastipoRelacionamento.setOptionsList(list);

        List<String> list_tipo_relacionamento = new ArrayList<>();
        list_tipo_relacionamento.add("Pai");
        list_tipo_relacionamento.add("Mãe");
        list_tipo_relacionamento.add("Filho(a)");
        list_tipo_relacionamento.add("Tio(a)");
        list_tipo_relacionamento.add("Sobrinho(a)");
        list_tipo_relacionamento.add("Avó");
        list_tipo_relacionamento.add("Avô");
        list_tipo_relacionamento.add("Neto(a)");
        list_tipo_relacionamento.add("Sogro(a)");
        list_tipo_relacionamento.add("Genro");
        list_tipo_relacionamento.add("Nora");
        list_tipo_relacionamento.add("Bisavó");
        list_tipo_relacionamento.add("Bisavô");
        list_tipo_relacionamento.add("Bisneto(a)");
        list_tipo_relacionamento.add("Cunhado(a)");
        list_tipo_relacionamento.add("Amigo(a)");
        list_tipo_relacionamento.add("Colega de trabalho e/ou Chefe");
        list_tipo_relacionamento.add("Namorado(a)");
        tipoRelacionamentoField.setOptionsList(list_tipo_relacionamento);

        tipoRelacionamentoUtentesesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoRelacionamentoUtentesesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (tipoRelacionamentoField.getValue() != null)
                            {
                                customer.setTipoRelacionamento(tipoRelacionamentoField.getValue().toString());
                            }
                            if (tipoRelacionamentoInvField.getValue() != null)
                            {
                                customer.setTipoRelacionamentoInv(tipoRelacionamentoInvField.getValue().toString());
                            }
                        })
                        .withScreenClass(TipoRelacionamentoUtentesEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe("linhastipoRelacionamento")
    protected void onLinhastipoRelacionamentoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoRelacionamentoUtentesesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoRelacionamentoUtentesesDl.setMaxResults(0);
        }
        tipoRelacionamentoUtentesesDl.load();
    }

    @Subscribe("reset_tipo_relacionamento_utentes")
    protected void onReset_tipo_relacionamento_utentesClick(Button.ClickEvent event) {
        tipoRelacionamentoField.setValue(null);
        tipoRelacionamentoInvField.setValue(null);
        tipoRelacionamentoUtentesesDl.removeParameter("tipoRelacionamento");
        tipoRelacionamentoUtentesesDl.removeParameter("tipoRelacionamentoInv");
        tipoRelacionamentoUtentesesDl.load();
    }

    @Subscribe("search_tipo_relacionamento_utentes")
    protected void onSearch_tipo_relacionamento_utentesClick(Button.ClickEvent event) {
        if (tipoRelacionamentoField.getValue() != null)
        {
            tipoRelacionamentoUtentesesDl.setParameter("tipoRelacionamento",  tipoRelacionamentoField.getValue().toString());
        }
        else
        {
            tipoRelacionamentoUtentesesDl.removeParameter("tipoRelacionamento");
        }

        if (tipoRelacionamentoInvField.getValue() != null)
        {
            tipoRelacionamentoUtentesesDl.setParameter("tipoRelacionamentoInv",  tipoRelacionamentoInvField.getValue().toString());
        }
        else
        {
            tipoRelacionamentoUtentesesDl.removeParameter("tipoRelacionamentoInv");
        }
        tipoRelacionamentoUtentesesDl.load();

    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Relacionamento dos Utentes");
    }

    @Subscribe("tipoRelacionamentoField")
    protected void onTipoRelacionamentoFieldValueChange(HasValue.ValueChangeEvent event) {
        List<String> list_topologia_rel_inv = new ArrayList<>();

        list_topologia_rel_inv.clear();
        if (event.isUserOriginated()) {

            if(event.getValue() != null)
            {
                if (event.getValue().equals("Mãe") || event.getValue().equals("Pai")) {
                    list_topologia_rel_inv.add("Filho(a)");
                }
                if (event.getValue().equals("Filho(a)")) {
                    list_topologia_rel_inv.add("Mãe");
                    list_topologia_rel_inv.add("Pai");
                }
                if (event.getValue().equals("Tio(a)")) {
                    list_topologia_rel_inv.add("Sobrinho(a)");
                }
                if (event.getValue().equals("Sobrinho(a)")) {
                    list_topologia_rel_inv.add("Tio(a)");
                }
                if (event.getValue().equals("Avó") || tipoRelacionamentoField.getValue().equals("Avô")) {
                    list_topologia_rel_inv.add("Neto(a)");
                }
                if (event.getValue().equals("Neto(a)")) {
                    list_topologia_rel_inv.add("Avó");
                    list_topologia_rel_inv.add("Avô");
                }
                if (event.getValue().equals("Sogro(a)")) {
                    list_topologia_rel_inv.add("Genro");
                    list_topologia_rel_inv.add("Nora");
                }
                if (event.getValue().equals("Cunhado(a)")) {
                    list_topologia_rel_inv.add("Cunhado(a)");
                }
                if (event.getValue().equals("Genro") || tipoRelacionamentoField.getValue().equals("Nora")) {
                    list_topologia_rel_inv.add("Sogro(a)");
                }
                if (event.getValue().equals("Bisavó") || tipoRelacionamentoField.getValue().equals("Bisavô")) {
                    list_topologia_rel_inv.add("Bisneto(a)");
                }
                if (event.getValue().equals("Bisneto(a)")) {
                    list_topologia_rel_inv.add("Bisavó");
                    list_topologia_rel_inv.add("Bisavô");
                }
                if (event.getValue().equals("Amigo(a)")) {
                    list_topologia_rel_inv.add("Amigo(a)");
                }
                if (event.getValue().equals("Colega de trabalho e/ou Chefe")) {
                    list_topologia_rel_inv.add("Colega de trabalho e/ou Chefe");
                }
                if (event.getValue().equals("Namorado(a)")) {
                    list_topologia_rel_inv.add("Namorado(a)");
                }
                tipoRelacionamentoInvField.setEditable(true);
                tipoRelacionamentoInvField.setValue(null);
                tipoRelacionamentoInvField.setOptionsList(list_topologia_rel_inv);
            }
            else
            {
                tipoRelacionamentoField.setValue(null);
                tipoRelacionamentoInvField.setValue(null);
                tipoRelacionamentoInvField.setEditable(false);
            }
        }
    }

    @Subscribe("tipoRelacionamentoUtentesesTable.remove")
    protected void onTipoRelacionamentoUtentesesTableRemove(Action.ActionPerformedEvent event) {
        tipoRelacionamentoUtentesesTableRemove.setConfirmation(false);
        if (tipoRelacionamentoUtentesesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do tipo de relacionamento dos utentes")
                    .withMessage("Deve seleccionar pelo um dos tipos de relacionamentos dos utentes")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TipoRelacionamentoUtentes user = tipoRelacionamentoUtentesesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do tipo de relacionamento dos utentes número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do tipo de relacionamento dos utentes número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tipoRelacionamentoUtentesesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}