<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head><title>Exclui MIS:</title></head>
<body>

<h2>Exclui MIS</h2><br>


<form action="excluiMis" method="POST">
Mis com o Usuário:	<input type="text" name="usuario.user" /><br>

		<input type="submit" value="Enviar" />
</form>

<br>

<h2>Lista Mis!</h2>

<!--  Cria o DAO -->
<jsp:useBean id="dao" class="br.com.ctic.mpe.dados.dao.MisDAO"></jsp:useBean>
<table>
	<tr>
		<td>User</td>
		<td>Localização</td>
		<td>Gateway</td>
		<td>Ultimo Acesso</td>
		<td>Primeiro Acesso</td>
	</tr>
	<!--  Percorre  contatos montando as linhas da tabela -->
	<c:forEach var="mis" items="${dao.lista}">
		<tr>
			<td> ${mis.usuario.user}</td>
			<td> ${mis.localizacao}</td>
			<td> ${mis.gateway}</td>
			<td> ${mis.ultimoAcesso}</td>
			<td> ${mis.primeiroAcesso}</td>
		</tr>


	</c:forEach>

</table>




</body></html>


