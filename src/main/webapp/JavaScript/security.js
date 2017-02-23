$(function() {
	/** ************menu**************** */
	$("header span#menu-icon").click(function() {
		$("div#menu").toggle()
	})
	/** *************change passoword menu********** */
	$("div#menu ul li#password").click(function() {
		$("div#menu").hide()
		$("div#container div#main-sec").hide();
		$("h2#password-message").text("")
		$("#new-password-message").text("")
		$("#old-password-message").text("")
		$("#confirm-password-message").text("")
		$("#sec-password-container").show();
		$("span#sec-home").show()
	})
	$("span#sec-home").click(function() {
		$("div#container div#main-sec").show();
		$("#sec-password-container").hide();
		$("h2#password-message").text("")
		$("#new-password-message").text("")
		$("#old-password-message").text("")
		$("#confirm-password-message").text("")
		$("span#sec-home").hide()
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
	$("div#sec-password-container form").submit(
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