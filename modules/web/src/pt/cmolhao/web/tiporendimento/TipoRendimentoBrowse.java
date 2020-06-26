package pt.cmolhao.web.tiporendimento;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoRendimento;
import pt.cmolhao.web.tiporelacionamentoutentes.TipoRelacionamentoUtentesEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoRendimento.browse")
@UiDescriptor("tipo-rendimento-browse.xml")
@LookupComponent("tipoRendimentoesTable")
@LoadDataBeforeShow
public class TipoRendimentoBrowse extends StandardLookup<TipoRendimento> {
    @Inject
    protected LookupField linhastipoRendimento;
    @Inject
    protected CollectionLoader<TipoRendimento> tipoRendimentoesDl;
    @Inject
    protected LookupField tipoRendimentoField;
    @Inject
    protected Table<TipoRendimento> tipoRendimentoesTable;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe("linhastipoRendimento")
    protected void onLinhastipoRendimentoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoRendimentoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoRendimentoesDl.setMaxResults(0);
        }
        tipoRendimentoesDl.load();
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
        linhastipoRendimento.setOptionsList(list);

        tipoRendimentoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoRendimentoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (tipoRendimentoField.getValue() != null)
                            {
                                customer.setTipoRendimento(tipoRendimentoField.getValue().toString());
                            }
                        })
                        .withScreenClass(TipoRendimentoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("reset_tipo_rendimento_utentes")
    protected void onReset_tipo_rendimento_utentesClick(Button.ClickEvent event) {
        tipoRendimentoField.setValue(null);
        linhastipoRendimento.setValue(null);
        tipoRendimentoesDl.setMaxResults(0);
        tipoRendimentoesDl.removeParameter("tipoRendimento");
        tipoRendimentoesDl.load();
    }

    @Subscribe("search_tipo_rendimento_utentes")
    protected void onSearch_tipo_rendimento_utentesClick(Button.ClickEvent event) {
        if (tipoRendimentoField.getValue() != null)
        {
            tipoRendimentoesDl.setParameter("tipoRendimento",  tipoRendimentoField.getValue().toString());
        }
        else
        {
            tipoRendimentoesDl.removeParameter("tipoRendimento");
        }
        tipoRendimentoesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Rendimentos");
    }

}