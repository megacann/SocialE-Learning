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
			<c:set scope="session" var="profile" value="${userProfile}" />
			<div class="navbar navbar-default">
				<div class="pFoto">
					<img src="${profile.getFoto()}">
					<div class="pNama">${profile.getfName()}
						${profile.getlName()}</div>
					<div id="action" class="btnProfil">
						<c:if test="${profile.getMembershipStatus() eq 0}">
							<button onclick="friendAction('${profile.getUsername()}',0)"
								class="btn btn-info btn-block btn-xs">Tambahkan Teman</button>
						</c:if>
						<c:if test="${profile.getMembershipStatus() eq 1}">
							<button onclick="friendAction('${profile.getUsername()}',1)"
								class="btn btn-warning btn-block btn-xs">Hapus
								Pertemanan</button>
						</c:if>
						<c:if test="${profile.getMembershipStatus() eq 2}">
							<button onclick="friendAction('${profile.getUsername()}',2)"
								class="btn btn-info btn-block btn-xs">Konfirmasi</button>
							<button onclick="friendAction('${profile.getUsername()}',3)"
								class="btn btn-warning btn-block btn-xs">Hapus
								Permintaan</button>
						</c:if>
						<c:if test="${profile.getMembershipStatus() eq 3}">
							<button onclick="friendAction('${profile.getUsername()}',4)"
								class="btn btn-warning btn-block btn-xs">Batalkan
								Permintaan</button>
						</c:if>
					</div>
				</div>
				<ul class="nav navbar-nav fLeft" id="actionList">
					<li><a href="profile.jsp">Profil</a></li>
					<li><a href="Personal?action=vBlogUser">Tulisan</a></li>
					<li><a href="Personal?action=vFriendUser">Teman</a></li>
					<li><a href="Personal?action=vGroupUser">Komunitas</a></li>
				</ul>
			</div>
			<div class="partmidside">
				<h1 class="title">Data Pribadi</h1>
				<div class="isiProfil">
					<div class="row"></div>
					<div class="isiProfil">
						<div class="row">
							<div class="label w35">Nama</div>
							<input type="text" name="fullname"
								value="${profile.getfName()} ${profile.getlName()}"
								class="form-control fLeft input-sm priv" disabled />
						</div>
						<div class="row">
							<div class="label w35">Alamat</div>
							<input type="text" name="address" value="${profile.getAddress()}"
								class="form-control fLeft input-sm priv" disabled />
						</div>
						<div class="row">
							<div class="label w35">Jenis Kelamin</div>
							<input type="radio" name="gender" value="1" class=" priv"
								disabled <c:if test="${profile.getGender() eq 1}">checked</c:if> />
							Laki-laki <input type="radio" name="gender" value="2"
								class=" priv" disabled
								<c:if test="${profile.getGender() eq 2}">checked</c:if> />
							Perempuan
						</div>
						<div class="row">
							<div class="label w35">Status Hubungan</div>
							<input type="radio" name="status" value="1" class=" priv"
								disabled <c:if test="${profile.getStatus() eq 1}">checked</c:if> />Lajang
							<input type="radio" name="status" value="2" class=" priv"
								disabled <c:if test="${profile.getStatus() eq 2}">checked</c:if> />Menikah
						</div>
						<div class="row">
							<div class="label w35">Tempat Lahir</div>
							<input type="text" value="${profile.getBirthPlace()}"
								name="birthPlace" class="form-control fLeft input-sm priv"
								disabled />
						</div>
						<div class="row">

							<div class="label w35">Tanggal Lahir</div>
							<input class="form-control fLeft input-sm priv" name="birthDate"
								disabled type="date" value="${profile.getBirthDate()}" />
						</div>
						<div class="row">
							<div class="label w35">Agama</div>
							<select name="religion" class="fLeft input-sm priv" disabled>
								<option value="1"
									<c:if test="${profile.getReligion() eq 1}">selected</c:if>>Islam</option>
								<option value="2"
									<c:if test="${profile.getReligion() eq 2}">selected</c:if>>Kristen</option>
								<option value="3"
									<c:if test="${profile.getReligion() eq 3}">selected</c:if>>Katholik</option>
								<option value="4"
									<c:if test="${profile.getReligion() eq 4}">selected</c:if>>Hindu</option>
								<option value="5"
									<c:if test="${profile.getReligion() eq 5}">selected</c:if>>Budha</option>
							</select>
						</div>
						<div class="row">
							<div class="label w35">No Telpon</div>
							<input type="tel" name="phone" value="${profile.getPhone()}"
								class="form-control fLeft input-sm priv" disabled />
						</div>
						<div class="row">
							<div class="label w35">Alamat e-Mail</div>
							<input type="email" name="email" value="${profile.getEmail()}"
								class="form-control fLeft input-sm priv" disabled />
						</div>
						<div class="row">
							<div class="label w35">Website</div>
							<input type="url" name="website" value="${profile.getWebsite()}"
								class="form-control fLeft input-sm priv" disabled />
						</div>
					</div>
				</div>
			</div>
			<c:if test="${fn:length(profile.getEducationList()) ne 0}">
				<div class="partmidside">
					<h1 class="title">Riwayat Pendidikan</h1>
					<div class="isiProfil" id="edu">
						<table class="w100" id="eduT">
							<tr>
								<th class="w35">Nama Institusi</th>
								<th class="w25">Jurusan</th>
								<th class="w20">Tahun Masuk</th>
								<th class="w20">Tahun Keluar</th>
							</tr>
							<c:forEach var="edu" items="${profile.getEducationList()}">
								<tr>
									<td>${edu.getInstitutionName()}</td>
									<td>${edu.getCourse()}</td>
									<td>${edu.getStartYear()}</td>
									<td>${edu.getEndYear()}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</c:if>
			<c:if test="${fn:length(profile.getSkillList()) ne 0}">
				<div class="partmidside">
					<h1 class="title">Daftar Kemampuan</h1>
					<div class="isiProfil" id="skill">
						<ol id="skilList">
							<c:forEach var="skill" items="${profile.getSkillList()}">
								<li>${skill.getSkillName()}</li>
							</c:forEach>
						</ol>
					</div>
				</div>
			</c:if>

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
