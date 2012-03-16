package driver;

import communication.Manager;


/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor Site do projeto:
 * http://labnet.nce.ufrj.br/cia2/
 * 
 * @version 1.0 01 Jan 2012
 * @author Renato Cesar Gil Azevedo
 **/

public class MsgHttp {
	private final short meuHost = 1; // E' o mesmo no tinyos ou arduino

	private short dhost;
	private short shost;
	private short codigo;
	private short metodo;
	private short path;
	private short dados;
	private short error;
	Manager manager = new Manager();
	
	public short getMeuhost() {
		return meuHost;
	}

	public MsgHttp criaHttp(int dhost, int codigo, int metodo, int path,
			int dados, int error) {
		
		this.setDhost((short) dhost);
		this.shost = meuHost;
		this.codigo = (short) codigo;
		this.metodo = (short) metodo;
		this.path = (short) path;
		this.dados = (short) dados;
		this.error = (short) error;
		return this;
	}

	public MsgHttp criaHttp(int dhost, int codigo, char metodo, int path,
			int dados, int error) {
		this.setDhost((short) dhost);
		this.shost = meuHost;
		this.codigo = (short) codigo;
		this.metodo = (short) 'P';
		this.path = (short) path;
		this.dados = (short) dados;
		this.error = (short) error;
		return this;
	}

	public MsgHttp criaHttp(int dhost, int codigo) {
		this.setDhost((short) dhost);
		this.shost = meuHost;
		this.codigo = (short) codigo;
		this.metodo = (short) 'P';
		this.path = 0;
		this.dados = 0;
		this.error = 0;
		return this;
	}

	public MsgHttp recebeHttpDoArduino(String msg) {
		
		this.verificaMensagemHttp(this.converteStringParaHttp(msg));
		
		return this;

	}

	public MsgHttp verificaMensagemHttp(MsgHttp http) {
		// Verifica se a mensagem e' para ele.
		
		if (http.dhost == meuHost) {
			http = manager.ArduinoMsgReceived(http);
			return http;
		} else {
			System.out.println(8);
			http.dhost = 0;
		}
		System.out.println(9);
		return http;
	}

	public short getMetodo() {
		return metodo;
	}

	public short getPath() {
		return path;
	}

	public short getDados() {
		return dados;
	}

	public short getDhost() {
		return dhost;
	}

	public void setDhost(short dhost) {
		this.dhost = dhost;
	}

	public short getShost() {
		return shost;
	}

	public short getCodigo() {
		return codigo;
	}

	public short getError() {
		return error;
	}

	public String converteHttpParaString(MsgHttp http) {

		String msg = http.getDhost() + "#" + http.shost + "#" + http.codigo + "#"
				+ http.metodo + "#" + http.path + "#" + http.dados + "#"
				+ http.error + "#";

		return msg;

	}

	public MsgHttp converteStringParaHttp(String buffer) {

		String vetorPacoteHttp[] = buffer.split("#");

		this.setDhost(Short.parseShort(vetorPacoteHttp[0]));
		this.shost = Short.parseShort(vetorPacoteHttp[1]);
		this.codigo = Short.parseShort(vetorPacoteHttp[2]);
		this.metodo = Short.parseShort(vetorPacoteHttp[3]);
		this.path = Short.parseShort(vetorPacoteHttp[4]);
		this.dados = Short.parseShort(vetorPacoteHttp[5]);
		this.error = Short.parseShort(vetorPacoteHttp[6]);

		return this;
	}

	@Override
	public String toString() {
		return "MsgArduino [dhost=" + getDhost() + ", shost=" + shost + ", codigo="
				+ codigo + ", metodo=" + metodo + ", path=" + path + ", dados="
				+ dados + ", error=" + error + "]";
	}

}
