package pt.cmolhao.web.habilitacoesliterarias;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.HabilitacoesLiterarias;

@UiController("cmolhao_HabilitacoesLiterarias.edit")
@UiDescriptor("habilitacoes-literarias-edit.xml")
@EditedEntityContainer("habilitacoesLiterariasDc")
@LoadDataBeforeShow
public class HabilitacoesLiterariasEdit extends StandardEditor<HabilitacoesLiterarias> {
}