package listener;
/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe usada para alterar a inicializacao do Tomcat,
 * possibilitando a inicializacao das threads de escuta.
 *
 * @version 1.0 01 Jan 2012
 * @author  José Renato da Silva Júnior
**/

import javax.servlet.*;

import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

import driver.Arduino;
import driver.TinyOS;

public  class ContextListener implements ServletContextListener {
  
  private ServletContext context = null;
  
  static MoteIF mif = createMif();
	
  public static TinyOS listener = new TinyOS(mif);
  public static Arduino listenerArduino = new Arduino("COM7", 9600);
  

  
  
  
  public static MoteIF createMif(){
	  PhoenixSource phoenix;
	  String source = "serial@/dev/ttyUSB1:micaz";	
		if (source == null) {
			phoenix = BuildSource.makePhoenix(PrintStreamMessenger.err);
		}
		else {
			phoenix = BuildSource.makePhoenix(source, PrintStreamMessenger.err);
		}

		MoteIF mif = new MoteIF(phoenix);
		return mif;
  }
  
  /** Este metodo e invocado quando a aplicacao web for removida
  * e nao esta apta a responder requisicoes
  */   
  public void contextDestroyed(ServletContextEvent event)
  {
    /** Interrompe as threads quando o tomcat for finalizado
     * */
	listener.interrupt();	
	listenerArduino.closeCom();
    this.setContext(null);

  }


  /**Este metodo e invocado quando a aplicacao web esta apta a receber requisicoes
   * */
  public void contextInitialized(ServletContextEvent event)
  {
    this.setContext(event.getServletContext());
    /** Inicia as threads que irao monitorar as porta seriais
     * */
    listener.start();
    listenerArduino.startListening();
    
  }

public void setContext(ServletContext context) {
	this.context = context;
}

public ServletContext getContext() {
	return context;
}
  
}