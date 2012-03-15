/*
 * TemperaturaLM335AZ.cpp
 *	    Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 *      Site do projeto: http://labnet.nce.ufrj.br/cia2/
 *
 *  	Created on: 02/2012
 *      Author: Renato Cesar Gil Azevedo
 */

#ifndef TEMPERATURALM335AZ_H_
#define TEMPERATURALM335AZ_H_

#include <Arduino.h>

class TemperaturaLM335AZ {
public:
	TemperaturaLM335AZ(int pinoUtilizado, int voltagem);
	int getPinoUtilizado() const;

	int getTemperatura();
	int getTemperatura(int mediaQuantidadeDeMedicoes,
			int intervaloDeMedicoesMilli);

	float getTemperaturaCelcius();
	float getTemperaturaCelcius(int mediaQuantidadeDeMedicoes,
			int intervaloDeMedicoesMilli);

	float getVoltagem() const;
	void setPinoUtilizado(int pinoUtilizado);
	void setTemperaturaCelcius(float temperaturaCelcius);
	void setVoltagem(float voltagem);
private:
	int pinoUtilizado;
	int temperatura;
	float temperaturaCelcius;
	float voltagem; //calibração
};

#endif /* TEMPERATURALM335AZ_H_ */
