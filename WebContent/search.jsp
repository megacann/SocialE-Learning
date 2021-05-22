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
				<h1 class="title">Warga</h1>
				<div class="groupmember">
					<ul class="member">
						<c:if test="${fn:length(userSearch) eq 0}">Tidak ditemukan</c:if>
						<c:forEach var="user" items="${userSearch}">
							<li><a
								href="Personal?action=vUser&username=${user.getUsername()}">
									<img src="${user.getFoto()}"> <br>${user.getfName()}
									${user.getlName()}
							</a> 
							<div id="memAct${user.getUsername()}">
							<c:if test="${user.getMembershipStatus() eq 0}">
									<button onclick="userAction('${user.getUsername()}',0)"
										class="btn btn-info btn-block btn-xs">Tambahkan Teman</button>
								</c:if> <c:if test="${user.getMembershipStatus() eq 1}">
									<button onclick="userAction('${user.getUsername()}',1)"
										class="btn btn-warning btn-block btn-xs">Hapus
										Pertemanan</button>
								</c:if> <c:if test="${user.getMembershipStatus() eq 2}">
									<button onclick="userAction('${user.getUsername()}',2)"
										class="btn btn-info btn-block btn-xs">Konfirmasi</button>
									<button onclick="userAction('${user.getUsername()}',3)"
										class="btn btn-warning btn-block btn-xs">Hapus
										Permintaan</button>
								</c:if> <c:if test="${user.getMembershipStatus() eq 3}">
									<button onclick="userAction('${user.getUsername()}',4)"
										class="btn btn-warning btn-block btn-xs">Batalkan
										Permintaan</button>
								</c:if></div></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="partmidside">
				<h1 class="title">Komunitas</h1>
				<div class="groupmember">
					<ul class="member">
						<c:if test="${fn:length(groupSearch) eq 0}">Tidak ditemukan</c:if>
						<c:forEach var="group" items="${groupSearch}">
							<li><a href="Controller?action=gDetail&gID=${group.getId()}"> <img
									src="sources/images/group.png"> <br>${group.getGroupName()}
							</a> 
							<div id="gAct${group.getId()}">
							<c:if test="${group.getStatus() eq 0}">
									<input type="button" value="Gabung Grup" onclick="groupAction(${group.getId()},0)"
										class="btn btn-info btn-block btn-xs">
								</c:if> <c:if test="${group.getStatus() eq 1}">
									<input type="button" value="Keluar Grup" onclick="groupAction(${group.getId()},1)"
										class="btn btn-warning btn-block btn-xs">
								</c:if> <c:if test="${group.getStatus() eq 2}">
									<input type="button" value="Batalkan Permintaan" onclick="groupAction(${group.getId()},2)"
										class="btn btn-warning btn-block btn-xs">
								</c:if></div></li>
						</c:forEach>
					</ul>
				</div>

			</div>
		</div>
		<div class="rightside">
			<div class="group-banner">
				<div class="group-title">Berita Terbaru</div>
				<%@ include file="news.html"%>
			</div>
		</div>

	</div>

	<!-- /container -->
	<footer>
		<%@ include file="footer.html"%>
	</footer>
</body>
</html>