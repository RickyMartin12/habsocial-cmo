package pt.cmolhao.web.valencias_relatorios_pessoas_sem_abrigo;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Valencias;

@UiController("cmolhao_Valencias.browseRelatoriosPessoasSemAbrigo")
@UiDescriptor("valencias-browse.xml")
@LookupComponent("valenciasesTable")
@LoadDataBeforeShow
public class ValenciasBrowse extends StandardLookup<Valencias> {
}