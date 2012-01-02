package driver;
/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe responsavel por enviar e receber mensagens para os nós de arquitetura crossbow e
 * atualizar o banco de dados com informacoes recebidas.
 *
 * @version 1.0 01 Jan 2012
 * @author  José Renato da Silva Júnior
**/

import java.io.IOException;
import java.sql.Connection;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import db.DataDB;

public class TinyOS  extends Thread implements MessageListener {

  private MoteIF moteIF;

  /**Construtor
   * */
  public TinyOS(MoteIF moteIF) {
    this.moteIF = moteIF;
    this.moteIF.registerListener(new tinyOSMsg(), this);
  }  
  /**Envia varios pacotes para a rede
   * */
  public void sendPackets() {
    tinyOSMsg payload = new tinyOSMsg();

    try {
      while (true) {
    	  
    	  moteIF.send(0, payload);
	try {Thread.sleep(1000);}
	catch (InterruptedException exception) {}
      }
    }
    catch (IOException exception) {
      System.err.println("Exception thrown when sending packets. Exiting.");
      System.err.println(exception);
    }
  }
  
  /**Envia um único pacote para a rede
   * */
  public void sendPacket(int sensorId, String sensorType) {
	    tinyOSMsg payload = new tinyOSMsg();
	    char met='G';
		int cmet = (int)met;   
	    payload.set_metodo(cmet);
	    if(sensorType.compareToIgnoreCase("demo")==0){
	    	payload.set_path(1);
	    }
	    if(sensorType.compareToIgnoreCase("photo")==0){
		    payload.set_path(2);
		}
	    if(sensorType.compareToIgnoreCase("umidade")==0){
		    payload.set_path(3);
		}
	    payload.set_dhost(sensorId);
	    
	    try {
			moteIF.send(0, payload);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  }
  /**Trata as mensagens recebidas
   * */
  public void messageReceived(int to, Message message) {
    tinyOSMsg msg = (tinyOSMsg)message;
    System.out.println("Received packet Metodo: " + msg.get_metodo() + "\nPath: " + msg.get_path() + "\nDados: " + msg.get_dados() + "\nDHost: " +  msg.get_dhost() + "\nShost: " +  msg.get_shost() + "\nCodigo: " +  msg.get_codigo() );
    Connection conn = DataDB.getConnInstance();
    if(msg.get_codigo()==2000){
    	switch(msg.get_path()){
    	case 1:
    		DataDB.insertService(conn, msg.get_shost(), "demo");
    		break;
    	case 2:
    		DataDB.insertService(conn, msg.get_shost(), "umidade");
    		break;
    	case 3:
    		DataDB.insertService(conn, msg.get_shost(), "photo");
    		break;
    	}
    }else{
	    switch(msg.get_path()){
	    	case 1:
	    		DataDB.insertData(conn, msg.get_shost(), "demo", msg.get_dados());
	    		break;
	    	case 2:
	    		DataDB.insertData(conn, msg.get_shost(), "umidade", msg.get_dados());
	    		break;
	    	case 3:
	    		DataDB.insertData(conn, msg.get_shost(), "photo", msg.get_dados());
	    		break;
	    }
    }
  }
    
    /**Inicia a Thread
     * */
	public void run(){
		sendPackets();
	}
}

