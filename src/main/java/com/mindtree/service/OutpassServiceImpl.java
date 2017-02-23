package com.mindtree.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mindtree.dao.DaoService;
import com.mindtree.entity.Request;
import com.mindtree.entity.RequestStatus;
import com.mindtree.entity.User;
import com.mindtree.entity.UserType;
import com.mindtree.exceptions.OutpassException;
import com.mindtree.mail.Mail;

/**
 * @author M1035881 Service class for the project
 */
@Service("outpassService")
public class OutpassServiceImpl implements OutpassService {
	@Autowired
	DaoService daoService;

	@Override
	public int redirectUser(String userId, String password) throws OutpassException {
		int type = daoService.validateUser(userId, password);
		return type;
	}

	@Override
	public void addRecord() throws OutpassException {
		boolean isEmpty = daoService.checkForUser();
		if (isEmpty) {
			User user = new User();
			user.setName("admin");
			user.setPassword("123321");
			user.setUserId("M1035007");
			user.setType(UserType.ADMIN);
			user.setUserStatus(RequestStatus.CHECKEDIN);
			daoService.addRecord(user);
			return;
		}
		return;

	}

	@Override
	public String getName(String userId) throws OutpassException {
		String name = daoService.getName(userId);
		return name;
	}

	/*****************************************
	 * *********CAMPUS MIND MODULE**************
	 ************************************/
	@Override
	public boolean registerRequest(Request requestObject) throws OutpassException {
		boolean inserted = false;
		inserted = daoService.insertRequest(requestObject);
		return inserted;
	}

	@Override
	public User getUser(String userId) throws OutpassException {
		User user = daoService.getUser(userId);
		return user;
	}

	@Override
	public ArrayList<Request> getRequestsByUser(String userId) throws OutpassException {
		ArrayList<Request> requests = daoService.getRequestsByUser(userId);
		return requests;
	}

	/*****************************************
	 * *********ADMIN MODULE**************
	 ************************************/
	@Override
	public ArrayList<Request> getPendingRequests() throws OutpassException {
		ArrayList<Request> requests = daoService.getPendingRequests();
		return requests;
	}

	@Override
	public boolean updateRequestStatus(String choice, String id) throws OutpassException {
		int requestId = Integer.parseInt(id);
		boolean updationDone = daoService.updateRequestStatus(choice, requestId);
		return updationDone;
	}

	@Override
	public boolean acceptAllRequests() throws OutpassException {
		try {
			boolean check = daoService.acceptAllRequests();
			return check;
		} catch (Exception e) {
			throw new OutpassException("Could not accept all requests!", e);
		}
	}

	@Override
	public boolean rejectAllRequests() throws OutpassException {
		try {
			boolean check = daoService.rejectAllRequests();
			return check;
		} catch (Exception e) {
			throw new OutpassException("Could not reject all requests!", e);
		}
	}

	@Override
	public boolean adduser(User user) throws OutpassException {
		boolean inserted = false;
		inserted = daoService.addUser(user);
		return inserted;
	}

	@Override
	public List<User> adminGenerateHeadcount() throws OutpassException {
		List<User> headCountList = daoService.adminGenerateHeadcount();
		Iterator<User> itrUser = headCountList.iterator();
		System.out.println("!!!!");
		System.out.println(headCountList);
		while (itrUser.hasNext()) {
			// only keep checkedout users
			if (itrUser.next().getUserStatus().equals(RequestStatus.CHECKEDOUT)) {
				int count = 0;
				Iterator<Request> itrRequest = headCountList.get(count).getRequests().iterator();
				// only keep 1 request
				while (itrRequest.hasNext()) {
					// dont get overflowed- remove user from request
					if (count == 0) {
						itrRequest.next().setUser(null);
					} else {
						itrRequest.next();
						itrRequest.remove();
					}
					count++;
				}
			} else {
				itrUser.remove();
			}
		}
		// apply some form of sorting, to get the latest request
		// business rule should also limit request to 1 request in 1 time slot
		// half done thru ajax,jquery
		// if(itrRequest.next().getFromTime()>){
		//
		// }
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n" + headCountList);

		return headCountList;
	}

	/*****************************************
	 * *********SECURITY MODULE **************
	 ************************************/
	@Override
	public List<Request> getRequestsForSecurity() throws OutpassException {
		try {
			// TODO Auto-generated method stub
			List<Request> requests = daoService.getRequestsForSecurity();
			return requests;
		} catch (Exception e) {
			throw new OutpassException("Could not fetch requests!", e);
		}
	}

	@Override
	public boolean securityUpdateStatus(int id, int value, String userId) throws OutpassException {
		try {
			// TODO Auto-generated method stub
			daoService.securityUpdateStatus(id, value, userId);
			return true;
		} catch (Exception e) {
			throw new OutpassException("Could not update requests!", e);
		}
	}

	@Override
	public boolean changePassword(String password, String currentPassword, String userId) throws OutpassException {

		return daoService.changePassword(password, currentPassword, userId);
	}

}
