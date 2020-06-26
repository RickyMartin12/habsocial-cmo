package pt.cmolhao.web.estados;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Estados;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_Estados.edit")
@UiDescriptor("estados-edit.xml")
@EditedEntityContainer("estadosDc")
@LoadDataBeforeShow
public class EstadosEdit extends StandardEditor<Estados> {


    @Inject
    protected TextField<UUID> idEstadosField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Estados - " + idEstadosField.getValue());
    }
}