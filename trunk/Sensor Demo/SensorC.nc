/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Aplicação do sensor exemplo.
 * 
 *
 * @version 1.0 17 Dec 2011
 * @author  Renato Cesar Gil Azevedo
 * @e-mail renatoazevedo@labnet.nce.ufrj.br
**/

#include "Timer.h"
#include "http.h"

module SensorC @safe() {
	uses {
		interface Leds;
		interface Boot;
		interface Get;
		interface Put;
		interface Envia;
		interface Read<uint16_t>;
	}
	
}
implementation {
int x;
	event void Boot.booted() {
		call Envia.start();
	
	}
	
	
	event void Read.readDone(error_t result, uint16_t data) {
	x=data;		
	}
	
	event void Envia.startDone (error_t e) {}
	event void Envia.stopDone (error_t e) {}
	
	event void Get.receive(http_msg_t rhttpmsg) {
		call Leds.led2Toggle();
		//Utilização do Get
		call Read.read();
		rhttpmsg.metodo = 'G'; 
		rhttpmsg.path = 1;
		rhttpmsg.dados = x; 
		rhttpmsg.dhost = rhttpmsg.shost; 
		rhttpmsg.codigo = 200;
		call Envia.send( rhttpmsg );
	}
	event void Put.receive(http_msg_t rhttpmsg) {
		call Leds.led0Toggle();
		//Utilização do Put
	} 
	
}
