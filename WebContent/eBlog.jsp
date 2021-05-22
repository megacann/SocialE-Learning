<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="head.html" />
<script type="text/javascript">
$(document).ready(function() {
	$("#CreateGroup").hide();
	$("#divCat").hide();
	$("#dialog-upload").hide();	
});

function showCat(){
	$("#inputCat").val("");
	$("#divCat").show();
	$("#spCat").hide();
}

function saveCat(id){
	if (id==0) {
		$("#divCat").hide();
		$("#spCat").show();
	} else {
		var name = $("#inputCat").val();
		if (name== "") {
			messageStatus("Nama kategori masih kosong.","multi-msgs");
			$('#inputCat').focus();
		} else {
			$
					.post(
							"Personal?action=aCategory",
							{
								nameCat : name
							},
							function(data) {
								if (data != "0") {
									messageStatus("Kategori berhasil ditambahkan.","multi-msgs");
									// Make input empty
									$('#inputCat').val("");
									//add option
									$("#category").append('<option value="'+data+'" selected id="opt'+data+'">'+name+'</option>');									


									$("#divCat").hide();
									$("#spCat").show();}
							});
		}
	}
	
}
function editBlog() {
	
	tinyMCE.triggerSave();
	var cid = $('#blogId').val();
	var title = $('#blogName').val();
	var body = $("#bodyBlog").val();
	var cat = $("#category").val();
	if (title == "") {
		messageStatus("Judul masih kosong.","multi-msgs");
		$('#blogName').focus();
	} else if (body == "" || body.trim() == '<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n\n</body>\n</html>') {
		messageStatus("Isi masih kosong.","multi-msgs");
		$('#blogBody').focus();
	} else {
		$
				.post(
						"Personal?action=uBlog",
						{
							blogId : cid,
							blogName : title,
							blogBody : body,
							category : cat
						},
						function(data) {
							if (data != "0") {
								window.location.href = "Personal?action=vDetailBlog&bID="+cid;
							}
						});
	}

}</script>
<script type="text/javascript" src="sources/tinymce/tinymce.min.js"></script>
<script type="text/javascript" src="sources/js/tinyMCE.js"></script>
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
				<c:forEach var="info" items="${contentList}">
					<div class="direction">
						<div class="desc">
						<div class="error">
							<p id="multi-msgs"></p>
						</div>
								<input type="hidden" value="${info.getId()}" id="blogId">
								<input class="inputJudul" type="text"
									value="${info.getTitle()}" placeholder="Judul"
									required="required" maxlength="50" id="blogName">
								<div class="w100 dispBlock">
									<div class="w65 fLeft">
										Kategori : <select name="category" id="category"
											class="input-sm">
											<option value="1" id="opt1">-</option>
											<c:forEach items="${catList}" var="category" varStatus="i">
												<option value="${category.key}" id="opt${category.key}" <c:if test="${category.value eq info.getCategory()}">selected</c:if>>${category.value}</option>
											</c:forEach>
										</select> <span onclick="showCat()" id="spCat">Buat Kategori?</span>
										<div class="w100" id="divCat">
											<input type="text" class="input-sm" maxlength="50"
												placeholder="tuliskan nama kategori" id="inputCat">
											<button onclick="saveCat(1)" class="btn btn-info btn-sm">Simpan</button>
											<button class="btn btn-warning btn-sm" onclick="saveCat(0)">Batal</button>
										</div>
									</div>
									<div class="w35 fRight alRight">
										<button id="addMedia" onclick="upload()">Tambah
											Gambar</button>
									</div>
								</div>
								<textarea id="bodyBlog" placeholder="isi blog" name="blogBody">${info.getBody()}</textarea>
								<input type="button" value="Simpan"
									class="btn btn-default btn-sm" onclick="editBlog()">
							<%@ include file="dialogUpload.html"%>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<!-- /col-md-9 -->
		<div class="rightside">
			<div class="group-banner">
				<div class="group-title">Daftar Blog</div>
				<ul id="listTitleBlog">
				<c:forEach items="${titleList}" var="blogList" varStatus="i">
						<li id="bID=${blogList.key}"><a
							href="Personal?action=vDetailBlog&bID=${blogList.key}">
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