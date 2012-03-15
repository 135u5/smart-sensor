/*
 * Discovery.h
 *	    Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 *      Site do projeto: http://labnet.nce.ufrj.br/cia2/
 *
 *  	Created on: 02/2012
 *      Author: Renato Cesar Gil Azevedo
 */

#ifndef DISCOVERY_H_
#define DISCOVERY_H_

class Discovery {
public:
	Discovery(short meuHost, short meuPath, short gateway);
	~Discovery();
    short getMeuHost() const;
    short getMeuPath() const;
    short getGateway() const;
    bool isModoDiscovery() const;
    void setModoDiscovery(bool modoDiscovery);
    void temporizadorDiscovery();
private:
	short meuHost;
	short gateway;
	short meuPath;
	bool modoDiscovery;
};

#endif /* DISCOVERY_H_ */
