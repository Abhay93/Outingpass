package com.mindtree.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mindtree.exceptions.OutpassException;

/**
 * @author M1035881 This class is to make a connection to database
 *
 */
public class ConnectionUtil {

	/**
	 * This method used to make Database connection
	 * 
	 * @return
	 * @throws OutpassException
	 */
	public static Connection makeConnection() throws OutpassException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/outpass_db", "root", "Welcome123");

			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OutpassException("Can't connect with database");
		} catch (ClassNotFoundException e) {
			throw new OutpassException("Can't connect with database");
		}
	}

	public static void closeConnection(Connection conn) throws SQLException, OutpassException {
		if (conn != null) {
			conn.close();

		} else {
			throw new OutpassException("Can't connect with database");
		}

	}
}
