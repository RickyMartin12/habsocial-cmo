package pt.cmolhao.web.tiporendimento;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoRendimento;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_TipoRendimento.edit")
@UiDescriptor("tipo-rendimento-edit.xml")
@EditedEntityContainer("tipoRendimentoDc")
@LoadDataBeforeShow
public class TipoRendimentoEdit extends StandardEditor<TipoRendimento> {


    @Inject
    protected TextField<UUID> idTipoRendimentoField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Rendimento - " + idTipoRendimentoField.getValue());
    }
}