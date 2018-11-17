/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluster;

import java.util.StringTokenizer;

/**
 *
 * @author pasid
 */
public class NewClass {

    public static void main(String[] args) {

        String a = "a,casa,rato/O rato roeu a roupa do rei de roma";

        StringTokenizer st = new StringTokenizer(a);

        String a1 = st.nextToken("/");

        String a2 = st.nextToken("/");

 
        
 
        String[] palavras = a1.split(",");

        String[] texto = a2.split(" ");
        
           System.out.println(a1); 
        System.out.println(a2);
        for (int i = 0; i < palavras.length; i++){
            System.out.println(palavras[i]);
        }
        
        System.out.println("Separadas por uma barra");
        for (int i = 0; i < texto.length; i++){
            System.out.println(texto[i]);
        }
        
        
      

    }
}
