package pt.cmolhao.web.tiposvalencia;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Tiposvalencia;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Tiposvalencia.browse")
@UiDescriptor("tiposvalencia-browse.xml")
@LookupComponent("tiposvalenciasTable")
@LoadDataBeforeShow
public class TiposvalenciaBrowse extends StandardLookup<Tiposvalencia> {
    @Inject
    private CollectionLoader<Tiposvalencia> tiposvalenciasDl;
    @Inject
    private LookupField linhasTiposValencias;

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
        linhasTiposValencias.setOptionsList(list);

    }

    @Subscribe("linhasTiposValencias")
    public void onLinhasTiposValenciasValueChange(HasValue.ValueChangeEvent event) {

        if (event.getValue() != null)
        {
            tiposvalenciasDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tiposvalenciasDl.setMaxResults(0);
        }
        tiposvalenciasDl.load();

    }
}