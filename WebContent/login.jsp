<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>BdgPinter</title>
<link href="sources/css/login.css" rel="stylesheet">
<link rel="shortcut icon" href="sources/images/favicon.ico">
</head>
<body>
	<header>
		<div class="logo">
			<img src="sources/images/logo.JPG">
		</div>
		<div class="login">
			<form method="post" action="Controller?action=login">
				<p class="kolom">
					<label>Username</label><input type="text" name="uname"
						placeholder="Masukan Username" autofocus required maxlength="30" />
				</p>
				<p class="kolom">
					<label>Password</label><input type="password" name="passwd"
						placeholder="Masukan Password" required>
				</p>
				<p class="kolom">
					<input type="submit" value="Masuk">
				</p>
			</form>
		</div>
	</header>
	<div class="container">
		
		<div class="cKiri">
			<h1>
			Selamat Datang Di Aplikasi BDGPinter
		</h1>
		</div>
		<div class="cKanan">
			<form method="post" action="Controller?action=signup">
				<h2>Buat akun</h2>

				<p>
					<label>Username</label><input type="text" name="uname"
						placeholder="Username" autofocus required maxlength="30" />
				</p>
				<p>
					<label>Password</label><input type="password" name="passwd"
						placeholder="Password" required>
				</p>
				<p>
					<label>Nama Depan</label><input type="text" name="fname"
						placeholder="Nama Depan" required maxlength="30" />
				</p>
				<p>
					<label>Nama Belakang</label><input type="text" name="lname"
						placeholder="Nama Belakang" maxlength="30" />
				</p>
				<p>
					<input type="submit" value="Daftar">
				</p>
			</form>
		</div>
	</div>
	<footer><span class="fLeft">Created by : Sistem Informasi Telkom University</span>
<span class="fRight">BDGPinter ©2014</span></footer>
</body>
</html>