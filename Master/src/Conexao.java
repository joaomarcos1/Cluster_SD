
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Helbert Monteiro
 */
public class Conexao {
    
    private final Thread thread;
    
    public Conexao(ArrayList<Node> listaNodes){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Master ON\n");
                new Gerencia().run(9000, listaNodes);
            }
        });
        
        thread.start();
    }
    
}