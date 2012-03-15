/*
 * Discovery.cpp
 *	    Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 *      Site do projeto: http://labnet.nce.ufrj.br/cia2/
 *
 *  	Created on: 02/2012
 *      Author: Renato Cesar Gil Azevedo
 */

#include "Discovery.h"
#include "Http.h"


Discovery::Discovery(short meuHost, short meuPath, short gateway) {
	this->meuHost = meuHost;
	this->meuPath = meuPath;
	this->gateway = gateway;
	this->modoDiscovery = true;
}


Discovery::~Discovery(){/*nothing to destruct*/}




short Discovery::getMeuHost() const {
	return meuHost;
}
short Discovery::getGateway() const {
	return gateway;
}

short Discovery::getMeuPath() const {
	return meuPath;
}

bool Discovery::isModoDiscovery() const {
	return modoDiscovery;
}



void Discovery::setModoDiscovery(bool modoDiscovery) {
	this->modoDiscovery = modoDiscovery;
}

void Discovery::temporizadorDiscovery() {
	if (this->modoDiscovery) {
		Http http;
		http.enviaHttp(http.criaHttp(this->gateway, this->getMeuHost(), 2000));
	}
}
