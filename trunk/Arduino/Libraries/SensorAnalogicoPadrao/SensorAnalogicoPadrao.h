/*
 * SensorAnalogicoPadrao.h
 *	    Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 *      Site do projeto: http://labnet.nce.ufrj.br/cia2/
 *
 *  	Created on: 02/2012
 *      Author: Renato Cesar Gil Azevedo
 */

#ifndef SENSORANALOGICOPADRAO_H_
#define SENSORANALOGICOPADRAO_H_

#include <Arduino.h>

class SensorAnalogicoPadrao {
public:
	SensorAnalogicoPadrao(int  pinoUtilizado);
    SensorAnalogicoPadrao(int  pinoUtilizado, short  minimoCalibrado, short  maximoCalibrado);
    float calibraSensor(short  minimoCalibrado, short  maximoCalibrado, short  pinoUtilizado) ;
    short getMaximoCalibrado() const;
    short getMinimoCalibrado() const;
    short getPinoUtilizado() const;
    float getResultado();
    void setMaximoCalibrado(short  maximoCalibrado);
    void setMinimoCalibrado(short  minimoCalibrado);
private:
    float resultado;
    short minimoCalibrado;
    short maximoCalibrado;
    int pinoUtilizado;
};

#endif /* SENSORANALOGICOPADRAO_H_ */
