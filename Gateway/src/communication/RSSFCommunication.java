package communication;

import driver.MsgHttp;
import listener.ContextListener;

public class RSSFCommunication {


	public void request(int sensorId, String sensorType, String plataform) {
		if(plataform.equals("TinyOS")){
			ContextListener.listener.sendPacket(sensorId, sensorType );	
		}else{
			MsgHttp http= new MsgHttp();
			ContextListener.listenerArduino.enviaHttp(http.converteHttpParaString(http
					.criaHttp(sensorId, 200, 71, 4, 0, 0)));
		}		

	}

}
