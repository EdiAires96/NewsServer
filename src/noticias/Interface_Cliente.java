/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noticias;


public interface Interface_Cliente   extends java.rmi.Remote 
{
	public void printOnClient (String s)throws java.rmi.RemoteException;
}
