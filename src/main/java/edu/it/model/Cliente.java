package edu.it.model;

import javax.persistence.*;

@Entity
@Table(name="clientes")

public class Cliente {
	@Id
	public String id;
	public String nombre;
	
	
	
	
	public Cliente() {
		super();
	}



	public Cliente(String id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}












}
