package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true,'unmappedColumns':['image_id']}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idfotosvalencia"))
})
@NamePattern("%s|idvalencia")
@Table(name = "fotos_valencia")
@Entity(name = "cmolhao_FotosValencia")
public class FotosValencia extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -6826908053605248548L;
    @Column(name = "daequipacolaboradores")
    protected Boolean daequipacolaboradores;
    @Column(name = "descricao", length = 1000)
    protected String descricao;
    @Column(name = "doequipamento")
    protected Boolean doequipamento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idvalencia")
    protected Valencias idvalencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_ID")
    protected FileDescriptor image;


    public FileDescriptor getImage() {
        return image;
    }

    public void setImage(FileDescriptor image) {
        this.image = image;
    }

    public Valencias getIdvalencia() {
        return idvalencia;
    }

    public void setIdvalencia(Valencias idvalencia) {
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