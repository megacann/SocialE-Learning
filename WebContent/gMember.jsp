<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="head.html" />
<link rel="StyleSheet"
	href="sources/sources/AutoComplete/css/ui-lightness/jquery-ui-1.9.2.custom.min.css"
	type="text/css" media="all" />
<link rel="StyleSheet"
	href="sources/sources/AutoComplete/css/jquery.tagedit.css"
	type="text/css" media="all" />
<script type="text/javascript"
	src="sources/sources/AutoComplete/js/jquery.autoGrowInput.js"></script>
<script type="text/javascript"
	src="sources/sources/AutoComplete/js/jquery.tagedit.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#CreateGroup").hide();
});

$(function() {
$( "#tagform-editonly" ).find('input.tag').tagedit({
autocompleteURL: 'Controller?action=inviteToGroup',
allowEdit: false,
allowAdd: false
});
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

				<div class="nav navbar-nav fLeft">
					<a href="Controller?action=gDetail&gID=${sessionSelectedGroup}"
						class="navbar-brand">${sessionGroupName}</a>
					<div id="gAct${sessionSelectedGroup}" class="btnGrup">
						<span onclick="gAction(${sessionSelectedGroup},1)">Keluar
							Grup</span>
					</div>
				</div>
				<ul class="nav navbar-nav fRight">
					<li><a href="Controller?action=groupMember">Anggota</a></li>
					<li><a href="Controller?action=groupBlog">Tulisan</a></li>
					<li><a href="Controller?action=viewFileInGroup">Berkas</a></li>
					<li><a href="Controller?action=groupEvent">Acara</a></li>
					<li><a href="Controller?action=viewQuizInGroup">Kuis</a></li>
				</ul>
			</div>
			<div class="partmidside">
				<c:if test="${sessionRoleinGroup eq 1 }">
					<form action="Controller?action=sendInvitation" method="post" id="tagform-editonly">
						Undang anggota lain <input type="text" name="tag[]" class="tag" id="memberID" />
						<input type="button" name="save" value="Undang" onClick="addMember();"/>
	    			</form>
    			</c:if>
    			<div id="invitedUser"></div>
				<h1 class="title">Permintaan Anggota</h1>
				<div class="groupmember">
					<!-- Pending request -->
					<ul class="member">
						<c:forEach var="member" items="${memberList}" varStatus="i">
							<c:if test="${member.membershipStatus eq 2}">
								<li id="member${i.index}"><a
									href="Personal?action=vUser&username=${member.username}"> <img
										src="${member.foto}"> <br> ${member.fName}
										${member.lName}
								</a>
								<c:if test="${sessionRoleinGroup eq 1 }">
									<input type="button" value="Terima"
									class="btn btn-info btn-block btn-xs" id="button1"
									onclick="memberRequest('member${i.index}','${member.username}','1','${member.foto}','${member.fName} ${member.lName}')">
									<input type="button" value="Tolak"
									class="btn btn-warning btn-block btn-xs" id="button2"
									onclick="memberRequest('member${i.index}','${member.username}','3','${member.foto}','${member.fName} ${member.lName}')">
								</c:if>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				<!-- Pending request -->
			</div>
			<div class="partmidside">
				<h1 class="title">Anggota</h1>
				<!-- member list -->
				<div class="groupmember">
					<ul class="member" id="memberList">
						<c:forEach var="member" items="${memberList}" varStatus="i">
							<c:if test="${member.membershipStatus eq 1 && member.role eq 2}">
								<li id="member${i.index }"><a
									href="Personal?action=vUser&username=${member.username}"> <img
										src="${member.foto}"> <br> ${member.fName}
										${member.lName}
								</a>
								<c:if test="${sessionRoleinGroup eq 1 }">
									<input type="button" value="Jadikan admin"
									class="btn btn-info btn-block btn-xs" id="button1"
									onclick="makeAsAdmin('${member.username}',member${i.index })">
									<input type="button" value="Keluarkan"
									class="btn btn-warning btn-block btn-xs" id="button2"
									onclick="deleteFromGroup('${member.username}',member${i.index })" >
								</c:if>
								</li>
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
						<c:forEach var="member" items="${memberList}">
							<c:if test="${member.membershipStatus eq 1 && member.role eq 1}">
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
				<div class="group-title">Daftar Konten</div>
				<ul id="listTitleBlog">
					<c:forEach items="${titleList}" var="blogList" varStatus="i">
						<li id="bID=${blogList.key}"><a
							href="Controller?action=vDetailCourse&cID=${blogList.key}">
								${blogList.value} </a></li>

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