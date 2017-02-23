<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<html>
<head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AMS</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="CSS/index.css">
<script type="text/javascript">
	function clearFun() {
		document.getElementById("mesg").innerHTML = "";
	}
	function disableBackButton() {
		window.history.forward();
	}
	setTimeout("disableBackButton()", 0);
</script>
</head>
<body>

	<noscript>Please enable JavaScript to use this site</noscript>
	<div id="blur-image"></div>
	<div id="main-content">
		<h2>OUTING PASS</h2>
		<div class="login">
			<h1>Login</h1>
			<h3 id="mesg">
				<c:if test="${message != null }">
				${message}
			</c:if>
			</h3>
			<form:form commandName="login" action="home.view" method="post">
				<form:input path="username" name="username" placeholder="User Id"
					required="required" />
				<form:password path="password" name="password"
					placeholder="Password" required="required" />
				<input type="submit" value="Login" class="btn btn-large" />
				<button type="reset" class="btn btn-large reset"
					onclick="clearFun()">Reset</button>
			</form:form>
		</div>
	</div>
</body>
</html>