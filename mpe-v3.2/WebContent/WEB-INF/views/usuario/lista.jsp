<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Lista usuários!</title>
</head>
<body>


<h2>Lista usuários!</h2>

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



</body>
</html>