/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * 
 * 
 *
 * @version 1.0 17 Dec 2011
 * @author  Renato Cesar Gil Azevedo
 * @e-mail renatoazevedo@labnet.nce.ufrj.br
**/

module HttpP {
	provides interface Envia;
	provides interface Get;
	provides interface Put;
	uses interface Start_Discovery;
	uses interface Stop_Discovery;
	uses interface Receive as AMReceive;
	uses interface AMSend as AMSender;
	uses interface Packet;
	uses interface SplitControl as Control;
	uses interface Leds;
	//uses interface Timer<TMilli> as Timer1;

}

implementation {
	message_t packet;
	bool lock;
	
	// Repassa os dados vindos da aplicação com destino a um host determinado.
	command void Envia.send (http_msg_t msg) {
		if (lock != FALSE) { return; }
		else {
			http_msg_t* httpmsg = (http_msg_t*)call Packet.getPayload(&packet, sizeof(http_msg_t));
			// Converte os dados vindos da aplicação para a estrutura padrão. 
			char met=msg.metodo;
			uint16_t cmet = (uint16_t) met;		
			httpmsg->metodo=cmet;
			httpmsg->path=msg.path;
			httpmsg->dados=msg.dados;
			httpmsg->dhost=msg.dhost;
			httpmsg->codigo=msg.codigo;
			httpmsg->shost = TOS_NODE_ID;
			//call Leds.set(httpmsg->dhost);
			// Luz de teste.
			//call Leds.led0Toggle();
			// Envia ao host.
			if (call AMSender.send(httpmsg->dhost, &packet, sizeof(http_msg_t)) == SUCCESS) {
				lock = TRUE;
			}
		}
	}

	command error_t Envia.start() {
		//Inicia o radio
		call Control.start();
		//Envia um sinal a interface Stop_Discovery para interromper o serviço.			
		call Start_Discovery.receive(); 
		return SUCCESS;
	
	}
	
	command error_t Envia.stop() { return SUCCESS; }
	
	default event void Envia.startDone (error_t err) {}
	default event void Envia.stopDone (error_t err) {}
	event void Control.startDone(error_t err) {}
	
	
	
	event void Control.stopDone(error_t err) {}
	
	default event void Get.receive ( http_msg_t) {}
	default event void Put.receive ( http_msg_t) {}
	
	event message_t* AMReceive.receive(message_t* bufPtr,	void* payload, uint8_t len) {
		// Se o tamanho for diferente do tamanho do pacote padrão.
		if (len != sizeof(http_msg_t)){
			return bufPtr;
		}
		// Caso contrario
		else {
	
			// Passa para variável o valor do payload
			http_msg_t* rhttpmsg = (http_msg_t*)payload;
			/*
			Inicio das verificações do pacote recebido.
			 */
	
			// Verifica se a mensagem é para ele. 
			if (rhttpmsg->dhost == TOS_NODE_ID) {

				/*
				Converte char pra int8 pois o TinyOS somente envia mensagens em inteiros.
				 */
	
				int met = rhttpmsg->metodo; 
				char cmet = ( char ) met;
				/*
				Inicio da verificação de metodos
				 */
				// Se for Get.
				if (cmet == 'G') {
					signal Get.receive ( *rhttpmsg );
				}
				// Caso contrario.
				else {
					// Se for Put.
					if (cmet == 'P') {
						// Envia os dados para a aplicação através da interface Put.		
						signal Put.receive ( *rhttpmsg );
					}
					// Caso contrario ele envia uma resposta de erro já que por enquanto só estamos trabalhando com o metodo Get e Put.
					else {
	
						// Cria o pacote
						http_msg_t* httpmsg;
						httpmsg = (http_msg_t*)call Packet.getPayload(&packet, sizeof(http_msg_t));
						call Leds.led0Toggle();
						if (httpmsg == NULL) {
							return bufPtr;
						}
						// Método não permitido.
						httpmsg->codigo = 405;
						httpmsg->dhost = rhttpmsg->shost;
						httpmsg->shost = TOS_NODE_ID;
						if (call AMSender.send(httpmsg->dhost, &packet, sizeof(http_msg_t)) == SUCCESS) {
							lock = TRUE;
							return bufPtr;	
						}
					}
				}
	
			}
			// Se host de destino não for do sensor.
			else {
				/* 
				 * Parâmetros necessários para atingir o serviço de descoberta Host de Destino = Origem e
				 * utilizamos o campo código com valor 2000 que normalmente não é utilizado no envio 
				 * somente no recebimento.
				 */
				if (rhttpmsg->codigo == 2000) {
					//Envia um sinal a interface Stop_Discovery para interromper o serviço.			
					call Stop_Discovery.receive(); 
					//call Leds.led2Toggle();
				}
				// Se não atingir os requisitos para entrar no Discovery é descartado com envio do código de erro.
				else { 
					http_msg_t* httpmsg;
					httpmsg = (http_msg_t*)call Packet.getPayload(&packet, sizeof(http_msg_t));
					if (httpmsg == NULL) {
						return bufPtr;
					}
					httpmsg->codigo = 404; // Not Found
					httpmsg->dhost = rhttpmsg->shost;
					httpmsg->shost = TOS_NODE_ID;
					if (call AMSender.send(httpmsg->dhost, &packet, sizeof(http_msg_t)) == SUCCESS) {
						lock = TRUE;
						return bufPtr;	
					}
				}
	
			}
	
		}
		return bufPtr;		
	}
	
	
	event void AMSender.sendDone(message_t* bufPtr, error_t error) {
		if (&packet == bufPtr) {
			lock = FALSE;
			//call Leds.led2Toggle();
		}
	}
	
}

