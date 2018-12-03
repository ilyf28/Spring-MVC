<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!--
	<meta id="_csrf" name="_csrf" content="${_csrf.token}" />
	<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}" />
	-->
	<sec:csrfMetaTags />
	<title>Login</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/components/core-min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/components/sha256-min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginSubmitBtn").click(function() {
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
			
			$("#loginAjaxBtn").click(function() {
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
					
					$.ajax({
						url: "/j_spring_security_check",
						data: $("#loginForm").serialize(),
						type: "POST",
						dataType: "JSON",
						beforeSend: function(XHR) {
							XHR.setRequestHeader("Accept", "application/json");
						}
					}).done(function( data, textStatus, jqXHR ) {
						var responseJSON = jqXHR.responseJSON;
						var success = responseJSON.response.success;
						
						if (success) {
							var url = responseJSON.response.url;
							if (url == null || url == "") url = '<c:url value="/" />';
							location.href = url;
						} else {
							showAlert("alert-danger", responseJSON.response.message);
						}
					}).fail(function( jqXHR, textStatus, errorThrown ) {
					});
				}
			});
		});
		
		function showAlert(alertClass, message) {
			$(".alert").removeClass().addClass("alert " + alertClass).text(message).alert("show").show();
		}
	</script>
</head>
<body>
	<div>
		<h1>로그인 화면</h1>
		<div class="alert" role="alert" style="display: none;"></div>
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
						<input type="button" id="loginSubmitBtn" value="Submit Login" />
						<input type="button" id="loginAjaxBtn" value="Ajax Login" />
					</td>
				</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="hidden" name="loginPw" value=""/>
		</form>
		<c:if test="${not empty error}">
		<script type="text/javascript">
			showAlert("alert-danger", "${error}");
		</script>
		</c:if>
		<c:if test="${not empty message}">
		<script type="text/javascript">
			showAlert("alert-success", "${message}");
		</script>
		</c:if>
	</div>
</body>
</html>