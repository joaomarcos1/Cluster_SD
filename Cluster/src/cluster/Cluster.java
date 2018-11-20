/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluster;

import com.google.gson.Gson;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author nupasd-ufpi
 */
public class Cluster extends JFrame {

    JLabel valor1 = new JLabel("Texto Recebido");
    JLabel valor2 = new JLabel("valor 2");

    JButton iniciar = new JButton("Iniciar");
    JLabel mostrarsoma = new JLabel("Resultado do Processamento:");
    JTextField tfsoma = new JTextField();
    JTextField tfvalor1 = new JTextField();
    JTextField tfvalor2 = new JTextField();

    Gson gson = new Gson();

    ArrayList<String> palavras = new ArrayList<>();
    ArrayList<String> palavrasJSON = new ArrayList<>();
    ArrayList<String> vezesPalavraJSON = new ArrayList<>();
    String texto = null;
    int codigo;

    JTextArea texto1 = new JTextArea();
    JScrollPane barraRolagem;

    JTextArea palavras_adicionadas = new JTextArea();

    public Cluster() {
        JPanel tela = new JPanel();
        tela.setLayout(null);
        tela.setBackground(Color.white);

        tela.add(valor1);
        valor1.setBounds(20, 80, 350, 40);

        tela.add(tfvalor1);
        tfvalor1.setBounds(130, 80, 240, 40);

        tela.add(mostrarsoma);
        mostrarsoma.setBounds(20, 180, 350, 40);

        tela.add(palavras_adicionadas);
        palavras_adicionadas.setBounds(20, 220, 430, 80);
        palavras_adicionadas.setBorder(new LineBorder(Color.GRAY));
        palavras_adicionadas.setLineWrap(true);
        palavras_adicionadas.setWrapStyleWord(true);
        palavras_adicionadas.setEditable(false);
        barraRolagem = new JScrollPane(palavras_adicionadas);
        barraRolagem.setBounds(20, 220, 430, 80);
        tela.add(barraRolagem);

        //tela.add(tfsoma);
        //tfsoma.setBounds(20, 220, 430, 80);
        tela.add(iniciar);

        iniciar.setBounds(20, 20, 400, 40);
        iniciar.addActionListener(
                new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Thread tsensor3 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            ServerSocket servidor3 = new ServerSocket(9010);

                            System.out.println("Esperando cliente se conectar ao servidor pela porta 9010");
                            while (true) {
                                Socket cliente1 = servidor3.accept();
                                System.out.println("Cliente " + cliente1.getInetAddress().getHostAddress() + " CONECTADO");

                                Scanner entrada = new Scanner(cliente1.getInputStream());
                                
                                System.out.println("recebendo dados do cliente");

                                String json = entrada.nextLine();

                                Modelo modelo2 = new Modelo();

                                //RECEBENDO A STRING JSON
                                modelo2 = gson.fromJson(json, Modelo.class);

                                texto = modelo2.getTexto();

                                palavras = modelo2.getPalavras();

                                tfvalor1.setText(texto);

                                //CHAMANDO FUNÇÃO CONTAGEM PALAVRAS ABAIXO
                                String[] textoDividido = texto.split(" ");
                                StringBuilder enviar = new StringBuilder();
                                double inicial = System.currentTimeMillis();
                                for (int i = 0; i < textoDividido.length; i++) {
                                    //tfsoma.setText("Palavra " + textoDividido[i] + ": " + contaPalavras(textoDividido[i], texto));
                                    palavrasJSON.add(textoDividido[i]);
                                    //enviar.append("Palavra ").append(textoDividido[i]).append(": ").append(contaPalavras(textoDividido[i], texto));
                                    vezesPalavraJSON.add((Integer.toString(contaPalavras(textoDividido[i], texto))));
                                }

                                double fim = System.currentTimeMillis();

                                //palavras_adicionadas.setText(enviar.toString());
                                String aa = enviar.toString();
                                palavras_adicionadas.insert(aa, palavras_adicionadas.getCaretPosition());
                                palavras_adicionadas.append("\n");

                                double tempoProcessamento = fim - inicial;
                                System.out.println("Tempo de Realização da Tarefa: " + tempoProcessamento);

                                //MONTANDO STRING JSON ABAIXO
                                codigo = 2;

                                Modelo modelo1 = new Modelo(codigo, palavrasJSON, vezesPalavraJSON);
                                json = "";
                                //transformando o modelo em Json
                                json = gson.toJson(modelo1);

                                //GERANDO GRÁFICOS NA TELA
                                //MANDANDO PARA O IP QUE RECEBER A MENSAGEM
                                Socket cliente = new Socket(cliente1.getInetAddress().getHostAddress(), 9000);
                                //int retorno = contaPalavras(texto);

                                //IMPRIMINDO CONTAGEM DE PALAVRAS
                                //System.out.println(retorno);
                                System.out.println("Enviando resposta para Head Master..");

                                PrintStream saida = new PrintStream(cliente.getOutputStream());

                                //ABAIXO ENVIAR PALAVRAS CONTADAS
                                //A VARIÁVEL enviar TEM A CONTAGEM DE TODAS AS PALAVRAS DA STRING LIDA
                                //ENVIANDO VARIÁVEL JSON COMO RESPOSTA
                                saida.print(json);
                                System.out.println("Resposta enviada");
                                saida.close();
                                cliente.close();

                            }

                        } catch (Exception a) {
                            System.out.println("\n\n Erro no servidor " + a.getMessage());

                        }

                    }
                });
                tsensor3.start();
            }
        }
        );

        add(tela);
        setVisible(true);
        setSize(480, 350);
        setLocation(440, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static int contaPalavras(String palavra, String Texto) {
        int quant = 0;
        String texto = Texto;
        String[] arrayString = texto.split(" ");
        for (int i = 0; i < arrayString.length; i++) {
            if (arrayString[i].equals(palavra)) {
                quant++;
            }
        }
        return quant;
    }

    public static void main(String[] args) {

        new Cluster();

    }

}
