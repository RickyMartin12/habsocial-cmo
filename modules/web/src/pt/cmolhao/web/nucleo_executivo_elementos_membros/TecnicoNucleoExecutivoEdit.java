package pt.cmolhao.web.nucleo_executivo_elementos_membros;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TecnicoNucleoExecutivo;

@UiController("cmolhao_TecnicoNucleoExecutivo.edit")
@UiDescriptor("tecnico-nucleo-executivo-edit.xml")
@EditedEntityContainer("tecnicoNucleoExecutivoDc")
@LoadDataBeforeShow
public class TecnicoNucleoExecutivoEdit extends StandardEditor<TecnicoNucleoExecutivo> {
}