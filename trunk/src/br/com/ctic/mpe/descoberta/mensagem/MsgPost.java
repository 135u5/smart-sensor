/**
 * Projeto - CIA2 - Meta WoT - Infraestrutura SmartSensor
 * Site do projeto: http://labnet.nce.ufrj.br/cia2/
 * Classe de envio de mensagens post.
 *
 * @version 1.0 29 Jul 2012
 * @author  Renato Cesar Gil Azevedo
 **/

package br.com.ctic.mpe.descoberta.mensagem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;

import br.com.ctic.mpe.dados.modelo.Mis;
import br.com.ctic.mpe.dados.modelo.No;
import br.com.ctic.mpe.dados.modelo.Usuario;

public class MsgPost {

	/* Envia mensagem de autenticação com o MPE e atualiza o ultimo acesso */
	public static void EnviaAuth(Usuario usuario, String link)
			throws IOException {

		String data = URLEncoder.encode("usuario.user", "UTF-8") + "="
				+ URLEncoder.encode(usuario.getUser(), "UTF-8");

		data += "&" + URLEncoder.encode("usuario.senha", "UTF-8") + "="
				+ URLEncoder.encode(usuario.getSenha(), "UTF-8");

		System.out.println(Calendar.getInstance().getTime()
				+ " MsgPost: Autenticação enviada com usuário: "
				+ usuario.getUser() + ".");

		Envia(data, link, false);

	}

	/*
	 * Envia mensagem de autenticação com o MPE e atualiza ou adiciona um novo
	 * MIS
	 */
	public static void EnviaMis(Mis mis, String link) throws IOException {

		String data = URLEncoder.encode("usuario.user", "UTF-8") + "="
				+ URLEncoder.encode(mis.getUsuario().getUser(), "UTF-8");

		data += "&" + URLEncoder.encode("usuario.senha", "UTF-8") + "="
				+ URLEncoder.encode(mis.getUsuario().getSenha(), "UTF-8");

		data += "&" + URLEncoder.encode("localizacao", "UTF-8") + "="
				+ URLEncoder.encode(mis.getLocalizacao(), "UTF-8");

		data += "&" + URLEncoder.encode("gateway", "UTF-8") + "="
				+ URLEncoder.encode(mis.getGateway(), "UTF-8");

		System.out.println(Calendar.getInstance().getTime()
				+ " MsgPost: Mis enviado com usuário: "
				+ mis.getUsuario().getUser() + ".");

		Envia(data, link, false);
	}

	/* Envia mensagem de autenticação com o MPE e adiciona um novo Nó */
	public static void EnviaNo(No no, String link) throws IOException {

		String data = URLEncoder.encode("mis.usuario.user", "UTF-8")
				+ "="
				+ URLEncoder
						.encode(no.getMis().getUsuario().getUser(), "UTF-8");

		data += "&"
				+ URLEncoder.encode("mis.usuario.senha", "UTF-8")
				+ "="
				+ URLEncoder.encode(no.getMis().getUsuario().getSenha(),
						"UTF-8");

		data += "&" + URLEncoder.encode("mis.gateway", "UTF-8") + "="
				+ URLEncoder.encode(no.getMis().getGateway(), "UTF-8");

		data += "&" + URLEncoder.encode("id", "UTF-8") + "="
				+ URLEncoder.encode(Integer.toString(no.getId()), "UTF-8");

		data += "&" + URLEncoder.encode("path", "UTF-8") + "="
				+ URLEncoder.encode(no.getPath(), "UTF-8");

		data += "&" + URLEncoder.encode("plataforma", "UTF-8") + "="
				+ URLEncoder.encode(no.getPlataforma(), "UTF-8");

		System.out.println(Calendar.getInstance().getTime()
				+ " MsgPost: Nó enviado com usuário: "
				+ no.getMis().getUsuario().getUser() + ".");

		Envia(data, link, false);
	}

	/* Envia mensagem de autenticação com o MPE e remove um Nó */
	public static void ExcuiNo(No no, String link) throws IOException {

		String data = URLEncoder.encode("mis.usuario.user", "UTF-8")
				+ "="
				+ URLEncoder
						.encode(no.getMis().getUsuario().getUser(), "UTF-8");

		data += "&"
				+ URLEncoder.encode("mis.usuario.senha", "UTF-8")
				+ "="
				+ URLEncoder.encode(no.getMis().getUsuario().getSenha(),
						"UTF-8");

		data += "&" + URLEncoder.encode("mis.gateway", "UTF-8") + "="
				+ URLEncoder.encode(no.getMis().getGateway(), "UTF-8");

		data += "&" + URLEncoder.encode("id", "UTF-8") + "="
				+ URLEncoder.encode(Integer.toString(no.getId()), "UTF-8");

		System.out.println(Calendar.getInstance().getTime()
				+ " MsgPost: Pedido de exclusão de Nó com usuário: "
				+ no.getMis().getUsuario().getUser() + ".");

		Envia(data, link, false);
	}

	/* Envia mensagem e habiltia ou não o modo de teste de resposta */
	public static void Envia(String data, String link, Boolean Teste)
			throws IOException {
		// Send data
		URL url = new URL(link);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();

		if (Teste) {
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				// while ((rd.readLine()) != null) {
				// Process line...
				System.out.println(line);
			}
			rd.close();
		} else {
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			rd.close();
		}
		wr.close();

	}

}
