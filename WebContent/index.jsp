<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>BdgPinter</title>
<link rel="stylesheet" href="sources/fancybox/jquery.fancybox.css"
	type="text/css" media="screen" />
<link href="sources/css/flat-ui.css" rel="stylesheet">
<link rel="shortcut icon" href="sources/images/favicon.ico">
<script src="sources/js/application.js"></script>
<script type="text/javascript" src="sources/fancybox/jquery-1.8.3.js"></script>
<script type="text/javascript" src="sources/fancybox/jquery.fancybox.js"></script>
<script type="text/javascript">
		$(document).ready(function() {
			$(".viewPPT").hide();
			
			<c:forEach var="file" items="${contentList}">
			<c:if test="${file.singleFile.location eq 'mp4' or file.singleFile.location eq 'flv'  or file.singleFile.location eq 'swf'}">
			$("a[rel=mp4${file.singleFile.id}]").fancybox({
				 width  : 600,
				    height : 300,
				    type   :'iframe'
			});
			</c:if>
			</c:forEach>

		<c:forEach var="file" items="${contentList}">
			<c:if test="${file.singleFile.location eq 'pdf'}">
					$("#pdfClass${file.singleFile.id}").fancybox({
				    width  : 600,
				    height : 300,
				    type   :'iframe'
				});
			</c:if>
		</c:forEach>
			
			
			$("a[rel=example_group]").fancybox();
			
			<c:forEach var="file" items="${contentList}">
			<c:if test="${file.singleFile.location eq 'ppt'}">
				$("a[rel=view${file.singleFile.id}]").fancybox();
			</c:if>
			</c:forEach>
		    });
</script>
<script type="text/javascript">
	var videopath = "";
	var swfplayer = "sources/fancyplayer_code/videos/flowplayer-3.1.1.swf";
</script>
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
	<!--set session attribute -->
	<c:if test="${!empty userInfo}">
		<c:set scope="session" var="sessionUsername"
			value="${userInfo.username}" />
		<c:set scope="session" var="sessionName"
			value="${userInfo.fName} ${userInfo.lName}" />
	</c:if>
	<!--set session attribute -->
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
				<h1 class="title">Status</h1>
				<div class="error">
							<p id="multi-msgs1"></p>
							</div>
				<textarea id="Status" placeholder="Status"
					class="form-control input-sm w100"></textarea>
				<input type="button" value="Simpan" class="btn btn-default btn-sm"
					onclick="addStatus()">

			</div>
			<div class="partmidside">
				<h1 class="title">Timeline</h1>
				<ul class="timeline" id="timelineList">
					<c:forEach var="content" items="${contentList}" varStatus="i">
						<c:if test="${content.type==1}">
							<li class="li" id="list${content.getId()}">
								<div class="direction">
									<div class="flag-wrapper">
										<span class="flag"><a
											href="Personal?action=vUser&username=${content.user.username}">${content.user.fName}
												${content.user.lName}</a> membuat tulisan baru</span>
									</div>
									<div class="desc">
										<h2>
											<a href="Personal?action=vDetailBlog&bID=${content.getId()}">${content.title}</a>
											<c:if test="${content.user.username eq sessionUsername}">
												<span class="glyphicon glyphicon-remove-circle" id="delIcon"
													onclick="delBlog(${content.getId()})"></span>
												<a href="Personal?action=editBlog&bID=${content.getId()}"><span
													class="glyphicon glyphicon-edit" id="delIcon"></span></a>
											</c:if>
										</h2>
										${content.body }
										<p class="kat">
											Kategori : <span>${content.getCategory()}</span>
										</p>

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
						<c:if test="${content.type==3}">
							<li class="li" id="list${content.getId()}">
								<div class="direction">
									<div class="flag-wrapper">
										<span class="flag"><a
											href="Personal?action=vUser&username=${content.user.username}">${content.user.fName}
												${content.user.lName}</a> membuat tulisan baru di <a
											href="Controller?action=gDetail&gID=${content.group.id}">${content.group.groupName}</a></span>
									</div>
									<div class="desc">
										<h2>
											<a href="Controller?action=vDetailCourse&cID=${content.getId()}&gID=${content.group.id}">${content.title}</a>
											<c:if test="${content.user.username eq sessionUsername}">
												<span class="glyphicon glyphicon-remove-circle" id="delIcon"
													onclick="delBlog(${content.getId()})"></span>
												<a href="Controller?action=editCourse&cID=${content.getId()}&gID=${content.group.id}"><span
													class="glyphicon glyphicon-edit" id="delIcon"></span></a>
											</c:if>
										</h2>
										${content.body }
										<p class="kat">
											Kategori : <span>${content.getCategory()}</span>
										</p>
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
						<c:if test="${content.type==4}">
							<li class="li" id="list${content.getId()}">
								<div class="direction">
									<div class="flag-wrapper">
										<span class="flag"><a
											href="Personal?action=vUser&username=${content.user.username}">${content.user.fName}
												${content.user.lName}</a> mengupload berkas baru di <a
											href="Controller?action=gDetail&gID=${content.group.id}">${content.group.groupName}</a></span>
									</div>
									<div class="desc">
										<h2>
											<a href="Controller?action=vDetailCourse&cID=${content.getId()}&gID=${content.group.id}">${content.title}</a>
											<c:if test="${content.user.username eq sessionUsername}">
												<span class="glyphicon glyphicon-remove-circle" id="delIcon"
													onclick="delBlog(${content.getId()})"></span>
												<a href="Controller?action=editCourse&cID=${content.getId()}&gID=${content.group.id}"><span
													class="glyphicon glyphicon-edit" id="delIcon"></span></a>
											</c:if>
										</h2>
										<p>${content.body}</p>
										<c:if
											test="${content.singleFile.location eq 'jpg' or content.singleFile.location eq 'jpeg' or content.singleFile.location eq 'png'}">
											<a rel="example_group"
												href="sources/GroupContent/${content.group.id}/${content.singleFile.id}.${content.singleFile.location}"
												title=""><button class="glyphicon glyphicon-file btn-delete btn btn-sm" >   Lihat Berkas</button>
											</a>
										</c:if>
										<c:if
											test="${content.singleFile.location eq 'mp4' or content.singleFile.location eq 'flv'  or content.singleFile.location eq 'swf'}">
											<a rel="mp4${content.singleFile.id}"
												href="sources/GroupContent/${content.group.id}/${content.singleFile.id}.${content.singleFile.location}"
												class="video_link"><button class="glyphicon glyphicon-file btn-delete btn btn-sm" >   Lihat Berkas</button></a>
										</c:if>
										<c:if test="${content.singleFile.location eq 'pdf'}">
											<a id="pdfClass${content.singleFile.id}"
												href="sources/GroupContent/${content.group.id}/${content.singleFile.id}.${content.singleFile.location}"><button class="glyphicon glyphicon-file btn-delete btn btn-sm" >   Lihat Berkas</button></a>
										</c:if>
										<c:if test="${content.singleFile.location eq 'ppt'}">
											<c:forEach var="i" begin="1"
												end="${content.singleFile.pageSize}">
												<c:choose>
													<c:when test="${i eq 1}">
														<a rel="view${content.singleFile.id}"
															href="sources/GroupContent/${content.group.id}/${content.singleFile.id}/slide-${i}.png"
															title="" id="click"><button class="glyphicon glyphicon-file btn-delete btn btn-sm" >   Lihat Berkas</button>
														</a>
													</c:when>
													<c:otherwise>
														<a rel="view${content.singleFile.id}"
															href="sources/GroupContent/${content.group.id}/${content.singleFile.id}/slide-${i}.png"
															title="" class="viewPPT">
															<button class="glyphicon glyphicon-file btn-delete btn btn-sm" >   Lihat Berkas</button>
														</a>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:if>
										<p class="kat">
											Kategori : <span>${content.getCategory()}</span>
										</p>
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
						<c:if test="${content.type==5}">
							<li class="li" id="list${content.getId()}">
									<div class="direction">
										<div class="flag-wrapper">

											<c:choose>
												<c:when test="${content.singleEvent.type==1}">
													<span class="flag"><a
														href="Personal?action=vUser&username=${content.user.username}">${content.user.fName}
															${content.user.lName}</a> membuat kuis baru di <a
														href="Controller?action=gDetail&gID=${content.group.id}">${content.group.groupName}</a></span>
												</c:when>
												<c:otherwise>
													<span class="flag"><a
														href="Personal?action=vUser&username=${content.user.username}">${content.user.fName}
															${content.user.lName}</a> membuat acara baru di <a
														href="Controller?action=gDetail&gID=${content.group.id}">${content.group.groupName}</a></span>
												</c:otherwise>
											</c:choose>

										</div>
										<div class="desc">
											<h2><a href="Controller?action=vDetailCourse&cID=${content.getId()}&gID=${content.group.id}">${content.title}</a>
												<c:if test="${content.user.username eq sessionUsername}">
													<span class="glyphicon glyphicon-remove-circle"
														id="delIcon" onclick="delBlog(${content.getId()})"></span>
													<a href="Controller?action=editCourse&cID=${content.getId()}&gID=${content.group.id}"><span
														class="glyphicon glyphicon-edit" id="delIcon"></span></a>
												</c:if>
											</h2>
											<div class="row ">${content.body}</div>
											<div class="row deskripsiAcara">
												<div class="w20 labelName">
													<span class="glyphicon glyphicon-home"></span> <label
														class="labelName">Lokasi</label>
												</div>
												<div class="w50 labelIsi">${content.singleEvent.place }</div>
											</div>
											<div class="row deskripsiAcara">
												<div class="w100 labelName">
													<span class="glyphicon glyphicon-time"></span> Waktu
												</div>

												<div class="w50 fLeft">
													<label class="w100 labelName">Mulai</label> <label
														class="w100 labelIsi">${content.singleEvent.startTime }</label>
												</div>
												<div class="w50 fRight">
													<label class="w100 labelName">Selesai</label> <label
														class="w100 labelIsi">${content.singleEvent.endTime }</label>
												</div>
											</div>
										<p class="kat">
											Kategori : <span>${content.getCategory()}</span>
										</p>
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
						<c:if test="${content.type==7}">
							<li class="li" id="list${content.getId()}">
								<div class="direction">
									<div class="flag-wrapper">
										<span class="flag"><a
											href="Personal?action=vUser&username=${content.user.username}">${content.user.fName}
												${content.user.lName}</a> membuat status baru</span>
									</div>
									<div class="desc">
										<p>${content.body }</p>
									</div>
									<div class="lead">
										<span class="kanan comment">${fn:length(content.getComment())}</span>
										<span class="kanan glyphicon glyphicon-comment"></span> <span
											class="kanan">${content.getPostedDate()}</span>
										<c:if test="${content.user.username eq sessionUsername}">
										<span class="kanan comment" onclick="delBlog(${content.getId()})">Hapus</span>
											</c:if>
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
					</c:forEach>
				</ul>
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