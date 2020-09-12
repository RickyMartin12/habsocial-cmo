package pt.cmolhao.web.pessoaltecnico;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.PessoalTecnico;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.web.pessoalauxiliar.PessoalAuxiliarEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@UiController("cmolhao_PessoalTecnico.browse")
@UiDescriptor("pessoal-tecnico-browse.xml")
@LookupComponent("pessoalTecnicoesTable")
@LoadDataBeforeShow
public class PessoalTecnicoBrowse extends StandardLookup<PessoalTecnico> {

    @Named("pessoalTecnicoesTable.remove")
    protected RemoveAction<PessoalTecnico> pessoalTecnicoesTableRemove;
    @Inject
    private LookupField linhasPessoalTecnica;
    @Inject
    private LookupPickerField<Valencias> idvalenciaField;

    @Inject
    private LookupField habilitacoes_literarias_id;

    @Inject
    private CollectionLoader<PessoalTecnico> pessoalTecnicoesDl;
    @Inject
    private GroupTable<PessoalTecnico> pessoalTecnicoesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;
    @Inject
    private DataManager dataManager;
    @Inject
    protected UiComponents uiComponents;

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
        linhasPessoalTecnica.setOptionsList(list);

        pessoalTecnicoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(pessoalTecnicoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (habilitacoes_literarias_id.getValue() != null)
                            {
                                customer.setHabilitacoesliterarias(habilitacoes_literarias_id.getValue().toString());
                            }
                            customer.setIdValencia(idvalenciaField.getValue());
                        })
                        .withScreenClass(PessoalTecnicoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe("linhasPessoalTecnica")
    public void onLinhasPessoalTecnicaValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            pessoalTecnicoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            pessoalTecnicoesDl.setMaxResults(0);
        }
        pessoalTecnicoesDl.load();
    }

    @Subscribe("search_pessoal_tecnico")
    public void onSearch_pessoal_tecnicoClick(Button.ClickEvent event) {
        // ID de valencia
        if (idvalenciaField.getValue() != null) {
            pessoalTecnicoesDl.setParameter("idValencia",  idvalenciaField.getValue().getId());
        } else {
            pessoalTecnicoesDl.removeParameter("idValencia");
        }

        // Habilitações Litetarias

        if (habilitacoes_literarias_id.getValue() != null) {
            pessoalTecnicoesDl.setParameter("habilitacoesliterarias",  habilitacoes_literarias_id.getValue().toString());
        } else {
            pessoalTecnicoesDl.removeParameter("habilitacoesliterarias");
        }


        pessoalTecnicoesDl.load();
    }

    @Subscribe("reset_search_pessoal_tecnico")
    public void onReset_search_pessoal_tecnicoClick(Button.ClickEvent event) {
        idvalenciaField.setValue(null);
        habilitacoes_literarias_id.setValue(null);
        pessoalTecnicoesDl.removeParameter("idValencia");
        pessoalTecnicoesDl.removeParameter("habilitacoesliterarias");
        pessoalTecnicoesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Pessoal Técnico");
        // Habilitações Literarias

        List<String> list = new ArrayList<>();
        list.add("Sem Habilidade Literária");
        list.add("1.º Ciclo Ens. Primário do 1.ª ano ao 4.ª Ano");
        list.add("1.º Ciclo Ens. Básico do 5.ª ano ao 9.ª Ano");
        list.add("Ensino Secundário");
        list.add("Barcerlato");
        list.add("Ensino Universitário");
        list.add("Mestrado");
        habilitacoes_literarias_id.setOptionsList(list);


        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            map.put(item.getDescricaotecnica() + " " , item);
        }
        idvalenciaField.setOptionsMap(map);


    }

    public Component generateValenciasDescricao(PessoalTecnico entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdValencia() != null)
        {
            label.setValue(entity.getIdValencia().getDescricaotecnica());
            return label;
        }
        return null;
    }

    @Subscribe("pessoalTecnicoesTable.remove")
    protected void onPessoalTecnicoesTableRemove(Action.ActionPerformedEvent event) {
        pessoalTecnicoesTableRemove.setConfirmation(false);
        if (pessoalTecnicoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do pessoal técnico")
                    .withMessage("Deve seleccionar pelo um do pessoal técnico")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            PessoalTecnico user = pessoalTecnicoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do pessoal técnico número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do pessoal técnico número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        pessoalTecnicoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }

    }
}