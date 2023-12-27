package edu.it.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import edu.it.dtos.ResultadoError;
import edu.it.dtos.ResultadoOK;
import edu.it.components.ConectorJPA;
import edu.it.components.Utiles;
import edu.it.model.Alumno;
import edu.it.model.Cliente;

public class ClienteController extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	//Inserto la nueva funcion de Utiles
    	Utiles.manejarRespuesta(request, response, (() -> {
		Cliente a = Utiles.deserializarInputStream(request, Cliente.class);
    		
    		// Lectura del contenido entrante
			//String s = Utiles.leerInputStreamReader(request.getInputStream()); esta lectura ahora se realiza en Utiles.Deserailizar...
			// Fin lectura contenido entrante
		//System.out.println(a.id + " " + a.nombre);
		Utiles.persistirObjeto(a);
    		return a;
    	}));
	
	
	
	
	
	
	
	
	
	
	
	
	}

}
