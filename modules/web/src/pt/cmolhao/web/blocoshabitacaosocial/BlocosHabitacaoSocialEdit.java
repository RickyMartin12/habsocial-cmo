package pt.cmolhao.web.blocoshabitacaosocial;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.BlocosHabitacaoSocial;

@UiController("cmolhao_BlocosHabitacaoSocial.edit")
@UiDescriptor("blocos-habitacao-social-edit.xml")
@EditedEntityContainer("blocosHabitacaoSocialDc")
@LoadDataBeforeShow
public class BlocosHabitacaoSocialEdit extends StandardEditor<BlocosHabitacaoSocial> {
}