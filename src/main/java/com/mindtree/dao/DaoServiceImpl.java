package com.mindtree.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mindtree.dao.util.CloseConnectionUtil;
import com.mindtree.entity.Request;
import com.mindtree.entity.RequestStatus;
import com.mindtree.entity.User;
import com.mindtree.entity.UserType;
import com.mindtree.exceptions.OutpassException;
import com.mindtree.mail.Mail;

public class DaoServiceImpl implements DaoService {
	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	@Qualifier("mail")
	private Mail mail;

	// private Logger logger = Logger.getLogger(getClass());

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean checkForUser() throws OutpassException {
		Session session = null;
		Transaction transaction = null;
		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query userQuery = session.createQuery("FROM User");
			if (userQuery.list().isEmpty()) {
				return true;
			}
			System.out.println("HI!!!!!!!!!!");

			// logger.info(user);
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new OutpassException("Can't connect with database", e);

		} finally {
			CloseConnectionUtil.closeSession(session);
			// logger.info("Closed session connection");
		}
		return false;
	}

	@Override
	public void addRecord(User user) throws OutpassException {
		Session session = null;
		Transaction transaction;
		try {
			System.out.println("ASD");

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.persist(user);
			transaction.commit();
			// session.close();
		} catch (Exception e) {

		} finally {
			CloseConnectionUtil.closeSession(session);

		}
	}

	@Override
	public int validateUser(String userId, String password) throws OutpassException {
		Session session = null;
		Transaction transaction = null;
		int type = 0;
		try {
			System.out.println(userId);
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query userData = session.createQuery("FROM User U WHERE U.userId = :userId");
			System.out.println("CHALDS");
			userData.setString("userId", userId);
			@SuppressWarnings({ "unchecked" })
			ArrayList<User> user = (ArrayList<User>) userData.list();
			System.out.println(user.size());
			if (user.isEmpty() == true) {
				type = -1;
				return type;
			}
			String DBPassword = user.get(0).getPassword();
			if (DBPassword.equals(password)) {
				type = ((User) user.get(0)).getType().ordinal();
				// return type;
			}
			transaction.commit();
		} catch (JDBCConnectionException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println(e.getMessage() + "******************************************");
			throw new OutpassException("Can't connect with database", e);

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new OutpassException("Can't connect with database", e);

		} finally {
			CloseConnectionUtil.closeSession(session);
			// logger.info("Closed session connection");
			System.out.println("*****************close sesiASDon************");
		}
		return type;
	}

	public String getName(String userId) throws OutpassException {
		try {
			User user = getUser(userId);
			return user.getName();
		} catch (Exception e) {
			throw new OutpassException("", e);
		}
	}

	/*****************************************
	 * *********CAMPUS MIND MODULE**********
	 ************************************/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mindtree.dao.DaoService#getUser(java.lang.String)
	 */
	public User getUser(String userId) throws OutpassException {
		Session session = null;
		Transaction transaction = null;
		User user = null;
		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query userQuery = session.createQuery("FROM User WHERE userId = :userId");
			userQuery.setString("userId", userId);
			user = (User) userQuery.list().get(0);
			// logger.info(user);
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new OutpassException("Can't connect with database", e);

		} finally {
			CloseConnectionUtil.closeSession(session);
			// logger.info("Closed session connection");
		}
		return user;
	}

	@Override
	public boolean insertRequest(Request requestObject) throws OutpassException {
		Session session = null;
		Transaction transaction = null;
		boolean done = false;
		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query deletePendingRequest = session.createQuery("DELETE FROM Request WHERE status = 1 AND user = :userId");
			deletePendingRequest.setString("userId", requestObject.getUser().getUserId());
			deletePendingRequest.executeUpdate();
			session.persist(requestObject);
			transaction.commit();
			done = true;

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			done = false;
			throw new OutpassException("Can't connect with database", e);

		} finally {
			CloseConnectionUtil.closeSession(session);
			// logger.info("Closed session connection");
		}
		return done;
	}

	@Override
	public ArrayList<Request> getRequestsByUser(String userId) throws OutpassException {
		Session session = null;
		Transaction transaction = null;
		// ArrayList<Request> requests=null;
		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query requestQuery = session.createSQLQuery(
					"SELECT R.* FROM requests AS R INNER JOIN users AS U WHERE U.user_id = R.user_id AND U.user_id = '"
							+ userId + "'");
			@SuppressWarnings("unchecked")
			ArrayList<Request> requests = (ArrayList<Request>) requestQuery.list();
			transaction.commit();
			return requests;

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new OutpassException("Can't connect with database", e);

		} finally {
			CloseConnectionUtil.closeSession(session);
			// logger.info("Closed session connection");
		}
	}

	/*****************************************
	 * *********ADMIN MODULE**************
	 ************************************/

	@Override
	public ArrayList<Request> getPendingRequests() throws OutpassException {
		Session session = null;
		Transaction transaction = null;
		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query requestQuery = session.createSQLQuery("SELECT R.* FROM requests AS R WHERE R.status = 1");
			// Query requestQuery = session.createSQLQuery("SELECT * FROM
			// requests where status = 2");

			@SuppressWarnings("unchecked")
			ArrayList<Request> requests = (ArrayList<Request>) requestQuery.list();

			transaction.commit();
			return requests;

		} finally {
			CloseConnectionUtil.closeSession(session);
			// logger.info("Closed session connection");
		}
	}

	@Override
	public boolean updateRequestStatus(String choice, int requestId) throws OutpassException {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("update Request as p set p.status = :value where p.id = :id");
			query.setInteger("id", requestId);
			if (choice.equals("approve")) {
				query.setInteger("value", 2);
			} else {
				query.setInteger("value", 3);
			}
			query.executeUpdate();
			System.out.println("HI" + session);
			Query getUser = session.createQuery("SELECT user from Request WHERE id = :id");
			getUser.setInteger("id", requestId);
			@SuppressWarnings("unchecked")
			ArrayList<User> users = (ArrayList<User>) getUser.list();
			User user = users.get(0);
			sendMail(user, choice);
			transaction.commit();
			return true;
		} finally {
			CloseConnectionUtil.closeSession(session);
		}
	}

	/**
	 * method to send mail to campus mind when request is approved rejected
	 * 
	 * @param requestObject
	 * @throws OutpassException
	 */
	private void sendMail(User user, String choice) throws OutpassException {
		String content = "Hi," + getName(user.getUserId()) + "\n Your request is " + choice + "ed";
		String sendTo = user.getEmail();
		mail.sendMail("asyadav1593@gmail.com", sendTo, "Request " + choice, content);

	}

	@Override
	public boolean acceptAllRequests() throws OutpassException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Query getUser = session.createQuery("SELECT user from Request WHERE status = :status");
			getUser.setInteger("status", 2);
			@SuppressWarnings("unchecked")
			ArrayList<User> users = (ArrayList<User>) getUser.list();

			String query = "update Request as p set p.status = :value where p.status = :status";
			session.createQuery(query).setInteger("status", 1).setInteger("value", 2).executeUpdate();
			transaction.commit();
			sendMailToAll(users, "approved");
			return true;
		} catch (Exception e) {
			throw new OutpassException("Could not accept all requests!", e);
		} finally {
			CloseConnectionUtil.closeSession(session);
		}
	}

	@Override
	public boolean rejectAllRequests() throws OutpassException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Query getUser = session.createQuery("SELECT user from Request WHERE status = :status");
			getUser.setInteger("status", 2);
			@SuppressWarnings("unchecked")
			ArrayList<User> users = (ArrayList<User>) getUser.list();
			String query = "update Request as p set p.status = :value where p.status = :status";
			session.createQuery(query).setInteger("status", 1).setInteger("value", 3).executeUpdate();
			transaction.commit();
			sendMailToAll(users, "rejected");
			return true;
		} catch (Exception e) {
			throw new OutpassException("Could not reject all requests!", e);
		} finally {
			CloseConnectionUtil.closeSession(session);
		}
	}

	private void sendMailToAll(ArrayList<User> users, String choice) throws OutpassException {
		String content = null;
		String sendTo = null;
		for (User user : users) {

			content = "Hi, " + getName(user.getUserId()) + "\n Your request is " + choice;
			sendTo = user.getEmail();
			mail.sendMail("asyadav1593@gmail.com", sendTo, "Request " + choice, content);
		}
	}

	@Override
	public boolean addUser(User user) throws OutpassException {
		Session session = null;
		Transaction transaction;
		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.persist(user);
			transaction.commit();
			// session.close();
			return true;
		} catch (Exception e) {

		} finally {
			CloseConnectionUtil.closeSession(session);

		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<User> adminGenerateHeadcount() throws OutpassException {
		Session session = null;
		ArrayList<User> headCountList = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Query getUser = session.createQuery("from User WHERE type = :type");
			getUser.setParameter("type", UserType.CAMPUSMIND);
			headCountList = (ArrayList<User>) getUser.list();
			transaction.commit();
			System.out.println("*******************************************************");
			System.out.println(headCountList);
			return headCountList;
		} catch (Exception e) {
			// e.printStackTrace();
			throw new OutpassException("Could not generate Headcount!!", e);
		} finally {
			CloseConnectionUtil.closeSession(session);
		}
	}

	/*****************************************
	 * *********SECURITY MODULE**************
	 ************************************/

	@SuppressWarnings("unchecked")
	@Override
	public List<Request> getRequestsForSecurity() throws OutpassException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			@SuppressWarnings("rawtypes")
			List request = session.createQuery("FROM Request r where r.status=2 or r.status=5 ORDER BY r.user").list();
			System.out.println(request);
			transaction.commit();
			// session.close();
			return request;
		} catch (Exception e) {
			throw new OutpassException("Could not fetch requests!!", e);
		} finally {
			CloseConnectionUtil.closeSession(session);

		}
	}

	@Override
	public boolean securityUpdateStatus(int id, int value, String userID) throws OutpassException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			// System.out.println("in
			// dao************************************************************");
			String query = "update Request as p set p.status = :value where p.id = :id";
			session.createQuery(query).setInteger("id", id).setInteger("value", value).executeUpdate();
			// System.out.println("Updated requests");
			transaction.commit();
			boolean done = false;
			done = securityUpdateUsers(value, userID);
			if (done)
				return true;
			else
				return false;
		} catch (Exception e) {
			throw new OutpassException("Could not update requests!!", e);
		} finally {
			CloseConnectionUtil.closeSession(session);
		}
	}

	@Override
	public boolean securityUpdateUsers(int value, String userId) throws OutpassException {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String query2 = "update User as u set u.userStatus = :value where u.userId=:userId";
			System.out.println("in dao*****************************************************************");
			session.createQuery(query2).setString("userId", userId).setInteger("value", value).executeUpdate();
			System.out.println("updated users");
			transaction.commit();
			return true;
		} catch (Exception e) {
			// System.out.println("Could not update user!!");
			e.printStackTrace();
			throw new OutpassException("Could not update user!!", e);
		} finally {
			CloseConnectionUtil.closeSession(session);
		}
	}

	@Override
	public boolean changePassword(String password, String currentPassword, String userId) throws OutpassException {
		System.out.println("PASSOWRD" + password);
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			User user = (User) session.get(User.class, userId);
			if (user.getPassword().equals(currentPassword)) {

				String query = "update User as u set u.password = :password where u.userId = :id";
				session.createQuery(query).setString("id", userId).setString("password", password).executeUpdate();
				transaction.commit();
				// session.close();
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new OutpassException("Could not update requests!!", e);
		} finally {
			CloseConnectionUtil.closeSession(session);

		}
	}

}