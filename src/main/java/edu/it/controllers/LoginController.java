package edu.it.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import edu.it.components.Utiles;
import edu.it.model.Alumno;
import edu.it.model.Usupass;

public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setStatus(200);
		Utiles.manejarRespuesta(req, resp, () -> {
			return Utiles.leerTodosLosRegistros();
		});
	}
	//Encriptar pass: se usan algoritmos de hash sha1, sha256, sha1024. El hash es unidireccional. longitud fija. el salt adiciona 2 UUID-> infinitos inpup dan el mismo output.  

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		Utiles.manejarRespuesta(request, response, () -> {
			//Usupass a = new Usupass(UUID.randomUUID().toString(),"Carlitos", "passDeCarlitos");
			//System.out.println("En serie es :--->>> "+a.usuario.toString());
			Usupass a = Utiles.deserializarInputStream(request, Usupass.class);
						//System.out.println("Persistiendo:--->>> "+a.id+a.usuario+a.password);
			a.setId(UUID.randomUUID().toString());
			a.setSalt("FALTA TEORIA");
			a.setSalt(String.join("__", UUID.randomUUID().toString(), UUID.randomUUID().toString()));
			String passAEncriptar = String.join("__",a.salt, a.password);
			a.password=DigestUtils.sha256Hex(passAEncriptar);//corregir: crear una envoltura que se llame passwor, que el el valorretornado por post. El atributo de la entity va a ser passwordEncriptado
			System.out.println("Persistiendo:--->>> "+a.id+a.nombre+a.password);
						
			Utiles.persistirObjeto(a);
			return a;
		}, 201);

	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("Estoy en el put del loguin");

	}
	
	public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Utiles.manejarRespuesta(req, resp, () -> {
			var id = req.getPathInfo().replace("/", "");//porque el primer caracter traido por pathInfo es la barra divisoria de la uri
			//Usupass a = Utiles.deserializarInputStream(req, Usupass.class);
			Utiles.borrarObjetoGenerico(id, Usupass.class);
			return "Se borro: " + id;
		});
		
		
	} 
	
	
}
