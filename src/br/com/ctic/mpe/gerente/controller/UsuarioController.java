/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Controlador do MVC para usuário
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.gerente.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ctic.mpe.dados.dao.UsuarioDAO;
import br.com.ctic.mpe.dados.modelo.Usuario;

@Controller
public class UsuarioController {

	@RequestMapping("adicionarUsuario")
	public String form() {
		return "from/adiciona-usuario";
	}

	@RequestMapping("adicionaUsuario")
	public String adicionaUsuario(@Valid Usuario usuario, BindingResult result) {

		if (result.hasFieldErrors("user")) {
			return "error/usuario-invalido";
		}
		if (result.hasFieldErrors("senha")) {
			return "error/senha-invalida";
		}

		UsuarioDAO dao = new UsuarioDAO();
		dao.adiciona(usuario);
		return "usuario/adicionado";
	}

	@RequestMapping(value = "listaUsuarios")
	public String listaUsuarios() {
		return "usuario/lista";
	}

	@RequestMapping("excluirUsuario")
	public String excluirUsuario() {
		return "from/exclui-usuario";
	}

	@RequestMapping("excluiUsuario")
	public String excluiUsuario(Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAO();
		dao.exclui(usuario);
		return "usuario/excluido";
	}

	@RequestMapping("alterarSenha")
	public String alterarSenha() {
		return "from/altera-senha-usuario";
	}

	@RequestMapping("alteraSenha")
	public String alteraSenha(Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAO();
		dao.atualiza(usuario);
		return "usuario/atualizado";
	}

}
