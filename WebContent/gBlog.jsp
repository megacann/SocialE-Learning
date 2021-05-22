<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="head.html" />
<script src="sources/js/jquery-ui.min.js"></script>
<link rel="stylesheet" href="sources/css/tab.css">
<script type="text/javascript" src="sources/tinymce/tinymce.min.js"></script>
<script type="text/javascript" src="sources/js/tinyMCE.js"></script>
<script>
	jQuery.browser = {};
	$(document)
			.ready(
					function() {
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
				<ul class="timeline" id="blog">
					<c:forEach var="info" items="${contentList}">
						<li class="li" id="list${info.getId()}">
							<div class="direction">
								<div class="desc">
									<h2>
										<a href="Controller?action=vDetailCourse&cID=${info.getId()}">${info.getTitle()}</a><span
											class="glyphicon glyphicon-remove-circle" id="delIcon"
											onclick="delBlog(${info.getId()})"></span><a
											href="Controller?action=editCourse&cID=${info.getId()}"><span
											class="glyphicon glyphicon-edit" id="delIcon"></span></a>
									</h2>
									<div>${info.getBody()}</div>
									<p class="kat">Kategori : <span>${info.getCategory()}</span></p>
								</div>
								<div class="lead">
									<span class="kanan comment">${fn:length(info.getComment())}</span>
									<span class="kanan glyphicon glyphicon-comment"></span> <span
										class="kanan">${info.getPostedDate()}</span>
								</div>
								<div class="commentBox">
									<ul id="commentList${info.getId()}">
										<c:forEach var="com" items="${info.getComment() }">
											<li id="cId${com.getId()}"><span class="name">${com.getCommentator()}</span>
												<c:if test="${com.getCommentator() eq sessionName}">
													<span class="glyphicon glyphicon-remove-circle"
														id="delIcon" onclick="delComment(${com.getId()})"></span>
													<span
														onclick="editComment(${com.getId()},'${com.getComment()}','${com.getTime()}',${info.getId()})"
														class="glyphicon glyphicon-edit" id="delIcon"></span>
												</c:if> <span class="time">${com.getTime()}</span><span class="isi">${com.getComment()}</span></li>
										</c:forEach>
									</ul>
									<div class="form-group" id="fComm${info.getId()}">
										<div class="input-group">
											<input id="body${info.getId()}" name="bodyC" type="text"
												class="form-control" placeholder="Tulis Komentar"
												maxlength="160" onkeydown="addComment(${info.getId()})">
											<span class="input-group-btn">
												<button type="submit" class="btn"
													onclick="aComment(${info.getId()})">
													<span class="glyphicon glyphicon-comment"></span>
												</button>
											</span>
										</div>
									</div>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>

		</div>
		<div class="rightside">
			<div class="group-banner">
				<div class="group-title">Daftar Blog</div>
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
