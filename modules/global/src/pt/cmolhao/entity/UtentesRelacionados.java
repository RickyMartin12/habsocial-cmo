package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true}")
@Table(name = "utentes_relacionados")
@Entity(name = "cmolhao_UtentesRelacionados")
public class UtentesRelacionados extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 6093174557325999978L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_rel_utentes")
    protected TipoRelacionamentoUtentes idTipoRelUtentes;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente_rel1")
    protected Utente idUtenteRel1;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente_rel2")
    protected Utente idUtenteRel2;


    @Column(name = "membro_agregado")
    protected Integer membroAgregado;
    @Column(name = "obs_conf")
    protected String obsConf;
    @Column(name = "obs_gerais")
    protected String obsGerais;

    public String getObsGerais() {
        return obsGerais;
    }

    public void setObsGerais(String obsGerais) {
        this.obsGerais = obsGerais;
    }

    public String getObsConf() {
        return obsConf;
    }

    public void setObsConf(String obsConf) {
        this.obsConf = obsConf;
    }

    public Integer getMembroAgregado() {
        return membroAgregado;
    }

    public void setMembroAgregado(Integer membroAgregado) {
        this.membroAgregado = membroAgregado;
    }

    public Utente getIdUtenteRel2() {
        return idUtenteRel2;
    }

    public void setIdUtenteRel2(Utente idUtenteRel2) {
        this.idUtenteRel2 = idUtenteRel2;
    }

    public Utente getIdUtenteRel1() {
        return idUtenteRel1;
    }

    public void setIdUtenteRel1(Utente idUtenteRel1) {
        this.idUtenteRel1 = idUtenteRel1;
    }

    public TipoRelacionamentoUtentes getIdTipoRelUtentes() {
        return idTipoRelUtentes;
    }

    public void setIdTipoRelUtentes(TipoRelacionamentoUtentes idTipoRelUtentes) {
        this.idTipoRelUtentes = idTipoRelUtentes;
    }
}