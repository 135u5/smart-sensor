/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe que implementa o DAO do MIS
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.dados.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.ctic.mpe.dados.modelo.Mis;
import br.com.ctic.mpe.dados.modelo.Usuario;

public class MisDAO {
	private Connection connection;

	public MisDAO() {
		try {

			connection = new ConnectionFactory().getConnection();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public MisDAO(Connection connection) {
		this.connection = connection;
	}

	// Verifica existência do MIS.
	public boolean existeMis(Mis mis) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from mis where user = ?");
			stmt.setString(1, mis.getUsuario().getUser());
			ResultSet rs = stmt.executeQuery();

			boolean encontrado = rs.next();
			rs.close();
			stmt.close();

			return encontrado;

		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível verificar a existência de "
					+ mis.getUsuario().getUser() + ".");
			throw new RuntimeException(e);
		}
	}

	// Adiciona MIS.
	public void adiciona(Mis mis) {
		try {

			String sql = "insert into mis (user, localizacao, gateway, ultimoacesso, primeiroacesso) values (?,?,?,?,?)";
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, mis.getUsuario().getUser());
			stmt.setString(2, mis.getLocalizacao());
			stmt.setString(3, mis.getGateway());

			stmt.setTimestamp(4, new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			stmt.setTimestamp(5, new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível adicionar "
					+ mis.getUsuario().getUser() + ".");

		}
	}

	// Lista MIS.
	public List<Mis> getLista() {
		try {
			List<Mis> mis = new ArrayList<Mis>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from mis");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Mis mis1 = new Mis();
				Usuario usuario = new Usuario();
				usuario.setUser(rs.getString("user"));
				usuario.setSenha(rs.getString("user"));
				mis1.setUsuario(usuario);
				// mis1.getUsuario().setUser(rs.getString("user"));
				mis1.setLocalizacao(rs.getString("localizacao"));
				mis1.setGateway(rs.getString("gateway"));

				mis1.setUltimoAcesso(rs.getTimestamp("ultimoacesso"));

				mis1.setPrimeiroAcesso(rs.getTimestamp("primeiroacesso"));

				mis.add(mis1);
			}

			rs.close();
			stmt.close();

			return mis;
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível listar usuários");
			throw new RuntimeException(e);
		}

	}

	// Exclui MIS.
	public void exclui(Mis mis) {
		String sql = "delete from mis where user=?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setString(1, mis.getUsuario().getUser());
			stmt.execute();
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível excluir usuário "
					+ mis.getUsuario().getUser() + ".");
		}
	}

	// Atualiza MIS.
	public void atualiza(Mis mis) {
		String sql = "update mis set localizacao = ?, gateway = ?, ultimoacesso = ? where user = ?";

		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setString(1, mis.getLocalizacao());
			stmt.setString(2, mis.getGateway());
			stmt.setTimestamp(3, new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			stmt.setString(4, mis.getUsuario().getUser());
			stmt.execute();

		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível atualizar "
					+ mis.getUsuario().getUser() + ".");
			// TODO Auto-generated catch block

		}

	}

	// Atualiza ultimo acesso do MIS.
	public void atualizaUltimoAcesso(Mis mis) {

		String sql = "update mis set ultimoacesso = ? where user = ?";

		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setTimestamp(1, new Timestamp(Calendar.getInstance()
					.getTimeInMillis()));
			stmt.setString(2, mis.getUsuario().getUser());
			stmt.execute();

		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível atualizar ultimo acesso de "
					+ mis.getUsuario().getUser() + ".");
			// TODO Auto-generated catch block

		}

	}
}