/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Helbert Monteiro
 */
public class Node {
    private String porta, ip;
    
    public Node(){}
    
    public Node(String porta, String ip){
        this.porta = porta;
        this.ip    = ip;
    }
    
    public String getPorta(){
        return porta;
    }
    
    public String getIp(){
        return ip;
    }
    
    public void setPorta(String porta){
        this.porta = porta;
    }
    
    public void setIp(String ip){
        this.ip = ip;
    }
}
