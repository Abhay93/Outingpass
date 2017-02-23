package com.mindtree.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author M1035881 Request POJO class
 */
@Entity
@Table(name = "requests")

public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	// @OneToOne
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Enumerated(EnumType.ORDINAL)
	private RequestType type;

	@Column(name = "request_from_date", length = 100)
	private String fromDate;

	@Column(name = "request_from_time", length = 100)
	private String fromTime;

	@Column(name = "request_to_date", length = 100)
	private String toDate;

	@Column(name = "request_to_time", length = 100)
	private String toTime;

	@Column(name = "comment", length = 100)
	private String comment;

	@Enumerated(EnumType.ORDINAL)
	private RequestStatus status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", type=" + type + ", fromDate=" + fromDate + ", fromTime=" + fromTime
				+ ", toDate=" + toDate + ", toTime=" + toTime + ", comment=" + comment + ", status=" + status + "]";
	}

}
