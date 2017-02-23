package com.mindtree.service;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.entity.Request;
import com.mindtree.entity.User;
import com.mindtree.exceptions.OutpassException;

public interface OutpassService {
	void addRecord() throws OutpassException;

	int redirectUser(String employeeId, String password) throws OutpassException;

	String getName(String mid) throws OutpassException;

	ArrayList<Request> getRequestsByUser(String employeeId) throws OutpassException;

	User getUser(String employeeId) throws OutpassException;

	boolean registerRequest(Request requestObject) throws OutpassException;

	ArrayList<Request> getPendingRequests() throws OutpassException;

	boolean adduser(User user) throws OutpassException;

	List<Request> getRequestsForSecurity() throws OutpassException;

	boolean updateRequestStatus(String choice, String id) throws OutpassException;

	boolean acceptAllRequests() throws OutpassException;

	boolean rejectAllRequests() throws OutpassException;

	boolean changePassword(String password, String currentPassword, String userId) throws OutpassException;

	List<User> adminGenerateHeadcount() throws OutpassException;

	boolean securityUpdateStatus(int id, int value, String userId) throws OutpassException;

}
