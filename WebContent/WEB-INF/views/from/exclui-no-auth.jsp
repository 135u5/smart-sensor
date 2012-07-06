<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head><title>Exclui Nó:</title></head>
<body>

<h2>Exclui Nó</h2><br>


<form action="excluiNoAuth" method="POST">

User: <input type="text" name="mis.usuario.user" /><br>
Senha:	<input type="text" name="mis.usuario.senha" /><br>
Gateway:	<input type="text" name="mis.gateway" /><br>
ID:	<input type="text" name="id" /><br>

		<input type="submit" value="Enviar" />
</form>

<br>


</body></html>


