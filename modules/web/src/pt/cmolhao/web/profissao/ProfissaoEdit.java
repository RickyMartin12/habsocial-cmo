package pt.cmolhao.web.profissao;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Profissao;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_Profissao.edit")
@UiDescriptor("profissao-edit.xml")
@EditedEntityContainer("profissaoDc")
@LoadDataBeforeShow
public class ProfissaoEdit extends StandardEditor<Profissao> {

    @Inject
    protected TextField<UUID> idProfissaoField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Profissões - " + idProfissaoField.getValue());
    }
}