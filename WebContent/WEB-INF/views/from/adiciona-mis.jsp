<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head><title>Adciona Mis:</title></head>
<body>

<h2>Adciona Mis:</h2><br>

<form action="adicionaMis" method="POST">
Usuário:	<input type="text" name="usuario.user" /><br>
Localizacão: <input type="text" name="localizacao" /><br>
Gateway: <input type="text" name="gateway" /><br>

		<input type="submit" value="Enviar" />
</form>
</body></html>


