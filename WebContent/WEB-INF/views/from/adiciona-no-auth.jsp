<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head><title>Adciona Usuário:</title></head>
<body>

<h2>Adciona Usuário:</h2><br>

<form action="adicionaNoAuth" method="POST">

User: <input type="text" name="mis.usuario.user" /><br>
Senha:	<input type="text" name="mis.usuario.senha" /><br>

Gateway: <input type="text" name="mis.gateway" /><br>
Nó:	<input type="text" name="id" /><br>
Serviço: <input type="text" name="path" /><br>
Plataforma: <input type="text" name="plataforma" /><br>
		<input type="submit" value="Enviar" />
</form>
</body></html>


