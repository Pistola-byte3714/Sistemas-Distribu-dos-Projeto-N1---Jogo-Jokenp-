package sever;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import threads.ThreadComput;
import threads.ThreadJogadorXJogador;

public class Server {
    static ArrayList<ThreadComput> threads = new ArrayList<>();
    static ArrayList<ThreadJogadorXJogador> threadJogadores = new ArrayList<>();
    public static void main(String[] args) {
        //VARIAVEIS
        final int PORT = 12345;
        ServerSocket serverSocket;
        Socket clientSocket = null;

        //CRIACAO DO SOCKET
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (Exception e) {
            System.out.println("A porta " + PORT + " já está em uso: " + e.getMessage());
            return;
        }

        //AGUARDANDO PEDIDO DE CONEXÃO DO SERVIDOR
        try {
            while (true) {
                System.out.println("Sistema aguardando pedido de conexão para iniciar o jogo...");
                clientSocket = serverSocket.accept();
                System.out.println("O jogo se conectou com: " + clientSocket.getInetAddress().getHostAddress());

                //Para direcionar para a thread certa de acordo com a escolha de jogo
                Scanner input = new Scanner(clientSocket.getInputStream());
                String msg;
                msg = input.nextLine();

                if(msg.equalsIgnoreCase("1")) {
                    ThreadComput threadCpu = new ThreadComput(clientSocket, threads);
                    threads.add(threadCpu); //adicionando a lista de Cliente vs CPU
                    threadCpu.start(); //thread não entra em execução se não for iniciada, por isso o Start
                }else if(msg.equals("2")){
                    ThreadJogadorXJogador threadJogadorVsJogador = new ThreadJogadorXJogador(clientSocket, threadJogadores);
                    threadJogadores.add(threadJogadorVsJogador); //adicionando para a lista da Thread de Jogador vs Jogador
                    threadJogadorVsJogador.start();
                } else {
                    //tem uma validação no CLIENTE que não permite colocar opções diferentes de 1 ou 2, mas essa se trata de uma segunda validação por segurança, parando o servidor
                    System.out.println("Erro, a opção desejada é inválida");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //FASE DE ENCERRAMENTO DA CONEXÃO
        try {
            serverSocket.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
    


