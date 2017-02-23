<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<html>
<head>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script src="JavaScript/campusMindJS.js"></script>
<link rel="stylesheet" href="CSS/outpass.css">
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
					<li id="new-request">New Request</li>
					<li id="view-history">View History</li>
					<!-- 					<li id="change-password">Change Password</li> -->

				</ul>
				<!-- 				<div id="change-password-div"> -->
				<!-- 					<input type="password" id="password"> <input type="submit" -->
				<!-- 						value="Change" id="password-submit"> -->
				<!-- 				</div> -->
			</div>
			<!-- form and history container -->
			<div id="main">
				<!-- form container -->
				<div id="request-form-container">
					<h3>Request for Stay/Halt Out of the Campus</h3>
					<h2 id="message"></h2>
					<form method="post" action="submitRequest.view" id="request-form">
						<ul id="form-list">
							<li><label for="request-type">Request Type<span>*</span></label>
								<br> <select id="request-type" name="request-type">
									<option>Select</option>
									<option>Normal stay outs</option>
									<option>Night outs</option>
									<option>Emergency stay outs</option>
							</select><span id="request-type-message"></span><input type="hidden"
								id="userId" value="${sessionScope.userId}"></li>
							<li><div id="from-duration-div">
									<div id="from-date-div">
										<label for="request-from-date">Request from Date<span>*</span></label>
										<br> <input type="date" name="request-from-date"
											id="request-from-date"><br> <span
											id="request-from-date-message"></span>
									</div>
									<div id="from-time-div">
										<label for="request-from-time">Request from Time<span>*</span></label>
										<br> <select id="request-from-time"
											name="request-from-time">
											<option>Select</option>
										</select> <br> <span id="request-from-time-message"></span>
									</div>
								</div></li>
							<li><div id="to-duration-div">
									<div id="to-date-div">
										<label for="request-to-date">Request to Date<span>*</span></label>
										<br> <input type="date" name="request-to-date"
											id="request-to-date"><br> <span
											id="request-to-date-message"></span>
									</div>

									<div id="to-time-div">
										<label for="request-to-time">Request to Time<span>*</span></label>
										<br> <select id="request-to-time" name="request-to-time">
											<option value="select">Select</option>
										</select><br> <span id="request-to-time-message"></span>
									</div>
								</div>
							<li><div>
									<label for="comment">Comment<span>*</span></label><br>
									<textarea id="comment" name="comment"
										placeholder="Reason for going out"></textarea>
									<span id="comment-message"></span>
								</div></li>
							<li><input type="submit" value="submit" id="request-submit">
								<button type="reset" id="reset">Reset</button></li>
						</ul>
					</form>
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
				<!-- 				<div id="acknowledgements"> -->
				<!-- 					<div id="first-row" class="row"> -->
				<!-- 						<div id="first-img" class="img"> -->
				<!-- 							<div> -->
				<!-- 								<img src="" /> -->
				<!-- 							</div> -->
				<!-- 						</div> -->
				<!-- 						<div id="second-img" class="img"> -->
				<!-- 							<div> -->
				<!-- 								<img src="" /> -->
				<!-- 							</div> -->
				<!-- 						</div> -->
				<!-- 						<div id="third-img" class="img"> -->
				<!-- 							<div> -->
				<!-- 								<img src="" /> -->
				<!-- 							</div> -->
				<!-- 						</div> -->
				<!-- 					</div> -->
				<!-- 					<div id="second-row" class="row"> -->
				<!-- 						<div id="fourth-img" class="img"> -->
				<!-- 							<div> -->
				<!-- 								<img src="" /> -->
				<!-- 							</div> -->
				<!-- 						</div> -->
				<!-- 						<div id="fifth-img" class="img"> -->
				<!-- 							<div> -->
				<!-- 								<img src="" /> -->
				<!-- 							</div> -->
				<!-- 						</div> -->
				<!-- 					</div> -->
				<!-- 					<div id="third-row" class="row"> -->
				<!-- 						<div id="sixth-img" class="img"> -->
				<!-- 							<div> -->
				<!-- 								<img src="" /> -->
				<!-- 							</div> -->
				<!-- 						</div> -->
				<!-- 					</div> -->
				<!-- 				</div> -->
			</div>
		</div>
		<footer>
			<!-- 			<p>Ackowledgements</p> -->
			<!-- 			<img src="images/mindtree-text-logo.png" /> -->
		</footer>
	</div>
</body>
</html>