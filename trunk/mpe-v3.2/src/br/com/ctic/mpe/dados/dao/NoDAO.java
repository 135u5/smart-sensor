/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe que implementa o DAO do NO
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.dados.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.ctic.mpe.dados.modelo.Mis;
import br.com.ctic.mpe.dados.modelo.No;

public class NoDAO {
	private Connection connection;

	public NoDAO() {
		try {

			connection = new ConnectionFactory().getConnection();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public NoDAO(Connection connection) {
		this.connection = connection;
	}

	// Verifica existência do Nó.
	public boolean existeNo(No no) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from no where gateway = ? and id= ?");
			stmt.setString(1, no.getMis().getGateway());
			stmt.setInt(2, no.getId());
			ResultSet rs = stmt.executeQuery();

			boolean encontrado = rs.next();
			rs.close();
			stmt.close();

			return encontrado;
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível verificar a existencia do nó "
					+ no.getId() + ".");
			throw new RuntimeException(e);
		}
	}

	// Adiciona Nó.
	public void adiciona(No no) {
		try {

			String sql = "insert into no (gateway, id, path, plataforma) values (?,?,?,?)";
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, no.getMis().getGateway());
			stmt.setInt(2, no.getId());
			stmt.setString(3, no.getPath());
			stmt.setString(4, no.getPlataforma());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível adicionar nó " + no.getId() + ".");
		}
	}

	// Lista os Nós.
	public List<No> getLista() {
		try {
			List<No> no = new ArrayList<No>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from no");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				No no1 = new No();
				Mis mis = new Mis();
				mis.setGateway(rs.getString("gateway"));
				no1.setMis(mis);
				// no1.getUsuario().setUser(rs.getString("gateway"));
				no1.setId(rs.getInt("id"));
				no1.setPath(rs.getString("path"));
				no1.setPlataforma(rs.getString("plataforma"));
				no.add(no1);
			}

			rs.close();
			stmt.close();

			return no;
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível listar os nós.");
			throw new RuntimeException(e);
		}
	}

	// Exclui Nó.
	public void exclui(No no) {
		String sql = "delete from no where gateway=? and id=?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setString(1, no.getMis().getGateway());
			stmt.setInt(2, no.getId());
			stmt.execute();
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível excluir nó " + no.getId() + ".");
		}
	}

	// Exclui todos os nós através de um gateway.
	public void excluiGateway(String gateway) {
		String sql = "delete from no where gateway=?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setString(1, gateway);
			stmt.execute();
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível excluir os nós do gateway " + gateway
					+ ".");
		}
	}

	// Atualiza Nó.
	public void atualiza(No no) {
		String sql = "update no set path = ?, plataforma = ? where gateway = ? and id = ?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setString(1, no.getPath());
			stmt.setString(2, no.getPlataforma());
			stmt.setString(3, no.getMis().getGateway());
			stmt.setInt(4, no.getId());

			stmt.execute();

		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " Não foi possível atualizar nó " + no.getId() + ".");

		}

	}
}