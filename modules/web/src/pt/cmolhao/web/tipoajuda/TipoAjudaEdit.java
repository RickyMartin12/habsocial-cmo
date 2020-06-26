package pt.cmolhao.web.tipoajuda;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoAjuda;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_TipoAjuda.edit")
@UiDescriptor("tipo-ajuda-edit.xml")
@EditedEntityContainer("tipoAjudaDc")
@LoadDataBeforeShow
public class TipoAjudaEdit extends StandardEditor<TipoAjuda> {

    @Inject
    protected TextField<UUID> idTipoAjudaField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Ajuda - " + idTipoAjudaField.getValue());
    }


}