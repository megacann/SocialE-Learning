<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="head.html" />
<script>
$(document).ready(function() {
	$("#CreateGroup").hide();
});
</script>
</head>
<body>

<!--check login -->
	<c:if test="${empty userInfo}">
		<jsp:forward page="login.jsp" />
	</c:if>
	<!--check login -->
	<header>
		<jsp:include page="header.html" />
	</header>
	<div class="container">
		<div class="leftside">
			<div class="profil">
				<c:set scope="session" var="info" value="${userInfo}" />
				<div class="profilKiri">
					<img src="${info.getFoto()}">
				</div>
				<div class="profilKanan">
					<a href="Personal?action=vUser&username=${info.getUsername()}"
						class="name" id="fullname">${info.getfName()}
						${info.getlName()}</a> <br> <a href="Personal?action=vProfile">Ubah
						Profil</a>
				</div>
			</div>
			<div class="group-banner" id="navbar-mygroup">
				<div class="group-title">Komunitas</div>
				<ul>
					<c:forEach var="memberGroupList" items="${groupList}">
						<li><a
							href="Controller?action=gDetail&gID=${memberGroupList.getId()}">
								${memberGroupList.getGroupName() } </a></li>

					</c:forEach>
					<li><a onclick="showCreateGroup()" id="btnCreate">Buat
							komunitas baru?</a>
						<div id="CreateGroup">
							<input type="text" id="namaGroup" name="groupName"
								placeholder="Nama Group"> <input type="submit"
								value="Buat Komunitas" onclick="createGroup()">
						</div></li>
				</ul>
			</div>
		</div>
		<div class="midside">
			<div class="partmidside">
				<h1 class="title">Pemberitahuan</h1>
				<c:if test="${fn:length(notifeList) ne 0}"><button onclick="deleteNotife()" class="glyphicon glyphicon-trash btn-delete btn btn-sm" >   Hapus Pemberitahuan</button></c:if>
				<ul class="notife" id="listNotife">
					<c:if test="${fn:length(notifeList) eq 0}">Tidak ada pemberitahuan</c:if>
						<c:forEach var="notificationList" items="${notifeList}">
						<li class="tgl">${notificationList.getDate()}
							<ul>
								<c:forEach var="notification" items="${notificationList.getNotife()}">
									<li class="r${notification.getStatus()}" id="notif${notification.getId()}"><a
										href="${notification.getLink()}" onclick="updateNotife(${notification.getId()});"><span class="time">${notification.getTime()}</span>${notification.getDescription()}</a></li>
								</c:forEach>
							</ul>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	<div class="rightside">
			<div class="group-banner">
				<div class="group-title">Berita Terbaru</div>
				<%@ include file="news.html"%>
			</div>
		</div>
		</div>
	<footer>
		<%@ include file="footer.html"%>
	</footer>
</body>
</html>
