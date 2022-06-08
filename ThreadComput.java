package threads;

import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ThreadComput extends Thread {
    private Socket cliente; //serve para armazenar o retorno do accept() -> pedido de conexão
    private Scanner input = null;
    private PrintStream output = null;
    private ArrayList<ThreadComput> threads;

 
    public ThreadComput(Socket cliente, ArrayList<ThreadComput> threads) {
        this.cliente = cliente;
        this.threads = threads; //para ter o controle certo das Threads se conectando na lista
    }

    @Override
    public void run() {
        //FASE DE COMUNICAÇÃO
        try {
            
            input = new Scanner(cliente.getInputStream()); //para ler as mensagens que virão dentro desse canal de comunicação

            output = new PrintStream(cliente.getOutputStream()); //para escrever no canal de comunicação do cliente

            String msg;

                msg = input.nextLine(); //uso scanner para ler um texto e guardo dentro dessa variavel

                Random random = new Random();
                int numero = (random.nextInt(3)) + 1; //+1 pq se inicia em zero
                String CPU = String.valueOf(numero); 

                if(msg.equalsIgnoreCase("1") && CPU.equalsIgnoreCase("2") || msg.equalsIgnoreCase("2") && CPU.equalsIgnoreCase("3") || msg.equalsIgnoreCase("3") && CPU.equalsIgnoreCase("1")) {
                    //CPU vence
                    output.println("Computador escolheu: " + CPU + ". O computador venceu ");
                }else if(CPU.equalsIgnoreCase("1") && msg.equalsIgnoreCase("2") || CPU.equalsIgnoreCase("2") && msg.equalsIgnoreCase("3") || CPU.equalsIgnoreCase("3") && msg.equalsIgnoreCase("1")) {
                    //Usuário VENCEU da CPU
                    output.println("Computador escolheu: " + CPU + ". Parabéns você venceu!");
                }else {
                    output.println("Computador escolheu: " + CPU + ". Empatou");
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
        //quando o construtor criar a thread, ele ira passar como param um Socket
    }
}

    

