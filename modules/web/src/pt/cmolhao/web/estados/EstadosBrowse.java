package pt.cmolhao.web.estados;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Estados;
import pt.cmolhao.web.tipoajuda.TipoAjudaEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Estados.browse")
@UiDescriptor("estados-browse.xml")
@LookupComponent("estadosesTable")
@LoadDataBeforeShow
public class EstadosBrowse extends StandardLookup<Estados> {
    @Inject
    protected LookupField linhasEstados;
    @Inject
    protected LookupField est_processos_apoio_id;
    @Inject
    protected CollectionLoader<Estados> estadosesDl;
    @Inject
    protected Table<Estados> estadosesTable;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    public void onInit(InitEvent event) {
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
        linhasEstados.setOptionsList(list);

        estadosesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(estadosesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (est_processos_apoio_id.getValue() != null)
                            {
                                customer.setEstadosProcessos(est_processos_apoio_id.getValue().toString());
                            }
                        })
                        .withScreenClass(EstadosEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("linhasEstados")
    protected void onLinhasEstadosValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            estadosesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            estadosesDl.setMaxResults(0);
        }
        estadosesDl.load();
    }

    @Subscribe("reset_tipo_ajuda")
    protected void onReset_tipo_ajudaClick(Button.ClickEvent event) {
        est_processos_apoio_id.setValue(null);
        linhasEstados.setValue(null);
        estadosesDl.removeParameter("descricaoTipoAjuda");
        estadosesDl.setMaxResults(0);
        estadosesDl.load();
    }

    @Subscribe("search_tipo_ajuda")
    protected void onSearch_tipo_ajudaClick(Button.ClickEvent event) {
        if (est_processos_apoio_id.getValue() != null) {
            estadosesDl.setParameter("descricaoTipoAjuda",  est_processos_apoio_id.getValue().toString());
        } else {
            estadosesDl.removeParameter("descricaoTipoAjuda");
        }
        estadosesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Estados");
    }


}