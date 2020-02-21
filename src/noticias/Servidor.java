/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noticias;

import java.rmi.*;
import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends java.rmi.server.UnicastRemoteObject implements Interface_Servidor {

    private ArrayList<Topico> lista_topicos;
    private ArrayList<Noticia> lista_noticias;
    private ArrayList<Utilizador> utilizadores;
    private static Interface_Cliente client;

    public Servidor() throws RemoteException, FileNotFoundException, IOException, ClassNotFoundException {
        super();
        System.setSecurityManager(new SecurityManager());

        //ler do ficheiro para os respectivos arrays
        File file_topicos = new File("D:\\users\\jcfpascoal\\OneDrive - Universidade da Beira Interior\\sd\\topicos.txt");

        if (file_topicos.exists()) {
            FileInputStream fis = new FileInputStream(file_topicos);
            ObjectInputStream ois = new ObjectInputStream(fis);
            lista_topicos = (ArrayList<Topico>) ois.readObject();

            fis.close();
            ois.close();
        } else {

            new File("D:\\users\\jcfpascoal\\OneDrive - Universidade da Beira Interior\\sd\\topicos.txt").createNewFile();
            lista_topicos = new ArrayList<Topico>();
        }

        File file_noticias = new File("D:\\users\\jcfpascoal\\OneDrive - Universidade da Beira Interior\\sd\\noticias.txt");
        if (file_noticias.exists()) {
            FileInputStream fos = new FileInputStream(file_noticias);
            ObjectInputStream ois = new ObjectInputStream(fos);
            lista_noticias = (ArrayList<Noticia>) ois.readObject();

            fos.close();
            ois.close();
        } else {
            new File("D:\\users\\jcfpascoal\\OneDrive - Universidade da Beira Interior\\sd\\noticias.txt").createNewFile();
            lista_noticias = new ArrayList<Noticia>();
        }

        File file_utilizadores = new File("D:\\users\\jcfpascoal\\OneDrive - Universidade da Beira Interior\\sd\\utilizadores.txt");
        if (file_utilizadores.exists()) {
            FileInputStream fos = new FileInputStream(file_utilizadores);
            ObjectInputStream ois = new ObjectInputStream(fos);
            utilizadores = (ArrayList<Utilizador>) ois.readObject();

            fos.close();
            ois.close();
        } else {
            new File("D:\\users\\jcfpascoal\\OneDrive - Universidade da Beira Interior\\sd\\utilizadores.txt").createNewFile();
            utilizadores = new ArrayList<Utilizador>();
        }

        try {
            Produtor p = new ProdutorImpl(lista_topicos, lista_noticias);
            Consumidor c = new ConsumidorImpl(lista_topicos, lista_noticias);

            Naming.rebind("Produtor", p);
            Naming.rebind("Consumidor", c);
        } catch (RemoteException r) {
            System.out.println("Exception in server" + r.getMessage());
        } catch (java.net.MalformedURLException u) {
            System.out.println("Exception in server - URL");
        }

    }

    public void subscribe(String name, Interface_Cliente cliente) throws java.rmi.RemoteException {

        System.out.println("Subscribe " + name);
        this.client = client;
    }

    public boolean login(String username, String password, Interface_Cliente cliente) throws java.rmi.RemoteException {
        for (Utilizador user : utilizadores) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                if (user.getPassword().equals(password)) {
                    return true;
                } else {
                    try {
                        cliente.printOnClient("Password errada");
                        return false;
                    } catch (RemoteException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

            }
        }
        try {
            cliente.printOnClient("Utilizador não registado.Tem de efectuar o registo");
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean regist(String username, String password, Interface_Cliente cliente) throws java.rmi.RemoteException {

        for (Utilizador user : utilizadores) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                cliente.printOnClient("Utilizador já se encontra registado");
                return false;
            }
        }
        
        synchronized(utilizadores)
        {
            utilizadores.add(new Utilizador(username, password));
            try {
                FileOutputStream file = new FileOutputStream(new File("D:\\users\\jcfpascoal\\OneDrive - Universidade da Beira Interior\\sd\\utilizadores.txt"));
                ObjectOutputStream oos = new ObjectOutputStream(file);
                oos.writeObject(utilizadores);
                oos.close();
                file.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        cliente.printOnClient("Registo efectuado com sucesso");
        return true;
    }

    public static void main(String[] args) {
        try {
            //Iniciar a execução do registry no porto desejado
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
        } catch (Exception e) {
            System.out.println("Exception starting RMI registry:");
            e.printStackTrace();
        }

        try {
            Servidor s = new Servidor();
            Naming.rebind("Server", s);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
