package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.UUID;

@DesignSupport("{'dbView':true,'imported':true}")
@Table(name = "documentos_apoio_financiamento_regulamentos")
@Entity(name = "cmolhao_DocumentosApoioFinanciamentoRegulamentos")
public class DocumentosApoioFinanciamentoRegulamentos extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 583710457075381119L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    protected FileDescriptor file;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apoio")
    protected Apoios idApoio;
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

    public Apoios getIdApoio() {
        return idApoio;
    }

    public void setIdApoio(Apoios idApoio) {
        this.idApoio = idApoio;
    }

    public FileDescriptor getFile() {
        return file;
    }

    public void setFile(FileDescriptor file) {
        this.file = file;
    }
}