$(function() {
	/** **************************************************** */
	/** ***************************ADMIN*********************** */
	/** **************************************************** */
	requestsList()
	/* show requests pending for approval */
	function requestsList() {
		$
				.ajax({
					url : "allrequests.view",
					type : "GET",
					cahce : false,
					dataType : "JSON",
					beforeSend : function() {
					},
					success : function(requestData) {
						if (requestData != '') {
							$("div#requests-container div#table-container")
									.html(
											"<table id='requests-table'>"
													+ "<thead><tr><th>Request ID</th><th>MID</th><th>From Date</th><th>From Time</th>"
													+ "<th>To Date</th><th>To Time</th><th>Comment</th><th>Action</th></tr></thead><tbody>")
							$
									.each(
											requestData,
											function(i, data) {
												$(
														"div#requests-container div#table-container table#requests-table")
														.append(
																"<tr><td>"
																		+ (i + 1)
																		+ "</td>"
																		+ "<td>"
																		+ data[8]
																		+ "</td>"
																		+ "<td>"
																		+ data[2]
																		+ "</td>"
																		+ "<td>"
																		+ data[3]
																		+ "</td>"
																		+ "<td>"
																		+ data[5]
																		+ "</td>"
																		+ "<td>"
																		+ data[6]
																		+ "</td>"
																		+ "<td>"
																		+ data[1]
																		+ "</td>"
																		+ "<td>"
																		+ "<img src = 'images/accept.png' id = '"
																		+ data[0]
																		+ "' class = 'accept'>"
																		+ "<img src = 'images/rejected.png' id = '"
																		+ data[0]
																		+ "' class = 'reject'>"
																		+ "</td>"
																		+ "</tr>")
											})
							$("div#requests-container div#table-container")
									.append("</tbody></table>")
							$("div#requests-container div#buttons")
									.html(
											"<form action='adminAcceptAll.view' method='GET'><input type='submit' id='accept-all' value = 'Accept All'></form><form action='adminRejectAll.view' method='GET'><input type='submit' id='reject-all' value = 'Reject All'></form>")
						} else {
							$("div#requests-container")
									.html(
											"<h2 id = 'history-message'>No requests waiting for your approval</h2>")
						}
					},
					error : function(data) {
						$("div#requests-container")
								.html(
										"<h2 id = 'history-message' style = 'color:#e60000; '>Sorry. We were unable to process your request. Try againHELLo.</h2>")
					}
				})
	}
	/* accept request submission */
	$("div#container div#main-pf")
			.on(
					'click',
					'img.accept',
					function() {
						$id = $(this).attr("id")
						$
								.ajax({
									url : "updateRequest.view",
									type : "POST",
									cahce : false,
									data : {
										choice : "approve",
										id : $id
									},
									beforeSend : function() {
									},
									success : function(data) {
										if (data == "success") {
											$("div#request-alert").show().text(
													"Request Approved").delay(
													800).fadeOut("Slow")
										} else {
											$("div#request-alert")
													.show()
													.text(
															"Sorry. We were unable to process your request. Try again.")
													.delay(800).fadeOut("Slow")
										}
										requestsList()
									}
								})
					})
	/* reject request submission */
	$("div#container div#main-pf")
			.on(
					'click',
					'img.reject',
					function() {
						$id = $(this).attr("id")
						$
								.ajax({
									url : "updateRequest.view",
									type : "POST",
									cahce : false,
									data : {
										choice : "reject",
										id : $id
									},
									beforeSend : function() {
									},
									success : function(data) {
										if (data == "success") {
											$("div#request-alert").show().text(
													"Request Rejected").delay(
													800).fadeOut("Slow")
										} else {
											$("div#request-alert")
													.show()
													.text(
															"Sorry. We were unable to process your request. Try again.")
													.delay(800).fadeOut("Slow")
										}
										requestsList()
									}
								})
					})
	/* show requests when clicked on request */
	$("#all-requests").click(function() {
		$("h2#message").text("")
		$("div#container div#main-pf div#requests-container").show();
		$("div#container div#main-pf div#add-user-form-container").hide();
		$("div#container div#main-pf div#head-count-container").hide();
		$("h2#message").text("")
		$("h2#password-message").text("")
		$("#new-password-message").text("")
		$("#old-password-message").text("")
		$("#confirm-password-message").text("")
		$("#password-container").hide();
		$("span#name-message").text("")
		$("span#id-message").text("")
		$("span#user-type-message").text("")
		$("#email-message").text("")
		requestsList()

	})

	/* show add user form */
	$("#create-user").click(function() {
		$("h2#message").text("")
		$("h3#all-request-msg").hide()
		$("div#container div#main-pf div#add-user-form-container").show();
		$("div#container div#main-pf div#requests-container").hide();
		$("div#container div#main-pf div#head-count-container").hide();
		$("h2#message").text("")
		$("h2#password-message").text("")
		$("#new-password-message").text("")
		$("#old-password-message").text("")
		$("#confirm-password-message").text("")
		$("#password-container").hide();
	})
	/* add user */
	$("#add-user-form")
			.submit(
					function(event) {
						event.preventDefault();
						$userType = $("#user-type").val()
						$email = $("#emailText").val()
						$name = $("#name").val()
						$userId = $("#userId").val()
						if (allFilled()) {

							$userData = {
								type : $userType,
								name : $name,
								userId : $userId,
								email : $email
							}
							$
									.ajax({
										url : $(this).attr("action"),
										type : "POST",
										contentType : 'application/json; charset=utf-8',
										data : JSON.stringify($userData),
										cahce : false,
										success : function(data) {
											if (data == "success") {
												$("h2#message").css({
													'color' : 'green'
												})
												$("h2#message")
														.text(
																"User added succesfully")

											} else {
												$("h2#message").css({
													'color' : '#e60000'
												})
												$("h2#message").text(
														"Error Occured")
											}
											$(
													"div#container div#main-pf div#add-user-form-container")
													.show();
											$(
													"div#container div#main-pf div#requests-container")
													.hide();
											$("#user-type").val("Select")
											$("#name").val("")
											$("#userId").val("")

										},
										error : function() {
											$("h2#message").css({
												'color' : '#e60000'
											})
											$("h2#message")
													.text(
															"Sorry. We were unable to process your request. Try again.")
										}
									})
						} else {
							$("h2#message").css({
								'color' : '#e60000'
							})
							$("h2#message").text("Fill all details")

						}
					})
	/* check if user type is selected */
	$("#user-type").change(function() {
		$request = $(this).val();
		if ($request == "Select") {
			$("span#user-type-message").text("Select user type")
		} else {
			// if ($request == "ADMIN") {
			// $("li#email").show()
			// } else {
			// $("li#email").hide()
			//
			// }
			$("span#user-type-message").text("")

		}
	})
	$("#emailText").focusout(function() {
		$email = $(this).val()
		if ($email == "") {
			$("#email-message").text("Email can't be empty")
		} else {
			if (!validateEmail($email)) {
				$("#email-message").text("Email not valid")
			} else {
				$("#email-message").text("")
			}
		}
	})
	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(email);
	}
	/* check if username is not empty */
	$("#name").focusout(function() {
		$request = $(this).val();
		if ($request == "") {
			$("span#name-message").text("Name can't be empty")
		} else {
			$("span#name-message").text("")

		}
	})
	/* check id of user */
	$("#userId").focusout(function() {
		$request = $(this).val();
		if ($request == "") {
			$("span#id-message").text("Enter user id")
		} else {
			$("span#id-message").text("")

		}
	})
	/* check if there are errors in form */
	function allFilled() {
		$userTypeMessage = $("#user-type-message").text()
		$nameMessage = $("#name-message").text()
		$idMessage = $("#id-message").text()
		$emailMessage = $("#email-message").text()
		if ($userTypeMessage == '' && $nameMessage == '' && $idMessage == ''
				&& $emailMessage == '' && $("#userType").val() != 'Select'
				&& $("#name").val() != '' && $("#userId").val() != ''
				&& $("#emailText").val() != '') {
			return true;
		}
		return false;

	}
	/* reset button */
	$("button#reset").click(function() {
		$("h2#message").text("")

		$("#user-type").val("Select")
		$("#name").val("")
		$("#userId").val("")
		$("span#name-message").text("")
		$("span#id-message").text("")
		$("span#user-type-message").text("")

	})
	/** *************head count*********** */
	$("li#head-count")
			.click(
					function() {
						$("h2#message").text("")
						$("div#container div#main-pf div#head-count-container")
								.show();

						$("div#container div#main-pf div#requests-container")
								.hide();
						$(
								"div#container div#main-pf div#add-user-form-container")
								.hide();
						$("h2#message").text("")
						$("h2#password-message").text("")
						$("#new-password-message").text("")
						$("#old-password-message").text("")
						$("#email-message").text("")
						$("#confirm-password-message").text("")
						$("#password-container").hide();
						$("span#name-message").text("")
						$("span#id-message").text("")
						$("span#user-type-message").text("")
						$
								.ajax({
									url : "getHeadCount.view",
									type : "GET",
									cahce : false,
									dataType : "JSON",
									beforeSend : function() {
									},
									success : function(requestData) {
										if (requestData != '') {
											$(
													"div#head-count-container div#head-count-table-container")
													.html(
															"<table id='head-count-table'>"
																	+ "<thead><tr><th>Request ID</th><th>MID</th><th>Name</th><th>From Date</th><th>From Time</th>"
																	+ "<th>To Date</th><th>To Time</th><th>Comment</th></tr></thead><tbody>")
											$
													.each(
															requestData,
															function(i, data) {
																$(
																		"div#head-count-container div#head-count-table-container table#head-count-table")
																		.append(
																				"<tr><td>"
																						+ (i + 1)
																						+ "</td>"
																						+ "<td>"
																						+ data.userId
																						+ "</td>"
																						+ "<td>"
																						+ data.name
																						+ "</td>"
																						+ "<td>"
																						+ data.requests[0].fromDate
																						+ "</td>"
																						+ "<td>"
																						+ data.requests[0].fromTime
																						+ "</td>"
																						+ "<td>"
																						+ data.requests[0].toDate
																						+ "</td>"
																						+ "<td>"
																						+ data.requests[0].toTime
																						+ "</td>"
																						+ "<td>"
																						+ data.requests[0].comment
																						+ "</td>"
																						+ "</tr>")
															})
											$(
													"div#head-count-container div#head-count-table-container")
													.append("</tbody></table>")
										} else {
											$("div#head-count-container")
													.html(
															"<h2 id = 'head-count-message'>No campus minds are out of the the campus</h2>")
										}

									}

								})
					})
	/** ************menu**************** */
	$("header span#menu-icon").click(function() {
		$("div#menu").toggle()
	})
	/** *************change password menu********** */
	$("div#menu ul li#password").click(function() {
		$("div#menu").hide()
		$("div#container div#main-pf div#add-user-form-container").hide();
		$("div#container div#main-pf div#requests-container").hide();
		$("div#container div#main-pf div#head-count-container").hide();
		$("h2#message").text("")
		$("span#name-message").text("")
		$("span#id-message").text("")
		$("span#user-type-message").text("")
		$("#email-message").text("")
		$("#new-password-message").text("")
		$("#old-password-message").text("")
		$("#new-password-message").text("")
		$("#old-password-message").text("")
		$("#confirm-password-message").text("")
		$("#password-container").show();
	})
	/** *************change passoword ********** */
	/** ********check current password******** */
	$("#oldpassword").focusout(function() {
		$oldPassword = $("#oldpassword").val()
		if ($oldPassword == "") {
			$("#old-password-message").text("Enter current password")

		} else {
			$("#old-password-message").text("")

		}

	})
	/** *********check new password************** */
	$("#newpassword").focusout(
			function() {
				$oldPassword = $("#oldpassword").val()
				$newPassword = $("#newpassword").val()
				if ($newPassword != "") {

					if ($newPassword == $oldPassword) {
						$("#new-password-message").text(
								"New password same as current password")
					} else {
						$("#new-password-message").text("")
					}
				} else {
					$("#new-password-message").text("Can't be blank")

				}
			})

	/** ******************check form********************** */
	function allDataFilled() {
		$confirmPasswordMessage = $("#confirm-password-message").text()
		$newPasswordMessage = $("#new-password-message").text()
		$oldPasswordMessage = $("#old-password-message").text()
		$oldPassword = $("#oldpassword").val()
		$confirmPassword = $("#confirmpassword").val()
		$newPassword = $("#newpassword").val()
		if ($oldPassword != "" && $newPassword != "" && $confirmPassword != ""
				&& $confirmPasswordMessage == "" && $newPasswordMessage == ""
				&& $oldPasswordMessage == "") {
			return true
		}
		return false;
	}
	/** * **************submit change password****************** */
	$("div#password-container form").submit(
			function(event) {
				event.preventDefault()
				if (allDataFilled()) {

					$newPassword = $("#newpassword").val()
					$oldPassword = $("#oldpassword").val()
					$.ajax({
						url : $(this).attr("action"),
						type : "POST",
						data : {
							currentPassword : $oldPassword,
							newPassword : $newPassword,
						// mid : "M1035881"
						},
						cache : false,
						success : function(data) {
							if (data == "success") {
								$("h2#password-message").css({
									'color' : 'green'
								})
								$("h2#password-message").text(
										"Password Changed")
							} else if (data == "failed") {
								$("h2#password-message").css({
									'color' : '#e60000'
								})
								$("h2#password-message").text(
										"Current password not correct")

							} else {
								$("h2#password-message").css({
									'color' : '#e60000'
								})
								$("h2#password-message").text(
										"Failed!!! Try later")

							}
						}

					})
				} else {
					$("h2#password-message").css({
						'color' : '#e60000'
					})
					$("h2#password-message").text("Fill all details correctly")
				}

			})
	$("#password-container #reset").click(function() {
		$("h2#password-message").text("")
		$("#confirm-password-message").text("")
		$("#new-password-message").text("")
		$("#old-password-message").text("")

	})
})