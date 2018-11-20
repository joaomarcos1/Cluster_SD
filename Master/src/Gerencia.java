/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.gson.Gson;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Helbert Monteiro
 */
public class Gerencia {
    
    private ServerSocket socketServidor;
    private Socket       dispositivoCliente;
    
    private Scanner  scanner;
    private String   json, subTexto;
    private int      index, codigoCliente, codigoNode;
    private String[] palavras;
    
    private Gson                   gson;
    private Modelo                 modelo, modeloRetorno;
    private NodePalavra            nodePalavra;
    private ArrayList<NodePalavra> listaNodePalavra;
    private Node                   cliente;
    
    public void run(int porta, ArrayList<Node> listaNodes){
        codigoCliente = 0;
        codigoNode    = 0;
        modeloRetorno = new Modelo();
        try {
            socketServidor = new ServerSocket(porta);
            System.out.println("Ouvindo na porta " + porta + "...");

            while(true){
                dispositivoCliente = socketServidor.accept();
                
                System.out.println("\n\nCliente conectado: " + dispositivoCliente.getInetAddress().getHostAddress());

                scanner = new Scanner(dispositivoCliente.getInputStream());
                json    = scanner.nextLine();
                
                gson   = new Gson();
                modelo = new Modelo();
                modelo = gson.fromJson(json, Modelo.class);
                
                if(codigoCliente == 0){
                    cliente = new Node(String.valueOf(modelo.getPortaCliente()), dispositivoCliente.getInetAddress().getHostAddress());
                    for(int i = 0; i < modelo.getPalavras().size(); i++){
                        modeloRetorno.iniciaVezes("0");
                    }
                    codigoCliente = 1;
                }
                
                switch(modelo.getCodigo()){
                    case 1:
                        distribuir(modelo, listaNodes);
                        break;
                    case 2:
                        System.out.println("Fazendo Redução ...");
                        reduzir(modelo, cliente, listaNodes, codigoNode);
                        break;
                }
                
                 dispositivoCliente.close();
            }
            
        }catch(IOException a) {
            System.out.println("Erro na Thread: " + a.getMessage());
        }
    }
    
    private void distribuir(Modelo modelo, ArrayList<Node> listaNodes){
        palavras         = modelo.getTexto().split(" ");
        listaNodePalavra = new ArrayList<>();
        index            = 0;
        
        for(int i = 0; i < listaNodes.size(); i++){
            nodePalavra = new NodePalavra(i);
            listaNodePalavra.add(nodePalavra);
        }
        
        for(int i = 0; i < palavras.length; i++){
            if(index < listaNodes.size()){
                listaNodePalavra.get(index).addPalavra(palavras[i]);
                index++;
            }else{
                index = 0;
                listaNodePalavra.get(index).addPalavra(palavras[i]);
                index++;
            }
        }
        
        System.out.println("Texto Inteiro: " + modelo.getTexto());
        subTexto = "";
        
        for(int i = 0; i < listaNodePalavra.size(); i++){
            for(int j = 0; j < listaNodePalavra.get(i).getListaPalavras().size(); j++){
                subTexto = subTexto + listaNodePalavra.get(i).getListaPalavras().get(j) + " ";
            }
            System.out.println("Palavras separadas para Node " + i + ": " + subTexto);
            
            modelo = new Modelo(modelo.getCodigo(), modelo.getPalavras(), subTexto);
            
            new Transmissor().enviar(modelo, listaNodes.get(i).getIp(), Integer.parseInt(listaNodes.get(i).getPorta()));
            
            subTexto = "";
        }
    }
    
    private void reduzir(Modelo modelo, Node cliente, ArrayList<Node> listaNodes, int codigoNode){
        for(int j = 0; j < modeloRetorno.getVezesPalavras().size(); j++){
            modeloRetorno.acrescentaValorVez(j, modelo.getValorVez(j));
        }
        codigoNode++;
        if(codigoNode == listaNodes.size()){
            modelo = new Modelo(modelo.getPalavras(), modeloRetorno.getVezesPalavras());
            new Transmissor().enviar(modelo, cliente.getIp(), Integer.parseInt(cliente.getPorta()));
            codigoCliente = 0;
            codigoNode    = 0;
        }
    }

}