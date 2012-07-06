<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head><title>Adciona MIS com autenticação:</title></head>
<body>

<h2>Adciona MIS com autenticação:</h2><br>

<form action="adicionaMisAuth" method="POST">
Usuário:	<input type="text" name="usuario.user" /><br>
Senha:		<input type="password" name="usuario.senha" /><br>
Localizacão: <input type="text" name="localizacao" /><br>
Gateway: <input type="text" name="gateway" /><br>

		<input type="submit" value="Enviar" />
</form>
</body></html>


