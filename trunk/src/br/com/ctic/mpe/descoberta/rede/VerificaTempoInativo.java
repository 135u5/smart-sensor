/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe que implementa o Thread de Verificação de tempo do serviço de descoberta
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.descoberta.rede;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import br.com.ctic.mpe.dados.dao.ConnectionFactory;
import br.com.ctic.mpe.dados.dao.MisDAO;
import br.com.ctic.mpe.dados.dao.NoDAO;
import br.com.ctic.mpe.dados.modelo.Mis;
import br.com.ctic.mpe.descoberta.mensagem.MsgPost;

public class VerificaTempoInativo extends Thread implements Runnable {
	private int expira = 200000;
	private int atualiza = 100000;
	private int consulta;

	public VerificaTempoInativo(int consulta) {
		super();
		this.consulta = consulta;
	}

	public float milisegundoParaMinuto(int num) {
		float resultado = ((float) num) / 60000;
		return resultado;
	}

	public void run() {
		System.out.println(Calendar.getInstance().getTime()
				+ "O intervalo para consultas foi configurado em "
				+ milisegundoParaMinuto(consulta) + " minutos.");
		try {
			Connection connection = new ConnectionFactory().getConnection();

			while (true) {
				MisDAO dao = new MisDAO(connection);
				List<Mis> mis1 = dao.getLista();
				for (Mis mis : mis1) {

					if ((Calendar.getInstance().getTimeInMillis() - mis
							.getUltimoAcesso().getTime()) > expira) {

						System.out.println(Calendar.getInstance().getTime()
								+ " Usuario: " + mis.getUsuario().getUser()
								+ " expirou o limite de "
								+ milisegundoParaMinuto(expira) + " minutos");

						NoDAO nodao = new NoDAO();
						nodao.excluiGateway(mis.getGateway());
						dao.exclui(mis);

					} else {

						if ((Calendar.getInstance().getTimeInMillis() - mis
								.getUltimoAcesso().getTime()) > atualiza) {

							System.out.println(Calendar.getInstance().getTime()
									+ " Usuario: " + mis.getUsuario().getUser()
									+ " atualizado a mais de "
									+ milisegundoParaMinuto(atualiza)
									+ " minutos");

							try {
								MsgPost.EnviaAuth(mis.getUsuario(), "http://"
										+ mis.getGateway()
										+ ":8080/mis-teste-v2.0/estadoMis");
								System.out.println(Calendar.getInstance()
										.getTime()
										+ " Envia auth: "
										+ mis.getUsuario().getUser()
										+ " "
										+ mis.getUsuario().getSenha());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {

							System.out.println(Calendar.getInstance().getTime()
									+ " Usuario: " + mis.getUsuario().getUser()
									+ " atualizado a menos de "
									+ milisegundoParaMinuto(atualiza)
									+ " minutos");

						}
					}

				}
				VerificaTempoInativo.sleep(consulta);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
