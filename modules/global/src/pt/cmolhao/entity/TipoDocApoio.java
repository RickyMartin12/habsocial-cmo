package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;


@DesignSupport("{'imported':true}")
@Table(name = "tipo_doc_apoio")
@Entity(name = "cmolhao_TipoDocApoio")
@NamePattern("%s|descricao")
public class TipoDocApoio extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 2204118539788597770L;
    @Lob
    @Column(name = "descricao")
    protected String descricao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_apoio")
    protected TipoAjuda idTipoApoio;

    public TipoAjuda getIdTipoApoio() {
        return idTipoApoio;
    }

    public void setIdTipoApoio(TipoAjuda idTipoApoio) {
        this.idTipoApoio = idTipoApoio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}