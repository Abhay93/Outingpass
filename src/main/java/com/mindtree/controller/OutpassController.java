package com.mindtree.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mindtree.dto.LoginForm;
import com.mindtree.entity.Request;
import com.mindtree.entity.RequestStatus;
import com.mindtree.entity.RequestType;
import com.mindtree.entity.User;
import com.mindtree.entity.UserType;
import com.mindtree.exceptions.OutpassException;
import com.mindtree.mail.Mail;
import com.mindtree.service.OutpassService;
import com.mindtree.validators.LoginValidator;

@Controller
public class OutpassController {
	@Autowired
	OutpassService outpassService;

	// private Logger logger = Logger.getLogger(getClass());

	/**
	 * This method is used to redirect to login page
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String addForm(ModelMap model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("login", loginForm);
		try {
			outpassService.addRecord();
		} catch (OutpassException e) {
			e.printStackTrace();
			return "error";
		}
		return "home";
	}

	/**
	 * This method is for login checking
	 * 
	 * @param loginForm
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/home")
	public String login(@ModelAttribute("login") LoginForm loginForm, ModelMap model, HttpServletRequest request,
			BindingResult errors, RedirectAttributes rm) {
		String userId = loginForm.getUsername();
		String password = loginForm.getPassword();
		System.out.println(userId);
		LoginValidator loginValidator = new LoginValidator();
		loginValidator.validate(loginForm, errors);
		if (errors.hasErrors()) {
			rm.addFlashAttribute("message", "Fill all Details");
			return "redirect:login.view";
		}
		// if (loginValidator.supports(LoginForm.class)){}
		try {
			int type = outpassService.redirectUser(userId, password);
			System.out.println(type);
			if (type != 0) {
				String name = null;
				HttpSession session = null;
				Request requestForm = new Request();
				switch (type) {
				case -1:
					rm.addFlashAttribute("message", "User Not Found");
					return "redirect:login.view";
				case 1:

					name = outpassService.getName(userId);
					session = request.getSession();
					session.setAttribute("userId", userId);
					session.setAttribute("name", name);
					model.addAttribute("requestForm", requestForm);
					return "CampusMindHome";
				case 2:
					name = outpassService.getName(userId);
					session = request.getSession();
					session.setAttribute("userId", userId);
					session.setAttribute("name", name);
					User user = new User();
					model.addAttribute("addUser", user);
					return "pfhome";
				case 3:
					name = outpassService.getName(userId);
					session = request.getSession();
					session.setAttribute("userId", userId);
					session.setAttribute("name", name);
					List<Request> requests = securityRequest();
					model.addAttribute("request", requests);
					return "securityHome";
				default:
					rm.addFlashAttribute("message", "Incorrect Password");
					return "redirect:login.view";
				}
			} else {
				rm.addFlashAttribute("message", "Incorrect Password");
				return "redirect:login.view";
			}

		} catch (

		OutpassException e)

		{

			// System.out.println(e.getMessage());
			// logger.debug(e.getCause());
			rm.addFlashAttribute("message", "Server Down");
			return "redirect:login.view";
		}

	}

	/****************************************
	 * **********CAMPUS MIND MODULE*********
	 *************************************/
	/**
	 * This method adds a new outing request
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitRequest")
	public String submitRequest(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		RequestType requestType = RequestType
				.valueOf(request.getParameter("type").replaceAll("\\s+", "").toUpperCase());
		String requestFromDate = request.getParameter("fromDate");
		String requestFromTime = request.getParameter("fromTime");
		String requestToDate = request.getParameter("toDate");
		String requestToTime = request.getParameter("toTime");
		String comment = request.getParameter("comment");

		User user = null;
		try {
			user = outpassService.getUser(userId);
		} catch (OutpassException e1) {
			e1.printStackTrace();
			return "error";
		}

		Request requestObject = new Request();

		requestObject.setType(requestType);
		requestObject.setUser(user);
		requestObject.setFromDate(requestFromDate);
		requestObject.setFromTime(requestFromTime);
		requestObject.setToDate(requestToDate);
		requestObject.setToTime(requestToTime);
		if (requestType.ordinal() == 0) {
			requestObject.setStatus(RequestStatus.APPROVED);
		} else {
			requestObject.setStatus(RequestStatus.PENDING);
		}
		requestObject.setComment(comment);
		boolean inserted;
		try {
			inserted = outpassService.registerRequest(requestObject);
			System.out.println(inserted);
			if (!inserted) {
				return "error";
			} else {
				return "success";
			}
		} catch (OutpassException e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * This method used to get all outing requests made by user
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/history")
	public ArrayList<Request> getRequestByUser(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		try {
			ArrayList<Request> requests = outpassService.getRequestsByUser(userId);
			System.out.println(requests.size());
			return requests;
		} catch (OutpassException e) {
			e.printStackTrace();
		}
		return null;

	}

	/****************************************
	 * **********ADMIN MODULE*********
	 *************************************/
	/**
	 * This method returns all pending requests
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/allrequests")
	public ArrayList<Request> getPendingRequests(HttpServletRequest request) {
		try {
			ArrayList<Request> requests = outpassService.getPendingRequests();
			return requests;
		} catch (OutpassException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * method to approve or reject a request
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateRequest")
	public String updateRequest(HttpServletRequest request) {

		String choice = request.getParameter("choice");
		String id = request.getParameter("id");
		boolean updationDone = false;
		try {
			updationDone = outpassService.updateRequestStatus(choice, id);
			if (!updationDone) {
				return "error";
			} else {
				return "success";
			}

		} catch (OutpassException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/adminAcceptAll")
	public String adminAcceptAll(Model model, RedirectAttributes rm) {
		try {
			boolean check = outpassService.acceptAllRequests();
			if (check == true) {
				rm.addFlashAttribute("mesg", "All requests accepted!!");
				return "redirect:pf.view";
			} else {
				rm.addFlashAttribute("mesg2", "Error in accepting requests!!");
				return "redirect:pf.view";
			}
		} catch (Exception e) {
			rm.addFlashAttribute("mesg2", e.getMessage());
			return "redirect:pf.view";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/adminRejectAll")
	public String adminRejectAll(Model model, RedirectAttributes rm) {
		try {
			boolean check = outpassService.rejectAllRequests();
			if (check == true) {
				rm.addFlashAttribute("mesg", "All requests Rejected!!");
				return "redirect:pf.view";
			} else {
				rm.addFlashAttribute("mesg2", "Error in rejecting requests!!");
				return "redirect:pf.view";
			}
		} catch (Exception e) {
			rm.addFlashAttribute("mesg2", e.getMessage());
			return "redirect:pf.view";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pf")
	public String pfHome(@ModelAttribute("login") LoginForm loginForm, Model model) {
		User user = new User();
		model.addAttribute("addUser", user);
		return "pfhome";
	}

	/**
	 * Admin creates a new user
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/adduser")
	public String addUser(@RequestBody User user, HttpServletRequest request) {

		UserType userType = user.getType();
		String password = null;
		if (userType.equals(UserType.ADMIN)) {
			password = "123321";

		} else if (userType.equals(UserType.CAMPUSMIND)) {
			password = "12345";

		} else {
			password = "123456";
		}
		user.setPassword(password);
		user.setUserStatus(RequestStatus.CHECKEDIN);
		System.out.println(user);
		boolean inserted;
		try {
			inserted = outpassService.adduser(user);
			System.out.println(inserted);
			if (!inserted) {
				return "error";
			} else {
				return "success";
			}
		} catch (OutpassException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "error";
		}
	}

	/**
	 * This method used to get head count
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getHeadCount")
	public ArrayList<User> getHeadCount(HttpServletRequest request) {
		try {
			ArrayList<User> requests = (ArrayList<User>) outpassService.adminGenerateHeadcount();
			System.out.println(requests);
			System.out.println("ABHAY");
			return requests;
		} catch (OutpassException e) {
			e.printStackTrace();
		}
		return null;

	}

	/****************************************
	 * **********SECURITY MODULE*********
	 *************************************/
	/**
	 * Get all approved or checked out requests
	 * 
	 * @return
	 */
	public List<Request> securityRequest() {
		try {
			List<Request> requests = outpassService.getRequestsForSecurity();
			return requests;
		} catch (OutpassException e) {
			return null;
		}

	}

	/**
	 * Method to change request status to checked out
	 * 
	 * @param current
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "changeRequestCheckout", method = RequestMethod.GET)
	public String changeRequestCheckout(@RequestParam("current") String current, ModelMap model,
			HttpServletRequest request, RedirectAttributes rm) {
		try {
			int curr = Integer.parseInt(current);
			HttpSession session = null;
			session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			boolean check = outpassService.securityUpdateStatus(curr, 5, userId);
			if (check == true) {
				// List<Request> requests =
				// outpassService.getRequestsForSecurity();
				// model.addAttribute("request", requests);
				return "redirect:security.view";
			}
			return "redirect:security.view";
		} catch (OutpassException e) {
			rm.addFlashAttribute("request", e.getMessage());
			return "redirect:security.view";
		}
	}

	/**
	 * Method to change request status to checked in
	 * 
	 * @param current
	 * @param model
	 * @param request
	 * @return
	 * @throws OutpassException
	 */
	@RequestMapping(value = "changeRequestCheckin", method = RequestMethod.GET)
	public String changeRequestCheckin(@RequestParam("current") String current, ModelMap model,
			HttpServletRequest request, RedirectAttributes rm) {
		try {
			int curr = Integer.parseInt(current);
			HttpSession session = null;
			session = request.getSession();
			String userId = (String) session.getAttribute("userId");
			boolean check = outpassService.securityUpdateStatus(curr, 4, userId);
			if (check == true) {
				// List<Request> requests =
				// outpassService.getRequestsForSecurity();
				// model.addAttribute("request", requests);
				return "redirect:security.view";
			}
			return "redirect:security.view";
		} catch (OutpassException e) {
			rm.addFlashAttribute("request", e.getMessage());
			return "redirect:security.view";
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/security")
	public String securityHome(@ModelAttribute("login") LoginForm loginForm, Model model) {
		List<Request> requests = securityRequest();
		model.addAttribute("request", requests);
		return "securityHome";
	}

	/********************************************
	 ****************** GENERAL*******************
	 ******************************************/

	/**
	 * method to change password
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changepassword")
	public String changePassword(HttpServletRequest request) {
		String password = request.getParameter("newPassword");
		String curretPassword = request.getParameter("currentPassword");
		HttpSession session = null;
		session = request.getSession();
		String mid = (String) session.getAttribute("userId");
		try {

			boolean changedPassword = outpassService.changePassword(password, curretPassword, mid);
			if (changedPassword) {
				return "success";
			}
			return "failed";
		} catch (OutpassException e) {
			return "error";
		}

	}

	/**
	 * Logout method
	 * 
	 * @param model
	 * 
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, ModelMap model) {
		HttpSession session = null;
		session = request.getSession();
		session.invalidate();
		LoginForm loginForm = new LoginForm();
		model.addAttribute("login", loginForm);
		return "home";
	}

}
