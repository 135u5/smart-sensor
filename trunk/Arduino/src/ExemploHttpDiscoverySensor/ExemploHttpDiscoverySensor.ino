//Exemplo de utilização do Http, Discovery e TemperaturaLM335AZ.h

//Importar as Bibliotecas.
#include "Http.h"
#include "Discovery.h"
#include "TemperaturaLM335AZ.h"

//Instancia os objetos;//

//Instancia Http
Http http;

//Ao instanciar o Discovery é necessário incluir: 
//(host do sensor, tipo de sensoriamento, gateway padrão)
//Tipo de sensoriamento:
//1 DemoSensor
//2 Umidade
//3 Luminosidade
//4 Temperatura
Discovery discovery(5, 4, 1);

//Ao instanciar o TemperaturaLM335AZ é necessário incluir: 
//Pino Utilizado, Voltagem(Calibração)
TemperaturaLM335AZ sensor(1, 5);

//Inicialização padrão do Arduino//
void setup() {
	//Específica o Bit rate (Utilizado na comunicações seriais inclusive o Xbee);
	Serial.begin(19200);
}

//Inicio do loop padrão
void loop() {
	//Inicializa o serviço do temporizador Discovery.
	discovery.temporizadorDiscovery();

	//Inicia o serviço de recebimento de mensagense atribui os valores ao objeto http.
	//É passado o objeto discovery para que as verificações no recebimento sejam possíveis.
	http = http.recebeHttp(discovery);

	//Verifica se o modo discovery esta ativo.
	if (discovery.isModoDiscovery()) {
		//Caso o código da menssagem seja 2000 ele desativa o modo discovery.
		if (http.getCodigo() == 2000
				&& discovery.getGateway() == http.getShost()) {
			discovery.setModoDiscovery(false);
		}
	} else {
		//Aqui você filtra ou utliza a mensagem como quiser.//
		//Neste exemplo vamo sensoriar quando pedido.//

		//Se o gateway pedir uma medição de temperatura.
		if (http.getShost() == discovery.getGateway() && http.getPath() == 4
				&& http.getMetodo() == (short) 'G') {
			http.enviaHttp(
					http.criaHttp(http.getShost(), discovery.getMeuHost(), 0,
							'P', discovery.getMeuHost(), sensor.getTemperatura(),
							0));

		}

	}
	//De quanto em quanto tempo o processo vai se repetir em milissegundos
	delay(1000);

}


