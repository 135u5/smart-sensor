package web.service;
/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe capaz de responder requisicoes HTML do tipo getdata
 * atraves da URL: http://localhost:8080/gateway/rest/getdata/{parametros}.
 *
 * @version 1.0 01 Jan 2012
 * @author  José Renato da Silva Júnior
**/

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import communication.Manager;

// Caminho da URL a ser respondido
@Path("/getdata/{sensorType}") 
public class Data {
	
	/** Responde com todos os dados sensoriados pelos sensores do tipo sensorType
	 * */
	@GET
	@Produces(MediaType.TEXT_PLAIN)	
	public String response(@PathParam("sensorType") String sensorType){
		/** Cria uma comunicacao com o gerente
		 * */
		Manager manager = new Manager();		
		/** Retorna a resposta do banco para o usuario
		 * */				
		return manager.NewQuery(sensorType);
		
	}
}
