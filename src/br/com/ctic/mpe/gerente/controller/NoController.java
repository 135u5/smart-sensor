/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Controlador do MVC para Nó
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.gerente.controller;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ctic.mpe.dados.dao.NoDAO;
import br.com.ctic.mpe.dados.dao.UsuarioDAO;
import br.com.ctic.mpe.dados.modelo.No;

@Controller
public class NoController {

	@RequestMapping("adicionarNo")
	public String adicionarNo() {
		return "from/adiciona-no";
	}

	@RequestMapping("adicionaNo")
	public String adicionaNo(No no) {

		NoDAO dao = new NoDAO();
		dao.adiciona(no);
		return "no/adicionado";
	}

	@RequestMapping("adicionarNoAuth")
	public String adicionarNoAuth() {
		return "from/adiciona-no-auth";
	}

	@RequestMapping("adicionaNoAuth")
	public String adicionaNoAuth(No no) {
		if (new UsuarioDAO().verificaUsuarioSenha(no.getMis().getUsuario())) {

			NoDAO dao = new NoDAO();

			if (dao.existeNo(no)) {
				System.out.println(Calendar.getInstance().getTime()
						+ " NO: Nó existe no banco");

				dao.atualiza(no);

				return "no/atualizado";

			} else {

				System.out.println(Calendar.getInstance().getTime()
						+ " NO: Nó não existe no banco");

				dao.adiciona(no);
				return "no/adicionado";

			}

		} else {

			System.out.println(Calendar.getInstance().getTime()
					+ " NO: Acesso negado");

			return "error/usuario-invalido";

		}
	}

	@RequestMapping(value = "listaNo")
	public String listaNos() {
		return "no/lista";
	}

	@RequestMapping("excluirNo")
	public String excluirNo() {
		return "from/exclui-no";
	}

	@RequestMapping("excluiNo")
	public String excluiNo(No no) {
		NoDAO dao = new NoDAO();
		dao.exclui(no);
		return "no/excluido";
	}

	@RequestMapping("excluirNoAuth")
	public String excluirNoAuth() {
		return "from/exclui-no-auth";
	}

	@RequestMapping("excluiNoAuth")
	public String excluiNoAuth(No no) {

		if (new UsuarioDAO().verificaUsuarioSenha(no.getMis().getUsuario())) {

			NoDAO dao = new NoDAO();

			if (dao.existeNo(no)) {
				System.out.println(Calendar.getInstance().getTime()
						+ " NO: Nó existe no banco");

				dao.exclui(no);

				return "no/excluido";

			} else {

				System.out.println(Calendar.getInstance().getTime()
						+ " NO: Nó não existe no banco");

				return "no/no-invalido";

			}

		} else {

			System.out.println(Calendar.getInstance().getTime()
					+ " NO: Acesso negado");

			return "error/usuario-invalido";

		}

	}

	@RequestMapping("atualizarNo")
	public String atualizarNo() {
		return "from/atualiza-no";
	}

	@RequestMapping("atualizaNo")
	public String atualizaNo(No no) {
		NoDAO dao = new NoDAO();
		dao.atualiza(no);
		return "no/atualizado";
	}

}
