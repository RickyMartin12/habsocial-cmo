package pt.cmolhao.web.habilitacoesliterarias;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.HabilitacoesLiterarias;

@UiController("cmolhao_HabilitacoesLiterarias.browse")
@UiDescriptor("habilitacoes-literarias-browse.xml")
@LookupComponent("habilitacoesLiterariasesTable")
@LoadDataBeforeShow
public class HabilitacoesLiterariasBrowse extends StandardLookup<HabilitacoesLiterarias> {
}