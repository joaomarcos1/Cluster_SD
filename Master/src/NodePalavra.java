
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
public class NodePalavra {
    
    int               node;
    ArrayList<String> listaPalavras = new ArrayList<>();
    
    public NodePalavra(int node){
        this.node = node;
    }
    
    public void addPalavra(String palavra){
        listaPalavras.add(palavra);
    }
    
    public int getNode(){
        return node;
    }
    
    public ArrayList<String> getListaPalavras(){
        return listaPalavras;
    }
    
}
