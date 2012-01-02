/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Arquivo de Configuração do componente Discovery.  
 * 
 *
 * @version 1.0 17 Dec 2011
 * @author  Renato Cesar Gil Azevedo
 * @e-mail renatoazevedo@labnet.nce.ufrj.br
**/

configuration DiscoveryC {
	provides interface Stop_Discovery;
	provides interface Start_Discovery;
}
implementation {
	components DiscoveryP as App, LedsC;
	App = Stop_Discovery;
	App = Start_Discovery;
	App.Leds -> LedsC;
	
	components HttpC;
	App.Envia -> HttpC; 
	
	components new TimerMilliC() as Timer0;
	App.Hello -> Timer0;
	
}
