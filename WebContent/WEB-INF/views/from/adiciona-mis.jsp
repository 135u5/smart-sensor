<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head><title>Adciona Mis:</title></head>
<body>

<h2>Adciona Mis:</h2><br>

<form action="adicionaMis" method="POST">
Usu�rio:	<input type="text" name="usuario.user" /><br>
Localizac�o: <input type="text" name="localizacao" /><br>
Gateway: <input type="text" name="gateway" /><br>

		<input type="submit" value="Enviar" />
</form>
</body></html>


