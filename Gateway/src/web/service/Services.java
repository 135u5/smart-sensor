package web.service;
/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe capaz de responder requisicoes HTML do tipo getservices 
 * atraves da URL: http://localhost:8080/gateway/rest/getservices/.
 *
 * @version 1.0 01 Jan 2012
 * @author  José Renato da Silva Júnior
**/

import java.sql.Connection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db.DataDB;

// Caminho da URL a ser respondido
 
@Path("/getservices")
public class Services {
	
	/** Responde com todos os servicos oferecidos pela RSSF
	 * */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String response(){
		/** Cria uma conexao com o banco
		 * */
		
		Connection conn = DataDB.getConnInstance();
		/** SQL que sera executado no banco de dados
		 * */
		String sql = "select DISTINCT * from SERVICES";// ORDER BY AREA";
		
		return DataDB.runQuery(conn, sql);
		
	}
}
