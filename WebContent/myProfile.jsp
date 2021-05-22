<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="head.html" />
<script>
jQuery.browser = {};
$(document).ready(function()
		{
	$("#CreateGroup").hide();
		$("#editFoto").hide();
		$("#simpanDataBtn").hide();
		$("#editSkill").hide();
		$("#editEdu").hide();
		$("#editPass").hide();
		
		$("#ubahBtn").click(function(){
			$("#editFoto").show();
			$("#ubahBtn").hide();			  
		});
		$("#editSkillBtn").click(function(){
			$("#editSkill").show();
			$("#editSkillBtn").hide();			  
		});
		$("#editEduBtn").click(function(){
			$("#editEdu").show();
			$("#editEduBtn").hide();			  
		});
		$("#editPassBtn").click(function(){
			$("#editPass").show();
			$("#editPassBtn").hide();			  
		});
		$("#multiform").submit(function(e)
		{
				$("#multi-msg1").html("<img src='sources/images/loading.gif'/>");

			var formObj = $(this);
			var formURL = formObj.attr("action");

		if(window.FormData !== undefined)  // for HTML5 browsers
//			if(false) 

			{
			//alert ("1");
				var formData = new FormData(this);
				$.ajax({
		        	url: formURL,
			        type: 'POST',
					data:  formData,
					mimeType:"multipart/form-data",
					contentType: false,
		    	    cache: false,
		        	processData:false,
					success: function(data, textStatus, jqXHR)
				    {
						 if (data == "hayoo") {
							 messageStatus("Hanya berkas berekstensi jpg, png dan jpeg yang boleh diunggah.","multi-msgs1");
						} else {
							$("#myFoto").attr("src",data);
							$("#fotoPP").attr("src",data);
							$("#editFoto").hide();
							$("#ubahBtn").show();

						}	
				    },
				  	error: function(jqXHR, textStatus, errorThrown) 
			    	{
				  		messageStatus("Foto belum dipilih.","multi-msgs1");
			    	} 	        
			   });
		        e.preventDefault();
		        e.unbind();
		   }
		   else  //for olden browsers
			{
				//generate a random id
				var  iframeId = 'unique' + (new Date().getTime());

				//create an empty iframe
				var iframe = $('<iframe src="javascript:false;" name="'+iframeId+'" />');

				//hide it
				iframe.hide();

				//set form target to iframe
				formObj.attr('target',iframeId);

				//Add iframe to body
				iframe.appendTo('body');
				iframe.load(function(e)
				{
					var doc = getDoc(iframe[0]);
					var docRoot = doc.body ? doc.body : doc.documentElement;
					var data = docRoot.innerHTML;
					$("#fotoPP").attr("src",data);
					$("#myFoto").attr("src",data);
					
				});
			
			}

		});


		$("#multi-post").click(function()
			{
				
			$("#multiform").submit();
			
		});

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
					<img src="${info.getFoto()}" id="fotoPP">
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
			<div class="partmidside">
				<h1 class="title">
					Foto Profil
					<button id="ubahBtn"
						class="fRight glyphicon glyphicon-pencil btn btn-warning btn-sm">Ubah
						Foto</button>
				</h1>

				<div class="isiProfil">
					<div class="row">
						<div class="photo w100 fLeft">
							<div class="error">
								<p id="multi-msgs1"></p>
							</div>
							<form method="post" enctype="multipart/form-data" id="multiform"
								name="multiform" action="Personal?action=uFoto">
								<img src="${profile.getFoto()}" id="myFoto"> <br>
								<div id="editFoto">
									<input type="file" name="foto" value="" class="input-sm mAuto" />
									<input type="submit" class="btn btn-info btn-sm" value="Simpan">
								</div>

							</form>

						</div>

					</div>
				</div>
			</div>
			<div class="partmidside">
				<h1 class="title">
					Informasi Akun
					<button id="editPassBtn"
						class="btn btn-warning btn-sm fRight glyphicon glyphicon-pencil">Ubah
						Password</button>
				</h1>
				<div class="isiProfil" id="pass">
					<div class="error">
						<p id="multi-msgs2"></p>
					</div>
					<div class="row">
						<div class="label w35">Username</div>
						<input type="text" name="Username"
							value="${profile.getUsername()}" placeholder="Username"
							class="form-control fLeft input-sm" maxlength="50" disabled />
					</div>
					<div id="editPass">
						<div class="row">
							<div class="label w35">Password Lama</div>
							<input type="password" name="PasswordOld" value=""
								placeholder="Ketik Password Lama"
								class="form-control fLeft input-sm pass" maxlength="30" />
						</div>
						<div class="row">
							<div class="label w35">Password Baru</div>
							<input type="password" name="PasswordNew" value=""
								placeholder="Ketik Password Baru"
								class="form-control fLeft input-sm pass" maxlength="30" />
						</div>
						<div class="row">
							<div class="label w35">Password Baru</div>
							<input type="password" name="PasswordNew2" value=""
								placeholder="Ketik Ulang Password Baru"
								class="form-control fLeft input-sm pass" maxlength="30" /> <span
								id="error"></span>
						</div>

						<button onclick="saveAkun();" class="btn btn-info btn-sm">Simpan</button>
					</div>

				</div>
			</div>
			<div class="partmidside">
				<h1 class="title">
					Data Pribadi
					<button onclick="editProfile('.priv');" id="editDataBtn"
						class="btn btn-warning btn-sm fRight glyphicon glyphicon-pencil">Ubah
						Data Diri</button>
				</h1>
				<div class="isiProfil" id="priv">
					<div class="error">
						<p id="multi-msgs3"></p>
					</div>
					<div class="row">
						<div class="label w20">Nama</div>
						<input type="text" name="fname" value="${profile.getfName()}"
							disabled placeholder="Nama depan" required
							class="form-control fLeft input-sm priv" maxlength="30" /> <input
							type="text" name="lname" value="${profile.getlName()}"
							placeholder="Nama belakang"
							class="form-control fLeft input-sm priv" maxlength="30" disabled />
					</div>
					<div class="row">
						<div class="label w20">Alamat</div>
						<input type="text" name="address" value="${profile.getAddress()}"
							placeholder="Alamat" class="form-control fLeft input-sm priv"
							maxlength="50" disabled />
					</div>
					<div class="row">
						<div class="label w20">Jenis Kelamin</div>
						<input type="radio" name="gender" value="1" class=" priv" disabled
							<c:if test="${profile.getGender() eq 1}">checked</c:if> />
						Laki-laki <input type="radio" name="gender" value="2"
							class=" priv" disabled
							<c:if test="${profile.getGender() eq 2}">checked</c:if> />
						Perempuan
					</div>
					<div class="row">
						<div class="label w20">Status Hubungan</div>
						<input type="radio" name="status" value="1" class=" priv" disabled
							<c:if test="${profile.getStatus() eq 1}">checked</c:if> />Lajang
						<input type="radio" name="status" value="2" class=" priv" disabled
							<c:if test="${profile.getStatus() eq 2}">checked</c:if> />Menikah
					</div>
					<div class="row">
						<div class="label w20">Tempat Lahir</div>
						<input type="text" value="${profile.getBirthPlace()}"
							name="birthPlace" placeholder="Tempat Lahir"
							class="form-control fLeft input-sm priv" maxlength="30" disabled />
					</div>
					<div class="row">

						<div class="label w20">Tanggal Lahir</div>
						<input class="form-control fLeft input-sm priv" name="birthDate"
							disabled type="date" value="${profile.getBirthDate()}"
							placeholder="Tanggal Lahir" />
					</div>
					<div class="row">
						<div class="label w20">Agama</div>
						<select name="religion" id="religion" class="fLeft input-sm priv"
							disabled>
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
						<div class="label w20">No Telpon</div>
						<input type="tel" name="phone" value="${profile.getPhone()}"
							disabled maxlength="15" placeholder="No Telpon"
							class="form-control fLeft input-sm priv" />
					</div>
					<div class="row">
						<div class="label w20">Alamat e-Mail</div>
						<input type="email" name="email" value="${profile.getEmail()}"
							disabled maxlength="30" placeholder="Alamat e-mail"
							class="form-control fLeft input-sm priv" />
					</div>
					<div class="row">
						<div class="label w20">Website</div>
						<input type="url" name="website" value="${profile.getWebsite()}"
							disabled placeholder="Website"
							class="form-control fLeft input-sm priv" maxlength="30" />
					</div>

					<button id="simpanDataBtn" onclick="saveProfile('.priv');"
						class="btn btn-info btn-sm">Simpan</button>

				</div>
			</div>
			<div class="partmidside">
				<h1 class="title">
					Riwayat Pendidikan
					<button id="editEduBtn"
						class="btn btn-warning btn-sm fRight glyphicon glyphicon-pencil">Tambah
						Riwayat</button>
				</h1>

				<div class="isiProfil" id="edu">
					<table class="w100" id="eduT">
						<tr>
							<th class="w35">Nama Institusi</th>
							<th class="w25">Jurusan</th>
							<th class="w20">Tahun Masuk</th>
							<th class="w20">Tahun Keluar</th>
							<th class="w20">Hapus</th>
						</tr>
						<c:forEach var="edu" items="${profile.getEducationList()}">
							<tr id="row${edu.getId()}">
								<td>${edu.getInstitutionName()}</td>
								<td>${edu.getCourse()}</td>
								<td>${edu.getStartYear()}</td>
								<td>${edu.getEndYear()}</td>
								<td><span class="glyphicon glyphicon-remove-circle"
									id="delIcon" onclick="delEdu(${edu.getId()})"></span></td>
							</tr>
						</c:forEach>
					</table>
					<br>
					<div class="row" id="editEdu">

						<div class="error">
							<p id="multi-msgs4"></p>
						</div>
						<input type="text" value="" name="instName"
							placeholder="Nama institusi"
							class="form-control fLeft input-sm w25" maxlength="30" /> <input
							type="text" value="" name="courseN" placeholder="Jurusan"
							class="form-control fLeft input-sm w20" maxlength="30" /> <input
							type="text" value="" name="startY" placeholder="Tahun Masuk"
							maxlength="4" class="form-control fLeft input-sm wp65" /> <input
							type="text" value="" name="endY" placeholder="Tahun Keluar"
							maxlength="4" class="form-control fLeft input-sm wp65" /> <input
							type="submit" class="btn btn-info btn-sm" onclick="addEdu()">
					</div>
				</div>
			</div>
			<div class="partmidside">
				<h1 class="title">
					Daftar Kemampuan
					<button id="editSkillBtn"
						class="btn btn-warning btn-sm fRight glyphicon glyphicon-pencil">Tambah
						Kemampuan</button>
				</h1>
				<div class="isiProfil" id="skill">
					<ol id="skilList">
						<c:forEach var="skill" items="${profile.getSkillList()}">
							<li id="skill${skill.getId()}">${skill.getSkillName()}<span
								class="glyphicon glyphicon-remove-circle" id="delIcon"
								onclick="delSkill(${skill.getId()})"></span></li>
						</c:forEach>
					</ol>
					<div class="row" id="editSkill">
						<div class="error">
							<p id="multi-msgs5"></p>
						</div>
						<input type="text" value="" name="nameS"
							placeholder="Masukan Kemampuan Anda"
							class="form-control fLeft input-sm" maxlength="30" /> <input
							type="submit" class="btn btn-info btn-sm" onclick="addSkill()">
					</div>
				</div>
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
