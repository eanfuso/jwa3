package edu.it.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
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
import edu.it.errores.NotFoundException;
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
			
		} catch (Exception ex) {//Cuando tenía IOException, no arrojaba el error correcto, ante un mal formateo del json, por ejemplo
			throw new BadRequestException("No se pudo interpretar Json");
		}
		
				
	}
	public static Object leerTodosLosRegistros() {//método en desarrollo
		var conn = new ConectorJPA();
	    var entityManager =	conn.getEntityManager();
	   // var tx = entityManager.getTransaction();
	    //tx.begin();//esta linea y la de arriba se sacan porque->"no hace falta transaccion para las lecturas..."
	    var qr = entityManager.createQuery("SELECT a FROM Alumno a"); //Alias a asociado a la tabla alumnos. El lenguaje es jpql, no sql
	    return qr.getResultList();//arriba en el query se pone el nombre de la clase, Alumno, no la tabla "Alumnos"
	   
	}
	
	
	public static void persistirObjeto(Object obj) {
		var conn = new ConectorJPA();
	    var entityManager =	conn.getEntityManager();
	    var tx = entityManager.getTransaction();
	    tx.begin();
	    entityManager.merge(obj);
	    tx.commit();
	}

	
	
	public static  void borrarObjeto(String id) {
	var conn = new ConectorJPA();
    var entityManager =	conn.getEntityManager();
    var tx = entityManager.getTransaction();
    tx.begin();
    var alumno = entityManager.find(Alumno.class, id);
    if (alumno == null) {
    	throw new NotFoundException("alumno no encontrado");
    }
    entityManager.remove(alumno);
    tx.commit();
	}
	
	
	
	public static void manejarRespuesta (
			HttpServletRequest req,
			HttpServletResponse res,
			InversionDeControl ioc) {
		
		manejarRespuesta(req, res, ioc, 200);
		
	}
	
	public static void manejarRespuesta (
			HttpServletRequest req,
			HttpServletResponse res,
			InversionDeControl ioc,
			Integer httpStatus) {
		res.setContentType("application/json");
		PrintWriter out = null;
		
		try {
			out = res.getWriter();
		var z = ioc.controlar(); //controlar es el metodo abstracto de la clase. Va a ejecutar el método pasado como argumento
		//System.out.println("La clase traida: "+ ioc.getClass());
		out.println(new Gson().toJson(new ResultadoOK(z)));
		res.setStatus(httpStatus);
	}catch (HttpException ex) {
		res.setStatus(ex.status);
	
		out.println(new Gson().toJson(new ResultadoError(ex.mensaje)));
	}
		catch (Exception ex) {
			res.setStatus(500);
			out.println(new Gson().toJson(new ResultadoError("Error en el servidor2")));
		}
	}
	public static Map<String, String> obtenerMapa(String key, String value){
		var a = new HashMap<String, String>();
		a.put(key, value);
		return a;
	}
	
	public static void validarPathInfoNotNull(String pathInfo) {
		if (pathInfo == null) {
			throw new BadRequestException("Se esperaba un parametro");
		}
	}
	public static void validarPathInfo(String uuid) {
		uuid = uuid.replace("/", "");
		
		if (uuid.equals("")) {
			throw new BadRequestException("se requiere el id del objeto");
		}
	}
	public static void validarId(String id) {
		
		id = id.replace("/", "");
		System.out.println(id);
		if (!id.matches("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}")) {
			throw new BadRequestException("El id enviado no tiene el formato correcto");
			
		}
		
		
		
	}
	
}
