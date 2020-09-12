package pt.cmolhao.web.nucleo_executivo_elementos_membros;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.ApoiosNucleoExecutivo;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_ApoiosNucleoExecutivo.edit")
@UiDescriptor("apoios-nucleo-executivo-edit.xml")
@EditedEntityContainer("apoiosNucleoExecutivoDc")
@LoadDataBeforeShow
public class ApoiosNucleoExecutivoEdit extends StandardEditor<ApoiosNucleoExecutivo> {

    @Inject
    protected TextField<UUID> idApoiosField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Apoios do núcleo executivo - " + idApoiosField.getValue());
    }
}