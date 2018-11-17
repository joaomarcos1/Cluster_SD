package cluster;


import java.util.ArrayList;

//estrutura da classe Modelo para ser usada com a class Gson
class Modelo {



    private int codigo;
    private ArrayList<String> palavras;
    private String texto;
    private ArrayList<String> vezesPalavra;

    public Modelo() {
    }

    public Modelo(int codigo, ArrayList<String> palavras, String texto) {
        this.codigo = codigo;
        this.palavras = palavras;
        this.texto = texto;
    }

    
    public Modelo(int codigo, ArrayList<String> palavras, ArrayList<String> vezes){
        
    }
    
    
    public ArrayList<String> getVezesPalavra() {
        return vezesPalavra;
    }

    public void setVezesPalavra(ArrayList<String> vezesPalavra) {
        this.vezesPalavra = vezesPalavra;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public ArrayList<String> getPalavras() {
        return palavras;
    }

    public void setPalavras(ArrayList<String> palavras) {
        this.palavras = palavras;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
