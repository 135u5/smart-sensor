<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<head><title>Adciona MIS com autentica��o:</title></head>
<body>

<h2>Adciona MIS com autentica��o:</h2><br>

<form action="adicionaMisAuth" method="POST">
Usu�rio:	<input type="text" name="usuario.user" /><br>
Senha:		<input type="password" name="usuario.senha" /><br>
Localizac�o: <input type="text" name="localizacao" /><br>
Gateway: <input type="text" name="gateway" /><br>

		<input type="submit" value="Enviar" />
</form>
</body></html>


