<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Lista Nó!</title>
</head>
<body>


<h2>Lista Nó!</h2>

<!--  Cria o DAO -->
<jsp:useBean id="dao" class="br.com.ctic.mpe.dados.dao.NoDAO"></jsp:useBean>
<table>
	<tr>
		<td>Gateway</td>
		<td>ID</td>
		<td>Serviço</td>
		<td>Plataforma</td>
		</tr>
	<!--  Percorre  contatos montando as linhas da tabela -->
	<c:forEach var="no" items="${dao.lista}">
		<tr>
			<td> ${no.mis.gateway}</td>
			<td> ${no.id}</td>
			<td> ${no.path}</td>
			<td> ${no.plataforma}</td>

		</tr>


	</c:forEach>

</table>



</body>
</html>