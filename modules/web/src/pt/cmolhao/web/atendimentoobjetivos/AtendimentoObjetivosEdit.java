package pt.cmolhao.web.atendimentoobjetivos;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.AtendimentoObjetivos;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_AtendimentoObjetivos.edit")
@UiDescriptor("atendimento-objetivos-edit.xml")
@EditedEntityContainer("atendimentoObjetivosDc")
@LoadDataBeforeShow
public class AtendimentoObjetivosEdit extends StandardEditor<AtendimentoObjetivos> {

    @Inject
    protected TextField<UUID> idAtendimentoObjetivosField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Atendimento Objetivos - " + idAtendimentoObjetivosField.getValue());
    }

}