package pt.cmolhao.web.Dashboard;

import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Atendimento;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;

@UiController("cmolhao_DashboardAtendimentoSocial")
@UiDescriptor("dashboard_atendimento_social.xml")
@LoadDataBeforeShow
public class DashboardAtendimentoSocial extends StandardLookup<Atendimento> {
    @Inject
    protected Label<String> htmlLabel;

    private static final String HTML = "<h1>Dashboard do Atendimento Social</h1>\n\n";
    private static final String HTML_table_atend_utentes = "<b>Tabela do Tipo de  Atendimento Social aos utentes</b>\n\n";
    private static final String HTML_table_arrendamento_utentes = "<b>Tabela do Tipo de  Arrendamento nos utentes</b>\n\n";
    private static final String HTML_table_moradores = "<b>Tabela de moradores no estrangeiro por freguesias</b>\n\n";
    private static final String HTML_graph_atend_utentes = "<h2>Gráfico do Tipo de  Atendimento Social em relação aos utentes</h2>\n\n";
    private static final String HTML_graph_arrendamento_utentes = "<h2>Gráfico do Tipo de  Arrendamento em relação aos  utentes</h2>\n\n";
    private static final String HTML_graph_moradores = "<h2>Gráfico de moradores no estrangeiro por freguesias</h2>\n\n";


    @Inject
    protected Label<String> htmlLabel_Valencia;
    @Inject
    protected Label<String> htmlLabel_Arrendamento;
    @Inject
    protected Label<String> htmlLabel_Moradores;
    @Inject
    protected Label<String> htmlLabel_Valencia_Graph;
    @Inject
    protected Label<String> htmlLabel_Arrendamento_Graph;
    @Inject
    protected Label<String> htmlLabel_Moradores_Graph;

    @Subscribe
    protected void onInit(InitEvent event) {
        htmlLabel.setValue(HTML);
        htmlLabel_Valencia.setValue(HTML_table_atend_utentes);
        htmlLabel_Arrendamento.setValue(HTML_table_arrendamento_utentes);
        htmlLabel_Moradores.setValue(HTML_table_moradores);
        htmlLabel_Valencia_Graph.setValue(HTML_graph_atend_utentes);
        htmlLabel_Arrendamento_Graph.setValue(HTML_graph_arrendamento_utentes);
        htmlLabel_Moradores_Graph.setValue(HTML_graph_moradores);
    }
}