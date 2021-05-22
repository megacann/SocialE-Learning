<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="head.html" />
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
	
}</script>
<script>
	jQuery.browser = {};
	$(document)
			.ready(
					function() {
						$("#addNews").hide();
						$("#divCat").hide();
						$("#multiform")
								.submit(
										function(e) {
											$("#multi-msg")
													.html(
															"<img src='sources/images/loading.gif'/>");

											var formObj = $(this);
											var formURL = formObj
													.attr("action");

											if (window.FormData !== undefined) // for HTML5 browsers
											//			if(false) 

											{
												//alert ("1");
												var formData = new FormData(
														this);
												$
														.ajax({
															url : formURL,
															type : 'POST',
															data : formData,
															mimeType : "multipart/form-data",
															contentType : false,
															cache : false,
															processData : false,
															success : function(
																	data,
																	textStatus,
																	jqXHR) {
																if (data != "0") {
																	if (data == "salah") {
																		messageStatus("Hanya berkas berekstensi jpg, png, bmp, gif, tiff dan jpeg yang boleh diunggah.","multi-msgs");
																	} else if(data == "judulKosong"){
																		messageStatus("Nama masih kosong.","multi-msgs");
																	}
																	else {
																	$("#newsList tr:first").after(data);
																	// Make input empty
																	messageStatus("Berhasil","multi-msgs");
																	$('#bodyBlog').val("");
																	$('input[name=title]').val("");
																	$('input[name=file]').val("");
																	$("#multi-msg")
																	.html('');
																	$("#addNews").hide();
																	$("#addNewsBtn").show();}
																	
																} else {
																	messageStatus("Gagal","multi-msgs");
																}
																
															},
															error : function(
																	jqXHR,
																	textStatus,
																	errorThrown) {
																messageStatus("Gambar belum dipilih.","multi-msgs");
															}
														});
												e.preventDefault();
												e.unbind();
											} else //for olden browsers
											{
												//generate a random id
												var iframeId = 'unique'
														+ (new Date().getTime());

												//create an empty iframe
												var iframe = $('<iframe src="javascript:false;" name="'+iframeId+'" />');

												//hide it
												iframe.hide();

												//set form target to iframe
												formObj
														.attr('target',
																iframeId);

												//Add iframe to body
												iframe.appendTo('body');
												iframe
														.load(function(e) {
															var doc = getDoc(iframe[0]);
															var docRoot = doc.body ? doc.body
																	: doc.documentElement;
															var data = docRoot.innerHTML;
															messageStatus("Gagal","multi-msgs");
														});

											}

										});

					});
</script>
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
				<input type="hidden" value="${sessionName}" name="Fullname">
				<button id="addNewsBtn" onclick="OpenDiv()"
					class="glyphicon glyphicon-pencil btn-delete btn btn-sm">
					Buat Berita</button>
				<div id="addNews">
					<form method="post" enctype="multipart/form-data" id="multiform"
						name="multiform" action="Personal?action=uploadNews">
						<input type="text" placeholder="Judul Berita"
							class="form-control input-sm w100" name="title">
												<textarea id="bodyBlog" placeholder="Deskripsi Berita"
							class="form-control input-sm w100" name="desc"></textarea>
						<div class="w100">
							Kategori : <select name="category" id="category" class="input-sm">
								<option value="1" id="opt1">-</option>
								<c:forEach items="${catList}" var="category" varStatus="i">
									<option value="${category.key}" id="opt${category.key}">${category.value}</option>
								</c:forEach>
							</select> <span onclick="showCat()" id="spCat">Buat Kategori?</span>
						</div>
						<div class="w100">
							<input type="file" name="file" />
						</div>
						<input type="submit" value="Unggah Poster"
							class="btn btn-info btn-sm" id="submit">
					</form>
					<div class="w100" id="divCat">
						<input type="text" class="input-sm" maxlength="50"
							placeholder="tuliskan nama kategori" id="inputCat">
						<button onclick="saveCat(1)" class="btn btn-info btn-sm">Simpan</button>
						<button class="btn btn-warning btn-sm" onclick="saveCat(0)">Batal</button>
					</div>

					<div id="multi-msgs"></div>
				</div>
				<h1 class="title">Daftar Berita</h1>
				<table class="w100" id="newsList">
					<tr>
						<th style="width: 20%;">Judul</th>
						<th style="width: 40%;">Deskripsi</th>
						<th style="width: 15%;">Lampiran</th>
						<th style="width: 20%;">Tanggal</th>
						<th style="width: 5%;">Aksi</th>
					</tr>
					<c:forEach var="info" items="${contentList}">
						<tr id="wiki${info.getId()}">
							<td><a
								href="Personal?action=vDetailWiki&wID=${info.getId()}">${info.getTitle()}</a></td>
							<td>${info.getBody()}</td>
							<td><img src="${info.singleFile.getLocation()}" width="80%"></td>
							<td>${info.getPostedDate()}</td>
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