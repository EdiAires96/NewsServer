/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noticias;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class Topico implements Serializable
{
	private String topico;
	private ArrayList<String> suscribers;

    public Topico(String topico) {
        this.topico = topico;
        suscribers=new ArrayList<String>();
    }

    public String getTopico() {
        return topico;
    }

    public ArrayList<String> getSuscribers() {
        return suscribers;
    }    

    public void setTopico(String topico) {
        this.topico = topico;
    }

    public void setSuscribers(String user) {
        this.suscribers.add(user);
    }

    @Override
    public String toString() {
        return "Topico{" + "topico=" + topico + ", suscribers=" + suscribers + '}';
    }   
        
}
