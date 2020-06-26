package pt.cmolhao.web.tiposvalencia;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Tiposvalencia;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_Tiposvalencia.edit")
@UiDescriptor("tiposvalencia-edit.xml")
@EditedEntityContainer("tiposvalenciaDc")
@LoadDataBeforeShow
public class TiposvalenciaEdit extends StandardEditor<Tiposvalencia> {

    @Inject
    protected TextField<UUID> idTipoValenciaField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Valência - " + idTipoValenciaField.getValue());
    }

}