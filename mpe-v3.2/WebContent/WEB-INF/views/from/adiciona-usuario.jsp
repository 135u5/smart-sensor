<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head><title>Adciona Usu�rio:</title></head>
<body>

<h2>Adciona Usu�rio:</h2><br>
<form:errors path="usuario.user" />

<form action="adicionaUsuario" method="POST">
Usuario:	<input type="text" name="user" /><br>
Senha: <input type="text" name="senha" /><br>
		<input type="submit" value="Enviar" />
</form>
</body></html>


