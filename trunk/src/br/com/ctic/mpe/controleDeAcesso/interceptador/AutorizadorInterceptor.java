/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe que implementa o interceptador para o login.
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.controleDeAcesso.interceptador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AutorizadorInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object controller) throws Exception {

		String uri = request.getRequestURI();
		/* Adiciona os destinos que podem ser acessados sem estar logado */
		if (uri.endsWith("loginForm") || uri.endsWith("efetuaLogin")
				|| uri.endsWith("adicionaNoAuth")
				|| uri.endsWith("adicionaMisAuth")
				|| uri.endsWith("excluiNoAuth")
				|| uri.endsWith("atualizaDescoberta")) {
			return true;
		}

		if (request.getSession().getAttribute("usuarioLogado") != null) {
			return true;
		}

		response.sendRedirect("loginForm");
		return false;
	}
}
