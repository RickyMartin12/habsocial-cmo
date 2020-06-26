package pt.cmolhao.web.apoios;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Apoios;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_Apoios.edit")
@UiDescriptor("apoios-edit.xml")
@EditedEntityContainer("apoiosDc")
@LoadDataBeforeShow
public class ApoiosEdit extends StandardEditor<Apoios> {

    @Inject
    protected TextField<UUID> idApoiosField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Apoios - " + idApoiosField.getValue());
    }
}