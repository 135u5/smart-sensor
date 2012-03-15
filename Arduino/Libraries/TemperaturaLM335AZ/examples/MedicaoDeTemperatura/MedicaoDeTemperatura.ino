#include "TemperaturaLM335AZ.h"
float resultado;
//Pino Utilizado e Voltagem(Calibração) 
TemperaturaLM335AZ sensor(1,1);

void setup() {
  Serial.begin(19200);
}

void loop() {

    //Temperatura medição padrão.
    resultado=sensor.getTemperatura();
    Serial.print("Resultado padrão: ");
    Serial.print(resultado);
    Serial.print(".");
    Serial.println();
    
    //Temperatura medição padrão com média(amostragens, intervalo entre elas em milisegundos).
    resultado=sensor.getTemperatura(10,500);
    Serial.print("Resultado padrão com média: ");
    Serial.print(resultado);
    Serial.print(".");
    Serial.println();
    
    //OBS: Experimental
    //Temperatura medição direta com conversão para celcius.
    resultado=sensor.getTemperaturaCelcius();
    Serial.print("Resultado em Celcius: ");
    Serial.print(resultado);
    Serial.print("c.");
    Serial.println();
    
    //OBS: Experimental
    //Temperatura medição com média em celcius com média(amostragens, intervalo entre elas em milisegundos)
    resultado=sensor.getTemperaturaCelcius(10,500);
    Serial.print("Resultado em Celcius com média: ");
    Serial.print(resultado);
    Serial.print("c.");
    Serial.println();
  
  delay(1000);
}



