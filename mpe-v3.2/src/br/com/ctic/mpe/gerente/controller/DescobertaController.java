/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Controlador do MVC para descoberta
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
public class DescobertaController {

	@RequestMapping("atualizarDescoberta")
	public String atualizarDescoberta() {
		return "from/atualiza-descoberta";
	}

	@RequestMapping("atualizaDescoberta")
	public String atualizaDescoberta(Mis mis) {

		if (new UsuarioDAO().verificaUsuarioSenha(mis.getUsuario())) {

			MisDAO dao = new MisDAO();

			if (dao.existeMis(mis)) {
				System.out.println(Calendar.getInstance().getTime()
						+ " Descoberta: Mis existe no banco");

				dao.atualizaUltimoAcesso(mis);

				return "mis/atualizado";

			} else {

				System.out.println(Calendar.getInstance().getTime()
						+ " Descoberta: Mis não existe no banco");

				dao.adiciona(mis);
				return "error/mis-invalido";

			}

		} else {

			return "error/usuario-invalido";

		}
	}
}
