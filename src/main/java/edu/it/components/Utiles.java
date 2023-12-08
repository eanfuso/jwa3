package edu.it.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.UUID;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.javafaker.Faker;

import edu.it.dtos.ResultadoError;
import edu.it.dtos.ResultadoOK;
import edu.it.errores.BadRequestException;
import edu.it.errores.HttpException;
import edu.it.interfaces.InversionDeControl;
import edu.it.model.Alumno;
import edu.it.model.Cliente;

public class Utiles {
	public static Alumno generarAlumnoRandom() {
		Faker fkr = new Faker();
		
		return new Alumno(
				UUID.randomUUID().toString(),
	            fkr.address().firstName(),
	            fkr.address().lastName(),
	            fkr.address().streetName(),
	            fkr.address().streetAddressNumber(),
	            fkr.address().state(),
	            fkr.address().country()
	        );
	}  //generar cliente random, en caso de ser necesario->
//		public static Cliente generarClienteRandom() {
//			Faker fkr = new Faker();
//			return new Cliente(
//					UUID.randomUUID().toString(),
//					fkr.address().firstName()					
//					);
//		}
//	
	
	private static String leerInputStreamReaderManejado(InputStream inputStream) throws Exception {
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		for (String s = br.readLine(); s != null; s = br.readLine()) {
			sb.append(s);
		}
		return sb.toString();
	}
	public static String leerInputStreamReader(InputStream inputStream) {
		try {
			return leerInputStreamReaderManejado(inputStream);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	public static <T> T deserializarInputStream(HttpServletRequest req, Class<T> clazz) {
		String serializado ="";
		try {
			serializado = leerInputStreamReader(req.getInputStream());
			return new Gson().fromJson(serializado, clazz);
			
		} catch (IOException ex) {
			throw new BadRequestException("No se pudo interpretar Json");
		}
		
				
	}
	
	
	public static void persistirObjeto(Object obj) {
		var conn = new ConectorJPA();
	    var entityManager =	conn.getEntityManager();
	    var tx = entityManager.getTransaction();
	    tx.begin();
	    entityManager.merge(obj);
	    tx.commit();
		
		
	}
	
	
	public static void manejarRespuesta (
			HttpServletRequest req,
			HttpServletResponse res,
			InversionDeControl ioc) throws IOException {
		res.setContentType("application/json");
		PrintWriter out = null;
		
		try {
			out = res.getWriter();
		ioc.controlar(); //controlar es el metodo abstracto de la clase. Va a ejecutar el método pasado como argumento
		//System.out.println("La clase traida: "+ ioc.getClass());
		out.println(new Gson().toJson(new ResultadoOK("Dato persistido...")));
		res.setStatus(201);
	}catch (HttpException ex) {
		res.setStatus(ex.status);
		out.println(new Gson().toJson(new ResultadoError("Error en el servidor1")));
	}
		catch (Exception ex) {
			res.setStatus(500);
			out.println(new Gson().toJson(new ResultadoError("Error en el servidor2")));
		}
	}
	
}
