<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<html>
<head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<!-- <script src="validate.js"></script> -->
<script src="JavaScript/adminJS.js"></script>

<link rel="stylesheet" href="CSS/outpass.css">
<script>
	function disableBackButton() {
		window.history.forward();
	}
	setTimeout("disableBackButton()", 0);
</script>
</head>
<body>
	<div id="out-container">
		<!-- header -->
		<header>
			<!-- 						<p id="logout">Log Out</p> -->
			<p id="title">OUTING PASS</p>
			<div id="meta">
				<p id="welcome">
					Welcome <span style="text-transform: capitalize">${sessionScope.name}</span>
					<span id="menu-icon"></span>
				</p>
				<!-- 				<img id="logout" src="images/logout.png"> -->
			</div>
			<div id="menu">
				<ul>
					<li id="password">Change Password</li>
					<li><a href="logout.view">Log Out</a></li>
				</ul>
			</div>
		</header>
		<!-- main container -->
		<div id="container">
			<!-- sidebar -->
			<div id="sidebar">
				<ul>
					<li id="all-requests">View Request</li>
					<li id="create-user">Create User</li>
					<li id="head-count">Head Count</li>
				</ul>
			</div>
			<!-- form and requests container -->
			<div id="main-pf">
				<!-- requests container -->
				<h3 style="color: green" id="all-request-msg">${mesg}</h3>
				<h3 style="color: #e60000" id="all-request-msg">${mesg2}</h3>
				<div id="requests-container">
					<div id="table-container"></div>
					<div id="buttons"></div>
				</div>

				<!-- forms container -->
				<div id="add-user-form-container">
					<h3>Create User</h3>
					<h2 id="message"></h2>
					<form:form commandName="addUser" action="adduser.view"
						method="post" id="add-user-form">
						<ul>
							<li><label for="user-type">User Type</label> <select
								id="user-type" path="userType" name="user-type">
									<option id="select">Select</option>
									<option id="CAMPUSMIND" value="CAMPUSMIND">CAMPUS MIND</option>
									<option id="ADMIN" value="ADMIN">ADMIN</option>
									<option id="SECURITY" value="SECURITY">SECURITY</option>
							</select><span id="user-type-message"></span></li>
							<li><label for="name">Name</label> <form:input path="name"
									type="text" id="name" name="name" placeholder="Name"
									required="required" pattern="[a-zA-Z][a-zA-Z\s]*"
									title="Invalid!!!!Only Alphabet allows" /><span
								id="name-message"></span></li>
							<li id="email"><label for="email">Email</label> <form:input
									path="email" type="text" id="emailText" name="email"
									title="Email not valid!!!" placeholder="Email" /><span
								id="email-message"></span></li>
							<li><label for="userId">ID</label> <form:input path="userId"
									type="text" id="userId" name="name" placeholder="ID"
									required="required" pattern="[a-zA-Z0-9]*"
									title="Invalid!!!!Only Alphabet and Digit allows" /><span
								id="id-message"></span></li>
							<li><input type="submit" value="Submit" id="submit" />
								<button type="reset" id="reset">Reset</button></li>
							<c:out value="${msg}"></c:out>
						</ul>
					</form:form>
				</div>
				<!-- head count container -->
				<div id="head-count-container">
					<div id="head-count-table-container"></div>
				</div>
				<!-- History container -->
				<div id="history-container">
					<div id="table-container"></div>
				</div>
				<!-- Change password container -->
				<div id="password-container">
					<h2 id="password-message"></h2>
					<form action="changepassword.view" method="post">
						<ul>
							<li><label for="oldpassword">Current Password</label><input
								type="password" name="oldpassword" id="oldpassword"
								placeholder="Current Password"><span
								id="old-password-message"></span></li>
							<li><label for="New Password">New Password</label><input
								type="password" name="newpassword" id="newpassword"
								placeholder="New Password"><span
								id="new-password-message"></span></li>
							<li><label for="Confirm Password">Confirm Password</label><input
								type="password" name="confirmpassoword"
								placeholder="Confirm password" id="confirmpassword"><span
								id="confirm-password-message"></span></li>
							<li><input type="submit" value="Change Password"
								id="changepassword">
								<button type="reset" id="reset">Reset</button></li>

						</ul>
					</form>
				</div>

			</div>
			<div id="request-alert">
				<p></p>
			</div>
		</div>
		<footer>
			<!-- 			<img src="images/mindtree-text-logo.png" /> -->
		</footer>
	</div>


</body>
</html>