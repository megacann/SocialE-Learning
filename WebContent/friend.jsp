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
			<h1 class="title">Permintaan Teman</h1>
			<div class="groupmember">
				<ul class="member">
					<c:forEach items="${friendList}" var="freindRequest" varStatus="i">
						<c:forEach items="${freindRequest.value}" var="list">
							<c:if test="${list.membershipStatus eq 2}">
								<li id="friend${freindRequest.key}">
									<a href="Personal?action=vUser&username=${list.username}"> <img src="${list.foto}"><br>${list.fName} ${list.lName}</a> 
									<input type="button" value="Terima" class="btn btn-info btn-block btn-xs" 
									onclick="friendRequest('friend${freindRequest.key}','${freindRequest.key}','1','${list.foto}','${list.fName} ${list.lName}','${list.username}')">
									<input type="button" value="Tolak" class="btn btn-warning btn-block btn-xs"
									onclick="friendRequest('friend${freindRequest.key}','${freindRequest.key}','4','${list.foto}','${list.fName} ${list.lName}','${list.username}')">
								</li>
							</c:if>
						</c:forEach>
					</c:forEach>
				</ul>
			</div>
			</div>
			<div class="partmidside">
			<h1 class="title">Temanku</h1>
			<div class="groupmember">
				<ul class="member" id="memberList">
					<c:forEach items="${friendList}" var="freindRequest">
						<c:forEach items="${freindRequest.value}" var="list">
							<c:if test="${list.membershipStatus eq 1}">
								<li id="friend${freindRequest.key}"><a href="Personal?action=vUser&username=${list.username}"> <img src="${list.foto}"><br>${list.fName} ${list.lName}</a>
								<input type="button" value="Hapus Teman" class="btn btn-warning btn-block btn-xs"
									onclick="friendRequest('friend${freindRequest.key}','${freindRequest.key}','3','${list.foto}','${list.fName} ${list.lName}','${list.username}')"></li>	
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