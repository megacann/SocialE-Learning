<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>BdgPinter</title>
<link rel="stylesheet" href="sources/fancybox/jquery.fancybox.css"
	type="text/css" media="screen" />
<link href="sources/css/flat-ui.css" rel="stylesheet">
<link rel="shortcut icon" href="sources/images/favicon.ico">
<script type="text/javascript" src="sources/fancybox/jquery-1.8.3.js"></script>
<script type="text/javascript" src="sources/fancybox/jquery.fancybox.js"></script>
<script type="text/javascript">
		$(document).ready(function() {
			$(".viewPPT").hide();
			
			
			<c:forEach var="file" items="${fileList}">
			<c:if test="${file.singleFile.location eq 'mp4' or file.singleFile.location eq 'flv'  or file.singleFile.location eq 'swf'}">
			$("a[rel=mp4${file.singleFile.id}]").fancybox({
				 width  : 600,
				    height : 300,
				    type   :'iframe'
			});
			</c:if>
			</c:forEach>
				
			<c:forEach var="file" items="${fileList}">
				<c:if test="${file.singleFile.location eq 'pdf'}">
					$("#pdfClass${file.singleFile.id}").fancybox({
				    width  : 600,
				    height : 300,
				    type   :'iframe'
				});
				</c:if>
				</c:forEach>
			
			
			$("a[rel=example_group]").fancybox();
			<c:forEach var="file" items="${fileList}">
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
				<h1 class="title">Gambar</h1>
				<ul class="member">
					<c:forEach var="file" items="${fileList}">
						<c:if
							test="${file.singleFile.location eq 'jpg' or file.singleFile.location eq 'jpeg' or file.singleFile.location eq 'png'}">
							<li id="img1"><a rel="example_group"
								href="sources/GroupContent/${sessionScope.sessionSelectedGroup}/${file.singleFile.id}.${file.singleFile.location}"
								title=""> <img alt=""
									src="sources/GroupContent/${sessionScope.sessionSelectedGroup}/${file.singleFile.id}.${file.singleFile.location}"
									width="128px" height="128px" />
							</a>${file.singleFile.fileName }</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<div class="partmidside">
				<h1 class="title">Video</h1>
				<ul class="member" id="videos">
					<c:forEach var="file" items="${fileList}">
						<c:if
							test="${file.singleFile.location eq 'mp4' or file.singleFile.location eq 'flv'  or file.singleFile.location eq 'swf'}">
							<li><a rel="mp4${file.singleFile.id}"
								href="sources/GroupContent/${sessionScope.sessionSelectedGroup}/${file.singleFile.id}.${file.singleFile.location}"
								class="video_link"><img src="sources/images/video.png"
									width="218" height="148" alt="gorilla"
									style="margin-bottom: 4px" /></a> ${file.singleFile.fileName }</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<div class="partmidside">
				<h1 class="title">Dokumen</h1>
				<ul class="member">
					<c:forEach var="file" items="${fileList}">
						<c:if test="${file.singleFile.location eq 'pdf'}">
							<li id="vdo1"><a id="pdfClass${file.singleFile.id}"
								href="sources/GroupContent/${sessionScope.sessionSelectedGroup}/${file.singleFile.id}.${file.singleFile.location}"><img
									src="sources/images/docs.png"></a> <br>${file.singleFile.fileName }</li>
						</c:if>
					</c:forEach>

					<c:forEach var="file" items="${fileList}">
						<c:if test="${file.singleFile.location eq 'ppt'}">
							<li id="vdo1"><c:forEach var="i" begin="1"
									end="${file.singleFile.pageSize}">
									<c:choose>
										<c:when test="${i eq 1}">
											<a rel="view${file.singleFile.id}"
												href="sources/GroupContent/${sessionScope.sessionSelectedGroup}/${file.singleFile.id}/slide-${i}.png"
												title="" id="click"> <img alt=""
												src="sources/GroupContent/${sessionScope.sessionSelectedGroup}/${file.singleFile.id}/slide-1.png"
												width="128px" height="128px">
											</a>
										</c:when>
										<c:otherwise>
											<a rel="view${file.singleFile.id}"
												href="sources/GroupContent/${sessionScope.sessionSelectedGroup}/${file.singleFile.id}/slide-${i}.png"
												title="" class="viewPPT"> <img alt="" src=""
												width="128px" height="128px">
											</a>
										</c:otherwise>
									</c:choose>
								</c:forEach> <a
								href="sources/GroupContent/${sessionScope.sessionSelectedGroup}/${file.singleFile.id}.ppt">${file.singleFile.fileName }</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>

		</div>
		<div class="rightside">
			<div class="group-banner">
				<div class="group-title">Daftar Berkas</div>
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
