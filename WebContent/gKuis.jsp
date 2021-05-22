<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="head.html" />
<link rel="stylesheet" href="sources/css/jquery-ui.css">
<script src="sources/js/jquery-ui.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#CreateGroup").hide();
	$("#dialog-confirm").hide();
	<c:forEach var="score" items="${scoreList}">
	$("#scoreDetail${score.singleEvent.id}${score.singleEvent.participant.participantID}").hide();
	</c:forEach>
});
</script>
<script>
function confirm(){
    $( "#dialog-confirm" ).dialog({
      resizable: false,
      height:200,
      modal: true,
      buttons: {
        "Saya Yakin": function() {
          $( this ).dialog( "close" );
          $( "form" ).submit();
        },
        "Batalkan": function() {
          $( this ).dialog( "close" );
        }
      }
    });
}
</script>
<script>
function showScoreDEtail(eID, fID){
	var x="#scoreDetail"+eID+fID;
	$.post("Controller?action=showScoreDetail", {
		eventID : eID,
		feedbackID : fID
	}, function(data) {
		//alert(data);
		$(x).html(data);
	});
	$(x).toggle("slow");
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
				<h1 class="title">Daftar Kuis</h1>
				
				<ol class="quizList">
					<c:forEach var="QList" items="${quizList}">
						<li>${QList.title}</li>
						${QList.body}<br>
						Waktu : ${QList.startTime} - ${QList.endTime} <br>
						Durasi: ${QList.time/60} menit <br>
						<form action="Controller?action=attemptQuiz" method="post" id="form">
							<input type="hidden" value="${QList.id}" name="eventID">
						</form>
						<button class="btn btn-warning btn-sm" onclick="confirm()" id="doQuiz">Kerjakan Kuis</button> <br><br>
						
					</c:forEach>
				</ol>
			</div>
			<div class="partmidside">
				<h1 class="title">Lihat Nilai</h1>
				<ol class="quizList">
				<c:forEach var="score" items="${scoreList}">
					<li>${score.title}</li>
					<div>nilai ${score.singleEvent.participant.result.score}</div>
					<button class="btn btn-warning btn-sm" onclick="showScoreDEtail(${score.singleEvent.id},${score.singleEvent.participant.participantID})" id="doQuiz">Lihat detail</button>
 
					<div id="scoreDetail${score.singleEvent.id}${score.singleEvent.participant.participantID}">
						<c:forEach items="${scoreDetail}" var="ScoreDetails">
							
						</c:forEach>
					</div><br><br>
				</c:forEach>
				</ol>
			</div>
		</div>
		<div class="rightside">
			<div class="group-banner">
				<div class="group-title">Daftar Kuis</div>
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
	<div id="dialog-confirm" title="Konfirmasi pelaksanaan kuis?">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Setelah mengerjakan kuis anda tidak dapat mengerjakannya lagi. Anda yakin?</p>
</div>
	<footer>
		<%@ include file="footer.html"%>
	</footer>
</body>
</html>
