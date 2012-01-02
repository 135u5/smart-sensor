/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Arquivo de Configuração da aplicação no sensor exemplo.
 * 
 *
 * @version 1.0 17 Dec 2011
 * @author  Renato Cesar Gil Azevedo
 * @e-mail renatoazevedo@labnet.nce.ufrj.br
**/

#include "http.h"

configuration SensorAppC {}
implementation {
	
	components MainC, SensorC as App, LedsC, new DemoSensorC();
	App.Boot -> MainC.Boot;
	App.Read -> DemoSensorC;
	App.Leds -> LedsC;
	
	
	components HttpC;
	App.Get -> HttpC;
	App.Put -> HttpC;
	App.Envia -> HttpC; 
	

}
