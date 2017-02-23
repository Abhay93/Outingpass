package com.mindtree.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	//
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "id")
	// private int id;

	@Id
	@Column(name = "user_id", length = 10)
	private String userId;

	@Column(name = "name", length = 100)
	private String name;

	@Enumerated(EnumType.ORDINAL)
	private UserType type;

	@Column(name = "password", length = 100)
	private String password;

	@Column(name = "email", length = 100)
	private String email;
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "user_status")
	private RequestStatus userStatus;// RULE: The enum status is restricted to

	// CHECKEDIN, CHECKEDOUT
	// this has to be taken care by code
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<Request> requests = new HashSet<Request>();

	// public int getId() {
	// return id;
	// }
	//
	// public void setId(int id) {
	// this.id = id;
	// }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Request> getRequests() {
		return requests;
	}

	public void setRequests(Set<Request> requests) {
		this.requests = requests;
	}

	public RequestStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(RequestStatus userStatus) {
		this.userStatus = userStatus;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", type=" + type + ", password=" + password + ", email="
				+ email + ", userStatus=" + userStatus + "]";
	}

}
