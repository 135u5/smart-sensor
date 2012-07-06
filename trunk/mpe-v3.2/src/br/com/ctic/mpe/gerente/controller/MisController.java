/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Controlador do MVC para MIS
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.gerente.controller;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.ctic.mpe.dados.dao.MisDAO;
import br.com.ctic.mpe.dados.dao.UsuarioDAO;
import br.com.ctic.mpe.dados.modelo.Mis;

@Controller
public class MisController {

	@RequestMapping("adicionarMis")
	public String adicionarMis() {
		return "from/adiciona-mis";
	}

	@RequestMapping("adicionaMis")
	public String adicionaMis(Mis mis) {

		MisDAO dao = new MisDAO();
		dao.adiciona(mis);
		return "mis/adicionado";
	}

	@RequestMapping("adicionarMisAuth")
	public String adicionarMisAuth() {

		return "from/adiciona-mis-auth";

	}

	@RequestMapping("adicionaMisAuth")
	public String adicionaMisAuth(Mis mis) {

		if (new UsuarioDAO().verificaUsuarioSenha(mis.getUsuario())) {

			MisDAO dao = new MisDAO();

			if (dao.existeMis(mis)) {
				System.out.println(Calendar.getInstance().getTime()
						+ " MIS: Mis existe no banco");

				dao.atualiza(mis);

				return "mis/atualizado";

			} else {

				System.out.println(Calendar.getInstance().getTime()
						+ " MIS: Mis não existe no banco");

				dao.adiciona(mis);
				return "mis/adicionado";

			}

		} else {

			return "error/mis-invalido";

		}
	}

	@RequestMapping(value = "listaMis")
	public String listaMiss() {
		return "mis/lista";
	}

	@RequestMapping("excluirMis")
	public String excluirMis() {
		return "from/exclui-mis";
	}

	@RequestMapping("excluiMis")
	public String excluiMis(Mis mis) {
		MisDAO dao = new MisDAO();
		dao.exclui(mis);
		return "mis/excluido";
	}

	@RequestMapping("atualizarMis")
	public String atualizarMis() {

		return "from/atualiza-mis";
	}

	@RequestMapping("atualizaMis")
	public String atualizaMis(Mis mis) {
		MisDAO dao = new MisDAO();
		dao.atualiza(mis);
		return "mis/atualizado";
	}

}
