package edu.it.components;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.javafaker.Faker;

import edu.it.interfaces.InversionDeControl;
import edu.it.model.Alumno;

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
	}
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
public static void manejarRespuesta (
		HttpServletRequest req,
		HttpServletResponse res,
		InversionDeControl ioc) {
	
	
	
}
}
