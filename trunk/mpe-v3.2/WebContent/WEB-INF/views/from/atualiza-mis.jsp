<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head><title>Atualiza MIS</title></head>
<body>

<h2>Atualiza MIS</h2><br>

	<form action="atualizaMis" method="POST">
Usuário que deseja atualizar:	<input type="text" name="usuario.user" /><br>

<br>
Localizacão: <input type="text" name="localizacao" /><br>
Gateway: <input type="text" name="gateway" /><br>
				<input type="submit" value="Enviar" />
</form>

</body></html>


