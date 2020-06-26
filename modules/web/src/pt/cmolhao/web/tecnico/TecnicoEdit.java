package pt.cmolhao.web.tecnico;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Tecnico;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_Tecnico.edit")
@UiDescriptor("tecnico-edit.xml")
@EditedEntityContainer("tecnicoDc")
@LoadDataBeforeShow
public class TecnicoEdit extends StandardEditor<Tecnico> {

    @Inject
    protected TextField<UUID> idTecnicoField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Técnico - " + idTecnicoField.getValue());
    }

}