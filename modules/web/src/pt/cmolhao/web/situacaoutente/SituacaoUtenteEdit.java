package pt.cmolhao.web.situacaoutente;

import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.SituacaoUtente;

import javax.inject.Inject;
import java.util.Date;
import java.util.UUID;

@UiController("cmolhao_SituacaoUtente.edit")
@UiDescriptor("situacao-utente-edit.xml")
@EditedEntityContainer("situacaoUtenteDc")
@LoadDataBeforeShow
public class SituacaoUtenteEdit extends StandardEditor<SituacaoUtente> {
    @Inject
    protected DateField<Date> dataInicioField;
    @Inject
    protected DateField<Date> dataFimField;
    @Inject
    protected TextField<UUID> idSituacaoUtenteField;

    @Subscribe("dataInicioField")
    protected void onDataInicioFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated())
        {
            if (event.getValue() != null)
            {
                dataFimField.setRangeStart(event.getValue());
            }
        }
    }

    @Subscribe("dataFimField")
    protected void onDataFimFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                dataInicioField.setRangeEnd(event.getValue());
            }
        }
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Situação do Utente - " + idSituacaoUtenteField.getValue());
    }
}