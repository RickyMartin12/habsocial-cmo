package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.UUID;

@DesignSupport("{'dbView':true,'imported':true}")
@Table(name = "documentacao_hab_social_programa_apoio_arrendamento_2_fase")
@Entity(name = "cmolhao_DocumentacaoHabSocialProgramaApoioArrendamento2Fase")
public class DocumentacaoHabSocialProgramaApoioArrendamento2Fase extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 6106697940154791847L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    protected FileDescriptor file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_habitacao_social")
    protected HabitacaoSocial idHabitacaoSocial;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rede_trabalho")
    protected RedeTrabalho idRedeTrabalho;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sub_rede_trabalho")
    protected SubRedeTrabalho idSubRedeTrabalho;
    @Lob
    @Column(name = "num_documento")
    protected String numDocumento;

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public SubRedeTrabalho getIdSubRedeTrabalho() {
        return idSubRedeTrabalho;
    }

    public void setIdSubRedeTrabalho(SubRedeTrabalho idSubRedeTrabalho) {
        this.idSubRedeTrabalho = idSubRedeTrabalho;
    }

    public RedeTrabalho getIdRedeTrabalho() {
        return idRedeTrabalho;
    }

    public void setIdRedeTrabalho(RedeTrabalho idRedeTrabalho) {
        this.idRedeTrabalho = idRedeTrabalho;
    }

    public HabitacaoSocial getIdHabitacaoSocial() {
        return idHabitacaoSocial;
    }

    public void setIdHabitacaoSocial(HabitacaoSocial idHabitacaoSocial) {
        this.idHabitacaoSocial = idHabitacaoSocial;
    }

    public FileDescriptor getFile() {
        return file;
    }

    public void setFile(FileDescriptor file) {
        this.file = file;
    }
}