package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.UUID;

@DesignSupport("{'dbView':true,'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idfotosvalencia"))
})
@Table(name = "fotos_valencias_nucleo_executivo")
@Entity(name = "cmolhao_FotosValenciasNucleoExecutivo")
public class FotosValenciasNucleoExecutivo extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 9050028073851096288L;
    @Column(name = "daequipacolaboradores")
    protected Boolean daequipacolaboradores;
    @Column(name = "descricao", length = 1000)
    protected String descricao;
    @Column(name = "doequipamento")
    protected Boolean doequipamento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvalencia")
    protected ValenciaNucleoExecutivo idvalencia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_ID")
    protected FileDescriptor image;

    public FileDescriptor getImage() {
        return image;
    }

    public void setImage(FileDescriptor image) {
        this.image = image;
    }

    public ValenciaNucleoExecutivo getIdvalencia() {
        return idvalencia;
    }

    public void setIdvalencia(ValenciaNucleoExecutivo idvalencia) {
        this.idvalencia = idvalencia;
    }

    public Boolean getDoequipamento() {
        return doequipamento;
    }

    public void setDoequipamento(Boolean doequipamento) {
        this.doequipamento = doequipamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getDaequipacolaboradores() {
        return daequipacolaboradores;
    }

    public void setDaequipacolaboradores(Boolean daequipacolaboradores) {
        this.daequipacolaboradores = daequipacolaboradores;
    }
}