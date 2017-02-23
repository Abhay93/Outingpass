package com.mindtree.entity;

/**
 * Request status enumeration
 * @author M1035881
 *
 */
public enum RequestStatus {
	WRONG, PENDING, APPROVED, REJECTED, CHECKEDIN, CHECKEDOUT;


	@Override
	public String toString() {
		return super.toString();
	}	
}
