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
				<div class="wikiDiv">

					<div class="wikiImg w100">
						<img src="sources/images/WikiBg.jpg">
					</div>
					<div class="w100 labelSandi">Masukan Kata Kunci :</div>
						<div class="form-group w100">
							<div class="input-group">
								<input class="form-control" onkeydown="keysearchWiki()" id="navbarInput-01" type="text" name="keySearch"
									placeholder="Kata Kunci"> <span
									class="input-group-btn">
									<button type="button" class="btn" onclick="searchWiki()">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</span>
							</div>
						</div>
						
					<ul id="resultList">						
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