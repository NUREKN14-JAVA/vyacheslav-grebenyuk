<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<title>User management</title>
</head>
<body>
	<table id="userTable" border="1">
		<tr>
			<th></th>
			<th>First name</th>
			<th>Last name</th>
			<th>Date of birth</th>
			<th>Actions</th>
		</tr>
		<c:forEach var="user" items="${userList}">
			<spring:url value="/users/edit.html" var="editUrl">
				<spring:param name="id" value="${user.id}" />
			</spring:url>
			<spring:url value="/users/delete.html" var="deleteUrl">
				<spring:param name="id" value="${user.id}" />
			</spring:url>
			<spring:url value="/users/details.html" var="detailsUrl">
				<spring:param name="id" value="${user.id}" />
			</spring:url>
			<tr>
				<td></td>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td>${user.dateOfBirth}</td>
				<td>
					<a href="${editUrl}">Edit</a>
					<a href="${deleteUrl}">Delete</a>
					<a href="${detailsUrl}">Details</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<spring:url value="/users/add.html" var="addUrl" />
	<a href="${addUrl}">Add user</a>
	
	<c:if test="${requestScope.error != null}">
		<script>
			alert('${requestScope.error}');
		</script>
	</c:if>
	<c:if test="${requestScope.message != null}">
		<script>
			alert('${requestScope.message}');
		</script>
	</c:if>
</body>
</html>