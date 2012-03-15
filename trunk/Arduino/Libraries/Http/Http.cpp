/*
 * Http.cpp
 *	    Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 *      Site do projeto: http://labnet.nce.ufrj.br/cia2/
 *
 *  	Created on: 02/2012
 *      Author: Renato Cesar Gil Azevedo
 */

#include "Http.h"
#include "Discovery.h"

#include <stdio.h>
#include <string.h>


short meuHost;

Http::Http() {
	this->dhost = 0;
	this->shost = 0;
	this->codigo = 0;
	this->metodo = 0;
	this->path = 0;
	this->dados = 0;
	this->error = 0;
}

Http::~Http() {/*nothing to destruct*/
}

short Http::getCodigo() const {

	return codigo;
}

short Http::getDados() const {
	return dados;
}

short Http::getDhost() const {
	return dhost;
}

short Http::getError() const {
	return error;
}

short Http::getMetodo() const {
	return metodo;
}

short Http::getPath() const {
	return path;
}

short Http::getShost() const {
	return shost;
}

void Http::setDhost(short dhost) {
	this->dhost = dhost;
}

Http Http::criaHttp(short dhost, short shost, short codigo, short metodo,
		short path, short dados, short error) {
	Http http;
	http.dhost = dhost;
	http.shost = shost;
	http.codigo = codigo;
	http.metodo = metodo;
	http.path = path;
	http.dados = dados;
	http.error = error;

	return http;
}

Http Http::criaHttp(short dhost, short(shost), short codigo, char metodo,
		short path, short dados, short error) {
	Http http;
	http.dhost = dhost;
	http.shost = shost;
	http.codigo = codigo;
	http.metodo = (short) metodo;
	http.path = path;
	http.dados = dados;
	http.error = error;

	return http;

}
Http Http::criaHttp(short dhost, short(shost), short codigo) {
	Http http;
	http.dhost = dhost;
	http.shost = shost;
	http.codigo = codigo;
	http.metodo = (short) 'P';
	http.path = 0;
	http.dados = 0;
	http.error = 0;

	return http;
}

String Http::converteHttpParaString(Http http) {
	char buf[25];

	sprintf(buf, "%d#%d#%d#%d#%d#%d#%d#", http.dhost, http.shost, http.codigo,
			http.metodo, http.path, http.dados, http.error);

	return buf;
}

Http Http::converteStringParaHttp(String msg) {
	int c, i;
	short vet[7];
	Http http;
	for (c = i = 0; c < 7; c++) {
		vet[c] = (short) msg.substring(i, msg.indexOf('#', i)).toInt();
		i = msg.indexOf("#", i) + 1;
	}

	http.dhost = vet[0];
	http.shost = vet[1];
	http.codigo = vet[2];
	http.metodo = vet[3];
	http.path = vet[4];
	http.dados = vet[5];
	http.error = vet[6];

	return http;
}

Http Http::recebeHttp(Discovery discovery) {
	meuHost=discovery.getMeuHost();
	Http http;
	String msg;
	while (Serial.available() > 0) {
		byte temp = Serial.read();
		if (temp != 255) {
			msg = msg + (char) temp;
		}
	}
	if (msg.length() > 0) {
		http = http.converteStringParaHttp(msg);
		delay(1000);
		Serial.flush();

		http = http.verificaMensagemHttp(http,discovery);
		if (http.dhost != 0) {
			return http;
		}

	}
	return http;
}

Http Http::verificaMensagemHttp(Http http, Discovery discovery) {
	//Verifica se a mensagem é para ele.
	if (http.dhost == discovery.getMeuHost()) {
		//Verifica se o Modo discovery está ativo.
		//if (false) {
		if (discovery.isModoDiscovery()) {
			if (http.codigo == 2000) {
				http.dhost = 0;
			}
		} else {
			//Verifica se o serviço existe
			if (http.path == discovery.getMeuPath()) {
				//Verifica se é Get ou Put.
				if (http.metodo == (short) 'G' || http.metodo == (short) 'P') {
					return http;
				} else {
					//Se não for Get ou Put envia mensagem de erro.
					http.criaHttp(http.shost, discovery.getMeuHost(), 405);
					http.enviaHttp(http);
					return http;
				}

			} else {
				http.criaHttp(http.shost, discovery.getMeuHost(), 404);
				http.enviaHttp(http);
				return http;
			}
		}
	} else {
		http.dhost = 0;
	}

	return http;
}

void Http::enviaHttp(Http http) {
	if(http.dhost!=0 && http.dhost!=meuHost){
	String msg = http.converteHttpParaString(http);
	Serial.println(msg);
	}

}

