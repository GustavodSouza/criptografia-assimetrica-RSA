package br.ufsm.poli.csi.cripto;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.security.PublicKey;

public class ObjetoTroca implements Serializable {

    private String nomeArquivo;
    private byte[] arquivoCriptografado;
    private SecretKey key;
    private PublicKey publicKey;
    private String mensagem;
    private String mensagemBruno;

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public byte[] getArquivoCriptografado() {
        return arquivoCriptografado;
    }

    public void setArquivoCriptografado(byte[] arquivoCriptografado) {
        this.arquivoCriptografado = arquivoCriptografado;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagemBruno() {
        return mensagemBruno;
    }

    public void setMensagemBruno(String mensagemBruno) {
        this.mensagemBruno = mensagemBruno;
    }
}
