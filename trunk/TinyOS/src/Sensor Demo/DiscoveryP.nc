/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Aplicação do componente Discovery.
 * 
 *
 * @version 1.0 17 Dec 2011
 * @author  Renato Cesar Gil Azevedo
 * @e-mail renatoazevedo@labnet.nce.ufrj.br
**/

module DiscoveryP {
	provides interface Stop_Discovery;
	provides interface Start_Discovery;
	uses interface Envia;
	uses interface Timer<TMilli> as Hello;
	uses interface Leds;
	
}
implementation {
	/* Ação do timer Hello que serve para anunciar o sensor a base */
	event void Hello.fired() {
		http_msg_t httpmsg;
		// Converte char para inteiro
		char met='P'; 
		uint16_t cmet = (uint16_t) met;
		// Utiliza o método Put já que incrementa dados a base.
		httpmsg.metodo = cmet; // Put
		// Avisa o tipo de sensoriamento existente no sensor.
		httpmsg.path = 1; // Demo
		httpmsg.dados = 0;
		// ID da base.
		httpmsg.dhost = 1; 
		// Código definido para aviso de Serviço de Descoberta.
		httpmsg.codigo = 2000;
		call Envia.send( httpmsg );		
		
	}

	command void Stop_Discovery.receive () {
		
		call Hello.stop();
		
	}
	
	command void Start_Discovery.receive () {
		call Hello.startPeriodic(1000);
		
	}
	
	event void Envia.startDone (error_t e) {}
	event void Envia.stopDone (error_t e) {}

	
	
}