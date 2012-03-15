/*
 * TemperaturaLM335AZ.h
 *	    Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 *      Site do projeto: http://labnet.nce.ufrj.br/cia2/
 *
 *  	Created on: 02/2012
 *      Author: Renato Cesar Gil Azevedo
 */

#include "TemperaturaLM335AZ.h";

TemperaturaLM335AZ::TemperaturaLM335AZ(int pinoUtilizado, int voltagem) {
	//Define o pino utilizado pelo sensor.
	pinMode(pinoUtilizado, INPUT);
	this->pinoUtilizado = pinoUtilizado;
	this->voltagem = (short) voltagem;
}

int TemperaturaLM335AZ::getPinoUtilizado() const {
	return pinoUtilizado;
}


//Obt�m a temperatura do sensor por medi��o �nica.
int TemperaturaLM335AZ::getTemperatura() {
	this->temperatura = analogRead(pinoUtilizado);
	this->temperatura;
}
//Obt�m a temperatura do sensor em Celsius, a medi��o obtida atrav�s da quantidade de vezes por um tempo em milissegundos determinado.
int TemperaturaLM335AZ::getTemperatura(int mediaQuantidadeDeMedicoes, int intervaloDeMedicoesMilli)
{
	int media[mediaQuantidadeDeMedicoes];
	int i;
	this->temperatura = 0;
	for (i = 0; i < mediaQuantidadeDeMedicoes; i++) {

		media[i] = analogRead(pinoUtilizado);
		this->temperatura = this->temperatura + media[i];
		delay(intervaloDeMedicoesMilli);

	}
	this->temperatura = this->temperatura / mediaQuantidadeDeMedicoes;
	return this->temperatura;
}



//OBS: As convers�es em Celsius s�o experimentais e necessitam de hardware espec�fico.
//Obt�m a temperatura do sensor por medi��o �nica em C�lsius.
float TemperaturaLM335AZ::getTemperaturaCelcius() {
	this->temperaturaCelcius  = (this->voltagem * analogRead(pinoUtilizado)
			* 100.0) / 1024.0;
	return this->temperaturaCelcius ;
}

//OBS: As convers�es em Celsius s�o experimentais e necessitam de hardware espec�fico.
//Obt�m a temperatura do sensor em C�lsius, a medi��o obtida atrav�s da quantidade de vezes por um tempo em milissegundos determinado.
float TemperaturaLM335AZ::getTemperaturaCelcius(int mediaQuantidadeDeMedicoes,
		int intervaloDeMedicoesMilli) {
	int media[mediaQuantidadeDeMedicoes];
	int i;
	this->temperaturaCelcius = 0;
	for (i = 0; i < mediaQuantidadeDeMedicoes; i++) {

		media[i] = (this->voltagem * analogRead(pinoUtilizado) * 100.0)
				/ 1024.0;
		this->temperaturaCelcius = this->temperaturaCelcius + media[i];
		delay(intervaloDeMedicoesMilli);

	}
	this->temperaturaCelcius = this->temperaturaCelcius / mediaQuantidadeDeMedicoes;
	return this->temperaturaCelcius;
}



float TemperaturaLM335AZ::getVoltagem() const {
	return voltagem;
}

void TemperaturaLM335AZ::setPinoUtilizado(int pinoUtilizado) {
	this->pinoUtilizado = pinoUtilizado;
}

void TemperaturaLM335AZ::setTemperaturaCelcius(float temperaturaCelcius) {
	this->temperaturaCelcius = temperaturaCelcius;
}

void TemperaturaLM335AZ::setVoltagem(float voltagem) {
	this->voltagem = voltagem;
}

