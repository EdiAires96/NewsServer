/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noticias;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.rmi.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends java.rmi.server.UnicastRemoteObject implements Interface_Cliente		
{
	private static Produtor produtor;
	private static Consumidor consumidor;
        private static Cliente cliente; 
        private static Interface_Servidor s;
	
	public Cliente() throws RemoteException
	{
		super();
	}
	
	public static String lerString()
	{
		String s =" ";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in), 1);
			s = in.readLine();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
		return s;
	}
	
	//metodo remoto
	public void printOnClient(String s) throws java.rmi.RemoteException
	{
		System.out.println("Message from server: "+s);
	}
	

	public static void Menu() throws RemoteException, ParseException
	{
		int sair=0;
		while (sair==0)
		{
			System.out.println("***********************************");
			System.out.println("1-Login");
			System.out.println("2-Registo");
			System.out.println("3-Convidado");
			System.out.println("0-Sair");
			System.out.println("--- >");
			
			String c = lerString();
			int op= Integer.parseInt(c);
			
			switch(op)
			{
				case 1:
				{
					MenuLogin();
					break;
				}
				case 2:
				{
					MenuRegisto();
					break;
				}
				case 3:
				{
					MenuConvidado();
				}
				case 0:
				{
					sair=1;
					break;
				}
			}
		}
	}
	
	public static void MenuLogin() throws RemoteException, ParseException
	{
                boolean isloged = false; 
                String user="";
                String password;
                while(!isloged){ 
                    
                    System.out.println("***********************************");
                    //ler credenciais
                    System.out.println("Username: ");
                    user=lerString();
                    System.out.println("Password: ");
                    password=lerString();
                    isloged = s.login(user, password, cliente);
                    
                }
                
		
		//procurar ao ficheiro de utilizadores o nome e confirmar palavra passe
		//verificaçao e entrada para outro menu
		
		MenuPC(user);
		
		
	}
	public static void MenuRegisto()
	{
                boolean isregist=false;
                String username;
                String password;
                
                while(!isregist){
                    System.out.println("***********************************");
                    //registo do utilizador e guardar credenciais num ficheiro
                    System.out.println("Username:");
                    username=lerString();
                    System.out.println("Password: ");
                    password = lerString();
                    try {
                        isregist=s.regist(username, password, cliente);
                    } catch (RemoteException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
		// verificar se ficheiro de Utilizadores exite , se sim passar os registos para um ArrayList<Utilizador> se nao criar um array e guardar.
		
		
		
	}
	
	public static void MenuConvidado() throws ParseException, RemoteException
	{
		int sair=0;
		while (sair==0)
		{
			System.out.println("***********************************");
			System.out.println("2-Consultar noticias");
			System.out.println("3-Consultar ultima noticia");
			System.out.println("0-Sair");
			
			int op;
			String n = lerString();
			op = Integer.parseInt(n);

			switch (op) 
			{
				case 2:
					System.out.println("Topico:");
					String topico = lerString();
					System.out.println("Data inicial: dd/MM/yyyy");
					String inicial = lerString();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date begin = sdf.parse(inicial);

					System.out.println("Data final : dd/MM/yyyy");
					String fim = lerString();
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
					Date end = sdf1.parse(fim);
				
					
					consumidor.seeNoticiasOfTopicoBetweenDate(topico, begin, end,(Interface_Cliente)cliente);
					break;

				case 3:
					System.out.println("Topico:");
					String top= lerString();
					
					consumidor.seeLastNoticiaOfTopico(top,(Interface_Cliente)cliente);
				case 0:
					sair=1;
					break;
			}

		}
	}
	
	public static void MenuPC(String user) throws RemoteException, ParseException
	{
		int sair=0;
		while (sair==0)
		{
			System.out.println("***********************************");
			System.out.println("1-Produtor");
			System.out.println("2-Consumidor");
			System.out.println("0-Sair");
			System.out.println("--- >");
			
			String c = lerString();
			int op= Integer.parseInt(c);
			switch(op)
			{
				case 1:
				{
					MenuProdutor(user);
					break;
				}
				
				case 2:
				{
					MenuConsumidor(user);
					break;
				}
				case 0:
				{
					sair=1;
					break;
				}
			}

		}
	}
	public static void MenuProdutor(String user) throws RemoteException
	{
		int sair=0;
		while (sair==0) 
		{
			System.out.println("************************************************");
			System.out.println("1-Adicionar um tópico");
			System.out.println("2-Consultar tópicos existentes");
			System.out.println("3-Inserir uma noticia");
			System.out.println("4-Consultar todas as noticias publicadas");
			System.out.println("0-Sair");
			
			int op;
			String n = lerString();
			op = Integer.parseInt(n);

			switch (op)
			{
				case 1:
				{
					System.out.println("Qual o topico que deseja adicionar:");
					String topico = lerString();
					
					produtor.addTopico(topico,(Interface_Cliente)cliente);
					break;
				}
				case 2:
					produtor.seeTopicos((Interface_Cliente)cliente);
					break;
				case 3:
				{
					System.out.println("Qual o topico a escrever");
					String top = lerString();
					System.out.println("Insira a noticia");
					String noticia = lerString();
					
					produtor.addNoticia(top,noticia,user,(Interface_Cliente)cliente);
					break;
				}
				case 4:
					produtor.seeNoticias((Interface_Cliente)cliente);
					break;
				case 0:
					sair=1;
					break;

			}

		}
	}
	
	public static void MenuConsumidor(String user) throws ParseException, RemoteException
	{
		int sair=0;
		while (sair==0)
		{
			System.out.println("***********************************");
			System.out.println("1-Subscrever um tópico");
			System.out.println("2-Consultar noticias");
			System.out.println("3-Consultar ultima noticia");
			System.out.println("0-Sair");
			
			int op;
			String n = lerString();
			op = Integer.parseInt(n);

			switch (op) 
			{
				case 1:
					System.out.println("Qual o topico que dejesa subscrever?");
					 String s=lerString();
					 
					consumidor.subscribeTopico(s,user,(Interface_Cliente)cliente);
					break;
				case 2:
					System.out.println("Topico:");
					String topico = lerString();
					System.out.println("Data inicial: dd/MM/yyyy");
					String inicial = lerString();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date begin = sdf.parse(inicial);

					System.out.println("Data final : dd/MM/yyyy");
					String fim = lerString();
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
					Date end = sdf1.parse(fim);
					
					consumidor.seeNoticiasOfTopicoBetweenDate(topico, begin, end,(Interface_Cliente)cliente);
					
					break;

				case 3:
					System.out.println("Topico:");
					String top= lerString();
					
					consumidor.seeLastNoticiaOfTopico(top,(Interface_Cliente)cliente);
	
				case 0:
					sair=1;
					break;
			}

		}
	}
	

	public static void main(String[] args)
	{
		System.setSecurityManager(new SecurityManager());
		try
		{
			produtor = (Produtor)Naming.lookup("Produtor");
			consumidor = (Consumidor)Naming.lookup("Consumidor");
			cliente = new Cliente();
			s =(Interface_Servidor)Naming.lookup("Server");
                        s.subscribe("Manel", (Interface_Cliente)cliente);
			Menu();
			exit(0);
			
		}
		catch (Exception r) {
			System.out.println("Exception in client" + r.getMessage());
		}

	}
}
