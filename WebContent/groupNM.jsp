<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="head.html" />
<script type="text/javascript">
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
			<c:set scope="request" var="grup" value="${group}" />
			<div class="navbar navbar-default">
				<div class="nav navbar-nav fLeft">
					<a href="Controller?action=gDetail&gID= ${grup.getId()}"
						class="navbar-brand">${grup.getGroupName()}</a>
				</div>
				<div id="gAct${grup.getId()}" class="fRight btnActGrup">
						<c:if test="${grup.getStatus() eq 0}">
							<input type="button" value="Gabung Grup"
								onclick="groupAction(${grup.getId()},0)"
								class="btn btn-info btn-xs btn-block ">

						</c:if>
						<c:if test="${grup.getStatus() eq 2}">
							<input type="button" value="Batalkan Permintaan"
								onclick="groupAction(${grup.getId()},2)"
								class="btn btn-warning btn-xs btn-block">
						</c:if>
					</div>
			</div>
			<div class="partmidside">
				<h1 class="title">Member</h1>
				<!-- member list -->
				<div class="groupmember">
					<ul class="member" id="memberList">
						<c:forEach var="member" items="${grup.getMember()}" varStatus="i">
							<c:if test="${member.role eq 2 && member.membershipStatus eq 1}">
								<li id="member${i.index }"><a
									href="Personal?action=vUser&username=${member.username}"> <img
										src="${member.foto}"> <br> ${member.fName}
										${member.lName}
								</a></li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
			<!-- member list -->
			<div class="partmidside">
				<h1 class="title">Admin</h1>
				<div class="groupmember">
					<ul class="member" id="adminDIV">
						<c:forEach var="member" items="${grup.getMember()}">
							<c:if test="${member.role eq 1 && member.membershipStatus eq 1}">
								<li id="member${i.index }"><a
									href="Personal?action=vUser&username=${member.username}"> <img
										src="${member.foto}"> <br> ${member.fName}
										${member.lName}
								</a></li>
							</c:if>
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