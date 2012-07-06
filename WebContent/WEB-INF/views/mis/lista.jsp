<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Lista MIS!</title>
</head>
<body>


<h2>Lista MIS!</h2>

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



</body>
</html>