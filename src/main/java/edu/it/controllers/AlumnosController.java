package edu.it.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

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
    			response.setContentType("application/json");
                
    			// Lectura del contenido entrante
    			String s = Utiles.leerInputStreamReader(request.getInputStream());
    			// Fin lectura contenido entrante
    			
    			Gson gson = new Gson();
    			Alumno a = gson.fromJson(s, Alumno.class);
    			
    			System.out.println(a.id);
    			System.out.println(a.nombre);
    			System.out.println(a.apellido);
    			System.out.println(a.pais);
    			
    			var conn = new ConectorJPA();
    		    var entityManager =	conn.getEntityManager();
    		    var tx = entityManager.getTransaction();
    		    tx.begin();
    		    entityManager.merge(a);
    		    tx.commit();
    			
    			PrintWriter out = response.getWriter();
                out.println("Alumno agregado de forma exitosa... ");
                
                response.setStatus(201);
        }
}
