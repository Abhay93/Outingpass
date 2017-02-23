package com.mindtree.dao;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.entity.Request;
import com.mindtree.entity.User;
import com.mindtree.exceptions.OutpassException;

public interface DaoService {

	boolean checkForUser() throws OutpassException;

	void addRecord(User user) throws OutpassException;

	int validateUser(String username, String password) throws OutpassException;

	User getUser(String employeeId) throws OutpassException;

	String getName(String employeeId) throws OutpassException;

	boolean insertRequest(Request requestObject) throws OutpassException;

	ArrayList<Request> getRequestsByUser(String employeeId) throws OutpassException;

	ArrayList<Request> getPendingRequests() throws OutpassException;

	boolean addUser(User user) throws OutpassException;

	List<Request> getRequestsForSecurity() throws OutpassException;

	boolean updateRequestStatus(String choice, int requestId) throws OutpassException;

	boolean rejectAllRequests() throws OutpassException;

	boolean acceptAllRequests() throws OutpassException;

	boolean changePassword(String password, String currentPassword, String userId) throws OutpassException;

	ArrayList<User> adminGenerateHeadcount() throws OutpassException;

	boolean securityUpdateStatus(int id, int value, String userID) throws OutpassException;

	boolean securityUpdateUsers(int value, String userId) throws OutpassException;

}
