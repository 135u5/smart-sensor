package web.service;
/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe capaz de responder requisicoes HTML do tipo request 
 * atraves da URL: http://localhost:8080/gateway/rest/request/{parametros}.
 *
 * @version 1.0 01 Jan 2012
 * @author  José Renato da Silva Júnior
**/

import java.sql.Connection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db.DataDB;

import listener.ContextListener;

// Caminho da URL a ser respondido
 
@Path("/request/{sensorId}/{sensorType}")
public class Request{
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@PathParam("sensorId") int sensorId,@PathParam("sensorType") String sensorType){
		/** Faz a requisicao para a o sensor com o id = sensorId
		 * */
		ContextListener.listener.sendPacket(sensorId, sensorType );
		/** Cria uma conexao com o banco
		 * */
		Connection conn = DataDB.getConnInstance();		
		/** SQL que sera executado no banco de dados
		 * */
		String sql = "select * from DATA_READ where sensor_id = "+sensorId;
		
		return DataDB.runQuery(conn, sql);
		
	}
}
