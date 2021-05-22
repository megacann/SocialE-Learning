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
	$("#divCat1").hide();
	$("#divCat2").hide();
	$("#dialog-upload").hide();	
});
</script>
<script>
function showCat(id){
	$("#inputCat"+id).val("");
	$("#divCat"+id).show();
	$("#spCat"+id).hide();
}

function saveCat(status,id){
	if (status==0) {
		$("#divCat"+id).hide();
		$("#spCat"+id).show();
	} else {
		var name = $("#inputCat"+id).val();
		if (name== "") {
			messageStatus("Nama kategori masih kosong.","multi-msgs");
			$('#inputCat'+id).focus();
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
									$('#inputCat'+id).val("");
									//add option
									$("#category"+id).append('<option value="'+data+'" selected id="opt'+id+data+'">'+name+'</option>');

									$("#divCat"+id).hide();
									$("#spCat"+id).show();									
								}
							});
		}
	}
	
}
function editBlog() {
	
	tinyMCE.triggerSave();
	var cid = $('#wikiId').val();
	var title = $('#titleBlog').val();
	var body = $("#bodyBlog").val();
	var cat = $("#category1").val();
	if (title == "") {
		messageStatus("Judul masih kosong.","multi-msgs1");
		$('#wikiName').focus();		
	} else if (body == "" || body.trim() == '<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n\n</body>\n</html>') {
		messageStatus("Isi masih kosong.","multi-msgs1");
		$('#wikiBody').focus();		
	} else {
		$
				.post(
						"Personal?action=uWiki",
						{
							wikiId : cid,
							wikiName : title,
							wikiBody : body,
							category : cat
						},
						function(data) {
							if (data != "0") {
								window.location.href = "Personal?action=vDetailWiki&wID="+cid;
							}
						});
	}

}
</script>
<script>
	jQuery.browser = {};
	$(document)
			.ready(
					function() {
						var wid = $('#idNews').val();
						$("#multiform2")
								.submit(
										function(e) {
											$("#multi-msgs")
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
																//alert(data);
																if (data != "0") {
																	if (data == "salah") {
																		messageStatus("Hanya berkas berekstensi jpg, png dan jpeg yang boleh diunggah.","multi-msgs");
																	} else if(data == "judulKosong"){
																		messageStatus("Judul masih kosong.","multi-msgs");
																	}
																	else {
																		messageStatus("Berhasil","multi-msgs");
																		window.location.href = "Personal?action=vDetailWiki&wID="+wid;}
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
				<c:forEach var="info" items="${contentList}">
				<c:if test="${info.type==2}">
					<div class="direction">
						<div class="desc">
						<div class="error">
							<p id="multi-msgs1"></p>
						</div>
								<input type="hidden" value="${info.getId()}" id="wikiId">
								<input id="titleBlog" class="inputJudul" type="text"
									value="${info.getTitle()}" placeholder="Judul Wiki"
									required="required" maxlength="50">
								<div class="w100 dispBlock">
									<div class="w65 fLeft">
										Kategori : <select name="category" id="category1"
											class="input-sm">
											<option value="1" id="opt11">-</option>
											<c:forEach items="${catList}" var="category" varStatus="i">
												<option value="${category.key}" id="opt1${category.key}" <c:if test="${category.value eq info.getCategory()}">selected</c:if>>${category.value}</option>
											</c:forEach>
										</select> <span onclick="showCat(1)" id="spCat1">Buat Kategori?</span>
										<div class="w100" id="divCat1">
											<input type="text" class="input-sm" maxlength="50"
												placeholder="tuliskan nama kategori" id="inputCat1">
											<button onclick="saveCat(1,1)" class="btn btn-info btn-sm">Simpan</button>
											<button class="btn btn-warning btn-sm" onclick="saveCat(0,1)">Batal</button>
										</div>
									</div>
									<div class="w35 fRight alRight">
										<button id="addMedia" onclick="upload()">Tambah
											Gambar</button>
									</div>
								</div>
								<textarea id="bodyBlog" placeholder="isi wiki">${info.getBody()}</textarea>
								<input type="button" value="Simpan"
									class="btn btn-default btn-sm" onclick="editBlog()">
							<%@ include file="dialogUpload.html"%>
						</div>
					</div>
					</c:if>
					<c:if test="${info.type==6}">
						<form method="post" enctype="multipart/form-data" id="multiform2"
						name="multiform" action="Personal?action=uNews">
						<input type="hidden" value="${info.getId()}" name="idNews" id="idNews">
						<input type="text" placeholder="Judul Berita"
							class="form-control input-sm w100" name="title" value="${info.getTitle()}">
												<textarea id="bodynews" placeholder="Deskripsi Berita"
							class="form-control input-sm w100" name="desc">${info.getBody()}</textarea>
						<div class="w100">
							Kategori : <select name="category2" id="category2" class="input-sm">
								<option value="1" id="opt21">-</option>
								<c:forEach items="${catList}" var="category" varStatus="i">
									<option value="${category.key}" id="opt2${category.key}" <c:if test="${category.value eq info.getCategory()}">selected</c:if>>${category.value}</option>
								</c:forEach>
							</select> <span onclick="showCat(2)" id="spCat2">Buat Kategori?</span>
						</div>
						<div class="w100">
							<input type="file" name="file" value="${info.singleFile.getFileName()}" />
						</div>
						<input type="submit" value="Unggah Poster"
							class="btn btn-info btn-sm" id="submit">
					</form>
					<div class="w100" id="divCat2">
						<input type="text" class="input-sm" maxlength="50"
							placeholder="tuliskan nama kategori" id="inputCat2">
						<button onclick="saveCat(1,2)" class="btn btn-info btn-sm">Simpan</button>
						<button class="btn btn-warning btn-sm" onclick="saveCat(0,2)">Batal</button>
					</div>

					<div id="multi-msgs"></div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
	<footer>
		<%@ include file="footer.html"%>
	</footer>
</body>
</html>