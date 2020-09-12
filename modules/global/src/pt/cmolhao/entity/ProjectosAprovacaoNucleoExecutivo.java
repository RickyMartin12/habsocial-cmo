package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'dbView':true,'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idprojectosemaprovacao"))
})
@Table(name = "projectos_aprovacao_nucleo_executivo")
@Entity(name = "cmolhao_ProjectosAprovacaoNucleoExecutivo")
public class ProjectosAprovacaoNucleoExecutivo extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 5674828837328816306L;
    @Column(name = "etapaprocesso", length = 45)
    protected String etapaprocesso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprojectosintervencao")
    protected ProjectoIntervencaoNucleoExecutivo idprojectosintervencao;


    @Column(name = "tipologia", length = 45)
    protected String tipologia;

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public ProjectoIntervencaoNucleoExecutivo getIdprojectosintervencao() {
        return idprojectosintervencao;
    }

    public void setIdprojectosintervencao(ProjectoIntervencaoNucleoExecutivo idprojectosintervencao) {
        this.idprojectosintervencao = idprojectosintervencao;
    }

    public String getEtapaprocesso() {
        return etapaprocesso;
    }

    public void setEtapaprocesso(String etapaprocesso) {
        this.etapaprocesso = etapaprocesso;
    }
}