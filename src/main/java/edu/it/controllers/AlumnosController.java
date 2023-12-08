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

public class AlumnosController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    	response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println("<h1>Hora Actual: ");
            out.println(System.currentTimeMillis());
            out.println("</h1>");
            response.setStatus(200);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	//Inserto la nueva funcion de Utiles
    	Utiles.manejarRespuesta(request, response, (() -> {
		Alumno a = Utiles.deserializarInputStream(request, Alumno.class);
    		
    		// Lectura del contenido entrante
			//String s = Utiles.leerInputStreamReader(request.getInputStream()); esta lectura ahora se realiza en Utiles.Deserailizar...
			// Fin lectura contenido entrante
		//System.out.println(a.id + " " + a.nombre);
		Utiles.persistirObjeto(a);
    		return a;
    	}));
    	
    		//	response.setContentType("application/json"); -migrado a utiles
    			//PrintWriter out = response.getWriter();

    			
    			
    			
    			
    			/*
    			Gson gson = new Gson();
    			Alumno a = null;
    			
    			try {
    			 a = gson.fromJson(s, Alumno.class);
    			}catch (Exception ex) {
        			out.println(gson.toJson(new ResultadoError("No se pudo grabar")));
        			response.setStatus(400);//bad request
    			}
    			System.out.println(a.id);
    			System.out.println(a.nombre);
    			System.out.println(a.apellido);
    			System.out.println(a.pais);
    			
    		try {	
    			var conn = new ConectorJPA();
    		    var entityManager =	conn.getEntityManager();
    		    var tx = entityManager.getTransaction();
    		    tx.begin();
    		    entityManager.merge(a);
    		    tx.commit();
    		}catch (Exception ex){
    			out.println(gson.toJson(new ResultadoError("No se pudo grabar")));
    			response.setStatus(500);//Internal server error
    			return ;
    			
    			
    		}
    		    
    		    
    		    
    			
    		out.println(gson.toJson(new ResultadoOK("Alumno agregado en forma exitosa...")));
                
                response.setStatus(201);
       */
        }
}
