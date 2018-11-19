/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluster;

import static cluster.EncriptaDecriptaAES.IV;
import static cluster.EncriptaDecriptaAES.decrypt;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;

/**
 *
 * @author pasid
 */
public class CRYPTO_AES_SERVER {

    static String chaveencriptacao = "0123456789abcdef";

    public static void main(String[] argv) throws IOException, Exception {

        ServerSocket servidor3 = new ServerSocket(9011);

        System.out.println("Esperando cliente se conectar ao servidor pela porta 9011");
        while (true) {
            Socket cliente1 = servidor3.accept();
            System.out.println("Cliente " + cliente1.getInetAddress().getHostAddress() + " CONECTADO");

            Scanner entrada = new Scanner(cliente1.getInputStream());
            System.out.println("recebendo dados do cliente");

            String texto = entrada.nextLine();

            byte[] recebido = texto.getBytes();

            for (int i = 0; i < recebido.length; i++){
                System.out.println(new Integer (recebido[i]));
            }
            
            System.out.println("Recebido: " + texto);

            String textodecriptado = decrypt(recebido, chaveencriptacao);

            System.out.println("Texto Decriptado: " + textodecriptado);

        }

    }

    public static String decrypt(byte[] textoencriptado, String chaveencriptacao) throws Exception {
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        decripta.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(decripta.doFinal(textoencriptado), "UTF-8");
    }

}
