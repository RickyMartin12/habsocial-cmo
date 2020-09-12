package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@DesignSupport("{'dbView':true,'imported':true}")
@Table(name = "documentacao_nucleo_executivo_atas")
@Entity(name = "cmolhao_DocumentacaoNucleoExecutivoAtas")
public class DocumentacaoNucleoExecutivoAtas extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -2910217503855180990L;
    @Temporal(TemporalType.DATE)
    @Column(name = "data_documento")
    protected Date dataDocumento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    protected FileDescriptor file;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rede_trabalho")
    protected RedeTrabalho idRedeTrabalho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sub_rede_trabalho")
    protected SubRedeTrabalho idSubRedeTrabalho;
    @Lob
    @Column(name = "nome_ficheiro")
    protected String nomeFicheiro;
    @Lob
    @Column(name = "numero_documentacao")
    protected String numeroDocumentacao;

    public String getNumeroDocumentacao() {
        return numeroDocumentacao;
    }

    public void setNumeroDocumentacao(String numeroDocumentacao) {
        this.numeroDocumentacao = numeroDocumentacao;
    }

    public String getNomeFicheiro() {
        return nomeFicheiro;
    }

    public void setNomeFicheiro(String nomeFicheiro) {
        this.nomeFicheiro = nomeFicheiro;
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

    public FileDescriptor getFile() {
        return file;
    }

    public void setFile(FileDescriptor file) {
        this.file = file;
    }

    public Date getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }
}