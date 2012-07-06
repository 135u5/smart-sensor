/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe que implementa o DAO do Usuario
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

import br.com.ctic.mpe.dados.modelo.Usuario;

public class UsuarioDAO {
	private Connection connection;

	public UsuarioDAO() {
		try {

			connection = new ConnectionFactory().getConnection();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public UsuarioDAO(Connection connection) {
		this.connection = connection;
	}

	// Verifica exist�ncia do Usuario.
	public boolean existeUsuario(Usuario usuario) {
		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuarios where user = ?");
			stmt.setString(1, usuario.getUser());
			ResultSet rs = stmt.executeQuery();

			boolean encontrado = rs.next();
			rs.close();
			stmt.close();

			return encontrado;
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " N�o foi poss�vel verificar a existencia do usu�rio "
					+ usuario.getUser() + ".");
			throw new RuntimeException(e);
		}
	}

	// Verifica senha do usu�rio.
	public boolean verificaUsuarioSenha(Usuario usuario) {

		if (usuario == null) {
			throw new IllegalArgumentException("Usu�rio nulo");
		}

		try {
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuarios where user = ? and senha = ?");
			stmt.setString(1, usuario.getUser());
			stmt.setString(2, usuario.getSenha());
			ResultSet rs = stmt.executeQuery();

			boolean encontrado = rs.next();
			rs.close();
			stmt.close();

			return encontrado;
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " N�o foi poss�vel verificar a existencia do usu�rio "
					+ usuario.getUser() + " e sua senha.");
			throw new RuntimeException(e);
		}
	}

	// Adiciona usu�rio.
	public void adiciona(Usuario usuario) {
		try {

			String sql = "insert into usuarios (user, senha) values (?,?)";
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, usuario.getUser());
			stmt.setString(2, usuario.getSenha());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " N�o foi poss�vel adicionar usu�rio "
					+ usuario.getUser() + ".");
		}
	}

	// Lista usu�rios.
	public List<Usuario> getLista() {
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from usuarios");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();

				usuario.setUser(rs.getString("user"));
				usuario.setSenha(rs.getString("senha"));

				usuarios.add(usuario);
			}

			rs.close();
			stmt.close();

			return usuarios;
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " N�o foi poss�vel listar usu�rios.");
			throw new RuntimeException(e);
		}
	}

	// Exclui usu�rio.
	public void exclui(Usuario usuario) {
		String sql = "delete from usuarios where user=?";
		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, usuario.getUser());
			stmt.execute();
		} catch (SQLException e) {
			System.out.println(Calendar.getInstance().getTime()
					+ " N�o foi poss�vel excluir usu�rio " + usuario.getUser()
					+ ".");
		}
	}

	// Atualiza usu�rio.
	public void atualiza(Usuario usuario) {
		String sql = "update usuarios set senha = ? where user = ?";

		PreparedStatement stmt;
		try {
			stmt = this.connection.prepareStatement(sql);

			stmt.setString(1, usuario.getSenha());
			stmt.setString(2, usuario.getUser());

			boolean x = stmt.execute();
			System.out.println(x);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(Calendar.getInstance().getTime()
					+ " N�o foi poss�vel atualizar usu�rio "
					+ usuario.getUser() + ".");
		}

	}

}