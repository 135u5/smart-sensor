/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Arquivo de Configuração da interface Envia provida pelo componente Http.
 * 
 *
 * @version 1.0 17 Dec 2011
 * @author  Renato Cesar Gil Azevedo
 * @e-mail renatoazevedo@labnet.nce.ufrj.br
**/

interface Envia {
	command void send (http_msg_t);
	command error_t start ();
	command error_t stop ();
	
	event void startDone(error_t);
	event void stopDone(error_t);
}