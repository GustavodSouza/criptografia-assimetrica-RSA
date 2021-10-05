package br.ufsm.poli.csi.cripto;

import javax.crypto.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class Bob {

    public static final String ALGORITHM = "RSA";

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //Armazeno a chave privada de BOB
        PrivateKey chavePrivada;

        ServerSocket ss = new ServerSocket(5555);
        System.out.println("[BOB] Aguardando conexão na porta 5555.");
        Socket s = ss.accept(); //Aceita a conexão.
        System.out.println("[BOB] Conexão recebida.");

        ObjectInputStream oin = new ObjectInputStream(s.getInputStream()); //Recebe o conteudo.
        ObjetoTroca objetoTroca = (ObjetoTroca) oin.readObject(); //Le o conteudo.

        System.out.println(objetoTroca.getMensagem());

        try {
            //Gera a chave publica
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(1024);
            final KeyPair key = keyGen.generateKeyPair();

            //Crio a chave privada para BOB
            chavePrivada = key.getPrivate();

            //Envia a chave para Alice
            ObjetoTroca chavePublica = new ObjetoTroca();
            chavePublica.setPublicKey(key.getPublic());
            ObjectOutputStream oout = new ObjectOutputStream(s.getOutputStream());
            oout.writeObject(chavePublica);

            ObjectInputStream oinn = new ObjectInputStream(s.getInputStream()); //Recebe o conteudo.
            ObjetoTroca objetoRecebido = (ObjetoTroca) oinn.readObject(); //Le o conteudo.

            Cipher cipherRSA = Cipher.getInstance(ALGORITHM);
            cipherRSA.init(Cipher.DECRYPT_MODE, chavePrivada);

            byte[] textoPlano = cipherRSA.doFinal(objetoRecebido.getArquivoCriptografado());

            System.out.println("[BOB] Texto plano decifrado: " + new String(textoPlano));

        } catch (Exception e) {
            System.out.println("Ops algo deu errado!!!");
        }
    }
}
