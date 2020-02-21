/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noticias;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author jferr
 */
public class ProdutorImpl extends java.rmi.server.UnicastRemoteObject implements Produtor{
    
    private ArrayList<Topico> topicos;
    private ArrayList<Noticia> noticias;

    public ProdutorImpl(ArrayList<Topico> topicos, ArrayList<Noticia> noticias) throws RemoteException {
        super();
        this.topicos = topicos;
        this.noticias = noticias;
    }
    
    public void addTopico(String topico,Interface_Cliente cliente) throws java.rmi.RemoteException{
        
        boolean exist = false;
        
        //Percorrer o array para ver se existe um topico com esse nome
        for(Topico tp : topicos){
            if(tp.getTopico().equalsIgnoreCase(topico)){
                exist = true;
            }
        }
        
        //Se nao existir adiciona topico ao array
        if(!exist){
            Topico tp = new Topico(topico);	
            
            synchronized (topicos) {
                topicos.add(tp);
                try {
                    FileOutputStream file = new FileOutputStream(new File("D:\\users\\jcfpascoal\\OneDrive - Universidade da Beira Interior\\sd\\topicos.txt"));
                    ObjectOutputStream oos = new ObjectOutputStream(file);
                    oos.writeObject(topicos);
                    oos.close();
                    file.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            cliente.printOnClient("Tópico Registado");
        }
        else{
            cliente.printOnClient("Já existe esse tópico...");
        }
    }
    
    public void seeTopicos(Interface_Cliente cliente) throws java.rmi.RemoteException{
        
        for(Topico tp : topicos){
            cliente.printOnClient(tp.getTopico()+", ");
        }
    }
    
    public void addNoticia(String topico, String noticia,String user,Interface_Cliente cliente) throws java.rmi.RemoteException{
        
        boolean exist = false;

        //Percorrer o array para ver se existe um topico com esse nome
        for (Topico tp : topicos) {
            if (tp.getTopico().equalsIgnoreCase(topico)) {
                exist = true;
            }
        }

        //Se nao existir alerta o consumidor
        if (!exist) {
            cliente.printOnClient("Não existe esse tópico...");
            return;
        }
        
        if(noticia.length()>180){
            cliente.printOnClient("Notícia demasiado grande. Limite de 180 carateres.");
            return;
        }
        
        Noticia n = new Noticia(topico,user,noticia,new Date());
        
        synchronized (noticias) {
            noticias.add(n);
            try {
                FileOutputStream file = new FileOutputStream(new File("D:\\users\\jcfpascoal\\OneDrive - Universidade da Beira Interior\\sd\\noticias.txt"));
                ObjectOutputStream oos = new ObjectOutputStream(file);
                oos.writeObject(noticias);
                oos.close();
                file.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        cliente.printOnClient("Notícia publicada");
    }
    
    public void seeNoticias(Interface_Cliente cliente) throws java.rmi.RemoteException {
        
        for(Noticia n : noticias)
            cliente.printOnClient(n.toString());
        
    }
    
}
