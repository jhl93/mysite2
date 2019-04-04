<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${pageContext.request.contextPath}/assets/css/mysite.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
<title>Mysite</title>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- header -->

		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		<!-- /navigation -->

		<div id="content">
			<div id="c_box">
				<div id="guestbook">
					<h2>방명록</h2>

					<form id="addform" action="${pageContext.request.contextPath}/gb" method="get">
						<input type="hidden" name="action" value="add">
						<table>
							<tr>
								<td>이름</td>
								<c:choose>
									<c:when test="${empty sessionScope.authUser}">
										<td><input type="text" name="name"></td>
									</c:when>
									<c:otherwise>
										<td><input type="text" name="name"
											value="${sessionScope.authUser.name}"></td>
									</c:otherwise>
								</c:choose>
								<td>비밀번호</td>
								<td><input type="password" name="password"></td>
							</tr>
							<tr>
								<td colspan=4><textarea name="content" cols="75" rows="8"
										form="addform"></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
							</tr>
						</table>
					</form>

					<c:forEach items="${requestScope.guestList}" var="vo">
					<table width=510 border=1>
						<tr>
							<td>${vo.no}</td>
							<td>${vo.name}</td>
							<td>${vo.regDate}</td>
							<td><a
								href="${pageContext.request.contextPath}/gb?action=dform&no=${vo.no}">삭제</a></td>
						</tr>
						<tr>
							<td colspan=4>${vo.content}</td>
						</tr>
					</table>
					<br>
					</c:forEach>
				</div>
				<!-- /guestbook -->
			</div>
			<!-- /c_box -->
		</div>
		<!-- /content -->

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- /footer -->
	</div>
	<!-- /container -->
</body>
</html>

