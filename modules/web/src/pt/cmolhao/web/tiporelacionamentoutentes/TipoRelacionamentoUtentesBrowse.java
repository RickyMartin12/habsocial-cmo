package pt.cmolhao.web.tiporelacionamentoutentes;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoRelacionamentoUtentes;
import pt.cmolhao.web.tipoatendimento.TipoAtendimentoEdit;

import javax.inject.Inject;
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
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

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
        linhastipoRelacionamento.setValue(null);
        tipoRelacionamentoUtentesesDl.setMaxResults(0);
        tipoRelacionamentoUtentesesDl.removeParameter("tipoRelacionamento");
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
        tipoRelacionamentoUtentesesDl.load();

    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Relacionamento dos Utentes");
    }


}