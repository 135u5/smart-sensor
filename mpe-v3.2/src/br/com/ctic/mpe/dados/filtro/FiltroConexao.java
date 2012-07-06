/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Filtro de conexões ao banco
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.dados.filtro;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import br.com.ctic.mpe.dados.dao.ConnectionFactory;

@WebFilter(filterName = "FiltroConexao", value = "/*")
public class FiltroConexao implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			Connection connection = new ConnectionFactory().getConnection();
			// pendurando a connection na requisição
			request.setAttribute("conexao", connection);
			chain.doFilter(request, response);
			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

}
