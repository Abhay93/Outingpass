package com.mindtree.dao.util;

import org.hibernate.Session;

import com.mindtree.exceptions.OutpassException;

public class CloseConnectionUtil
{
	public static void closeSession(Session session) throws OutpassException
	{
		try
		{
			if(session!=null)
			{
				session.close();
			}
		}
		catch(Exception e)
		{
			throw(new OutpassException("Could not close connection preperly!!",e));
		}
	}
}
