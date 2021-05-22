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
			<div class="navbar navbar-default">
				<div class="pFoto">
					<img src="${profile.getFoto()}">
					<div class="pNama">${profile.getfName()}
						${profile.getlName()}</div>
					<div id="action" class="btnProfil">
						<c:if test="${profile.getMembershipStatus() eq 0}">
							<button onclick="friendAction('${profile.getUsername()}',0)"
								class="btn btn-info btn-block btn-xs">Tambahkan Teman</button>
						</c:if>
						<c:if test="${profile.getMembershipStatus() eq 1}">
							<button onclick="friendAction('${profile.getUsername()}',1)"
								class="btn btn-warning btn-block btn-xs">Hapus
								Pertemanan</button>
						</c:if>
						<c:if test="${profile.getMembershipStatus() eq 2}">
							<button onclick="friendAction('${profile.getUsername()}',2)"
								class="btn btn-info btn-block btn-xs">Konfirmasi</button>
							<button onclick="friendAction('${profile.getUsername()}',3)"
								class="btn btn-warning btn-block btn-xs">Hapus
								Permintaan</button>
						</c:if>
						<c:if test="${profile.getMembershipStatus() eq 3}">
							<button onclick="friendAction('${profile.getUsername()}',4)"
								class="btn btn-warning btn-block btn-xs">Batalkan
								Permintaan</button>
						</c:if>
					</div>
				</div>
				<ul class="nav navbar-nav fLeft" id="actionList">
					<li><a href="profile.jsp">Profil</a></li>
					<li><a href="Personal?action=vBlogUser">Tulisan</a></li>
					<li><a href="Personal?action=vFriendUser">Teman</a></li>
					<li><a href="Personal?action=vGroupUser">Komunitas</a></li>
				</ul>
			</div>
			<div class="partmidside">
				<h1 class="title">Teman</h1>
				<div class="groupmember">
					<c:if test="${fn:length(friendList) eq 0}">Tidak Ada</c:if>
					<ul class="member" id="memberList">
						<c:forEach items="${friendList}" var="freindRequest">
							<c:forEach items="${freindRequest.value}" var="list">
								<c:if test="${list.membershipStatus eq 1}">
									<li><a
										href="Personal?action=vUser&username=${list.username}"> <img
											src="${list.foto}"><br>${list.fName} ${list.lName}
									</a></li>
								</c:if>
							</c:forEach>
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
