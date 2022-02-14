package pt.cmolhao.web.habitacao_social_relatorios_dados;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.HabitacaoSocial;

@UiController("cmolhao_HabitacaoSocial.browseRelatorios")
@UiDescriptor("habitacao-social-browse.xml")
@LookupComponent("habitacaoSocialsTable")
@LoadDataBeforeShow
public class HabitacaoSocialBrowse extends StandardLookup<HabitacaoSocial> {
}