/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noticias;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;


public class ConsumidorImpl extends java.rmi.server.UnicastRemoteObject implements Consumidor {
    
    private ArrayList<Topico> topicos;
    private ArrayList<Noticia> noticias;

    public ConsumidorImpl(ArrayList<Topico> topicos, ArrayList<Noticia> noticias) throws RemoteException {
        super();
        this.topicos = topicos;
        this.noticias = noticias;
    }
    
    public void subscribeTopico(String topico,String user,Interface_Cliente cliente) throws RemoteException{
        
        boolean exist=false; 
        
        //Percorrer o array para ver se existe um topico com esse nome
        for(Topico tp : topicos){
            if(tp.getTopico().equalsIgnoreCase(topico)){
                exist = true;
                tp.setSuscribers(user);
            }
        }
        
        //Se nao existir alerta o consumidor
        if(!exist){
            cliente.printOnClient("O tópico não existe...");
            return;
        }
        
        cliente.printOnClient("Tópico subscrito");
        
    }
    
    public boolean compDate(Date date, Date begin, Date end){
        if(begin.before(date) && end.after(date)){
            return true;
        }
        return false;
    }
    
    public void seeNoticiasOfTopicoBetweenDate(String topico, Date begin, Date end,Interface_Cliente cliente) throws RemoteException{
        
        for(Noticia n : noticias){
            if(n.getTopico().equalsIgnoreCase(topico)){
                
                boolean verify=compDate(n.getData(),begin,end);
                
                if(verify){
                    cliente.printOnClient(n.toString());
                    return;
                }
                
            }
        }
        
        cliente.printOnClient("Não foram encontradas noticias");
        
    }
    
    public void seeLastNoticiaOfTopico(String topico,Interface_Cliente cliente)throws RemoteException{
        
        for (int i = noticias.size()-1; i <= 0; i--) {
            if(noticias.get(i).getTopico().equalsIgnoreCase(topico)){
                cliente.printOnClient(noticias.get(i).toString());
                return;
            }
        }
        
        cliente.printOnClient("Não existe noticias relacionadas com esse tópico");
    }
    
    
}
