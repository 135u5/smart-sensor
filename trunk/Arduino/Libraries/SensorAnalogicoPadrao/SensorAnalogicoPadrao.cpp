/*
 * SensorAnalogicoPadrao.cpp
 *	    Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 *      Site do projeto: http://labnet.nce.ufrj.br/cia2/
 *
 *  	Created on: 02/2012
 *      Author: Renato Cesar Gil Azevedo
 */

#import "SensorAnalogicoPadrao.h"

//Define a utiliza��o do m�todo sem calibra��o previa no sensor.
SensorAnalogicoPadrao::SensorAnalogicoPadrao(int pinoUtilizado) {
	//Define o pino utilizado pelo sensor.
	pinMode(pinoUtilizado, INPUT);
	this->minimoCalibrado = -255;
	this->maximoCalibrado = -255;

}

//Define a utiliza��o do m�todo com calibra��o previa no sensor.
SensorAnalogicoPadrao::SensorAnalogicoPadrao(int pinoUtilizado,
		short minimoCalibrado, short maximoCalibrado) {
	this->pinoUtilizado = pinoUtilizado;
	this->minimoCalibrado = minimoCalibrado;
	this->maximoCalibrado = maximoCalibrado;

}

short SensorAnalogicoPadrao::getMaximoCalibrado() const {
	return maximoCalibrado;
}

short SensorAnalogicoPadrao::getMinimoCalibrado() const {
	return minimoCalibrado;
}

short SensorAnalogicoPadrao::getPinoUtilizado() const {
	return pinoUtilizado;
}

float SensorAnalogicoPadrao::getResultado() {
	//Se foi definido o m�todo de calibra��o e os valores n�o sejam default.
	if (this->minimoCalibrado > -255 && this->maximoCalibrado > -255) {
		this->resultado = this->calibraSensor(this->minimoCalibrado, this->maximoCalibrado,
				this->pinoUtilizado);

	} else {
		this->resultado = analogRead(pinoUtilizado);
	}
	return this->resultado;
}

void SensorAnalogicoPadrao::setMaximoCalibrado(short maximoCalibrado) {
	this->maximoCalibrado = maximoCalibrado;
}

void SensorAnalogicoPadrao::setMinimoCalibrado(short minimoCalibrado) {
	this->minimoCalibrado = minimoCalibrado;
}


//OBS: Este tipo de calibra��o � exemplificado no site do arduino, no entanto n�o funciona em todo tipo de sensor.
float SensorAnalogicoPadrao::calibraSensor(short minimoCalibrado,
		short maximoCalibrado, short pinoUtilizado) {
	float val = 0;
// calibre durante os 5 segundos iniciais
	while (millis() < 5000) {
		val = analogRead(pinoUtilizado);

		// registre o m�ximo valor do sensor
		if (val > maximoCalibrado) {
			maximoCalibrado = val;
		}

		// registre o m�nimo do sensor
		if (val < minimoCalibrado) {
			minimoCalibrado = val;
			val = analogRead(pinoUtilizado);

			// aplique a calibra��o � leitura no sensor
			val = map(val, minimoCalibrado, maximoCalibrado, 0, 255);

			// caso o valor esteja fora do intervalo visto durante a calibra��o descomentar
			//val = constrain(val, 0, 255);
		}
	}
	return val;
}

