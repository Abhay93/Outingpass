<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
<script src="JavaScript/security.js"></script>
<script>
	function test(status) {
		alert(value);
	}
</script>

<link rel="stylesheet" href="CSS/outpass.css">

</head>
<body>

	<div id="out-container">
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
			<!-- form and requests container -->
			<div id="main-sec">
				<!-- requests container -->
				<div id="requests-container">
					<div id="table-container">
						<c:choose>
							<c:when test="${request=='[]'}">

								<p>No Outing Requests to display!!</p>

							</c:when>

							<c:when test="${request!='[]'}">
								<table id="requests-table">
									<thead>
										<tr>
											<th>Request ID</th>
											<th>MID</th>
											<th>From Date</th>
											<th>From Time</th>
											<th>To Date</th>
											<th>To Time</th>
											<th>Comment</th>
											<th>Check out</th>
											<th>Check in</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${request}" var="req" varStatus="index">
											<tr>
												<td>${index.index+1}</td>
												<td>${req.getUser().getUserId()}</td>
												<td>${req.getFromDate()}</td>
												<td>${req.getFromTime()}</td>
												<td>${req.getToDate()}</td>
												<td>${req.getToTime()}</td>
												<td>${req.getComment()}</td>

												<c:set var="current" scope="page">${req.getStatus()}</c:set>
												<c:set var="checkin" value="3" />
												<c:choose>
													<c:when test="${current eq 'APPROVED'}">
														<td><a
															href="changeRequestCheckout.view?current=${req.getId()}">Check
																out</a></td>
														<td><a>Check in</a></td>
													</c:when>

													<c:when test="${current eq 'CHECKEDOUT'}">
														<td><img src="images/accept.png" id="${req.getId()}"
															class="checkedout" /></td>
														<td><a
															href="changeRequestCheckin.view?current=${req.getId()}">Check
																in</a></td>
													</c:when>
												</c:choose>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:when>
						</c:choose>
					</div>
					<div id="buttons"></div>
				</div>
			</div>
			<span id="sec-home"
				style="display: none; cursor: pointer; color: #3B5998">Home</span>
			<div
				style="background: #e0e0d1; width: 30%; margin: auto; border-radius: 5px;">
				<!-- Change password container -->
				<div id="sec-password-container">
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
		</div>

		<footer>
			<!-- 			<img src="images/mindtree-text-logo.png" /> -->
		</footer>
	</div>


</body>
</html>