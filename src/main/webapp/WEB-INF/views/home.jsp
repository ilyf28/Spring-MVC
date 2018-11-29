<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
	<script type="text/javascript">
		function logout() {
			document.getElementById("logoutForm").submit();
		}
	</script>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<c:if test="${not empty username}">
<p>
	<span style="color:gray;">
		${username} 님 반갑습니다. <a href="javascript:logout();">로그아웃</a>
	</span>
</p>
<form name="logoutForm" id="logoutForm" action="/j_spring_security_logout" method="POST">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</c:if>
</body>
</html>