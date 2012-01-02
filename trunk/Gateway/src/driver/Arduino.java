package driver;
/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe responsavel por enviar e receber mensagens para os nós de arquitetura arduino e
 * atualizar o banco de dados com informacoes recebidas.
 *
 * @version 1.0 01 Jan 2012
 * @author  José Renato da Silva Júnior
**/

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;

import db.DataDB;

public class Arduino implements SerialPortEventListener, Runnable  {
	
	private CommPortIdentifier cp = null;
	private SerialPort port = null;
	private OutputStream output ;
	
	private InputStream input;
	private Thread readingThread;
	
	@SuppressWarnings("unused")
	private String mode="ESCRITA";
	
	private boolean listening;
	private boolean initialization = false;
	
	private String comPort="COM5";
	private int baundRate=9600;

//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
// <><><><><><><> Métodos de configuração <><><><><><><>.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	/**Atribui o valor para a porta de comunicação.
	 */
	public void SetCOM(String con)
	{comPort = con;}
	
	/**Retorna a porta de comunicação configurada.
	 */
	public String GetCOM()
	{return comPort;}

	/**Atribui o valor para a baund rate.
	 */
	public void SetBaundRate(int br)
	{baundRate = br;}
		
	/**Retorna o valor para a baund rate.
	 */
	public int GetBaundRate()
	{return baundRate;}
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm

	
	
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
// <><><><><><><>       Construtores      <><><><><><><>.
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	/**
	 * Construtor definindo a porta de comunicacao  
	 */
	public Arduino(String con, int br)
		{
		this.SetCOM(con);
		this.SetBaundRate(br);
		this.startCommunication();
		}
	
	/**Construtor sem definir a porta de comunicação.
	 */
	public Arduino()
		{}//InicializarComunicacao();}
//mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm
	
	/**Metodo para fazer o parsing da mensagem
	 */
	private void parsingMSG(String MSG){
		int sensorId;
		@SuppressWarnings("unused")
		String sensorType;
		int dataRead;
		sensorId = 99991;
		System.out.println("Sensor ID: " + sensorId);
		sensorType = "xxx";
		dataRead = Integer.parseInt(MSG.substring(6, MSG.length()));
		System.out.println("Dado lido: " + dataRead);
		
		
		Connection conn = DataDB.getConnInstance();
 		DataDB.insertData(conn, sensorId, "Arduino", dataRead);
 	   
	}
	

	 public void stopListening(){
		listening=!listening;
		port.notifyOnDataAvailable(listening);}
	 
	 
	 public void closeCom(){
	        try {
	            port.close();
	        } catch (Exception e) {
	            System.out.println("Erro fechando porta: " + e);
	            System.exit(0);
	        }
     	}

	 

		public void startListening() {	
			listening=true;
			  try {
	              input = port.getInputStream();
	          } catch (Exception e) {
	        	  System.out.println("Erro de listener: " + e);
	          }
	          try {
	              port.addEventListener(this);
	          } catch (Exception e) {
	              System.out.println("Erro de listener: " + e);
	              System.exit(1);
	          }
	          //porta.notifyOnDataAvailable(true);
	          port.notifyOnDataAvailable(listening);
	          try {
	              readingThread = new Thread(this);
	              readingThread.start();
                  run();
	          } catch (Exception e) {
	              System.out.println("Erro de Thred: " + e);
	          }
		}	
		
	/**Envia a menssagem para a porta especificada.
	 */
	public void sendMsg(String menssagem){
	
		try {
			output.write(menssagem.getBytes());
			System.out.println("A messagem '" + menssagem + "' foi enviada com sucesso.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERRO - Não foi possivel enviar a menssagem. A Inicialização é " + initialization);
		}
		
	 }	
	
	/**Esta função é responsável por estabilizar a conecção com a porta especificada.
	 */
	@SuppressWarnings("static-access")
	public void startCommunication(){ 
	 
		try {
			cp = CommPortIdentifier.getPortIdentifier(comPort);
			System.out.println(" A Porta "+  comPort + " foi encontrada.");
			try {
				port = (SerialPort)cp.open("ArduinoDrive",1000);
				System.out.println(" A Porta "+  comPort + " foi aberta.");
				try {
					port.setSerialPortParams(baundRate, port.DATABITS_8, port.STOPBITS_2, port.PARITY_NONE);
					System.out.println(" A Porta "+  comPort + " foi configurada em 9600.");
					try {
						output = port.getOutputStream();
						input = port.getInputStream();
						System.out.println(" As variáveis de entrada e saida foram criadas.");
						initialization =true;
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("ERRO - A variável de saida não pode ser criada");
					}
				} catch (UnsupportedCommOperationException e) {
			    	e.printStackTrace();
					System.out.println("ERRO - A Porta "+  comPort + " não pode ser configurada.");
				}
			} catch (PortInUseException e) {
				e.printStackTrace();
				System.out.println("A Porta " +comPort+ " está sendo utilizada por outro aplicativo.");
			}
		} catch (NoSuchPortException e) {
			e.printStackTrace();
			System.out.println("Porta "+  comPort + " não foi encontrada.");
		}
}

	@Override
	public void serialEvent(SerialPortEvent ev){       
        StringBuffer buffer = new StringBuffer();
        int newData = 0;
        switch (ev.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
            case SerialPortEvent.DATA_AVAILABLE:
            //Novo algoritmo de leitura.

                while(newData != -1){

                    try{
                        newData = input.read();
                        if(newData == -1){
                            break;
                        }

                        if('\r' == (char)newData){
                        	
                     //  	bufferLeitura.delete(0, bufferLeitura.length());	
                      //  bufferLeitura.append('\n');

                        }else{

                            buffer.append((char)newData);

                        }

                    }catch(IOException ioe){

                        System.out.println("Erro de leitura serial: " + ioe);

                    }

                }

                System.out.println(new String(buffer));
                parsingMSG(new String(buffer));
                break;

        }

}
    



	@Override
	public void run(){
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Erro de Thred: " + e);
        }

}    





}