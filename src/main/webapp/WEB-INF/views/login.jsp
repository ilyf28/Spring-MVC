<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/components/core-min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/components/sha256-min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginBtn").click(function() {
				if ($("#loginId").val().trim() == "") {
					alert("ID를 입력해주세요.");
					$("#loginId").focus();
					return false;
				} else if ($("#loginPassword").val().trim() == "") {
					alert("Password를 입력해주세요.");
					$("#loginPassword").focus();
					return false;
				} else {
					var hash = CryptoJS.SHA256($("#loginPassword").val());
					$("input[name='loginPw']").val(hash);
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
						<input type="password" name="loginPassword" id="loginPassword" value="" maxlength="20" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" id="loginBtn" value="확인" />
					</td>
				</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="hidden" name="loginPw" value=""/>
		</form>
		<c:if test="${not empty error}">
			<font color="red">
				<span>${error}</span>
			</font>
		</c:if>
		<c:if test="${not empty message}">
			<font color="blue">
				<span>${message}</span>
			</font>
		</c:if>
	</div>
</body>
</html>