package br.ufsm.poli.csi.cripto;

import javax.crypto.*;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class Alice {

    public static final String ALGORITHM = "RSA";

    public static void main(String[] args) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, ClassNotFoundException {
        //Armazena a chave publica recebida de BOB
        PublicKey chavePublica;

        Socket s = new Socket("localhost", 5555); //cria socket para conexão.
        ObjetoTroca objetoTroca = new ObjetoTroca();
        objetoTroca.setMensagem("Solicitando chave publica de BOB");
        ObjectOutputStream oout = new ObjectOutputStream(s.getOutputStream());
        oout.writeObject(objetoTroca);

        ObjectInputStream oin = new ObjectInputStream(s.getInputStream()); //Recebe o conteudo.
        ObjetoTroca objetoRecebido = (ObjetoTroca) oin.readObject(); //Le o conteudo.
        chavePublica = objetoRecebido.getPublicKey();

        JFileChooser fc = new JFileChooser(""); //Dialogo para escolher arquivo.
        System.out.println("Selecionando arquivo");
        if (fc.showDialog(new JFrame(), "OK") == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile(); //Pega o arquivo selecionado do tipo File.
            FileInputStream fin = new FileInputStream(f); //Abre o arquivo e pega o conteudo.
            byte[] bArray = new byte[(int) fin.getChannel().size()]; //Pega o tamanho do arquivo.
            fin.read(bArray); //Le o arquivo e coloca os dados no bytearray
            System.out.println("Arquivo selecionado");

            Cipher cipherRSA = Cipher.getInstance(ALGORITHM);
            cipherRSA.init(Cipher.ENCRYPT_MODE, chavePublica); //Criptografia RSA.
            byte[] textoCifrado = cipherRSA.doFinal(bArray);

            ObjetoTroca objetoCriptografado = new ObjetoTroca();
            objetoCriptografado.setArquivoCriptografado(textoCifrado);
            objetoCriptografado.setNomeArquivo(f.getName());
            ObjectOutputStream objetoSaida = new ObjectOutputStream(s.getOutputStream());
            objetoSaida.writeObject(objetoCriptografado);
            s.close();
        }
    }
}
