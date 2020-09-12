package pt.cmolhao.web.pessoalauxiliar;

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
import pt.cmolhao.entity.Localizacoes;
import pt.cmolhao.entity.PessoalAuxiliar;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.web.habitacaosocial.HabitacaoSocialEdit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@UiController("cmolhao_PessoalAuxiliar.browse")
@UiDescriptor("pessoal-auxiliar-browse.xml")
@LookupComponent("pessoalAuxiliarsTable")
@LoadDataBeforeShow
public class PessoalAuxiliarBrowse extends StandardLookup<PessoalAuxiliar> {
    @Named("pessoalAuxiliarsTable.remove")
    protected RemoveAction<PessoalAuxiliar> pessoalAuxiliarsTableRemove;
    @Inject
    private CollectionLoader<PessoalAuxiliar> pessoalAuxiliarsDl;
    @Inject
    private LookupField linhasPessoalAuxiliar;
    @Inject
    private LookupPickerField<Valencias> idvalenciaField;
    @Inject
    private DataManager dataManager;
    @Inject
    private LookupField habilitacoes_literarias_id;
    @Inject
    private GroupTable<PessoalAuxiliar> pessoalAuxiliarsTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;
    @Inject
    protected UiComponents uiComponents;

    @Inject
    private Dialogs dialogs;

    public Component generateValenciasDescricao(PessoalAuxiliar entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdValencia() != null)
        {
            label.setValue(entity.getIdValencia().getDescricaotecnica());
            return label;
        }
        return null;


    }



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
        linhasPessoalAuxiliar.setOptionsList(list);

        pessoalAuxiliarsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(pessoalAuxiliarsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (habilitacoes_literarias_id.getValue() != null)
                            {
                                customer.setHabilitacoesliterarias(habilitacoes_literarias_id.getValue().toString());
                            }
                            customer.setIdValencia(idvalenciaField.getValue());
                        })
                        .withScreenClass(PessoalAuxiliarEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("search_pssoal_auxiliar")
    public void onSearch_pssoal_auxiliarClick(Button.ClickEvent event) {

        // ID de valencia
        if (idvalenciaField.getValue() != null) {
            pessoalAuxiliarsDl.setParameter("idValencia",  idvalenciaField.getValue().getId());
        } else {
            pessoalAuxiliarsDl.removeParameter("idValencia");
        }

        // Habilitações Litetarias

        if (habilitacoes_literarias_id.getValue() != null) {
            pessoalAuxiliarsDl.setParameter("habilitacoesliterarias",  habilitacoes_literarias_id.getValue().toString());
        } else {
            pessoalAuxiliarsDl.removeParameter("habilitacoesliterarias");
        }


        pessoalAuxiliarsDl.load();


    }

    @Subscribe("linhasPessoalAuxiliar")
    public void onLinhasPessoalAuxiliarValueChange(HasValue.ValueChangeEvent event) {

        if (event.getValue() != null)
        {
            pessoalAuxiliarsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            pessoalAuxiliarsDl.setMaxResults(0);
        }
        pessoalAuxiliarsDl.load();

    }

    @Subscribe("reset_search_pssoal_auxiliar")
    public void onReset_search_pssoal_auxiliarClick(Button.ClickEvent event) {
        idvalenciaField.setValue(null);
        habilitacoes_literarias_id.setValue(null);
        pessoalAuxiliarsDl.removeParameter("idValencia");
        pessoalAuxiliarsDl.removeParameter("habilitacoesliterarias");
        pessoalAuxiliarsDl.load();
    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Pessoal Auxiliar");

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

    @Subscribe("pessoalAuxiliarsTable.remove")
    protected void onPessoalAuxiliarsTableRemove(Action.ActionPerformedEvent event) {
        pessoalAuxiliarsTableRemove.setConfirmation(false);
        if (pessoalAuxiliarsTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do pessoal auxiliar")
                    .withMessage("Deve seleccionar pelo um do pessoal auxiliar")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            PessoalAuxiliar user = pessoalAuxiliarsTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do pessoal auxiliar número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do pessoal auxiliar número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        pessoalAuxiliarsTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }

    }
}