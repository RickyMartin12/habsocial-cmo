package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntIdentityIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true}")
@NamePattern("%s - %s|idAtendimento,idAtendimentoObjectivos")
@Table(name = "objetivos_atendimento")
@Entity(name = "cmolhao_ObjetivosAtendimento")
public class ObjetivosAtendimento extends BaseIntIdentityIdEntity {
    private static final long serialVersionUID = 4055467735666617323L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atendimento")
    protected Atendimento idAtendimento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atendimento_objectivos")
    protected AtendimentoObjetivos idAtendimentoObjectivos;

    public AtendimentoObjetivos getIdAtendimentoObjectivos() {
        return idAtendimentoObjectivos;
    }

    public void setIdAtendimentoObjectivos(AtendimentoObjetivos idAtendimentoObjectivos) {
        this.idAtendimentoObjectivos = idAtendimentoObjectivos;
    }

    public Atendimento getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(Atendimento idAtendimento) {
        this.idAtendimento = idAtendimento;
    }
}