package pt.cmolhao.web.Dashboard;

import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.screen.*;


import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;

@UiController("cmolhao_DashboardValencia")
@UiDescriptor("dashboard_valencia.xml")
@LookupComponent("valenciasesTable")
@LoadDataBeforeShow
public class DashboardValencia extends StandardLookup<Valencias> {

    private static final String HTML = "<h1>Dashboard de Valências e os seus tipos em cada instituição</h1>\n";
    private static final String HTML_Instituicao = "<h2>Gráfico circular sobre as instituição em relação a capacidade</h2>";
    private static final String HTML_Valencia = "<h2>Gráfico de barras sobre os tipos de valência em relação a capacidade</h2>";

    @Inject
    private Label<String> htmlLabel;
    @Inject
    private Label<String> htmlLabel_Instituicao;
    @Inject
    private Label<String> htmlLabel_Valencia;


    @Subscribe
    protected void onInit(InitEvent event) {
        htmlLabel.setValue(HTML);
        htmlLabel_Instituicao.setValue(HTML_Instituicao);
        htmlLabel_Valencia.setValue(HTML_Valencia);

    }
}