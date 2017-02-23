$(function() {
	/** ****************************************************** */
	/** *******************CAMPUS MIND *********************** */
	/** ****************************************************** */
	$todayDate = new Date()
	$todayDate.setHours(0, 0, 0, 0)
	$("#request-form-container input").prop('disabled', true);
	$("#request-from-time").prop("disabled", true);
	$("#request-to-time").prop("disabled", true);
	$("#request-from-time").html("")
	$("#request-to-time").html("")

	/*
	 * Show request form when user clicks on New Request
	 */
	$("#new-request").click(function() {
		$("div#menu").hide()
		$("#password-container").hide();
		$("#request-form-container").show();
		$("#history-container").hide();
		$("#acknowledgements").hide();
		$("div#hisotry-container table#history-table").html("")
		$("h2#message").text("")
		$("h2#password-message").text("")
		$("#new-password-message").text("")
		$("#old-password-message").text("")
		$("#confirm-password-message").text("")
	})

	/*
	 * events when user selects a request type
	 */
	$("#request-type").change(function() {
		$request = $(this).val();
		if ($request == "Select") {
			$("#request-type-message").text("Select a request")
			$("input").prop('disabled', true);
			$("#request-from-time").prop("disabled", true);
			$("#request-to-time").prop("disabled", true);
			$("#request-from-time").html("")
			$("#request-to-time").html("")

		} else {
			$("#request-type-message").text("")
			$("input#request-from-date").prop('disabled', false);
			$("#request-from-time").prop("disabled", false);
			$("#request-submit").prop("disabled", false)
		}

	})

	/*
	 * Validate from date
	 */
	$("#request-from-date").change(
			function() {
				$fromDate = new Date($(this).val())
				$fromDate.setHours(0, 0, 0, 0)
				$request = $("#request-type").val();
				$time = "<option value = '7:00'>7:00 AM</option>"
						+ "<option value = '8:00'>8:00 AM</option>"
						+ "<option value = '9:00'>9:00 AM</option>"
						+ "<option value = '10:00'>10:00 AM</option>"
						+ "<option value = '11:00'>11:00 AM</option>"
						+ "<option value = '12:00'>12:00 PM</option>"
						+ "<option value = '13:00'>1:00 PM</option>"
						+ "<option value = '14:00'>2:00 PM</option>"
						+ "<option value = '15:00'>3:00 PM</option>"
						+ "<option value = '16:00'>4:00 PM</option>"
						+ "<option value = '17:00'>5:00 PM</option>"
						+ "<option value = '17:30'>5:30 pm</option>"
						+ "<option value = '18:00'>6:00 PM</option>"
						+ "<option value = '18:30'>6:30 PM</option>"
						+ "<option value = '19:00'>7:00 PM</option>"
						+ "<option value = '19:30'>7:30 PM</option>"
						+ "<option value = '20:00'>8:00 PM</option>"
						+ "<option value = '20:30'>8:30 PM</option>"
						+ "<option value = '21:00'>9:00 PM</option>"
						+ "<option value = '21:30'>9:30 PM</option>"
						+ "<option value = '22:00'>10:00 PM</option>"
						+ "<option value = '22:30'>10:30 PM</option>"
				/* check if request type is night out or not */
				if ($request == "Night outs") {
					/* check if its a weekday or weekend */
					$day = $fromDate.getDay()
					if ($day == 0) {
						/* check if from date is greater than today's date */
						if ($fromDate.getTime() < $todayDate.getTime()) {
							$("#request-from-date-message").text(
									"From date can't be before today")
						} else {
							$("#request-from-date-message").text("")
							$("input#request-to-date").prop("disabled", false)
							$("#request-to-time").prop("disabled", false);
							$("#request-from-time").html(
									"<option>Select</option>" + $time)
							$("#request-to-time").html(
									"<option>Select</option>" + $time)
						}
					} else if ($day == 6) {
						/* check if from date is greater than today's date */
						if ($fromDate.getTime() < $todayDate.getTime()) {
							$("#request-from-date-message").text(
									"From date can't be before today")
						} else {
							$("#request-from-date-message").text("")
							$("input#request-to-date").prop("disabled", false)
							$("#request-to-time").prop("disabled", false);
							$("#request-from-time").html(
									"<option>Select</option>" + $time)
							$("#request-to-time").html(
									"<option>Select</option>" + $time)
						}
					} else {
						$("#request-from-date-message").text(
								"Night outs not allowed on working days")
						$("#request-from-time").html("<option>Select</option>")
						$("#request-to-time").html("<option>Select</option>")
					}
				}
				/* request is normal or emergency */
				else {
					/* check if dates are similar or not */
					if ($fromDate.getTime() == $todayDate.getTime()) {
						$("#request-from-date-message").text("")
						$("input#request-to-date").prop("disabled", false)

						$("#request-to-time").prop("disabled", false);
						/* check if its a weekday or weekend */
						$day = $fromDate.getDay()
						/* if Sunday */
						if ($day == 0) {
							$("#request-from-time").html(
									"<option>Select</option>" + $time)
							$("#request-to-time").html(
									"<option>Select</option>" + $time)

						}
						/* its a weekday */
						else {
							$("#request-from-time").html(
									"<option>Select</option>" + $time)
							$("#request-to-time").html(
									"<option>Select</option>" + $time)

						}

					}
					/* dates are not similar */
					else {
						$("#request-from-date-message").text(
								"From date must be today's date")

					}
				}
			})
	/*
	 * check from time
	 */
	$("#request-from-time").change(
			function() {
				$fromDate = new Date($("#request-from-date").val())
				$fromDate.setHours(0, 0, 0, 0)
				$request = $("#request-type").val();
				$fromTime = $(this).val()
				$hour = $fromTime.split(":")[0]
				$min = $fromTime.split(":")[1]
				$day = $fromDate.getDay()
				/* check if request is night out */
				if ($request != "Emergency stay outs") {
					/* check if not sunday */
					if ($day != 0) {
						if ($hour <= 17) {
							if ($min != 30) {
								$("#request-from-time-message").text(
										"Can't go out before 5:30 PM")
							} else {
								$("#request-from-time-message").text("")
							}
						} else {
							$("#request-from-time-message").text("")

						}
					}
				} else {
					$("#request-from-time-message").text("")

				}
			})

	/*
	 * Validate to date
	 */
	$("#request-to-date")
			.change(
					function() {
						$fromDate = new Date($("#request-from-date").val())
						$fromDate.setHours(0, 0, 0, 0)
						$toDate = new Date($(this).val())
						$toDate.setHours(0, 0, 0, 0)
						$request = $("#request-type").val();
						/* check if request is normal stay out request */
						if ($request == "Normal stay outs") {
							if ($toDate.getTime() == $todayDate.getTime()) {
								$("#request-to-date-message").text("")

							} else {
								$("#request-to-date-message").text(
										"To date must be same as today's date")

							}
						} else if ($request == "Night outs") {

							/* check if to date is greater than todays date */
							if ($toDate.getTime() < $todayDate.getTime()) {
								/* check if to date is greater than from date */
								if ($toDate.getTime() <= $fromDate.getTime()) {
									$("#request-to-date-message")
											.text(
													"To date must be after today's date and from date")
								} else {
									$("#request-to-date-message")
											.text(
													"To date can't be before today's date")

								}
							} else {
								/* check if to date is greater than from date */
								if ($toDate.getTime() <= $fromDate.getTime()) {
									$("#request-to-date-message").text(
											"To date must be after from date")
								} else {
									$("#request-to-date-message").text("")

								}

							}
						}
						/* Emergency request */
						else {
							/* check if to date is greater than todays date */
							if ($toDate.getTime() < $todayDate.getTime()) {
								/* check if to date is greater than from date */
								if ($toDate.getTime() < $fromDate.getTime()) {
									$("#request-to-date-message")
											.text(
													"To date must be after today's date and from date")
								} else {
									$("#request-to-date-message")
											.text(
													"To date can't be before today's date")

								}
							} else {
								/* check if to date is greater than from date */
								if ($toDate.getTime() < $fromDate.getTime()) {
									$("#request-to-date-message").text(
											"To date must be after from date")
								} else {
									$("#request-to-date-message").text("")

								}

							}

						}
					})
	/*
	 * check to time
	 */
	$("#request-to-time").change(
			function() {
				$fromDate = new Date($("#request-from-date").val())
				$fromDate.setHours(0, 0, 0, 0)
				$toDate = new Date($("#request-to-date").val())
				$toDate.setHours(0, 0, 0, 0)
				$request = $("#request-type").val();
				$fromTime = $("#request-from-time").val()
				$hour = $fromTime.split(":")[0]
				$min = $fromTime.split(":")[1]
				$day = $fromDate.getDay()
				$toTime = $(this).val()

				$toHour = $toTime.split(":")[0]
				$toMin = $toTime.split(":")[1]
				if ($request == "Normal stay outs"
						|| $request == "Emergency stay outs") {
					if ($toHour < $hour) {
						$("#request-to-time-message").text("Invalid time")
					} else if ($toHour == $hour) {
						if ($toMin <= $min) {
							$("#request-to-time-message").text("Invalid time")

						} else {
							$("#request-to-time-message").text("")

						}

					} else {
						$("#request-to-time-message").text("")

					}

				} else {
					$("#request-to-time-message").text("")

				}

			})
	/*
	 * check if comment is there
	 */
	$("#comment").focusout(function() {
		$comment = $("#comment").val()
		if ($comment == "") {
			$("#comment-message").text("Comment missing")
		} else {
			$("#comment-message").text("")

		}
	})

	/*
	 * Form submission
	 */
	$("#request-form")
			.submit(
					function(event) {
						event.preventDefault()
						$userId = $("#userId").val()
						$requestType = $("#request-type").val();
						$requestFromDate = $("#request-from-date").val();
						$requestFromTime = $("#request-from-time").val();
						$requestToDate = $("#request-to-date").val();
						$requestToTime = $("#request-to-time").val();
						$comment = $("#comment").val();
						// first check if all data is filled
						if (allFilled()) {
							$dataObject = {
								userId : $userId,
								type : $requestType,
								fromDate : $requestFromDate,
								fromTime : $requestFromTime,
								toDate : $requestToDate,
								toTime : $requestToTime,
								comment : $comment
							};
							// then send ajax request for form submission
							$
									.ajax({
										url : $(this).attr("action"),
										type : "GET",
										data : $dataObject,
										cahce : false,
										success : function(data) {
											if (data == "success") {
												$("h2#message")
														.text(
																"Request submitted succesfully")
												$("h2#message").css({
													'color' : 'green'
												})
											} else {
												$("h2#message").css({
													'color' : '#e60000'
												})
												$("h2#message").text(
														"Error Occured")
											}
											$("#request-form-container input")
													.prop('disabled', true);
											$("#request-from-date").val("")
											$("#request-to-date").val("")
											$("#request-from-time").html("")
											$("#request-to-time").html("")
											$("#request-type-message").text("")
											$("#request-from-date-message")
													.text("")
											$("#request-to-date-message").text(
													"")
											$("#comment-message").text("")
											$(
													'#request-type option[value="Select"]')
													.attr("selected", true);
											$("#comment").val("");
											$("#request-form-container input")
													.prop('disabled', true);
											$("#request-from-time").prop(
													"disabled", true);
											$("#request-to-time").prop(
													"disabled", true);

										},
										error : function() {
											$("h2#message").css({
												'color' : '#e60000'
											})
											$("h2#message")
													.text(
															"Sorry. We were unable to register your request. Try again.")
										}
									})
						} else {
							$("h2#message").css({
								'color' : '#e60000'
							})
							$("h2#message").text("Fill all details correctly")
						}
					})

	/*
	 * function to check if everything is filled, and there is no error
	 */

	function allFilled() {
		$requestTypeMessage = $("#request-type-message").text()
		$requestFromDateMessage = $("#request-from-date-message").text()
		$requestToDateMessage = $("#request-to-date-message").text()
		$commentMesage = $("#comment-message").text()
		if ($requestTypeMessage == '' && $requestFromDateMessage == ''
				&& $requestToDateMessage == '' && $commentMesage == ''
				&& $("#request-type").val() != 'Select'
				&& $("#request-from-date").val() != ''
				&& $("#request-from-time").val() != 'Select'
				&& $("#request-to-date").val() != ''
				&& $("#request-to-time").val() != 'Select'
				&& $("#comment").val() != '') {
			return true;
		}
		return false;

	}
	/* reset button */
	$("button#reset").click(function() {
		$("h2#message").text("")
		$('#request-type option[value="Select"]').attr("selected", true);
		$("#request-from-date").val("")
		$("#comment").val("");
		$("#request-form-container input").prop('disabled', true);
		$("#request-from-time").prop("disabled", true);
		$("#request-to-time").prop("disabled", true);
		$("#request-from-time").html("")
		$("#request-to-time").html("")
		$("#request-type-message").text("")
		$("#request-from-date-message").text("")
		$("#request-to-date-message").text("")
		$("#comment-message").text("")

	})

	/*
	 * Show request history table when clicked on view history
	 */

	$("#view-history")
			.click(
					function() {
						$("div#menu").hide()
						$("#history-container").show();
						$("#request-form-container").hide();
						$("#password-container").hide();
						$("#acknowledgements").hide();
						$("h2#message").text("")
						$("#request-type-message").text("")
						$("#request-from-date-message").text("")
						$("#request-to-date-message").text("")
						$("#comment-message").text("")
						$("h2#password-message").text("")
						$("#new-password-message").text("")
						$("#old-password-message").text("")
						$("#confirm-password-message").text("")

						$userId = $("#userId").val()
						$
								.ajax({
									url : "history.view",
									type : "GET",
									data : {
										userId : $userId
									},
									dataType : "JSON",
									cache : false,
									success : function(requestData) {
										if (requestData != '') {
											$(
													"div#history-container div#table-container")
													.html(
															"<table id='history-table'>"
																	+ "<thead><tr><th>Request ID</th><th>Request Type</th><th>From Date</th><th>From Time</th>"
																	+ "<th>To Date</th><th>To Time</th><th>Status</th><th>Comment</th></tr></thead><tbody>")
											$
													.each(
															requestData,
															function(i, data) {
																var status;
																switch (data[4]) {
																case 1:
																	status = "Pending";
																	break;
																case 2:
																	status = "Approved";
																	break;
																case 3:
																	status = "Rejected";
																	break;
																case 4:
																	status = "Checked In";
																	break;
																case 5:
																	status = "Checked Out";
																	break;

																}
																var type;
																switch (data[7]) {
																case 0:
																	type = "Normal Stay Out";
																	break;
																case 1:
																	type = "Night Out";
																	break;
																case 2:
																	type = "Emeergency Stay out";
																	break;
																}

																$(
																		"div#history-container div#table-container table#history-table")
																		.append(
																				"<tr><td>"
																						+ (i + 1)
																						+ "</td>"
																						+ "<td>"
																						+ type
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
																						+ status
																						+ "</td>"
																						+ "<td>"
																						+ data[1]
																						+ "</td>"
																						+ "</tr>")
															})
											$(
													"div#history-container div#table-container")
													.append("</tbody></table>")
										} else {
											$("div#history-container")
													.html(
															"<h2 id = 'history-message'>No previous requests found</h2>")
										}
									}
								})
					})
	/** **********acknowledgements*********** */
	$("footer p").click(function() {
		$("div#menu").hide()
		$("#history-container").hide();
		$("#password-container").hide();
		$("#request-form-container").hide();
		$("h2#message").text("")
		$("#acknowledgements").show();
		$("h2#password-message").text("")
		$("#confirm-password-message").text("")

	})
	/** ************menu**************** */
	$("header span#menu-icon").click(function() {
		$("div#menu").toggle()
	})
	/** *************change passoword menu********** */
	$("div#menu ul li#password").click(function() {
		$("div#menu").hide()
		$("#history-container").hide();
		$("#request-form-container").hide();
		$("h2#message").text("")
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

	/** *********check confirm passwrod************** */
	$("#confirmpassword").focusout(function() {
		$oldPassword = $("#oldpassword").val()
		$confirmPassword = $(this).val();
		$newpassword = $("#newpassword").val()
		if ($confirmPassword != $newpassword) {
			$("#confirm-password-message").text("Password not matching")
		} else {
			$("#confirm-password-message").text("")
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
							mid : "M1035881"
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