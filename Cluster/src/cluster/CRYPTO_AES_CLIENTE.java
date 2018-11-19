/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluster;

import static cluster.EncriptaDecriptaAES.IV;
import static cluster.EncriptaDecriptaAES.textopuro;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;

/**
 *
 * @author pasid
 */
public class CRYPTO_AES_CLIENTE {

    static String chaveencriptacao = "0123456789abcdef";
    static String IV = "AAAAAAAAAAAAAAAA";
    static String textopuro = "TESTE CRIPTOGRAFIA";

    public static void main(String[] argv) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Socket cliente;
                try {
                    cliente = new Socket("127.0.0.1", 9011);

                    System.out.println("Enviando encriptado para Head Master..");

                    System.out.println("Texto Puro: " + textopuro);

                    byte[] textoencriptado = encrypt(textopuro, chaveencriptacao);

                    System.out.print("Texto Encriptado: " + textoencriptado);

                    StringBuilder aa = new StringBuilder();

                    for (int i = 0; i < textoencriptado.length; i++) {
                        System.out.print(new Integer(textoencriptado[i]) + " ");
                        aa.append(textoencriptado[i]);
                    }

                    System.out.println("");

                    try (PrintStream saida = new PrintStream(cliente.getOutputStream())) {
                        //VARIÁVEL PARA ENVIAR
                        saida.print(aa);
                        System.out.println("Resposta enviada");
                    }
                    cliente.close();
                } catch (IOException ex) {
                    Logger.getLogger(Criptografia.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Erro no envio da MENSAGEM CRIPTOGRADA!");
                } catch (Exception ex) {
                    Logger.getLogger(CRYPTO_AES_CLIENTE.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        thread.start();

    }

    public static byte[] encrypt(String textopuro, String chaveencriptacao) throws Exception {
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return encripta.doFinal(textopuro.getBytes("UTF-8"));
    }

}
