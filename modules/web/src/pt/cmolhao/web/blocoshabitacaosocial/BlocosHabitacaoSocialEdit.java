package pt.cmolhao.web.blocoshabitacaosocial;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.BlocosHabitacaoSocial;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_BlocosHabitacaoSocial.edit")
@UiDescriptor("blocos-habitacao-social-edit.xml")
@EditedEntityContainer("blocosHabitacaoSocialDc")
@LoadDataBeforeShow
public class BlocosHabitacaoSocialEdit extends StandardEditor<BlocosHabitacaoSocial> {

    @Inject
    protected TextField<UUID> idBlocoshabitacaoSocialField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Blocos de Habitação Social - " + idBlocoshabitacaoSocialField.getValue());
    }
}