package org.torpay.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "request_log")
public class RequestLog extends BaseEntity {

	@Column(name = "request")
	private String request;

	@Column(name = "response")
	private String response;

	@Column(name = "creation_date")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mi:ss")
	private DateTime creationDate;

	@Column(name = "reference")
	private Long reference;
	
	@Column(name = "creator")
	private String creator;

	
	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

}