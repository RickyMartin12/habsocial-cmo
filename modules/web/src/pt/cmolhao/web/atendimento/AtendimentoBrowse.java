package pt.cmolhao.web.atendimento;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Atendimento;

@UiController("cmolhao_Atendimento.browse")
@UiDescriptor("atendimento-browse.xml")
@LookupComponent("atendimentoesTable")
@LoadDataBeforeShow
public class AtendimentoBrowse extends StandardLookup<Atendimento> {
}