<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head><title>Atualiza Nó</title></head>
<body>

<h2>Atualiza Nó</h2><br>

	<form action="atualizaNo" method="POST">
Nó:	<input type="text" name="id" /><br>
Gateway: <input type="text" name="mis.gateway" /><br>

Modificações:<br>
Serviço: <input type="text" name="path" /><br>
Plataforma: <input type="text" name="plataforma" /><br>
				<input type="submit" value="Enviar" />
</form>

</body></html>


