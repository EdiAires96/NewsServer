/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noticias;

import java.util.Date;


public interface Consumidor extends java.rmi.Remote{

    public void subscribeTopico(String topico,String user, Interface_Cliente cliente) throws java.rmi.RemoteException;
    
    public void seeNoticiasOfTopicoBetweenDate(String topico, Date begin, Date end, Interface_Cliente cliente) throws java.rmi.RemoteException;
    
    public void seeLastNoticiaOfTopico(String topico, Interface_Cliente cliente) throws java.rmi.RemoteException;
    
}
