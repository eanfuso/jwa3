package edu.it.model;

import javax.persistence.*;


@Entity
@Table(name="usuarios")
public class Usupass {
	@Id
	public String id;
	public String nombre;
	public String salt;
	@Column(name="password_encriptada")
	public String password;
	
	
	public Usupass() {
	
		
	}
	
	public Usupass(String id, String nombre, String password, String salt) {
		
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.salt=salt;
	}
	



	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}





	public String getNombre() {
		return nombre;
	}


	public void setNombre(String usuario) {
		this.nombre = usuario;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

}
