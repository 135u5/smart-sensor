package driver;

/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Driver de Teste Arduino (Renato Azevedo)
 *
 * @version 1.2 01 Fev 2012
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

import listener.ContextListener;

public class Arduino implements SerialPortEventListener, Runnable {

	private CommPortIdentifier cp = null;
	private SerialPort port = null;
	private OutputStream output;

	private InputStream input;
	private Thread readingThread;

	private boolean listening;
	private boolean initialization = false;

	private static String comPort = "/dev/ttyACM0";
	private int baundRate = 19200;

//	static Arduino driverArduino = new Arduino(comPort, 19200);

	/**
	 * Atribui o valor para a porta de comunicação.
	 */
	public void SetCOM(String con) {
		comPort = con;
	}

	/**
	 * Retorna a porta de comunicação configurada.
	 */
	public String GetCOM() {
		return comPort;
	}

	/**
	 * Atribui o valor para a baund rate.
	 */
	public void SetBaundRate(int br) {
		baundRate = br;
	}

	/**
	 * Retorna o valor para a baund rate.
	 */
	public int GetBaundRate() {
		return baundRate;
	}

	/**
	 * Construtor definindo a porta de comunicacao
	 */
	public Arduino(String con, int br) {
		this.SetCOM(con);
		this.SetBaundRate(br);
		this.startCommunication();
	}

	/**
	 * Construtor sem definir a porta de comunicação.
	 */
	public Arduino() {
	}

	public void stopListening() {
		listening = !listening;
		port.notifyOnDataAvailable(listening);
	}

	public void closeCom() {
		try {
			port.close();
		} catch (Exception e) {
			System.out.println("Erro fechando porta: " + e);
			System.exit(0);
		}
	}

	public void startListening() {
		listening = true;
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
		// porta.notifyOnDataAvailable(true);
		port.notifyOnDataAvailable(listening);
		try {

			readingThread = new Thread(this);
			readingThread.start();

			run();

		} catch (Exception e) {
			System.out.println("Erro de Thred: " + e);
		}
	}

	/**
	 * Envia a menssagem para a porta especificada.
	 */
	public void enviaHttp(String menssagem) {

		try {
			output.write(menssagem.getBytes());
			System.out.println("A messagem '" + menssagem
					+ "' foi enviada com sucesso.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out
					.println("ERRO - Não foi possivel enviar a menssagem. A Inicialização é "
							+ initialization);
		}

	}

	/**
	 * Esta função é responsável por estabilizar a conecção com a porta
	 * especificada.
	 */
	@SuppressWarnings("static-access")
	public void startCommunication() {
		System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0"); //add this line ttyACMx [0,1,2,..]
		try {
			cp = CommPortIdentifier.getPortIdentifier(comPort);
			System.out.println(" A Porta " + comPort + " foi encontrada.");
			try {
				port = (SerialPort) cp.open("ArduinoDrive", 1000);
				System.out.println(" A Porta " + comPort + " foi aberta.");
				try {
					port.setSerialPortParams(baundRate, port.DATABITS_8,
							port.STOPBITS_2, port.PARITY_NONE);
					System.out.println(" A Porta " + comPort
							+ " foi configurada em " + baundRate + ".");
					try {
						output = port.getOutputStream();
						input = port.getInputStream();
						System.out
								.println(" As variáveis de entrada e saida foram criadas.");
						initialization = true;
					} catch (IOException e) {
						e.printStackTrace();
						System.out
								.println("ERRO - A variável de saida não pode ser criada");
					}
				} catch (UnsupportedCommOperationException e) {
					e.printStackTrace();
					System.out.println("ERRO - A Porta " + comPort
							+ " não pode ser configurada.");
				}
			} catch (PortInUseException e) {
				e.printStackTrace();
				System.out.println("A Porta " + comPort
						+ " está sendo utilizada por outro aplicativo.");
			}
		} catch (NoSuchPortException e) {
			e.printStackTrace();
			System.out.println("Porta " + comPort + " não foi encontrada.");
		}
	}

	@Override
	public void serialEvent(SerialPortEvent ev) {
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
			// Novo algoritmo de leitura.

			while (newData != -1) {

				try {
					newData = input.read();
					if (newData == -1) {
						break;
					}

					if ('\n' == (char) newData || '\r' == (char) newData) {
						
						break;
					} else {

						buffer.append((char) newData);

					}

				} catch (IOException ioe) {

					System.out.println("Erro de leitura serial: " + ioe);

				}

			}
			
			
			
			String pacoteHttp = new String(buffer);
			
			//System.out.println(pacoteHttp);

			MsgHttp http = new MsgHttp();
			
			
			http.recebeHttpDoArduino(pacoteHttp);
			//System.out.println(http);

			if (http.getDhost() != 0) {
				if (http.getCodigo() == 2000 || http.getCodigo() == 405) {

					ContextListener.listenerArduino.enviaHttp(http.converteHttpParaString(http));

					// Apos ser reconhecido na rede, o gateway pede um
					// sensoriamento após um intervalo 1000 milissegundos.
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					ContextListener.listenerArduino.enviaHttp(http.converteHttpParaString(http
							.criaHttp(http.getDhost(), 200, 71, 4, 0, 0)));
				}
			} else {
				//System.out.println(http);

			}

			break;

		}

	}

	@Override
	public void run() {
		try {
			Thread.sleep(1500);
		} catch (Exception e) {
			System.out.println("Erro de Thred: " + e);
		}

	}

//	public static void main(String[] args) {
//
//		driverArduino.startListening();
//
//	}

}