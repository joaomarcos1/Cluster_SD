
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
public class Modelo {
    
    private int               codigo;
    private ArrayList<String> palavras;
    private String            texto;
    private ArrayList<String> vezesPalavra = new ArrayList<>();
    private int               portaCliente;
    
    public Modelo(){}
    
    public Modelo(int codigo, ArrayList<String> palavras, String texto){
        this.codigo   = codigo;
        this.palavras = palavras;
        this.texto    = texto;
    }
    
    public Modelo(int codigo, ArrayList<String> palavras, ArrayList<String> vezesPalavra){
        this.codigo       = codigo;
        this.palavras     = palavras;
        this.vezesPalavra = vezesPalavra;
    }
    
    public Modelo(ArrayList<String> palavras, ArrayList<String> vezesPalavras){
        this.palavras     = palavras;
        this.vezesPalavra = vezesPalavras;
    }
    
    public int getCodigo(){
        return codigo;
    }
    
    public ArrayList<String> getPalavras(){
        return palavras;
    }
    
    public String getTexto(){
        return texto;
    }
    
    public ArrayList<String> getVezesPalavras(){
        return vezesPalavra;
    }
    
    public void iniciaVezes(String vez){
        vezesPalavra.add(vez);
    }
    
    public String getValorVez(int i){
        return vezesPalavra.get(i);
    }
    
    public void acrescentaValorVez(int i, String valor){
        int soma = Integer.parseInt(vezesPalavra.get(i)) + Integer.parseInt(valor);
        vezesPalavra.set(i, String.valueOf(soma));
    }
    
    public int getPortaCliente(){
        return portaCliente;
    }
    
}
