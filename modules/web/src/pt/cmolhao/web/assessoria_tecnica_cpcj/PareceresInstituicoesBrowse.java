package pt.cmolhao.web.assessoria_tecnica_cpcj;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.Pareceres;
import pt.cmolhao.entity.PareceresInstituicoes;
import pt.cmolhao.entity.TipoPareceres;

import javax.inject.Inject;
import java.util.*;

@UiController("cmolhao_PareceresInstituicoes.browseAssessoriaTecnicaCPCJ")
@UiDescriptor("pareceres-instituicoes-browse.xml")
@LookupComponent("pareceresInstituicoesesTable")
@LoadDataBeforeShow
public class PareceresInstituicoesBrowse extends StandardLookup<PareceresInstituicoes> {
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected LookupPickerField<Pareceres> idParecerField;
    @Inject
    protected LookupPickerField<TipoPareceres> idTipoParecerField;
    @Inject
    protected LookupField linhasTipoParaceres;
    @Inject
    protected CollectionContainer<Pareceres> pareceresesDc;
    @Inject
    protected CollectionContainer<TipoPareceres> tipoPareceresesDc;
    @Inject
    protected CollectionLoader<PareceresInstituicoes> pareceresInstituicoesesDl;

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
        linhasTipoParaceres.setOptionsList(list);
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar de Pareceres - Assessoria Tecnica do CPCJ");

        idParecerField.setEditable(false);

        Map<String, Pareceres> map = new HashMap<>();
        Collection<Pareceres> customers = pareceresesDc.getItems();
        for (Pareceres item : customers) {
            if (item.getNome().equals("Assessoria Técnica"))
            {
                idParecerField.setValue(item);
                map.put(item.getNome(), item);
            }
        }

        idParecerField.setOptionsMap(map);

        if(idParecerField.getValue() != null)
        {
            Map<String, TipoPareceres> map2 = new HashMap<>();
            Collection<TipoPareceres> customers2 = tipoPareceresesDc.getItems();
            for (TipoPareceres item2 : customers2) {
                if (item2.getIdTipoPareceres().getId().equals(idParecerField.getValue().getId()))
                {
                    if (item2.getNome().equals("CPCJ"))
                    {
                        idTipoParecerField.setValue(item2);
                        map2.put(item2.getNome(), item2);
                    }

                }
            }
            idTipoParecerField.setEditable(false);
            idTipoParecerField.setOptionsMap(map2);
        }
    }

    @Subscribe("reset_tipo_pareceres")
    protected void onReset_tipo_pareceresClick(Button.ClickEvent event) {
        idInstituicaoField.setValue(null);
        pareceresInstituicoesesDl.removeParameter("idInstituicao");
        pareceresInstituicoesesDl.load();
    }

    @Subscribe("search_tipo_pareceres")
    protected void onSearch_tipo_pareceresClick(Button.ClickEvent event) {
        if (idInstituicaoField.getValue() != null) {
            pareceresInstituicoesesDl.setParameter("idInstituicao",  idInstituicaoField.getValue().getId());
        } else {
            pareceresInstituicoesesDl.removeParameter("idInstituicao");
        }

        pareceresInstituicoesesDl.load();
    }

    @Subscribe("linhasTipoParaceres")
    protected void onLinhasTipoParaceresValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            pareceresInstituicoesesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            pareceresInstituicoesesDl.setMaxResults(0);
        }
        pareceresInstituicoesesDl.load();
    }
}