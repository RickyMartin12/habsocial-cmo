package pt.cmolhao.web.atendimento_objetivos;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.AtendimentoObjetivos;

@UiController("cmolhao_AtendimentoObjetivos.edit")
@UiDescriptor("atendimento-objetivos-edit.xml")
@EditedEntityContainer("atendimentoObjetivosDc")
@LoadDataBeforeShow
public class AtendimentoObjetivosEdit extends StandardEditor<AtendimentoObjetivos> {
}