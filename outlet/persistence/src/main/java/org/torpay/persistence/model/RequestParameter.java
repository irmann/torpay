package org.torpay.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "request_parameter")
public class RequestParameter extends BaseEntity {

	@Column(name = "request_id")
	private Long requestId;

	@Column(name = "name")
	private String name;

	@Column(name = "value")
	private String value;

	@Column(name = "parent")
	private String parent;

	@Column(name = "type")
	private String type;

	@Column(name = "creation_date")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mi:ss")
	private DateTime creationDate;

	@Column(name = "modified_date")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mi:ss")
	private DateTime modifiedDate;

	@Column(name = "creator")
	private String creator;

	@Column(name = "modified_by")
	private String modifiedBy;

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	public DateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(DateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {

		return "RequestParameter: id=" + id + ", name=" + name + ", type="
				+ type + ", value=" + value + " ,parent=" + parent
				+ " ,creator=" + creator + ", modifiedBy=" + modifiedBy
				+ " ,modifiedDate=" + modifiedDate + ", creationDate="
				+ creationDate;
	}

}