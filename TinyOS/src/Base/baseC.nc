/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Arquivo da aplicação do Radio da Base.
 * 
 *
 * @version 1.0 17 Dec 2011
 * @author  Renato Cesar Gil Azevedo
 * @e-mail renatoazevedo@labnet.nce.ufrj.br
**/

#include "Timer.h"
#include "http.h"


module baseC @safe(){
	uses {
		interface Leds;
		interface Boot;
		interface SplitControl as RadioControl;
		interface SplitControl as Control;
		interface Receive as SerialReceive;
		interface AMSend as SerialSend;
		interface Receive as RadioReceive;
		interface AMSend as RadioSend;
		interface Packet as RadioPacket;
		interface Packet as SerialPacket;

	}
}
implementation {
	message_t *spacket;
	message_t *bpacket;
	
	event void Boot.booted() {
		call RadioControl.start();
		call Control.start();
	}

	event void RadioControl.startDone(error_t err) {}
	
	event void RadioControl.stopDone(error_t err) {}
	
	event void Control.startDone(error_t err) {}
	
	event void Control.stopDone(error_t err) {}
 
	
	event message_t* RadioReceive.receive(message_t* bufPtr,	void* payload, uint8_t len) {
	
		if (bpacket != NULL) { return bufPtr; }
		else {
			if (len != sizeof(http_msg_t)) {return bufPtr;}
			else {
				http_msg_t* httpmsg = (http_msg_t*)payload;
				// Caso o método seja igual a 2000 ele envia uma mensagem de volta para que o sensor saiba que foi reconhecido.
				call Leds.set(httpmsg->metodo);
				if(httpmsg->codigo == 2000)	{ 
					if (call RadioSend.send(httpmsg->shost, bufPtr, sizeof(http_msg_t)) == SUCCESS) {
						spacket = bufPtr;
	
					} 
				}			
				// Também é enviada a mensagem para aplicação java para que possa ser armazenado no banco de dados. 
				if (call SerialSend.send(AM_BROADCAST_ADDR, bufPtr, sizeof(http_msg_t)) == SUCCESS) {
					bpacket = bufPtr;
				}
				else { return bufPtr; }
			}
		}
	}
	
	event message_t* SerialReceive.receive(message_t* bufPtr, void* payload, uint8_t len) {
		//call Leds.led2Toggle();
		if (spacket != NULL) { return bufPtr; } 
		else {
			if (len != sizeof(http_msg_t)) {return bufPtr;}
			else {
				http_msg_t* httpmsg = (http_msg_t*)payload;
	

				httpmsg->shost=TOS_NODE_ID;
	
				if (call RadioSend.send(httpmsg->dhost, bufPtr, sizeof(http_msg_t)) == SUCCESS) {
					spacket = bufPtr;
	
				}
				else { return bufPtr; }
			}
		}
	}

	event void SerialSend.sendDone(message_t* bufPtr, error_t error) {
		if (bpacket == bufPtr) {
			bpacket = NULL;
	
		}
	}
	event void RadioSend.sendDone(message_t* bufPtr, error_t error) {
		if (spacket == bufPtr) {
			//call Leds.led1Toggle();
			spacket = NULL;

		}
	}
	
}
