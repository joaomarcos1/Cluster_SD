/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluster;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Criptografia {

    public static void main(String[] argv) {

        try {

            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey chaveDES = keygenerator.generateKey();

            Cipher cifraDES;

            // Cria a cifra 
            cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Inicializa a cifra para o processo de encriptação
            cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES);

            // Texto puro
            byte[] textoPuro = "infomração à ser ".getBytes();

            System.out.println("Texto [Formato de Byte] : " + textoPuro);
            System.out.println("Texto Puro : " + new String(textoPuro));

            // Texto encriptado
            byte[] textoEncriptado = cifraDES.doFinal(textoPuro);

            System.out.println("Texto Encriptado : " + textoEncriptado);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    Socket cliente;
                    try {
                        cliente = new Socket("127.0.0.1", 9011);
                   
                        System.out.println("Enviando encriptado para Head Master..");

                        try (PrintStream saida = new PrintStream(cliente.getOutputStream())) {
                            saida.print(textoEncriptado);
                            System.out.println("Resposta enviada");
                        }
                        cliente.close();
                     } catch (IOException ex) {
                        Logger.getLogger(Criptografia.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            thread.start();

            /* // Inicializa a cifra também para o processo de decriptação
                    cifraDES.init(Cipher.DECRYPT_MODE, chaveDES);

                    // Decriptografa o texto
                    byte[] textoDecriptografado = cifraDES.doFinal(textoEncriptado);

                    System.out.println("Texto Decriptografado : " + new String(textoDecriptografado));
             */
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
        }

    }

}
