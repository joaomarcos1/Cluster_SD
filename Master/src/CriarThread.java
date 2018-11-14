/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Helbert Monteiro
 */
public class CriarThread {
    
    private ServerSocket socketServidor;
    private Socket       dispositivoCliente;
    
    private Scanner scanner;
    private String  mensagem;
    private int     divisor, index1, index2;
    
    
    public void run(int porta, ArrayList<Node> listaNodes){
        try {
            socketServidor = new ServerSocket(porta);
            System.out.println("Ouvindo na porta " + porta + "...");

            while(true) {
                dispositivoCliente = socketServidor.accept();
                System.out.println("\n\nCliente conectado: " + dispositivoCliente.getInetAddress().getHostAddress());

                scanner = new Scanner(dispositivoCliente.getInputStream());
                mensagem = scanner.nextLine();
                
                divisor = mensagem.length() / listaNodes.size();
                index1  = 0;
                index2  = divisor;
                
                System.out.println("Mensagem Inteira: " + mensagem);
                
                for(int i = 0; i < listaNodes.size(); i++){
                    new Transmissor().enviar(mensagem.substring(index1, index2), listaNodes.get(i).getIp(), Integer.parseInt(listaNodes.get(i).getPorta()));
                    index1 += divisor;
                    index2 += divisor;
                }

                dispositivoCliente.close();
            }
        }catch(IOException a) {
            System.out.println("Erro na Thread: " + a.getMessage());
        }
    }

    void run(String porta, String ip) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}