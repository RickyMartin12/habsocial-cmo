package pt.cmolhao.web.situacaoutente;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.SituacaoUtente;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Inject
    private Notifications notifications;

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

    @Subscribe(target = Target.DATA_CONTEXT)
    private void onPreCommit(DataContext.PreCommitEvent event) throws ParseException {
        if (dataInicioField.getValue() == null)
        {
            java.util.Date date2 = null;
            String dataFim = "1970-01-01";
            try {
                DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                date2 = formatter2.parse(dataFim);
                dataInicioField.setValue(date2);
            } catch (ParseException e) {
                throw e;
            }

        }
        if (dataFimField.getValue() == null)
        {
            java.util.Date date2 = null;
            String dataFim = "1970-01-01";
            try {
                DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                date2 = formatter2.parse(dataFim);
                dataFimField.setValue(date2);
            } catch (ParseException e) {
                throw e;
            }
        }


        if(dataInicioField.getValue().after(dataFimField.getValue()) || dataFimField.getValue().before(dataInicioField.getValue())) {
            notifications.create()
                    .withCaption("Data de inicio nao pode ser superior a data de fim e/ou a data de fim nao pode ser inferior a data de inicio ")
                    .show();
            event.preventCommit();
        }

    }


}