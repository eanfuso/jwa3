package edu.it.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.it.components.Utiles;
import edu.it.dtos.ResultadoOK;
import edu.it.model.Alumno;

public class InventarAlumnoController extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException {
	            response.setContentType("application/json");
	            PrintWriter out = response.getWriter();
	            
	            var alumnos = new ArrayList<Alumno>();
	            
	            for (Integer x : new Integer[5]) {
	            	alumnos.add(Utiles.generarAlumnoRandom());
	            }
	            
	            Gson gson = new Gson();
	            
	  //   var resultado = new ResultadoOK(alumnos); //encapsulo alumnos en resultadoOK
	            //ese lo convierto a json y, al conformarse así el mensaje del server, con
	          //el status OK en este caso
	            
	            var resultado = new ResultadoOK(Utiles.generarAlumnoRandom()); 
	            String alumnoJson = gson.toJson(resultado);
	            out.println(alumnoJson);
	            
	            response.setStatus(200);
	    }
}
