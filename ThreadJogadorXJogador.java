package threads;

import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadJogadorXJogador extends Thread {

    private Socket cliente; 
    private Scanner input = null;
    private PrintStream output = null;
    private ArrayList<ThreadJogadorXJogador> threads;

    //CONSTRUTOR 
    public ThreadJogadorXJogador(Socket cliente, ArrayList<ThreadJogadorXJogador> threads) {
        this.cliente = cliente;
        this.threads = threads; 
    }

    @Override
    public void run() {
        //FASE DE COMUNICAÇÃO
        try {

            input = new Scanner(cliente.getInputStream()); 

            output = new PrintStream(cliente.getOutputStream()); 

            String msg1;
            String nome1;
            String msg2;
            String nome2;

                msg1 = input.nextLine(); 
                nome1 = input.nextLine();

                msg2 = input.nextLine();
                nome2 = input.nextLine();

                if(msg1.equalsIgnoreCase("1") && msg2.equalsIgnoreCase("2") || msg1.equalsIgnoreCase("2") && msg2.equalsIgnoreCase("3") || msg1.equalsIgnoreCase("3") && msg2.equalsIgnoreCase("1")) {
                    //CPU vence
                    output.println("Jogador " + nome2 + " venceu!");
                }else if(msg2.equalsIgnoreCase("1") && msg1.equalsIgnoreCase("2") || msg2.equalsIgnoreCase("2") && msg1.equalsIgnoreCase("3") || msg2.equalsIgnoreCase("3") && msg1.equalsIgnoreCase("1")) {
                    //Usuário VENCEU da CPU
                    output.println("Jogador " + nome1 + " venceu!");
                }else {
                    output.println("Empatou");
                }



        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //FASE DE ENCERRAMENTO DA CONEXÃO
        try {
            input.close();
            cliente.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

}

