<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="head.html" />
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
					<c:forEach var="content" items="${eventList}" varStatus="i">
						<li class="li" id="list${content.getId()}"><c:set var="event"
								value="${content.getSingleEvent()}"></c:set>
							<div class="direction">
								<div class="desc">
									<h2><a href="Controller?action=vDetailCourse&cID=${content.getId()}">${content.getTitle()}</a>
										<c:if test="${content.user.username eq sessionUsername}">
											<span class="glyphicon glyphicon-remove-circle" id="delIcon"
												onclick="delBlog(${content.getId()})"></span>
											<a href="Controller?action=editCourse&cID=${content.getId()}"><span
												class="glyphicon glyphicon-edit" id="delIcon"></span></a>
										</c:if>
									</h2>
									<div class="row ">${content.body}</div>
									<div class="row" id="konfirm${content.id}">
										<c:choose>
											<c:when test="${event.status==1}">
												<span class="eventC">Anda Tidak Akan Menghadiri Acara</span>
											</c:when>
											<c:when test="${event.status==2}">
												<span class="eventC">Anda Akan Menghadiri Acara</span>
											</c:when>
											<c:otherwise>
												<button class="btn btn-info btn-sm"
													onclick="confirmEvent(1,${content.id})">Hadir</button>
												<button class="btn btn-warning btn-sm"
													onclick="confirmEvent(2,${content.id})">Tidak
													Hadir</button>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="row deskripsiAcara">
										<div class="w20 labelName">
											<span class="glyphicon glyphicon-home"></span> <label
												class="labelName">Lokasi</label>
										</div>
										<div class="w50 labelIsi">${event.place }</div>
									</div>
									<div class="row deskripsiAcara">
										<div class="w100 labelName">
											<span class="glyphicon glyphicon-time"></span> Waktu
										</div>

										<div class="w50 fLeft">
											<label class="w100 labelName">Mulai</label> <label
												class="w100 labelIsi">${event.startTime }</label>
										</div>
										<div class="w50 fRight">
											<label class="w100 labelName">Selesai</label> <label
												class="w100 labelIsi">${event.endTime }</label>
										</div>
									</div>
								</div>
								<div class="lead">
									<span class="kanan comment">${fn:length(content.getComment())}</span>
									<span class="kanan glyphicon glyphicon-comment"></span> <span
										class="kanan">${content.getPostedDate()}</span>
								</div>
								<div class="commentBox">
									<ul id="commentList${content.getId()}">
										<c:forEach var="com" items="${content.getComment() }">
											<li id="cId${com.getId()}"><span class="name">${com.getCommentator()}</span>
												<c:if test="${com.getCommentator() eq sessionName}">
													<span class="glyphicon glyphicon-remove-circle"
														id="delIcon" onclick="delComment(${com.getId()})"></span>
													<span
														onclick="editComment(${com.getId()},'${com.getComment()}','${com.getTime()}',${content.getId()})"
														class="glyphicon glyphicon-edit" id="delIcon"></span>
												</c:if> <span class="time">${com.getTime()}</span><span class="isi">${com.getComment()}</span></li>
										</c:forEach>
									</ul>
									<div class="form-group" id="fComm${content.getId()}">
										<div class="input-group">
											<input id="body${content.getId()}" name="bodyC" type="text"
												class="form-control" placeholder="Tulis Komentar"
												maxlength="160" onkeydown="addComment(${content.getId()})">
											<span class="input-group-btn">
												<button type="submit" class="btn"
													onclick="aComment(${content.getId()})">
													<span class="glyphicon glyphicon-comment"></span>
												</button>
											</span>
										</div>
									</div>
								</div>

							</div></li>
					</c:forEach>
				</ul>
			</div>

		</div>
		<div class="rightside">
			<div class="group-banner">
				<div class="group-title">Daftar Acara</div>
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
