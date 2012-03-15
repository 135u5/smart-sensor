/*
 * Http.h
 *	    Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 *      Site do projeto: http://labnet.nce.ufrj.br/cia2/
 *
 *  	Created on: 02/2012
 *      Author: Renato Cesar Gil Azevedo
 */

#ifndef HTTP_H_
#define HTTP_H_

#include "Discovery.h"
#include <Arduino.h>
#include <string.h>


class Http {
public:
	Http();
	~Http();
    short getCodigo() const;
    short getDados() const;
    short getDhost() const;
    short getError() const;
    short getMetodo() const;
    short getPath() const;
    short getShost() const;
    void setDhost(short  dhost);
    Http criaHttp(short dhost,short shost, short codigo, short metodo, short path, short dados, short error);
    Http criaHttp(short dhost,short shost, short codigo, char metodo, short path, short dados, short error);
    Http criaHttp(short dhost,short shost, short codigo);
    String converteHttpParaString(Http http);
    Http converteStringParaHttp(String msg);
    void enviaHttp(Http http);
    Http recebeHttp(Discovery discovery);
    Http verificaMensagemHttp(Http http,Discovery discovery);




private:
	short dhost;
	short shost;
	short codigo;
	short metodo;
	short path;
	short dados;
	short error;
};


#endif /* HTTP_H_ */
