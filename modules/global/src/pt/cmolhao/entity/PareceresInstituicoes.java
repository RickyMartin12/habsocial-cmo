package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true}")
@Table(name = "pareceres_instituicoes")
@Entity(name = "cmolhao_PareceresInstituicoes")
public class PareceresInstituicoes extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 2431195889111726253L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instituicao")
    protected Instituicoes idInstituicao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parecer")
    protected pt.cmolhao.entity.Pareceres idParecer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_parecer")
    protected pt.cmolhao.entity.TipoPareceres idTipoParecer;

    public pt.cmolhao.entity.TipoPareceres getIdTipoParecer() {
        return idTipoParecer;
    }

    public void setIdTipoParecer(pt.cmolhao.entity.TipoPareceres idTipoParecer) {
        this.idTipoParecer = idTipoParecer;
    }

    public pt.cmolhao.entity.Pareceres getIdParecer() {
        return idParecer;
    }

    public void setIdParecer(pt.cmolhao.entity.Pareceres idParecer) {
        this.idParecer = idParecer;
    }

    public Instituicoes getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Instituicoes idInstituicao) {
        this.idInstituicao = idInstituicao;
    }
}