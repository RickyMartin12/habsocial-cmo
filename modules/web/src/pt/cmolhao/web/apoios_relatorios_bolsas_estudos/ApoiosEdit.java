package pt.cmolhao.web.apoios_relatorios_bolsas_estudos;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Apoios;

@UiController("cmolhao_Apoios.editRelatoriosBolsasEstudos")
@UiDescriptor("apoios-edit.xml")
@EditedEntityContainer("apoiosDc")
@LoadDataBeforeShow
public class ApoiosEdit extends StandardEditor<Apoios> {
}