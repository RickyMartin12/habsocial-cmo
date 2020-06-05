package pt.cmolhao.web.atendimentoobjetivos;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.AtendimentoObjetivos;

@UiController("cmolhao_AtendimentoObjetivos.browse")
@UiDescriptor("atendimento-objetivos-browse.xml")
@LookupComponent("atendimentoObjetivosesTable")
@LoadDataBeforeShow
public class AtendimentoObjetivosBrowse extends StandardLookup<AtendimentoObjetivos> {
}