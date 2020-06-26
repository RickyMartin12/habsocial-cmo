package pt.cmolhao.web.tipossituacoesutentes;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TiposSituacoesUtentes;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_TiposSituacoesUtentes.edit")
@UiDescriptor("tipos-situacoes-utentes-edit.xml")
@EditedEntityContainer("tiposSituacoesUtentesDc")
@LoadDataBeforeShow
public class TiposSituacoesUtentesEdit extends StandardEditor<TiposSituacoesUtentes> {

    @Inject
    protected TextField<UUID> idTipoSituacaoUtenteField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Situação do Utente - " + idTipoSituacaoUtenteField.getValue());
    }
}