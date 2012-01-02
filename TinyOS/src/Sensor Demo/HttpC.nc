/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Arquivo de Configuração do componente Http.
 * 
 *
 * @version 1.0 17 Dec 2011
 * @author  Renato Cesar Gil Azevedo
 * @e-mail renatoazevedo@labnet.nce.ufrj.br
**/

configuration HttpC {
	provides interface Envia;
	provides interface Get;
	provides interface Put;
}
implementation {
	components HttpP as App, LedsC;
	App = Envia;
	App = Get;
	App = Put;
	App.Leds -> LedsC;

	components DiscoveryC;
	App.Start_Discovery -> DiscoveryC; 
	App.Stop_Discovery -> DiscoveryC;


	components ActiveMessageC;
	App.Packet -> ActiveMessageC;
	App.Control -> ActiveMessageC;


	components new AMReceiverC(AM_HTTP_MSG);
	App.AMReceive -> AMReceiverC;

	components new AMSenderC(AM_HTTP_MSG);
	App.AMSender -> AMSenderC;



	//components new TimerMilliC() as Timer1;
	//App.Timer1 -> Timer1;


}

