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
				<ul class="timeline" id="blog">
					<c:forEach var="content" items="${contentList}" varStatus="i">
						<c:if test="${content.type==2}">
							<li class="li" id="list${content.getId()}">
								<div class="direction">
									<div class="desc">
										<h2>
											<a href="Personal?action=vDetailWiki&wID=${content.getId()}">${content.title}</a>
											<a
												href="Personal?action=editWiki&wID=${content.getId()}"><span
												class="glyphicon glyphicon-edit" id="delIcon"></span></a>
										</h2>
										<div>${content.body }</div>
										<p class="kat">Kategori : <span>${content.getCategory()}</span></p>
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
													</c:if> <span class="time">${com.getTime()}</span><span
													class="isi">${com.getComment()}</span></li>
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
								</div>
							</li>
						</c:if>
						<c:if test="${content.type==6}">
							<li class="li" id="list${content.getId()}">
								<div class="direction">
									<div class="desc">
										<h2>
											<a href="Personal?action=vDetailWiki&wID=${content.getId()}">${content.title}</a>
										</h2>
										<p>${content.body}</p>
										<p class="kat">Kategori : <span>${content.getCategory()}</span></p>
										<div class="imgDiv">
												<img class="newsImg" alt="${content.singleFile.fileName}.${content.singleFile.location}"
													src="${content.singleFile.getLocation()}">
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
													</c:if> <span class="time">${com.getTime()}</span><span
													class="isi">${com.getComment()}</span></li>
											</c:forEach>
										</ul>
										<div class="form-group">
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
								</div>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="rightside">
			<div class="group-banner">
				<div class="group-title">Lihat Lainnya</div>
				<ul id="listTitleBlog">
				<c:forEach items="${titleList}" var="blogList" varStatus="i">
						<li id="bID=${blogList.key}"><a
							href="Personal?action=vDetailWiki&wID=${blogList.key}">
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