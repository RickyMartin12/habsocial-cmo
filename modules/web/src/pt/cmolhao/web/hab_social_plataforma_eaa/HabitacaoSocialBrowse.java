package pt.cmolhao.web.hab_social_plataforma_eaa;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.HabitacaoSocial;

@UiController("cmolhao_HabitacaoSocial.browsePlataformaEaa")
@UiDescriptor("habitacao-social-browse.xml")
@LookupComponent("habitacaoSocialsTable")
@LoadDataBeforeShow
public class HabitacaoSocialBrowse extends StandardLookup<HabitacaoSocial> {
}