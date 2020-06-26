package pt.cmolhao.web.tipossituacoesutentes;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TiposSituacoesUtentes;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TiposSituacoesUtentes.browse")
@UiDescriptor("tipos-situacoes-utentes-browse.xml")
@LookupComponent("tiposSituacoesUtentesesTable")
@LoadDataBeforeShow
public class TiposSituacoesUtentesBrowse extends StandardLookup<TiposSituacoesUtentes> {
    @Inject
    protected CollectionLoader<TiposSituacoesUtentes> tiposSituacoesUtentesesDl;
    @Inject
    protected LookupField linhasTiposSituacoesUtentes;

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
        linhasTiposSituacoesUtentes.setOptionsList(list);
    }

    @Subscribe("linhasTiposSituacoesUtentes")
    protected void onLinhasTiposSituacoesUtentesValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tiposSituacoesUtentesesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tiposSituacoesUtentesesDl.setMaxResults(0);
        }
        tiposSituacoesUtentesesDl.load();
    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Situações dos Utentes");
    }
}