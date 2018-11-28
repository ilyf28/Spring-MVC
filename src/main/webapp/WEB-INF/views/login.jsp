<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginBtn").click(function() {
				if ($("#loginId").val().trim() == "") {
					alert("ID를 입력해주세요.");
					$("#loginId").focus();
					return false;
				} else if ($("#loginPw").val().trim() == "") {
					alert("Password를 입력해주세요.");
					$("#loginPw").focus();
					return false;
				} else {
					$("#loginForm").submit();
				}
			});
		});
	</script>
</head>
<body>
	<div>
		<h1>로그인 화면</h1>
		<form name="loginForm" id="loginForm" action="/j_spring_security_check" method="POST">
			<table>
				<tr>
					<td>ID</td>
					<td>
						<input type="text" name="loginId" id="loginId" value="" maxlength="20" />
					</td>
				</tr>
				<tr>
					<td>Password</td>
					<td>
						<input type="password" name="loginPw" id="loginPw" value="" maxlength="20" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" id="loginBtn" value="확인" />
					</td>
				</tr>
				<c:if test="${not empty param.fail}">
				<tr>
					<td colspan="2">
						<font color="red">
							<span>Your login attempt was not successful, try again.</span>
							<span>Reason: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</span>
						</font>
					</td>
				</tr>
				<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" />
				</c:if>
			</table>
		</form>
	</div>
</body>
</html>