<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="head.html" />

<link rel="StyleSheet" href="sources/sources/AutoComplete/css/ui-lightness/jquery-ui-1.9.2.custom.min.css" type="text/css" media="all"/>
<link rel="StyleSheet" href="sources/sources/AutoComplete/css/jquery.tagedit.css" type="text/css" media="all"/>
<script type="text/javascript" src="sources/sources/AutoComplete/js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="sources/sources/AutoComplete/js/jquery.autoGrowInput.js"></script>
<script type="text/javascript" src="sources/sources/AutoComplete/js/jquery.tagedit.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$("#CreateGroup").hide();
	$("#saveAns").hide();
});
$(function() {
$( "#tagform-editonly" ).find('input.tag').tagedit({
autocompleteURL: 'Controller?action=inviteToGroup',
allowEdit: false,
allowAdd: false
});
});
</script>

<script>
$( document ).ready(function() {
	var myVar=setInterval(function(){myTimer()},1000);
	var x=$("#timeLeft").text();		
	//alert(x);
	function myTimer(){
		x=x-1;
		$("#timeLeft").text(x);
	}
});
</script>
<script>
	function updateQuiz(a,b){
		$.post("Controller?action=updateAnswer", {
			answer : b,
			qID : a
		}, function(data) {
			//alert('data=' + data);
			if (data == "true"){}
				$("#saveAns").text(data);
				$("#saveAns").fadeIn("slow");
				$("#saveAns").fadeOut("slow");
		});
	}
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
				<h1 class="title">Kuis I</h1> <span id="timeLeft">Waktu : ${time} detik</span>
				<div class="row">
					<div id="saveAns"></div>
					<c:forEach items="${event.question}" var="question" varStatus="i">
						<div id="soal" class="question">
							<div class="soal">${i.index+1}. ${question.question}</div>
							<input type="radio" name="answer${question.id}" value="A" onClick="updateQuiz(${question.id},'A')">${question.a}<br>
							<input type="radio" name="answer${question.id}" value="B" onClick="updateQuiz(${question.id},'B')">${question.b}<br>
							<input type="radio" name="answer${question.id}" value="C" onClick="updateQuiz(${question.id},'C')">${question.c}<br>
							<input type="radio" name="answer${question.id}" value="D" onClick="updateQuiz(${question.id},'D')">${question.d}<br>
							<input type="radio" name="answer${question.id}" value="E" onClick="updateQuiz(${question.id},'E')">${question.e}<br>
						</div>
					</c:forEach>
				</div>
				<div class="row"><button class="btn btn-info btn-sm" onclick="saveQuiz()">Simpan</button></div>
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
