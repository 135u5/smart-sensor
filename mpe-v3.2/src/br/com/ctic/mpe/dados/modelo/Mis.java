/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe Modelo MIS
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.dados.modelo;

import java.sql.Timestamp;

public class Mis {

	private Usuario usuario;
	private String localizacao;
	private String gateway;
	private Timestamp ultimoAcesso;
	private Timestamp primeiroAcesso;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public Timestamp getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Timestamp timestamp) {
		this.ultimoAcesso = timestamp;
	}

	public Timestamp getPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(Timestamp primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}

}