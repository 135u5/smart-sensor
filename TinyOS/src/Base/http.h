/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Arquivo de estrutura	da mensagem.
 * 
 *
 * @version 1.0 17 Dec 2011
 * @author  Renato Cesar Gil Azevedo
 * @e-mail renatoazevedo@labnet.nce.ufrj.br
**/

#ifndef HTTP_H
#define HTTP_H

typedef nx_struct http_msg {
	/* 
	Método:
	Com a utilização de Chopin:
	PUT -> U -> 0x55
	GET -> G -> 0x47
	 */
	nx_uint16_t metodo;
	/* 
	Tipo de sensoriamento:
	1 DemoSensor
	2 Umidade
	3 Luminosidade
	 */
	nx_uint16_t path; 
	// Dados Sensoriados ou Genéricos.
	nx_uint16_t dados; 
	// Host de Destino.
	nx_uint16_t dhost; 
	// Host de Origem.
	nx_uint16_t shost; 
	// Código de retorno de erro, também é utilizado no Sistema de descoberta.
	nx_uint16_t codigo;  
	nx_uint16_t error; 
} http_msg_t;

enum {
		AM_HTTP_MSG = 7,
};
#endif
