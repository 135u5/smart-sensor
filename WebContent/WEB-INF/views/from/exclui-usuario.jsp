<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head><title>Exclui usuário:</title></head>
<body>

<h2>Exclui usuário</h2><br>


<form action="excluiUsuario" method="POST">
Usuário:	<input type="text" name="user" /><br>

		<input type="submit" value="Enviar" />
</form>

<br>

<h2>Lista de usuários!</h2>

<!--  Cria o DAO -->
<jsp:useBean id="dao" class="br.com.ctic.mpe.dados.dao.UsuarioDAO"></jsp:useBean>
<table>
	<tr>
		<td>User</td>
		<td>Senha</td>
	</tr>
	<!--  Percorre  contatos montando as linhas da tabela -->
	<c:forEach var="usuario" items="${dao.lista}">
		<tr>
			<td> ${usuario.user}</td>
			<td> ${usuario.senha}</td>


		</tr>


	</c:forEach>

</table>





</body></html>


