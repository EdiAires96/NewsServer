/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noticias;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Asus
 */
public class Noticia implements Serializable
{
	private String topico;
	private String produtor;
	private String noticia;
	private Date data;

	public Noticia(String topico, String produtor, String noticia, Date data) {
		this.topico = topico;
		this.produtor = produtor;
		this.noticia = noticia;
		this.data = data;
	}

	public String getTopico() {
		return topico;
	}

	public void setTopico(String topico) {
		this.topico = topico;
	}

	public String getProdutor() {
		return produtor;
	}

	public void setProdutor(String produtor) {
		this.produtor = produtor;
	}

	public String getNoticia() {
		return noticia;
	}

	public void setNoticia(String noticia) {
		this.noticia = noticia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "noticia{" + "topico=" + topico + ", produtor=" + produtor + ", noticia=" + noticia + ", data=" + data + '}';
	}
	
	
}
