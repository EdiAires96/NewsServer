/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noticias;


public interface Produtor extends java.rmi.Remote{
    
    public void addTopico(String topico, Interface_Cliente cliente) throws java.rmi.RemoteException;
    
    public void seeTopicos(Interface_Cliente cliente) throws java.rmi.RemoteException;
    
    public void addNoticia(String topico, String noticia,String user, Interface_Cliente cliente) throws java.rmi.RemoteException;
    
    public void seeNoticias(Interface_Cliente cliente) throws java.rmi.RemoteException;

    
}
