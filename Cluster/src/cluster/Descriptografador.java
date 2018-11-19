/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluster;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Descriptografador {

    public static void main(String[] argv) throws InvalidKeyException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

        KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
        SecretKey chaveDES = keygenerator.generateKey();

        Cipher cifraDES;

        // Cria a cifra 
        cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding");

        // Inicializa a cifra para o processo de encriptação
        //cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES);
        ServerSocket servidor3 = new ServerSocket(9011);

        System.out.println("Esperando cliente se conectar ao servidor pela porta 9011");
        while (true) {
            
            //cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding");

             cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES);

            
            Socket cliente1 = servidor3.accept();
            System.out.println("Cliente " + cliente1.getInetAddress().getHostAddress() + " CONECTADO");

            Scanner entrada = new Scanner(cliente1.getInputStream());
            System.out.println("recebendo dados do cliente");

            String texto = entrada.nextLine();

            //byte[] textoCriptografado = texto.getBytes();
            //System.out.println("Recebido: " + textoCriptografado);
            System.out.println("Recebido: " + texto);

            
            
            // Texto encriptado
            byte[] textoEncriptado = cifraDES.doFinal(texto.getBytes());

            //System.out.println("Texto Encriptado : " + textoEncriptado);

            // Inicializa a cifra também para o processo de decriptação
            cifraDES.init(Cipher.DECRYPT_MODE, chaveDES);

            // Decriptografa o texto
            byte[] textoDecriptografado = cifraDES.doFinal(textoEncriptado);

            System.out.println("Texto Decriptografado : " + new String(textoDecriptografado));
            System.out.println("------------");
        }
    }

}
