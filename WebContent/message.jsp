<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<h1 class="title">Pesan</h1>				
				<div class="error">
							<p id="multi-msgs"></p>
						</div>
				<input type="text"
					class="form-control input-sm w100" onkeyup="getFullname();" onblur="cekName();"
					placeholder="Penerima" required maxlength="30" id="receiverMsg" />
				<ul id="selectReceiver">
				</ul>
				<div id="listReceiver"></div>
				<textarea class="form-control input-sm w100" id="bodyMsg"
					placeholder="Isi Pesan" maxlength="160" onkeydown="aMessage()"></textarea>
				<input type="button" value="Send" onclick="addMessage()"
					class="btn btn-default btn-sm">
			</div>
			<div class="partmidside">
				<ul class="message" id="listMsgs">

				</ul>
			</div>
		</div>
		<div class="rightside">
			<div class="group-banner">
				<div class="group-title">Pesan Tersimpan</div>
				<ul id="uList">
					<c:forEach var="uList" items="${receiverList}">
						<li id="uMsg${uList.getUsername()}" class="r${uList.getStatus()}"><span
							onclick="vMsg('${uList.getUsername()}','${uList.getfName()} ${uList.getlName()}');">${uList.getfName()}
								${uList.getlName()}</span>
						<c:if test="${uList.getStatus() eq 1}">
								<span class="navbar-unread"></span>
							</c:if> <span class="glyphicon glyphicon-remove-circle" id="delIcon"
							onclick="delRecMsg('${uList.getUsername()}');"></span></li>

					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<!-- /container -->
	<footer>
		<%@ include file="footer.html"%>
	</footer>
</body>
</html>