package pt.cmolhao.web.Dashboard;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import java.awt.*;

@UiController("cmolhao_DashboardValencia")
@UiDescriptor("dashboard_valencia.xml")
@LookupComponent("valenciasesTable")
@LoadDataBeforeShow
public class DashboardValencia extends StandardLookup<Valencias> {

    private static final String HTML = "<h1>Dashboard de Valências e os seus tipos em cada instituição</h1>\n";
    private static final String HTML_Instituicao = "<h2>Gráfico circular sobre as instituição em relação ao número de pessoas</h2>";
    private static final String HTML_Valencia = "<h2>Gráfico de barras sobre as áreas de intervenção em relação ao número de pessoas</h2>";

    @Inject
    private com.haulmont.cuba.gui.components.Label<String> htmlLabel;
    @Inject
    private com.haulmont.cuba.gui.components.Label<String> htmlLabel_Instituicao;
    @Inject
    private com.haulmont.cuba.gui.components.Label<String> htmlLabel_Valencia;


    @Subscribe
    protected void onInit(InitEvent event) {
        htmlLabel.setValue(HTML);
        htmlLabel_Instituicao.setValue(HTML_Instituicao);
        htmlLabel_Valencia.setValue(HTML_Valencia);

    }
}