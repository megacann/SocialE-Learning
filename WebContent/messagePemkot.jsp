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
	<c:if test="${empty userInfo or userInfo.username ne 'pemkot'}">
		<jsp:forward page="login.jsp" />
	</c:if>
	<!--check login -->
	<header>
		<jsp:include page="headerPemkot.html" />
	</header>
	<div class="container">
		<div class="leftside">
			<div class="profil">
				<h1 class="title">Selamat Datang</h1>

				<c:set scope="session" var="info" value="${userInfo}" />
				<div class="photo w100 fLeft">
					<img src="${info.getFoto()}"><br> <a
						href="Personal?action=vProfile" class="name" id="fullname">${info.getfName()}
						${info.getlName()}</a>

				</div>

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