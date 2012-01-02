/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Arquivo de Configuração do Radio da Base.
 * 
 *
 * @version 1.0 17 Dec 2011
 * @author  Renato Cesar Gil Azevedo
 * @e-mail renatoazevedo@labnet.nce.ufrj.br
**/

#include "http.h"

configuration baseAppC {}
implementation {
  components MainC, baseC as App, LedsC;
  components ActiveMessageC;
  components new AMReceiverC(AM_HTTP_MSG);
  components new AMSenderC(AM_HTTP_MSG);
  components SerialActiveMessageC as AMSerial;
  
  App.Boot -> MainC.Boot;
  
  App.RadioReceive -> AMReceiverC;
  App.RadioSend -> AMSenderC;
  App.RadioPacket -> AMSenderC; 
  App.SerialReceive -> AMSerial.Receive[AM_HTTP_MSG];
  App.SerialSend -> AMSerial.AMSend[AM_HTTP_MSG];
  App.Control -> AMSerial;
  App.SerialPacket -> AMSerial;
  App.RadioControl -> ActiveMessageC;
  App.Leds -> LedsC;
}
