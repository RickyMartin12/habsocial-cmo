package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idprojectosemaprovacao"))
})
@Table(name = "projectos_em_aprovacao")
@Entity(name = "cmolhao_ProjectosEmAprovacao")
public class ProjectosEmAprovacao extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 766498386604972900L;
    @Column(name = "etapaprocesso", length = 45)
    protected String etapaprocesso;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprojectosintervencao")
    protected ProjectosIntervencao idprojectosintervencao;
    @Column(name = "tipologia", length = 45)
    protected String tipologia;

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public ProjectosIntervencao getIdprojectosintervencao() {
        return idprojectosintervencao;
    }

    public void setIdprojectosintervencao(ProjectosIntervencao idprojectosintervencao) {
        this.idprojectosintervencao = idprojectosintervencao;
    }

    public String getEtapaprocesso() {
        return etapaprocesso;
    }

    public void setEtapaprocesso(String etapaprocesso) {
        this.etapaprocesso = etapaprocesso;
    }
}