/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noticias;


public interface Interface_Servidor  extends java.rmi.Remote
{
	//public void printOnServer(String s) throws java.rmi.RemoteException;
    public void subscribe (String s, Interface_Cliente cliente) throws java.rmi.RemoteException;
    public boolean login(String username, String password, Interface_Cliente cliente) throws java.rmi.RemoteException;
    public boolean regist(String username, String password, Interface_Cliente cliente) throws java.rmi.RemoteException;
        
}
