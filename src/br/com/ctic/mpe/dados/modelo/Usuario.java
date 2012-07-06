/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe Modelo Usuario
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.dados.modelo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.ctic.mpe.controleDeAcesso.seguranca.ControleDeIntegridade;

public class Usuario {

	@NotNull
	@Size(min = 5)
	private String user;

	@NotNull
	@Size(min = 5)
	private String senha;

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = ControleDeIntegridade.md5(senha);
	}
}
