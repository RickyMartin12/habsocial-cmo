package pt.cmolhao.web.tipocivil;

import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoCivil;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UiController("cmolhao_TipoCivil.edit")
@UiDescriptor("tipo-civil-edit.xml")
@EditedEntityContainer("tipoCivilDc")
@LoadDataBeforeShow
public class TipoCivilEdit extends StandardEditor<TipoCivil> {

    @Inject
    protected TextField<UUID> idTipoCivilField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Civil - " + idTipoCivilField.getValue());
    }

}