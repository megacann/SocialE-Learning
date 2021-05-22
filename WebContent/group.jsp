<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page autoFlush="true" buffer="1094kb"%>  
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
			
		$("a[rel=mp4Baru]").fancybox({
				width  : 600,
			    height : 300,
			    type   :'iframe'
		});
		$("#pdfBaru").fancybox({
		    width  : 600,
		    height : 300,
		    type   :'iframe'
		});
		$("a[rel=pptBaru]").fancybox();
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
<link rel="stylesheet" href="sources/css/tab.css">
<script type="text/javascript">
$(document).ready(function() {
	$("#CreateGroup").hide();
	$("#divCat1").hide();
	$("#divCat2").hide();
	$("#divCat3").hide();
	$("#divCat4").hide();
	$("#dialog-upload").hide();	
	$("#dialog-confirm").hide();
});
</script>
<script>
function confirmKuis(){
    $( "#dialog-confirm" ).dialog({
      resizable: false,
      height:200,
      modal: true,
      buttons: {
        "Saya Yakin": function() {
          $( this ).dialog( "close" );
          $( "#formKuis" ).submit();
        },
        "Batalkan": function() {
          $( this ).dialog( "close" );
        }
      }
    });
}
</script>
<script>
function showCat(id){
	$("#inputCat"+id).val("");
	$("#divCat"+id).show();
	$("#spCat"+id).hide();
}

function saveCat(stat,id){
	if (stat==0) {
		$("#divCat"+id).hide();
		$("#spCat"+id).show();
	} else {
		var name = $("#inputCat"+id).val();
		if (name== "") {
			$('#inputCat'+id).focus();
			messageStatus("Berhasil","multi-msgs"+id);
		} else {
			$
					.post(
							"Personal?action=aCategory",
							{
								nameCat : name
							},
							function(data) {
								if (data != "0") {
									messageStatus("Kategori berhasil ditambahkan.","multi-msgs"+id);
									// Make input empty
									$('#inputCat'+id).val("");
									//add option
									$("#category1").append('<option value="'+data+'" selected id="opt'+id+data+'">'+name+'</option>');
									$("#category2").append('<option value="'+data+'" selected id="opt'+id+data+'">'+name+'</option>');
									$("#category3").append('<option value="'+data+'" selected id="opt'+id+data+'">'+name+'</option>');
									$("#category4").append('<option value="'+data+'" selected id="opt'+id+data+'">'+name+'</option>');

									$("#divCat"+id).hide();
									$("#spCat"+id).show();
								}
							});
		}
	}
	
}
</script>
<script type="text/javascript" src="sources/tinymce/tinymce.min.js"></script>
<script type="text/javascript" src="sources/js/tinyMCE.js"></script>
<script>
	jQuery.browser = {};
	$(document)
			.ready(
					function() {
						$("#CreateGroup").hide();
						$("#quiz_1").hide();
						$("#quiz_2").hide();
						$("#quiz_3").hide();
						$("#dialog-message" ).text("");
						$("#multi-msgs4").text("");
						$("#submit").hide();
						$("#btnFile").click(function(){
							$("#submit").show();		  
						});
						
						$("#multiform2")
						.submit(
								function(e) {
									$("#multi-msgs2")
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
														
														if (data != "0" ) {
												if (data == "salah") {
													messageStatus("Hanya berkas berekstensi mp4, swf, pdf, ppt, jpg, png dan jpeg yang boleh diunggah.","multi-msgs2");
												} else if(data == "judulKosong"){
													messageStatus("Judul masih kosong.","multi-msgs2");
												}
												else {
													$(data).prependTo($('#contentList'));
													$("#multi-msgs2").html("");
													messageStatus("Berhasil","multi-msgs2");
													$('textarea#bodyBlog2').val("");
													$('input[name=title]').val("");
													$('input[name=file]').val("");
												}
											} else {
												messageStatus("Gagal","multi-msgs2");
											}
														
													},
													error : function(
															jqXHR,
															textStatus,
															errorThrown) {
														messageStatus("Berkas belum dipilih.","multi-msgs2");
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
													messageStatus("Gagal","multi-msgs2");
												});

									}

								});
						

					});
	$(function() {
		$('article.tabs').tabs();
	});
</script>
<script>
	function warningQuestion(){
		alert("Lengkapi tanggal dan deskripsi terlebih dahulu!!!");
	}
	function createEvents(){
		$("#quiz_1").show();
		$("#quiz_2").hide();
		$("#quiz_3").hide();
	}
	function createQuestion(){
		$("#quiz_2").show();
		$("#quiz_1").hide();
		$("#quiz_3").hide();
	}
	function inviteMember(){
		$("#quiz_3").show();
		$("#quiz_2").hide();
		$("#quiz_1").hide();
	}
	function tambahSoal(){
		var $input = $("<li><div class='soal'><textarea rows='3' class='w100' name='soal'></textarea></div> A <input type='text' name='answer1'><br>B <input type='text' name='answer2'><br>C <input type='text' name='answer3'><br>D <input type='text' name='answer4'><br>E <input type='text' name='answer5'><br></li>"+
				"Kunci Jawaban : <select name='key'><option value='A'>A</option><option value='B'>B</option><option value='C'>C</option><option value='D'>D</option><option value='E'>E</option></select><br>");
		$("#quiz").append($input);
		//alert("hello");
	}
	function simpanWaktu(){
		var a=$("input[name=QuizTitle").val();
		var b=$("input[name=QuizDesc").val();
		var c=$("input[name=QuizStart").val();
		var d=$("input[name=quizTimeStart").val();
		var e=$("input[name=QuizEnd").val();
		var f=$("input[name=quizTimeEnd").val();
		var userN=$("#usernameCreator").val();
		var cat = $("#category4").val();
		if (a == "" || c=="" || e=="") {messageStatus("Data tidak boleh kosong","multi-msgs4");} else {
			$.post("Controller?action=createQuizEvent", {
				QuizTitle : a,
				QuizDesc : b,
				QuizStart : c,
				quizTimeStart : d,
				QuizEnd : e,
				quizTimeEnd : f,
				quizCat : cat
			}, function(data, status) {
				
				if (data == "false") {
					messageStatus("Gagal","multi-msgs4");
				} else {
					messageStatus("Berhasil","multi-msgs4");
					$('#buatPertanyaan').attr('class', 'btn btn-info btn-sm');
					$('#buatPertanyaan').attr('onclick', 'createQuestion()');
					createQuestion();
				}
				
			});
		}
		
	}
	function SimpanJawaban(){
		var eventID=$("#eventID").val();
		var answer1Array = new Array();
		var answer2Array = new Array();
		var answer3Array = new Array();
		var answer4Array = new Array();
		var answer5Array = new Array();
		var soalArray = new Array();
		var keyArray = new Array();
		$("input[name=answer1]").each(function() { answer1Array.push($(this).val()); });
		$("input[name=answer2]").each(function() { answer2Array.push($(this).val()); });
		$("input[name=answer3]").each(function() { answer3Array.push($(this).val()); });
		$("input[name=answer4]").each(function() { answer4Array.push($(this).val()); });
		$("input[name=answer5]").each(function() { answer5Array.push($(this).val()); });
		$("textarea[name=soal]").each(function() { soalArray.push($(this).val()); });
		$("select[name=key]").each(function() { keyArray.push($(this).val()); });
		//alert("A :" +answer1Array.toString());
		//alert("B :" +answer2Array);
		//alert("key :" +keyArray.toString());
		$.post("Controller?action=createQuestion", {
			answer1 : answer1Array.toString(),
			answer2 : answer2Array.toString(),
			answer3 : answer3Array.toString(),
			answer4 : answer4Array.toString(),
			answer5 : answer5Array.toString(),
			soal : soalArray.toString(),
			key : keyArray.toString()
		}, function(data) {
			$("#tabs-min-4").html("Kuis Berhasil Disimpan.");
			
		});
	}
	function uploadKonten(){
		
		

	}
	
</script>
</head>
<body>
	<!--check login -->
	<c:if test="${empty userInfo}">
		<jsp:forward page="login.jsp" />
	</c:if>
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
				<article id="tabs-min" class="tabs">
					<ul>
						<li><a href="#tabs-min-1">#Bagikan Tulisan</a></li>
						<li><a href="#tabs-min-2">#Bagikan File</a></li>
						<li><a href="#tabs-min-3">#Buat Acara</a></li>
						<c:if test="${sessionRoleinGroup eq 1 }">
							<li><a href="#tabs-min-4">#Buat Kuis</a></li>
						</c:if>
					</ul>
					<div id="tabs-min-1">
						<div class="error">
							<p id="multi-msgs1"></p>
						</div>
						<input id="titleBlog" class="inputJudul" type="text"
							placeholder="Judul Blog" required="required" maxlength="50">
						<div class="w100 dispBlock">
							<div class="w65 fLeft">
								Kategori : <select name="category" id="category1"
									class="input-sm">
									<option value="1" id="opt11">-</option>
									<c:forEach items="${catList}" var="category" varStatus="i">
										<option value="${category.key}" id="opt1${category.key}">${category.value}</option>
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
								<button id="addMedia" onclick="upload()">Tambah Gambar</button>
							</div>
						</div>
						<textarea id="bodyBlog" placeholder="isi blog"></textarea>
						<input type="button" value="Simpan" class="btn btn-default btn-sm"
							onclick="addBlogGroup('${sessionUsername}')">
						<%@ include file="dialogUpload.html"%>
					</div>
					<div id="tabs-min-2">
						<div class="error">
							<p id="multi-msgs2"></p>
						</div>
						<form method="post" enctype="multipart/form-data" id="multiform2"
							name="multiform2" action="Controller?action=uploadContent">
							<input type="text" placeholder="Judul Konten"
								class="form-control input-sm w100" name="title" id="titleBlog2">
							<textarea id="bodyBlog2" placeholder="Deskripsi Konten"
								class="form-control input-sm w100" name="desc"></textarea>
							<div class="w100">
								Kategori : <select name="category2" id="category2"
									class="input-sm">
									<option value="1" id="opt21">-</option>
									<c:forEach items="${catList}" var="category" varStatus="i">
										<option value="${category.key}" id="opt2${category.key}">${category.value}</option>
									</c:forEach>
								</select> <span onclick="showCat(2)" id="spCat2">Buat Kategori?</span>
							</div>
							<div class="w100">
								<input type="file" name="file" />
							</div>
							<input type="submit" value="Unggah konten"
								class="btn btn-info btn-sm" id="submitKonten">
						</form>
						<div class="w100" id="divCat2">
							<input type="text" class="input-sm" maxlength="50"
								placeholder="tuliskan nama kategori" id="inputCat2">
							<button onclick="saveCat(1,2)" class="btn btn-info btn-sm">Simpan</button>
							<button class="btn btn-warning btn-sm" onclick="saveCat(0,2)">Batal</button>
						</div>
						<div class="error">
							<p id="multi-msgs2"></p>
						</div>
					</div>
					<div id="tabs-min-3">
						<div class="error">
							<p id="multi-msgs3"></p>
						</div>
						<input type="hidden" value="${sessionUsername}"
							id="usernameCreator"> <input type="text" name="eventName"
							class="form-control input-sm w100" placeholder="Nama acara">
						<input type="text" name="eventDetail"
							class="form-control input-sm w100" placeholder="Deskripsi acara">
						<input type="text" name="eventPlace"
							class="form-control input-sm w100" placeholder="Tempat acara">
						<div class="row">
							<label class="label w20">Mulai</label> <input type="date"
								name="eventStart" class="input-sm w35" placeholder="Mulai acara">
							<input type="time" name="timeStart" class="input-sm w20"
								placeholder="Mulai acara">
						</div>
						<div class="row">
							<label class="label w20">Selesai</label> <input type="date"
								name="eventEnd" class="input-sm w35" placeholder="Selesai acara">
							<input type="time" name="timeEnd" class="input-sm w20"
								placeholder="Selesai acara">
						</div>
						<div class="row">
							Kategori : <select name="category" id="category3"
								class="input-sm">
								<option value="1" id="opt31">-</option>
								<c:forEach items="${catList}" var="category" varStatus="i">
									<option value="${category.key}" id="opt3${category.key}">${category.value}</option>
								</c:forEach>
							</select> <span onclick="showCat(3)" id="spCat3">Buat Kategori?</span>
							<div class="w100" id="divCat3">
								<input type="text" class="input-sm" maxlength="50"
									placeholder="tuliskan nama kategori" id="inputCat3">
								<button onclick="saveCat(1,3)" class="btn btn-info btn-sm">Simpan</button>
								<button class="btn btn-warning btn-sm" onclick="saveCat(0,3)">Batal</button>
							</div>
						</div>
						<div class="row">
							<input type="button" value="Buat acara"
								class="btn btn-info btn-sm" onclick="createEvnt()">
						</div>

					</div>
					<c:if test="${sessionRoleinGroup eq 1 }">
						<div id="tabs-min-4">
							<div class="error">
								<p id="multi-msgs4"></p>
							</div>
							<input type="button" value="#1 Tentukan Waktu"
								class="btn btn-info btn-sm" id="tentukanWaktu"
								onclick="createEvents()">
							<div id="quiz_1">
								<h1>Tentukan Nama, Waktu dan Tanggal Kuis</h1>
								<input type="text" name="QuizTitle"
									class="form-control input-sm w100" placeholder="Nama Kuis">
								<input type="text" name="QuizDesc"
									class="form-control input-sm w100" placeholder="Deskripsi Kuis">
								<div class="row">
									<label class="label w20">Mulai</label> <input type="date"
										name="QuizStart" class="input-sm w35"
										placeholder="Tanggal Mulai"> <input type="time"
										name="quizTimeStart" class="input-sm w20"
										placeholder="Mulai acara">
								</div>
								<div class="row">
									<label class="label w20">Selesai</label> <input type="date"
										name="QuizEnd" class="input-sm w35"
										placeholder="Tanggal Selesai"> <input type="time"
										name="quizTimeEnd" class="input-sm w20"
										placeholder="Selesai acara">
								</div>
								<div class="row">
									Kategori : <select name="category" id="category4"
										class="input-sm">
										<option value="1" id="opt1">-</option>
										<c:forEach items="${catList}" var="category" varStatus="i">
											<option value="${category.key}" id="opt${category.key}">${category.value}</option>
										</c:forEach>
									</select> <span onclick="showCat(4)" id="spCat4">Buat Kategori?</span>
									<div class="w100" id="divCat4">
										<input type="text" class="input-sm" maxlength="50"
											placeholder="tuliskan nama kategori" id="inputCat4">
										<button onclick="saveCat(1,4)" class="btn btn-info btn-sm">Simpan</button>
										<button class="btn btn-warning btn-sm" onclick="saveCat(0,4)">Batal</button>
									</div>
								</div>
								<input type="button" name="simpanWaktu" value="Simpan Waktu"
									class="btn btn-default btn-sm" onclick="simpanWaktu()">
							</div>
							<c:set var="waktu" scope="session"
								value='${sessionScope["seesionEID"]}' />
							<c:if test="${empty waktu}">
								<input type="button" value="#2 Buat Pertanyaan"
									class="btn btn-warning btn-sm" id="buatPertanyaan"
									onclick="warningQuestion()">

							</c:if>
							<c:if test="${!empty waktu}">
								<input type="button" value="#2 Buat Pertanyaan"
									class="btn btn-warning btn-sm" id="buatPertanyaan"
									onclick="createQuestion()">

							</c:if>
							<div id="quiz_2">
								<h1>Buat pertanyaan</h1>
								<ol class="quiz" id="quiz">
									<li>
										<div class="soal">
											<input type="hidden" id="eventID">
											<textarea rows="3" class="w100" name="soal"></textarea>
										</div> A <input type="text" name="answer1"><br> B <input
										type="text" name="answer2"><br> C <input
										type="text" name="answer3"><br> D <input
										type="text" name="answer4"><br> E <input
										type="text" name="answer5"><br> Kunci Jawaban : <select
										name="key">
											<option value='A'>A</option>
											<option value='B'>B</option>
											<option value='C'>C</option>
											<option value='D'>D</option>
											<option value='E'>E</option>
									</select><br>
									</li>
								</ol>
								<div>
									<input type="button" value="Tambah Soal Baru"
										class="btn btn-warning btn-sm" onclick="tambahSoal()">
									<input type="button" value="Simpan Soal"
										onclick="SimpanJawaban()" class="btn btn-info btn-sm">
								</div>
							</div>
							<!-- <input type="button" value="#3 Undang anggota group untuk mengerjakan" class="btn btn-info btn-sm" id="buatPertanyaan" onclick="inviteMember()"> -->
							<!-- <div id="quiz_3"> -->
							<!-- <h1>Undang anggota group yang lain untuk mengerjakan kuis</h1> -->
							<!-- <input type="time" name="quizTimeEnd" class="form-control input-sm w100" placeholder="Selesai acara"> -->
							<!-- </div>   -->
						</div>
					</c:if>
				</article>
			</div>
			<div class="partmidside">
				<ul class="timeline" id="contentList">
					<c:forEach var="content" items="${contentList}" varStatus="i">
						<c:if test="${content.type==3}">
							<li class="li" id="list${content.getId()}">
								<div class="direction">
									<div class="flag-wrapper">
										<span class="flag"><a
											href="Personal?action=vUser&username=${content.user.username}">${content.user.fName}
												${content.user.lName}</a> membuat tulisan baru</span>
									</div>
									<div class="desc">
										<h2>
											<a
												href="Controller?action=vDetailCourse&cID=${content.getId()}">${content.title}</a>
											<c:if
												test="${content.user.username eq sessionUsername or sessionRoleinGroup eq 1}">
												<span class="glyphicon glyphicon-remove-circle" id="delIcon"
													onclick="delBlog(${content.getId()})"></span>
												<a
													href="Controller?action=editCourse&cID=${content.getId()}"><span
													class="glyphicon glyphicon-edit" id="delIcon"></span></a>
											</c:if>
										</h2>
										<div>${content.body }</div>
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
													<c:if
														test="${com.getCommentator() eq sessionName or sessionRoleinGroup eq 1}">
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
												${content.user.lName}</a> mengupload berkas baru</span>
									</div>
									<div class="desc">
										<h2>
											<a
												href="Controller?action=vDetailCourse&cID=${content.getId()}">${content.title}</a>
											<c:if
												test="${content.user.username eq sessionUsername or sessionRoleinGroup eq 1}">
												<span class="glyphicon glyphicon-remove-circle" id="delIcon"
													onclick="delBlog(${content.getId()})"></span>
												<a
													href="Controller?action=editCourse&cID=${content.getId()}"><span
													class="glyphicon glyphicon-edit" id="delIcon"></span></a>
											</c:if>
										</h2>
										<p>${content.body}</p>
										<c:if
											test="${content.singleFile.location eq 'jpg' or content.singleFile.location eq 'jpeg' or content.singleFile.location eq 'png'}">
											<a rel="example_group"
												href="sources/GroupContent/${sessionSelectedGroup}/${content.singleFile.id}.${content.singleFile.location}"
												title="">
												<button
													class="glyphicon glyphicon-file btn-delete btn btn-sm">
													Lihat Berkas</button>
											</a>
										</c:if>
										<c:if
											test="${content.singleFile.location eq 'mp4' or content.singleFile.location eq 'flv'  or content.singleFile.location eq 'swf'}">
											<a rel="mp4${content.singleFile.id}"
												href="sources/GroupContent/${sessionSelectedGroup}/${content.singleFile.id}.${content.singleFile.location}"
												class="video_link"><button
													class="glyphicon glyphicon-file btn-delete btn btn-sm">
													Lihat Berkas</button></a>
										</c:if>
										<c:if test="${content.singleFile.location eq 'pdf'}">
											<a id="pdfClass${content.singleFile.id}"
												href="sources/GroupContent/${sessionSelectedGroup}/${content.singleFile.id}.${content.singleFile.location}"><button
													class="glyphicon glyphicon-file btn-delete btn btn-sm">
													Lihat Berkas</button></a>
										</c:if>
										<c:if test="${content.singleFile.location eq 'ppt'}">
											<c:forEach var="i" begin="1"
												end="${content.singleFile.pageSize}">
												<c:choose>
													<c:when test="${i eq 1}">
														<a rel="view${content.singleFile.id}"
															href="sources/GroupContent/${sessionSelectedGroup}/${content.singleFile.id}/slide-${i}.png"
															title="" id="click"><button
																class="glyphicon glyphicon-file btn-delete btn btn-sm">
																Lihat Berkas</button> </a>
													</c:when>
													<c:otherwise>
														<a rel="view${content.singleFile.id}"
															href="sources/GroupContent/${sessionSelectedGroup}/${content.singleFile.id}/slide-${i}.png"
															title="" class="viewPPT">
															<button
																class="glyphicon glyphicon-file btn-delete btn btn-sm">
																Lihat Berkas</button>
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
													<c:if
														test="${com.getCommentator() eq sessionName or sessionRoleinGroup eq 1}">
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
							<li class="li" id="list${content.getId()}"><c:forEach
									var="event" items="${content.event}">
									<div class="direction">

										<div class="flag-wrapper">

											<c:choose>
												<c:when test="${event.type==1}">
													<span class="flag"><a
														href="Personal?action=vUser&username=${content.user.username}">${content.user.fName}
															${content.user.lName}</a> membuat kuis baru</span>
												</c:when>
												<c:otherwise>
													<span class="flag"><a
														href="Personal?action=vUser&username=${content.user.username}">${content.user.fName}
															${content.user.lName}</a> membuat acara baru</span>
												</c:otherwise>
											</c:choose>

										</div>
										<div class="desc">
											<h2>
												<a
													href="Controller?action=vDetailCourse&cID=${content.getId()}">${content.title}</a>
												<c:if
													test="${com.getCommentator() eq sessionName or sessionRoleinGroup eq 1}">
													<span class="glyphicon glyphicon-remove-circle"
														id="delIcon" onclick="delBlog(${content.getId()})"></span>
													<a
														href="Controller?action=editCourse&cID=${content.getId()}"><span
														class="glyphicon glyphicon-edit" id="delIcon"></span></a>
												</c:if>
											</h2>
											<div class="row ">${content.body}</div>
											<div class="row" id="konfirm${content.id}">
												<c:choose>
													<c:when test="${event.type == 1}">
														<c:choose>
															<c:when test="${event.status==1}">
																<span class="eventC">Anda sudah mengerjakan</span>
															</c:when>
															<c:otherwise>
																<form action="Controller?action=attemptQuiz"
																	method="post" id="formKuis">
																	<input type="hidden" value="${event.id}" name="eventID">
																</form>
																<button class="btn btn-warning btn-sm"
																	onclick="confirmKuis()" id="doQuiz">Kerjakan
																	Kuis</button>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${event.status==1}">
																<span class="eventC">Anda Tidak Akan Menghadiri
																	Acara</span>
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
														<c:if
															test="${com.getCommentator() eq sessionName or sessionRoleinGroup eq 1}">
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
								</c:forEach></li>
						</c:if>
					</c:forEach>
				</ul>
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
	<div id="dialog-confirm" title="Konfirmasi pelaksanaan kuis?">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span>Setelah
			mengerjakan kuis anda tidak dapat mengerjakannya lagi. Anda yakin?
		</p>
	</div>
	<!-- /container -->
	<footer>
		<%@ include file="footer.html"%>
	</footer>
</body>
</html>
