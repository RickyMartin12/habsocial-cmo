package pt.cmolhao.web.profissao;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Profissao;

@UiController("cmolhao_Profissao.browse")
@UiDescriptor("profissao-browse.xml")
@LookupComponent("profissaosTable")
@LoadDataBeforeShow
public class ProfissaoBrowse extends StandardLookup<Profissao> {
}