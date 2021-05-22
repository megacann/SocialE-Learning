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
</script>
<script>
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
									$("#spCat").show();									
								}
							});
		}
	}
	
}

</script>
<script type="text/javascript" src="sources/tinymce/tinymce.min.js"></script>
<script type="text/javascript" src="sources/js/tinyMCE.js"></script>
</head>
<body>
	<!--check login -->
	<c:if test="${empty userInfo or userInfo.username ne 'pemkot'}">
		<jsp:forward page="login.jsp" />
	</c:if>

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
		<div class="midrightside">
			<div class="partmidside">
			<div class="error">
							<p id="multi-msgs"></p>
						</div>
				<input type="hidden" value="${sessionName}" name="Fullname">
				<button onclick="OpenDiv('#addWiki');" id="addNewsBtn"
					class="glyphicon glyphicon-pencil btn-delete btn btn-sm">
					Buat Wiki</button>
				<div id="addNews" style="display: none;">
					<input id="titleBlog" class="inputJudul" type="text"
						placeholder="Judul Blog" required="required" maxlength="50">
					<div class="w100 dispBlock">
						<div class="w65 fLeft">
							Kategori : <select name="category" id="category" class="input-sm">
								<option value="1" id="opt1">-</option>
								<c:forEach items="${catList}" var="category" varStatus="i">
									<option value="${category.key}" id="opt${category.key}">${category.value}</option>
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
							<button id="addMedia" onclick="upload()">Tambah Gambar</button>
						</div>
					</div>
					<textarea id="bodyBlog" placeholder="isi blog"></textarea>
					<input type="button" value="Simpan" class="btn btn-default btn-sm"
						onclick="addWiki()">
					<%@ include file="dialogUpload.html"%>
				</div>

				<h1 class="title">Daftar Wiki</h1>
				<table class="w100" id="wikiList">
					<tr>
						<th style="width: 25%;">Judul</th>
						<th style="width: 40%;">Isi</th>
						<th style="width: 20%;">Tanggal</th>
						<th style="width: 10%;">Editor</th>
						<th style="width: 5%;">Aksi</th>
					</tr>
					<c:forEach var="info" items="${contentList}">
						<tr id="wiki${info.getId()}">
							<td><a
								href="Personal?action=vDetailWiki&wID=${info.getId()}">${info.getTitle()}</a></td>
							<td>${info.getBody()}</td>
							<td>${info.getPostedDate()}</td>
							<td>${info.user.getfName()}</td>
							<td><span class="glyphicon glyphicon-trash" id="delIcon"
								onclick="delWiki(${info.getId()})"></span> <a
								href="Personal?action=editWiki&wID=${info.getId()}"><span
									class="glyphicon glyphicon-edit"></span></a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<footer>
		<%@ include file="footer.html"%>
	</footer>
</body>
</html>