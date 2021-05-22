// -------------------Nunung-----------------------
// Notification
window.onload = function() {
	countNotife();
	countMessage();
	countFriendReq();
	setInterval(countNotife, 3000);
	setInterval(countMessage, 3000);
	setInterval(countFriendReq, 3000);
};

// autocomplete Penerima Pesan
function getFullname() {
	var key = $("#receiverMsg").val();
	if ((key.length == 0) || (key == "")) {
		$("#selectReceiver").html("");
	} else {
		$.post("Personal?action=vAllUser", {
			key : key
		}, function(data) {
			if (data != null) {
				$("#selectReceiver").html(data);
			} else {
				alert("Data Salah");
			}
		});
	}
}
// set select Penerima Pesan
function selectUser(id) {
	var username = $("#username" + id).val();
	var name = $("#name" + id).text();
	$("#receiverMsg").val(name);
	$("#usernameMsg").val(username);
	$("#selectReceiver").html("");
	vMsg(username, name);
}

// set fullname
function getName() {
	var name = $("#fullname").text();
	return name;
}
// 
function confirmEvent(act, cID) {
	$
			.post(
					"Controller?action=confirmEvent",
					{
						act : act,
						id : cID
					},
					function(data) {
						if (data != "false") {
							var x = "";
							switch (act) {
							case 2:
								x = '<span class="eventC">Anda Tidak Akan Menghadiri Acara</span>';
								break;
							case 1:
								x = '<span class="eventC">Anda Akan Menghadiri Acara</span>';
								break;
							default:
								x = '';
								break;
							}
							$("#konfirm" + cID).html(x);
						}
					});
}
// cek open
function OpenDiv() {
	$("#addNewsBtn").hide();
	$("#addNews").show();
}
// approve friend
function groupAction(gID, status) {
	$
			.post(
					"Personal?action=aGSearch",
					{
						status : status,
						groupID : gID
					},
					function(data) {
						if (data != "0") {
							var x = "";
							switch (status) {
							case 0:
								x = '<input type="button" value="Batalkan Permintaan" onclick="groupAction('
										+ gID
										+ ',2)" class="btn btn-warning btn-block btn-xs">';
								break;
							case 1:
								x = '<input type="button" value="Gabung Grup" onclick="groupAction('
										+ gID
										+ ',0)" class="btn btn-info btn-block btn-xs">';
								break;
							case 2:
								x = '<input type="button" value="Gabung Grup" onclick="groupAction('
										+ gID
										+ ',0)" class="btn btn-info btn-block btn-xs">';
								break;
							default:
								x = '';
								break;
							}
							$("#gAct" + gID).html(x);
						}
					});
	// });
	// });
}

// approve friend
function gAction(gID, status) {
	$.post("Personal?action=aGSearch", {
		status : status,
		groupID : gID
	}, function(data) {
		if (data != "0") {
			window.location.href = "Controller?action=gDetail&gID=" + gID;
		}
	});
	// });
	// });
}
// approve friend
function userAction(uname, status) {
	$
			.post(
					"Personal?action=aFSearch",
					{
						status : status,
						username : uname
					},
					function(data) {
						if (data != "0") {
							var x = "";
							var username = "'" + uname + "'";
							switch (status) {
							case 0:
								x = '<button onclick="friendAction('
										+ username
										+ ',4)" class="btn btn-warning btn-block btn-xs"> Batalkan Permintaan</button>';
								break;
							case 1:
								x = '<button onclick="friendAction('
										+ username
										+ ',0)" class="btn btn-info btn-block btn-xs"> Tambahkan Teman</button>';
								break;
							case 2:
								x = '<button onclick="friendAction('
										+ username
										+ ',1)" class="btn btn-warning btn-block btn-xs"> Hapus Pertemanan</button>';
								break;
							case 3:
								x = '<button onclick="friendAction('
										+ username
										+ ',0)" class="btn btn-info btn-block btn-xs"> Tambahkan Teman</button>';
								break;
							case 4:
								x = '<button onclick="friendAction('
										+ username
										+ ',0)" class="btn btn-info btn-block btn-xs"> Tambahkan Teman</button>';
								break;
							default:
								x = '';
								break;
							}
							$("#memAct" + uname).html(x);
						}
					});
	// });
	// });
}
// approve friend
function friendAction(username, status) {
	$
			.post(
					"Personal?action=aFriend",
					{
						status : status
					},
					function(data) {
						if (data != "0") {
							var x = "";
							username = "'" + username + "'";
							switch (status) {
							case 0:
								x = '<button onclick="userAction('
										+ username
										+ ',4)" class="btn btn-warning btn-block btn-xs">Batalkan Permintaan</button>';
								break;
							case 1:
								x = '<button onclick="userAction('
										+ username
										+ ',0)" class="btn btn-info btn-block btn-xs">Tambahkan Teman</button>';
								break;
							case 2:
								x = '<button onclick="userAction('
										+ username
										+ ',1)" class="btn btn-warning btn-block btn-xs">Hapus Pertemanan</button>';
								break;
							case 3:
								x = '<button onclick="userAction('
										+ username
										+ ',0)" class="btn btn-info btn-block btn-xs">Tambahkan Teman</button>';
								break;
							case 4:
								x = '<button onclick="userAction('
										+ username
										+ ',0)" class="btn btn-info btn-block btn-xs">Tambahkan Teman</button>';
								break;
							default:
								x = '';
								break;
							}
							$("#action").html(x);
						}
					});
	// });
	// });
}
function deleteNotife() {
	var cek = confirm('Apakah anda yakin akan menghapus?');
	if (cek == true) {
		$.post("Personal?action=dNotife", {}, function(data, status) {
			if (data != "0") {
				$("#listNotife").html("");
			} else {
				alert("Data Salah");
			}
		});
	}
}
// Get current time
function getTime() {
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth();
	var monthNames = [ "Januari", "Februari", "Maret", "April", "Mei", "Juni",
			"Juli", "Agustus", "September", "Oktober", "November", "Desember" ];
	var yyyy = today.getFullYear();
	var hours = today.getHours();
	var minutes = today.getMinutes();
	if (dd < 10) {
		dd = '0' + dd;
	}
	if (hours < 10) {
		hours = '0' + hours;
	}
	if (minutes < 10) {
		minutes = '0' + minutes;
	}
	return dd + ' ' + monthNames[mm] + ' ' + yyyy + ' Pukul ' + hours + ':'
			+ minutes;
}
// searchWiki
function searchWiki() {
	var key = $("input[name=keySearch]").val();
	if (key != "") {
		$.post("Personal?action=searchWiki", {
			key : key
		}, function(data, status) {
			if (data != "" && status == "success") {
				$("#resultList").html(data);
			} else {
				$("#resultList").html("Tidak Ditemukan");
			}
		});
	} else {
		$("#resultList").html("Tuliskan Kata Kunci");
	}
}
function keysearchWiki() {
	if (event.keyCode == 13) {
		searchWiki();
	}
}

// Insert Comment
function aComment(id) {
	var comment = $("#body" + id).val();
	if (comment != "") {
		$
				.post(
						"Personal?action=aComment",
						{
							contentID : id,
							body : comment
						},
						function(data, status) {
							if (data != "0") {
								// insert new li
								var t = "'" + getTime() + "'";
								var c = "'" + comment + "'";
								$('#commentList' + id)
										.append(
												'<li id="cId'
														+ data
														+ '"><span class="name">'
														+ getName()
														+ '</span><span class="glyphicon glyphicon-remove-circle" id="delIcon" onclick="delComment('
														+ data
														+ ')"></span><span onclick="editComment('
														+ data
														+ ','
														+ c
														+ ','
														+ t
														+ ','
														+ id
														+ ')" class="glyphicon glyphicon-edit" id="delIcon"></span><span class="time">'
														+ getTime()
														+ '</span><span class="isi">'
														+ comment
														+ '</span></li>');
								// Make input empty
								$("#body" + id).val("");
							} else {
								alert("Data Salah");
							}
						});
	} else {
		$("#body").after("<span class='error'>Tuliskan komentar Anda</span>");
	}
}
function addComment(id) {
	if (event.keyCode == 13) {
		aComment(id);
	}
}
// Delete Comment
function delComment(id) {
	var cek = confirm('Apakah anda yakin akan menghapus?');
	if (cek == true) {
		$.post("Personal?action=dComment", {
			commentID : id
		}, function(data, status) {
			if (data != "0") {
				// insert new li

				$('#cId' + id).remove();
			} else {
				alert("Data Salah");
			}
		});
	}
}
// disabled
function editProfile(id) {
	$("#simpanDataBtn").show();
	$(id).removeAttr("disabled");
	$("#editDataBtn").hide();
}
// Edit Profil
function saveAkun() {
	var passO = $("input[name=PasswordOld]").val();
	var passN1 = $("input[name=PasswordNew]").val();
	var passN2 = $("input[name=PasswordNew2]").val();

	if (passO == "") {
		$("input[name=PasswordOld]").focus();
		messageStatus("Tuliskan password lama.","multi-msgs2");
	} else if (passN1 == "") {
		$("input[name=PasswordNew]").focus();
		messageStatus("Tuliskan password baru.","multi-msgs2");
	} else if (passN2 == "") {
		$("input[name=PasswordNew2]").focus();
		messageStatus("Tuliskan kembali password baru.","multi-msgs2");
	} else if (passN1 != passN2) {
		messageStatus("Password baru tidak sama.","multi-msgs2");
	} else {
		$.post("Personal?action=uPassword", {
			passO : passO,
			passN : passN1
		}, function(data, status) {
			if (data != "0") {
				
				messageStatus("Password berhasil diganti.","multi-msgs2");
				$("#editPass").hide();
				$("#editPassBtn").show();
			} else {
				$("input[name=PasswordOld]").focus();
				messageStatus("Password lama salah.","multi-msgs2");
			}
		});
	}

}
function validateEmail($email) {
	  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	  if( !emailReg.test( $email ) ) {
	    return false;
	  } else {
	    return true;
	  }
	}
//
function saveProfile(id) {
	var fn = $("input[name=fname]").val();
	var ln = $("input[name=lname]").val();
	var add = $("input[name=address]").val();
	var jk = $("input[name=gender]:checked").val();
	var sh = $("input[name=status]:checked").val();
	var tmp = $("input[name=birthPlace]").val();
	var tgl = $("input[name=birthDate]").val();
	var ag = $("#religion").val();
	var nt = $("input[name=phone]").val();
	var em = $("input[name=email]").val();
	var web = $("input[name=website]").val();
	if (em != "") {
		if( !validateEmail(em)) { $("input[name=email]").focus();
		messageStatus("Alamat email salah.","multi-msgs3"); }else {
			$
					.post(
							"Personal?action=uProfile",
							{
								fName : fn,
								lName : ln,
								address : add,
								gender : jk,
								status : sh,
								bPlace : tmp,
								bDate : tgl,
								religion : ag,
								phone : nt,
								email : em,
								website : web
							},
							function(data, status) {
								if (data != "0") {
									$(id).attr('disabled', 'disabled');
									$("#simpanDataBtn").hide();
									$("#editDataBtn").show();
								} else {
									messageStatus("Data tidak berhasil disimpan.","multi-msgs3");
								}
							});
			}
	} 

}
//
function showCreateGroup() {
	$("#CreateGroup").show();
	$("#btnCreate").hide();
}
//

function editComment(id, comment, time, contID) {
	time = "'" + time + "'";
	var x = '<div class="form-group" ><div class="input-group"> <input id="editText'
			+ id
			+ '" type="text" class="form-control" value="'
			+ comment
			+ '" maxlength="160" onkeydown="editCommentIsi('
			+ id
			+ ','
			+ time
			+ ','
			+ contID
			+ ')"> <span class="input-group-btn"> <button type="submit" class="btn" onclick="editComments('
			+ id
			+ ','
			+ time
			+ ','
			+ contID
			+ ')"> <span class="glyphicon glyphicon-comment"></span> </button> </span></div></div>';
	$("#fComm" + contID).hide();
	$("#cId" + id).html(x);
}
//
// Insert Comment
function editCommentIsi(id, time, cid) {
	if (event.keyCode == 13) {
		editComments(id, time, cid);
	}
}
//
function editComments(id, time, cid) {
	var a = $("#editText" + id).val();
	$
			.post(
					"Personal?action=uComment",
					{
						cid : id,
						body : a
					},
					function(data) {
						var x = '<span class="name">'
								+ getName()
								+ '</span><span class="glyphicon glyphicon-remove-circle" id="delIcon" onclick="delComment('
								+ id
								+ ')"></span><span onclick="editComment('
								+ id
								+ ','
								+ a
								+ ','
								+ time
								+ ','
								+ cid
								+ ')" class="glyphicon glyphicon-edit" id="delIcon"></span><span class="time">'
								+ time + '</span><span class="isi">' + a
								+ '</span>';
						$("#cId" + id).html(x);
					});
	$("#fComm" + cid).show();
}
//
function updateNotife(id) {
	$.post("Personal?action=uNotife", {
		notifID : id
	}, function(data) {
	});
}

// addEduProfil
function addEdu() {
	var i = $("input[name=instName]").val();
	var c = $("input[name=courseN]").val();
	var s = $("input[name=startY]").val();
	var e = $("input[name=endY]").val();

	if (i == "") {
		$("input[name=instName]").focus();
		messageStatus("Nama sekolah kosong.","multi-msgs4");
	} else if (s == "") {
		$("input[name=startY]").focus();
		messageStatus("Tuliskan tahun masuk (Angka).","multi-msgs4");
	} else if (e == "") {
		$("input[name=endY]").focus();
		messageStatus("Tuliskan tahun lulus (Angka).","multi-msgs4");
	} else {
		$
				.post(
						"Personal?action=aEducation",
						{
							instName : i,
							courseN : c,
							startY : s,
							endY : e
						},
						function(data) {
							if (data != "0") {
								// insert new row
								messageStatus("Berhasil ditambahkan.","multi-msgs4");
								var table = document.getElementById("eduT");
								var row = table.insertRow(table.rows.length);
								row.setAttribute('id', 'row' + data);
								var ix = row.insertCell(0);
								var cx = row.insertCell(1);
								var sx = row.insertCell(2);
								var ex = row.insertCell(3);
								var dx = row.insertCell(4);
								ix.innerHTML = i;
								cx.innerHTML = c;
								sx.innerHTML = s;
								ex.innerHTML = e;
								dx.innerHTML = '<span class="glyphicon glyphicon-remove-circle" id="delIcon" onclick="delEdu('
										+ data + ')"></span>';
								// Make input empty
								$("input[name=instName]").val("");
								$("input[name=courseN]").val("");
								$("input[name=startY]").val("");
								$("input[name=endY]").val("");

							} else {
								messageStatus("Masukan data dengan benar.","multi-msgs4");
							}
						});
	}
}
function delEdu(id) {
	var cek = confirm('Apakah anda yakin akan menghapus?');
	if (cek == true) {
		$.post("Personal?action=dEducation", {
			eduID : id
		}, function(data, status) {
			if (data != "0") {
				$('#row' + id).remove();
			} else {
				alert("Data Salah");
			}
		});
	}
}
// addEduProfil
function addSkill() {
	var n = $("input[name=nameS]").val();
	if (n != "") {
		$
				.post(
						"Personal?action=aSkill",
						{
							nameS : n
						},
						function(data) {
							if (data != "0") {
								// insert new li
								$('#skilList')
										.append(
												'<li>'
														+ $("input[name=nameS]")
																.val()
														+ ' <span class="glyphicon glyphicon-remove-circle" id="delIcon" onclick="delSkill('
														+ data
														+ ')"></span></li>');
								// Make input empty
								$("input[name=nameS]").val("");
							} else {
								$("input[name=nameS]").focus();
								messageStatus("Tidak berhasil disimpan.","multi-msgs5");
							}
						});
	} else {
		$("input[name=nameS]").focus();
		messageStatus("Masukkan kemampuan anda.","multi-msgs5");
	}

}
function delSkill(id) {
	var cek = confirm('Apakah anda yakin akan menghapus?');
	if (cek == true) {
		$.post("Personal?action=dSkill", {
			skillID : id
		}, function(data, status) {
			if (data != "0") {
				$('#skill' + id).remove();
			} else {
				alert("Data Salah");
			}
		});
	}
}
//

function addStatus() {
	var body = $("#Status").val();	
	if (body == "") {
		$('#Status').focus();
		messageStatus("Status masih kosong.","multi-msgs1");
	} else {
		$
				.post(
						"Personal?action=addStatus",
						{
							bodyBlog : body
						},
						function(data) {
							if (data != "0") {
								// insert new li
								var newList = '<li class="li" id="list'
										+ data
										+ '"> <div class="direction"> <div class="flag-wrapper"> <span class="flag"><a href="Personal?action=vProfile">'
										+ getName()
										+ '</a> membuat status baru</span> </div> <div class="desc"> <p>'
										+ body
										+ '</p> </div> <div class="lead"> <span class="kanan comment">0</span> <span class="kanan glyphicon glyphicon-comment"></span> <span class="kanan">'
										+ getTime()
										+ '</span> <span class="kanan comment" onclick="delBlog('+data+')">Hapus</span></div> <div class="commentBox"> <ul id="commentList'
										+ data
										+ '"></ul> <div class="form-group" id="fComm'
										+ data
										+ '"> <div class="input-group"> <input id="body'
										+ data
										+ '" name="bodyC" type="text" class="form-control" placeholder="Tulis Komentar" maxlength="160" onkeydown="addComment('
										+ data
										+ ')"> <span class="input-group-btn"> <button type="submit" class="btn" onclick="aComment('
										+ data
										+ ')"> <span class="glyphicon glyphicon-comment"></span> </button> </span> </div> </div> </div> </div> </li>';
								$(newList).prependTo($('#timelineList'));
								messageStatus("Berhasil disimpan.","multi-msgs1");
								// Make input empty
								$('#Status').val("");
							}
						});
	}

}
// insertBlog
function addBlog() {
	tinyMCE.triggerSave();
	var title = $('#titleBlog').val();
	var body = $("#bodyBlog").val();
	var cat = $("#category").val();
	var nCat = $("#opt"+cat).text();
	if (title == "") {
		$('#titleBlog').focus();
		messageStatus("Judul masih kosong.","multi-msgs");
	} else if (body == "" || body.trim() == '<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n\n</body>\n</html>') {
		$('#bodyBlog').focus();
		messageStatus("Isi masih kosong","multi-msgs");
	} else {
		$
				.post(
						"Personal?action=aBlog",
						{
							titleBlog : title,
							bodyBlog : body,
							category : cat
						},
						function(data) {
							if (data != "0") {
								// insert new li
								var newList = '<li class="li" id="list'
										+ data
										+ '"> '
										+ '<div class="direction"> '
										+ '<div class="desc"> '
										+ '<h2><a href="Personal?action=vDetailBlog&bID='
										+ data
										+ '">'
										+ title
										+ '</a><span class="glyphicon glyphicon-remove-circle" id="delIcon" onclick="delBlog('
										+ data
										+ ')"></span><a href="Personal?action=editBlog&bID='
										+ data
										+ '"><span class="glyphicon glyphicon-edit" id="delIcon"></span></a></h2>'
										+ '<div>'
										+ body
										+ '</div> <p class="kat">Kategori : <span>'+nCat+'</span></p>	'
										+ '</div>	'
										+ '<div class="lead">'
										+ '<span class="kanan comment">0</span> '
										+ '<span class="kanan glyphicon glyphicon-comment"></span> <span class="kanan">'
										+ getTime()
										+ '</span> '
										+ '</div>		'
										+ '<div class="commentBox"> <ul id="commentList'
										+ data
										+ '"></ul> <div class="form-group" id="fComm'
										+ data
										+ '"> <div class="input-group"> <input id="body'
										+ data
										+ '" name="bodyC" type="text" class="form-control" placeholder="Tulis Komentar" maxlength="160" onkeydown="addComment('
										+ data
										+ ')"> <span class="input-group-btn"> <button type="submit" class="btn" onclick="aComment('
										+ data
										+ ')"> <span class="glyphicon glyphicon-comment"></span> </button> </span> </div> </div> </div> </div> </li>';
								$(newList).prependTo($('#blog'));
								messageStatus("Berhasil disimpan.","multi-msgs");
								var newTitle = '<li id="bID='
										+ data
										+ '"><a href="Personal?action=vDetailBlog&bID='
										+ data + '"> ' + title + '</a></li>';
								$(newTitle).prependTo($('#listTitleBlog'));

								// Make input empty
								$('#titleBlog').val("");
								$('#btnFile').val("");
								$('#multi-msg').html("");
								tinyMCE.activeEditor.setContent("");
							}
						});
	}

}
//update

// insertBlog
function addBlogGroup(username) {
	tinyMCE.triggerSave();
	var title = $('#titleBlog').val();
	var body = $("#bodyBlog").val();
	var cat = $("#category1").val();
	var nCat = $("#opt1"+cat).text();
	if (title == "") {
		$('#titleBlog').focus();
		messageStatus("Judul masih kosong.","multi-msgs1");
	} else if (body == ""|| body.trim() == '<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n\n</body>\n</html>') {
		$('#bodyBlog').focus();
		messageStatus("Isi masih kosong.","multi-msgs1");
	} else {

		$
				.post(
						"Controller?action=createCourse",
						{
							titleBlog : title,
							bodyBlog : body,
							category : cat
						},
						function(data) {
							if (data != "false") {
								// insert new li
								var newList = '<li class="li" id="list'
										+ data
										+ '"> <div class="direction"> <div class="flag-wrapper"> <span class="flag"><a href="Personal?action=vUser&username='
										+ username
										+ '">'
										+ getName()
										+ '</a> membuat tulisan baru</span> </div> <div class="desc"> <h2> <a href="Controller?action=vDetailCourse&cID='
										+ data
										+ '">'
										+ title
										+ '</a> <span class="glyphicon glyphicon-remove-circle" id="delIcon" onclick="delBlog('
										+ data
										+ ')"></span> <a href="Controller?action=editCourse&cID='
										+ data
										+ '"><span class="glyphicon glyphicon-edit" id="delIcon"></span></a> </h2> '
										+ body
										+ '</div><p class="kat">Kategori : <span>'+nCat+'</span></p> <div class="lead"> <span class="kanan comment">0</span> <span class="kanan glyphicon glyphicon-comment"></span> <span class="kanan">'
										+ getTime()
										+ '</span> </div> <div class="commentBox"> <ul id="commentList'
										+ data
										+ '"></ul> <div class="form-group" id="fComm'
										+ data
										+ '"> <div class="input-group"> <input id="body'
										+ data
										+ '" name="bodyC" type="text" class="form-control" placeholder="Tulis Komentar" maxlength="160" onkeydown="addComment('
										+ data
										+ ')"> <span class="input-group-btn"> <button type="submit" class="btn" onclick="aComment('
										+ data
										+ ')"> <span class="glyphicon glyphicon-comment"></span> </button> </span> </div> </div> </div> </div> </li>';
								$(newList).prependTo($('#contentList'));
								var newTitle = '<li id="bID='
									+ data
									+ '"><a href="Controller?action=vDetailCourse&cID='
									+ data + '"> ' + title + '</a></li>';
								$(newTitle).prependTo($('#listTitleBlog'));
								messageStatus("Berhasil.","multi-msgs1");
								// Make input empty
								$('#titleBlog').val("");
								tinyMCE.activeEditor.setContent("");
							} else {
								messageStatus("Gagal disimpan.","multi-msgs1");
							}
						});
	}
}
// insert Wiki
function addWiki() {
	tinyMCE.triggerSave();
	var title = $('#titleBlog').val();
	var body = $("#bodyBlog").val();
	var cat = $("#category").val();
	if (title == "") {
		$('#titleBlog').focus();
		messageStatus("Judul masih kosong.","multi-msgs");
	} else if (body == ""  || body.trim() == '<!DOCTYPE html>\n<html>\n<head>\n</head>\n<body>\n\n</body>\n</html>') {
		$('#bodyBlog').focus();
		messageStatus("Isi masih kosong.","multi-msgs");
	} else {
		$
				.post(
						"Personal?action=aWiki",
						{
							titleWiki : title,
							bodyWiki : body,
							category : cat
						},
						function(data) {
							if (data != "0") {
								// insert new li
								var table = document.getElementById("wikiList");
								var row = table.insertRow(table.rows.length);
								row.setAttribute('id', 'wiki' + data);
								var j = row.insertCell(0);
								var i = row.insertCell(1);
								var t = row.insertCell(2);
								var e = row.insertCell(3);
								var a = row.insertCell(4);
								messageStatus("Berhasil","multi-msgs");
								j.innerHTML = '<a href="Personal?action=vDetailWiki&wID='
										+ data + '">' + title + '</a>';
								i.innerHTML = body;
								t.innerHTML = getTime();
								e.innerHTML = $("input[name=Fullname]").val();
								a.innerHTML = '<span class="glyphicon glyphicon-trash" id="delIcon" onclick="delWiki('
										+ data
										+ ')"></span> <a href="Personal?action=editWiki&wID='
										+ data
										+ '"><span class="glyphicon glyphicon-edit"></span></a>';
								// Make input empty
								$('#titleBlog').val("");
								tinyMCE.activeEditor.setContent("");
								$("#addNews").hide();
								$("#addNewsBtn").show();
							}
						});
	}
}
// update Wiki
function updateWiki(id) {
	tinyMCE.triggerSave();
	var title = $('#titleBlog').val();
	var body = $("#bodyBlog").val();
	if (title == "") {
		$('#titleBlog').after(
				"<div class='error'>Data Tidak Boleh Kosong!</div>");
	} else if (body == "") {
		$('#bodyBlog').after(
				"<div class='error'>Data Tidak Boleh Kosong!</div>");
	} else {

		$
				.post(
						"Personal?action=uWiki",
						{
							wikiid : id,
							titleWiki : title,
							bodyWiki : body
						},
						function(data) {
							if (data != "0") {
								// insert new li
								var table = document.getElementById("wikiList");
								var row = table.insertRow(table.rows.length);
								row.setAttribute('id', 'row' + data);
								var j = row.insertCell(0);
								var i = row.insertCell(1);
								var t = row.insertCell(2);
								var e = row.insertCell(3);
								var a = row.insertCell(4);
								j.innerHTML = '<a href="Personal?action=vDetailWiki&wID='
										+ data + '">' + title + '</a>';
								i.innerHTML = body;
								t.innerHTML = getTime();
								e.innerHTML = $("input[name=Fullname]").val();
								a.innerHTML = '<span class="glyphicon glyphicon-trash" id="delIcon" onclick="delWiki('
										+ data
										+ ')"></span> <a href="Personal?action=editWiki&wID='
										+ data
										+ '"><span class="glyphicon glyphicon-edit"></span></a>';
								// Make input empty
								$('#titleBlog').val("");
								tinyMCE.activeEditor.setContent("");
							}
						});
	}
}
// delete blog
function delBlog(id) {
	var cek = confirm('Apakah anda yakin akan menghapus?');
	if (cek == true) {
		$.post("Personal?action=dBlog", {
			contentID : id
		}, function(data) {
			if (data != "0") {
				// insert new li
				$('#list' + id).remove();
			} else {
				alert("Data Salah");
			}
		});
	} else {
		return false;
	}

}
// delete blog
function delWiki(id) {
	var cek = confirm('Apakah anda yakin akan menghapus?');
	if (cek == true) {
		$.post("Personal?action=dBlog", {
			contentID : id
		}, function(data) {
			if (data != "0") {
				$('#wiki' + id).remove();
			} else {
				alert("Data Salah");
			}
		});
	} else {
		return false;
	}

}
// count notife
function countNotife() {
	$.post("Personal?action=cNotife", {}, function(data) {
		if (data != "0") {
			// alert(data);
			$('#notif').addClass('navbar-new');
			$("#notif").html(data);
		} else {
			$('#notif').removeClass('navbar-new');
			$("#notif").html("");
		}
	});
}
// count notife
function countMessage() {
	$.post("Personal?action=cMessage", {}, function(data) {
		if (data != "0") {
			// alert(data);
			$('#msgNumber').addClass('navbar-new');
			$("#msgNumber").html(data);
		} else {
			$('#msgNumber').removeClass('navbar-new');
			$("#msgNumber").html("");
		}
	});
}
// count notife
function countFriendReq() {
	$.post("Personal?action=cFriend", {}, function(data) {
		if (data != "0") {
			// alert(data);
			$('#frdNumber').addClass('navbar-new');
			$("#frdNumber").html(data);
		} else {
			$('#frdNumber').removeClass('navbar-new');
			$("#frdNumber").html("");
		}
	});
}
// SendMessage
function addMessage() {
	var bodyMsg = $('#bodyMsg').val();
	var name = $('#receiverMsg').val();

	if (bodyMsg == "") {
		$('#bodyMsg').focus();
		messageStatus("Isi pesan masih kosong","multi-msgs");
	} else if (name == "") {
		$('#receiverMsg').focus();
		messageStatus("Penerima masih kosong.","multi-msgs");
	} else {

		$.post("Personal?action=aMessage", {
			body : bodyMsg,
			name : name
		}, function(data) {
			if (data != "0") {

				vMsg(data, name);
				// insert new li
				var newList = "<li class='r1' id='" + data
						+ "'><a href='Personal?action=vUser&username=" + data
						+ "'><span class='receiver'>" + getName()
						+ "</span></a><span class='sendTime'>" + getTime()
						+ "</span><span class='bodyMsg'>" + bodyMsg
						+ "</span></li>";
				$(newList).prependTo($('#listMsgs'));
				messageStatus("Berhasil dikirim.","multi-msgs");

				// Make input empty
				$("#bodyMsg").val("");
				$("#receiverMsg").val(name);
			} else {
				messageStatus("Penerima tidak terdaftar.","multi-msgs");
			}
		});
	}
}
function aMessage() {
	if (event.keyCode == 13) {
		addMessage();
	}

}
//
function delRecMsg(id) {
	var cek = confirm('Apakah anda yakin akan menghapus?');
	if (cek == true) {
		$.post("Personal?action=dMessage", {
			receiver : id
		}, function(data) {
			if (data != "0") {
				// insert new li
				$('#uMsg' + id).remove();
			} else {
				alert("Data Salah");
			}
		});
	} else {
		return false;
	}
}
//
function vMsg(username, nama) {
	$.post("Personal?action=vDetailMessage", {
		uName : username
	}, function(data) {
		if (data != "0") {
			$("#receiverMsg").val(nama);
			$("#listMsgs").html(data);
			$('#uMsg' + username).removeClass('r1');
			$('#uMsg' + username).addClass('r2');
		} else {
			$("#listMsgs").html("");
		}
	});
}
//
function search() {
	if (event.keyCode == 13) {
		var act = "search";
		var username = $('#navbarInput-01').val();
		$.get("Personal", {
			action : act,
			keys : username
		}, function(data) {
		});
	}
}

// -------------------Herdi-----------------------
// approve group member
function memberRequest(remove, userName, actions, foto, FName) {
	// $(document).ready(function(){
	// $("#button1").click(function(){
	$("#"+remove).fadeOut("500", function() {
		$("#"+remove).remove();
	});
	$
			.post(
					"Controller?action=memberReqAct",
					{
						userName : userName,
						status : actions
					},
					function(data, status) {
						if (status == "success" && actions == 1) {
							// var x=$(remove).html();
							var x = "<li id='"+remove+"'><a href='Personal?action=vUser&username="+userName+"'> <img src='"
									+ foto
									+ "'><br>"
									+ FName
									+ "</a><input type='button' value='Jadikan admin' class='btn btn-info btn-block btn-xs' id='button1' onclick=makeAsAdmin('"+userName+"',"+remove+")><input type='button' value='Keluarkan' class='btn btn-warning btn-block btn-xs' id='button2' onclick=deleteFromGroup('"+userName+"',"+remove+") ></li>";
							$("#memberList").append(x);
						}
					});
	// });
	// });
}
// approve friend
function friendRequest(remove, ID, actions, foto, FName, UName) {
	// $(document).ready(function(){
	// $("#button1").click(function(){
	$("#"+remove).fadeOut("500", function() {
		$("#"+remove).remove();
	});
	$
			.post(
					"Controller?action=friendReqAct",
					{
						id : ID,
						status : actions
					},
					function(data, status) {
						if (status == "success" && actions == 1) {
							// var x=$(remove).html();
							var x = "<li id='"+remove+"'><a href='Personal?action=vUser&username="
									+ UName
									+ "'> <img src='"
									+ foto
									+ "'><br>"
									+ FName
									+ "</a>"
									+ "<input type='button' value='Hapus Teman' class='btn btn-warning btn-block btn-xs' onclick=friendRequest('"+remove+"','"
									+ ID
									+ "','3','"
									+ foto
									+ "','','"
									+ UName
									+ "')></li>";
							$("#memberList").append(x);
						}
					});
	// });
	// });
}
// function send invitation to join group
function addMember() {
	// var tag = $("input[name=tag]").val();
	var taskArray = new Array();
	$("input[name=tag]").each(function() {
		taskArray.push($(this).val());
	});
	var tag = "";
	for (var i = 0; i < taskArray.length; i++) {
		tag += taskArray[i] + ",";
	}
	$.post("Controller?action=sendInvitation", {
		member : tag
	}, function(data) {
		// alert ('data='+data);
		$("#memberList").append(data);
		$(".tagedit-listelement.tagedit-listelement-old").remove();
	});
}
// function makeAsAdmin
function makeAsAdmin(uname, remove) {
	$.post("Controller?action=makeAsAdmin", {
		username : uname
	}, function(data) {
		// alert ('data='+data);
		$("#adminDIV").append(data);
	});
	$(remove).fadeOut("500", function() {
		$(this).remove();
	});
}
// function delete member from group
function deleteFromGroup(uname, remove) {
	$.post("Controller?action=kickMember", {
		username : uname
	}, function(data) {
		// alert ('data='+data);
		if (data == "true")
			$(remove).fadeOut("500", function() {
				$(this).remove();
			});
	});
}
// function Content Sharing
function contentSharing() {
	// alert("asd");
	var x = $("[name='file']").val();
	var y = $("[name='title']").val();
	var z = $("[name='desc']").val();
	if (x == "") {
		$("[name='file']").after(
				"<div class='error'>Data Tidak Boleh Kosong!</div>");
	} else if (y == "") {
		$("[name='title']").after(
				"<div class='error'>Data Tidak Boleh Kosong!</div>");
	} else if (z == "") {
		$("[name='desc']").after(
				"<div class='error'>Data Tidak Boleh Kosong!</div>");
	} else {

		$.post("Controller?action=uploadContent", {
			file : x,
			title : y,
			desc : z
		}, function(data) {
			if (data == "true")
				$(remove).fadeOut("500", function() {
					$(this).remove();
				});
		});
	}
}
// saveQuiz
function saveQuiz() {
	window.location.href = "Controller?action=viewQuizInGroup";
}
// function create event
function createEvnt() {
	var v = $("[name='eventName']").val();
	var w = $("[name='eventDetail']").val();
	var x = $("[name='eventPlace']").val();
	var y = $("[name='eventStart']").val();
	var z = $("[name='eventEnd']").val();
	var a = $("[name='timeStart']").val();
	var b = $("[name='timeEnd']").val();
	var userN = $("#usernameCreator").val();
	var cat = $("#category3").val();
	var nCat = $("#opt3"+cat).text();
	if (v == "") {
		messageStatus("Tuliskan nama acara.","multi-msgs3");
		$("[name='eventName']").focus();
	} else if (y == "") {
		messageStatus("Masukkan waktu mulai acara.","multi-msgs3");
		$("[name='eventStart']").focus();
	} else if (z == "") {
		messageStatus("Masukkan tanggal selesai acara.","multi-msgs3");
		$("[name='eventEnd']").focus();
	} else {
		$
				.post(
						"Controller?action=createEvent",
						{
							eventName : v,
							eventDetail : w,
							eventPlace : x,
							eventStart : y,
							eventEnd : z,
							timeStart : a,
							timeEnd : b,
							category : cat
						},
						function(data) {
							if (data != "false") {
								messageStatus("Berhasil disimpan.","multi-msgs3");
								var newEvent = '<li class="li" id="list'
										+ data
										+ '"> <div class="direction"> <div class="flag-wrapper"> <span class="flag"><a href="Personal?action=vUser&username='
										+ userN
										+ '">'
										+ getName()
										+ '</a> membuat acara baru</span> </div> <div class="desc"> <h2><a href="Controller?action=vDetailCourse&cID='
										+ data
										+ '">'
										+ v
										+ '</a> <span class="glyphicon glyphicon-remove-circle" id="delIcon" onclick="delBlog('
										+ data
										+ ')"></span><a href="Personal?action=editBlog&bID='
										+ data
										+ '"><span class="glyphicon glyphicon-edit" id="delIcon"></span></a> </h2> <div class="row ">'
										+ w
										+ '</div> <div class="row" id="konfirm'
										+ data
										+ '"><span class="eventC">Anda Akan Menghadiri Acara</span></div> <div class="row deskripsiAcara"> <div class="w20 labelName"> <span class="glyphicon glyphicon-home"></span> <label class="labelName">Lokasi</label> </div> <div class="w50 labelIsi">'
										+ x
										+ '</div> </div> <div class="row deskripsiAcara"> <div class="w100 labelName"> <span class="glyphicon glyphicon-time"></span> Waktu </div><div class="w50 fLeft"> <label class="w100 labelName">Mulai</label> <label class="w100 labelIsi">'
										+ y
										+ ' Pukul '
										+ a
										+ '</label> </div> <div class="w50 fRight"> <label class="w100 labelName">Selesai</label> <label class="w100 labelIsi">'
										+ z
										+ ' Pukul '
										+ b
										+ '</label> </div> </div> <p class="kat">Kategori : <span>'+nCat+'</span></p></div> <div class="lead"> <span class="kanan comment">0</span> <span class="kanan glyphicon glyphicon-comment"></span> <span class="kanan">'+getTime()+'</span> </div> <div class="commentBox"> <div class="form-group"> <div class="input-group"> <input id="body'
										+ data
										+ '" name="bodyC" type="text" class="form-control" placeholder="Tulis Komentar" maxlength="160" onkeydown="addComment('
										+ data
										+ ')"><span class="input-group-btn"> <button type="submit" class="btn" onclick="aComment('
										+ data
										+ ')"> <span class="glyphicon glyphicon-comment"></span></button> </span> </div> </div> </div> </div></li>';
								$(newEvent).prependTo($('#contentList'));
								$("[name='eventName']").val("");
								$("[name='eventDetail']").val("");
								$("[name='eventPlace']").val("");
								$("[name='eventStart']").val("");
								$("[name='eventEnd']").val("");
								$("[name='timeStart']").val("");
								$("[name='timeEnd']").val("");
							}
							// $(remove).fadeOut("500", function() {
							// $(this).remove();
							// });
						});
	}
}

function messageStatus(isi,div){
	$("#"+div).hide();
	$("#"+div).text(isi);
	$("#"+div).fadeIn("1000");
	$("#"+div).fadeOut("10000");
}
function createGroup() {
	if ($("#namaGroup").val() == "") {
		$("#namaGroup").focus();
	} else {
		$
				.post(
						"Controller?action=createGroup",
						{
							groupName : $("#namaGroup").val()
						},
						function(data) {
							if (data != "false") {
								window.location.href = "Controller?action=gDetail&gID="+data;
								$("#namaGroup").val("");
							}
							// $(remove).fadeOut("500", function() {
							// $(this).remove();
							// });
						});
	}
}
