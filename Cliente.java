package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    
    public static void main(String[] args) {

        final String IP = "192.168.0.11";
        final int PORT = 12345;
        Socket socket;
        PrintStream output = null;
        PrintStream outputJogo = null;
        String escolha;

        Scanner input = null;
        Scanner teclado = null;
        Scanner tecladoEscolhaJogo = null; 
        Scanner sc = new Scanner(System.in);

        // PEDIDO DE CONEXÃO
        
        try {
            socket = new Socket(IP, PORT); 
            outputJogo = new PrintStream(socket.getOutputStream()); 

            
            System.out.println("_____JOKENPO_____");
            System.out.println("Ola!");
            System.out.println("Bem vindo ao Jogo");
            System.out.println("Qual tipo de jogo quer jogar?");
            System.out.println("Opções:");
            System.out.println("Escolha 1 para jogar contra o computador \nEscolha 2 para jogar contra outro jogado");

            tecladoEscolhaJogo = new Scanner(System.in); 
            escolha = tecladoEscolhaJogo.nextLine(); 

                while(!(escolha.equalsIgnoreCase("1") || escolha.equalsIgnoreCase("2"))){
                    System.out.println("Essa opção está indisponível, digite uma tecla para continuar: ");
                    escolha = tecladoEscolhaJogo.nextLine();
                }

            outputJogo.println(escolha); 


        }catch (Exception e) {
            System.out.println("Não foi possível conectar-se :(");
            System.out.println("possível causa do erro: " + e.getMessage());
            return; //sem conexão a aplicação para
        }

        
        //FASE DE COMUNICAÇÃO
        try {
            
            output = new PrintStream(socket.getOutputStream()); 
            input = new Scanner(socket.getInputStream()); 
            teclado = new Scanner(System.in);

            if (escolha.equalsIgnoreCase("1")){
                System.out.println("Digite seu nome: ");
                String nome = sc.next();

               
                String msg;
                System.out.println("");
                System.out.println("1. Pedra");
                System.out.println("2. Papel");
                System.out.println("3. Tesoura");
                System.out.print("Digite a opçção desejada: ");
                msg = teclado.nextLine(); 

                    while (!((msg.equalsIgnoreCase("1")) || (msg.equalsIgnoreCase("2")) || (msg.equalsIgnoreCase("3")))){
                        System.out.println("Opção invalida, tente novamente: ");
                        msg = teclado.nextLine();
                    }

                output.println(msg); 
                output.println(nome);
                
                System.out.println(input.nextLine()); 

            }else if (escolha.equalsIgnoreCase("2")){
                System.out.println("Jogador 1 digite seu nome: ");
                String nome = sc.next();

                
                String msg;
                System.out.println("Escolha Pedra(1), Papel(2) ou Tesoura(3): ");
                msg = teclado.nextLine(); 

                while (!((msg.equalsIgnoreCase("1")) || (msg.equalsIgnoreCase("2")) || (msg.equalsIgnoreCase("3")))){
                    System.out.println("Opção invalida, tente novamente: ");
                    msg = teclado.nextLine();
                }
                output.println(msg); 
                output.println(nome);

                System.out.println("Jogador 2 digite seu nome: ");
                String nome2 = sc.next();

                
                String msg2;
                System.out.println("Escolha Pedra(1), Papel(2) ou Tesoura(3): ");
                msg2 = teclado.nextLine(); //ao invés de receber mensagem mandada pelo input eu crio um Scanner para ler oq foi digitado

                while (!((msg2.equalsIgnoreCase("1")) || (msg2.equalsIgnoreCase("2")) || (msg2.equalsIgnoreCase("3")))){
                    System.out.println("Opção invalida, tente novamente: ");
                    msg2 = teclado.nextLine();
                }

                output.println(msg2); //system.out.println -> para exibir na tela do servidor
                output.println(nome2);


                //while (!msg.equalsIgnoreCase("exit")); //para caso a mensagem seja === exit(desconsiderando maiusculas ou minusculas) ele finaliza a aplicação
                System.out.println(input.nextLine()); //mensagem de VENCER, PERDER ou EMPATAR
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //FASE DE ENCERRAMENTO DA CONEXÃO
        try {
            output.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

    


        
