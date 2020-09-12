package pt.cmolhao.web.membros_rede_trabalho;

import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.MembrosRedeTrabalho;
import pt.cmolhao.entity.RedeTrabalho;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_MembrosRedeTrabalho.edit")
@UiDescriptor("membros-rede-trabalho-edit.xml")
@EditedEntityContainer("membrosRedeTrabalhoDc")
@LoadDataBeforeShow
public class MembrosRedeTrabalhoEdit extends StandardEditor<MembrosRedeTrabalho> {
    @Inject
    protected TextField<UUID> idMembrosTrabalho;
    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Membros de Rede de Trabalho - " + idMembrosTrabalho.getValue());


    }


}