package pt.cmolhao.web.pareceres_instituicao;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.Pareceres;
import pt.cmolhao.entity.PareceresInstituicoes;
import pt.cmolhao.entity.TipoPareceres;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UiController("cmolhao_PareceresInstituicoes.edit")
@UiDescriptor("pareceres-instituicoes-edit.xml")
@EditedEntityContainer("pareceresInstituicoesDc")
@LoadDataBeforeShow
public class PareceresInstituicoesEdit extends StandardEditor<PareceresInstituicoes> {
    @Inject
    protected CollectionContainer<Pareceres> pareceresesDc;
    @Inject
    protected CollectionContainer<TipoPareceres> tipoPareceresesDc;
    @Inject
    protected CollectionContainer<Instituicoes> InstituicaoDc;
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected LookupPickerField<Pareceres> idParecerField;
    @Inject
    protected LookupPickerField<TipoPareceres> idTipoParecerField;
    @Inject
    protected TextField<UUID> idParaceresInstituicoesField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Pareceres dde Instituição - " + idParaceresInstituicoesField.getValue());


        if (idParecerField.getValue() != null) {
            Map<String, TipoPareceres> map = new HashMap<>();
            Collection<TipoPareceres> customers = tipoPareceresesDc.getItems();
            for (TipoPareceres item : customers) {
                if (item.getIdTipoPareceres().getId().equals(idParecerField.getValue().getId()))
                {
                    map.put(item.getNome(), item);
                }
            }
            idTipoParecerField.setEditable(true);
            idTipoParecerField.setOptionsMap(map);
        }

    }



    @Subscribe("idParecerField")
    protected void onIdParecerFieldValueChange(HasValue.ValueChangeEvent<Pareceres> event) {
        if (event.isUserOriginated()) {

            if (event.getValue() != null) {
                Map<String, TipoPareceres> map = new HashMap<>();
                Collection<TipoPareceres> customers = tipoPareceresesDc.getItems();
                for (TipoPareceres item : customers) {
                    if (item.getIdTipoPareceres().getId().equals(event.getValue().getId()))
                    {
                        idTipoParecerField.setValue(null);
                        map.put(item.getNome(), item);
                    }
                }
                idTipoParecerField.setEditable(true);
                idTipoParecerField.setOptionsMap(map);
            }
        }
    }


}