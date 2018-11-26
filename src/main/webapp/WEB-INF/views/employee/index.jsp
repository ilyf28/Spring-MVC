<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="common.model.DataMap" %>
<%
	DataMap employeeList = (DataMap) request.getAttribute("employeeList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee</title>
</head>
<body>
	<h1>Employee Index Page</h1>
	
	<table>
		<caption>임직원 목록</caption>
		<colgroup>
			<col width="20%" />
			<col width="20%" />
			<col width="" />
			<col width="10%" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">사번</th>
				<th scope="col">이름</th>
				<th scope="col">입사일</th>
				<th scope="col">근무현황</th>
			</tr>
		</thead>
		<tbody>
		<%if (employeeList.getArraySize() > 0) { %>
		<%for (int i = 0, size = employeeList.getArraySize(); i < size; i++) { %>
			<tr>
				<td><%=employeeList.getString("EMPLOYEECODE", i)%></td>
				<td><%=employeeList.getString("NAME", i)%></td>
				<td><%=employeeList.getValue("JOINDATE", i)%></td>
				<td><%=employeeList.getString("ISWORKING", i)%></td>
			</tr>
		<%} %>
		<%} else { %>
			<tr>
				<td colspan="4">조회 내용이 없습니다.</td>
			</tr>
		<%} %>
		</tbody>
	</table>
</body>
</html>