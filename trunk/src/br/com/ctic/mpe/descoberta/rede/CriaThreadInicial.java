/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe que chama a Thread de Verificação de tempo do serviço de descoberta
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.descoberta.rede;

import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class CriaThreadInicial extends HttpServlet {

	static {
		Thread thread = new Thread(new VerificaTempoInativo(30000));
		thread.start();

	}

}
