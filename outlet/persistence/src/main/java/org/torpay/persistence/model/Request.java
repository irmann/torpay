package org.torpay.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "request")
public class Request extends BaseEntity {

	@Column(name = "action")
	private String action;

	@Column(name = "provider")
	private String provider;

	@Column(name = "merchant")
	private String merchant;

	@Column(name = "parameters")
	private String parameters;

	@Column(name = "request_content")
	private String request_content;

	@Column(name = "request_string")
	private String requestString;

	@Column(name = "request_parameters")
	private String requestParameters;

	@Column(name = "password")
	private String password;

	@Column(name = "token")
	private String token;

	@Column(name = "security_code")
	private String securityCode;

	@Column(name = "payment_number")
	private String paymentNumber;

	@Column(name = "client_request_id")
	private String clientRequestId;

	@Column(name = "external_reference_1")
	private String externalReference1;

	@Column(name = "external_reference_2")
	private String externalReference2;

	@Column(name = "external_reference_3")
	private String externalReference3;

	@Column(name = "trace_number")
	private String traceNumber;

	@Column(name = "transaction_code")
	private String transactionCode;

	@Column(name = "log")
	private String log;
	
	@Column(name = "type")
	private String type;

	@Column(name = "creator")
	private String creator;

	@Column(name = "creation_date")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mi:ss")
	private DateTime creationDate;

	@Column(name = "response_error_code")
	private String responseErrorCode;

	@Column(name = "response_status")
	private String responseStatus;

	@Column(name = "response_message")
	private String responseMessage;

	@Column(name = "response_entrance_reference")
	private String responseEntranceReference;

	@Column(name = "response_technical_error_message")
	private String responseTechnicalErrorMessage;

	@Column(name = "response_stack_trace")
	private String responseStackTrace;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getRequestContent() {
		return request_content;
	}

	public void setRequestContent(String request_content) {
		this.request_content = request_content;
	}

	public String getRequestString() {
		return requestString;
	}

	public void setRequestString(String request_string) {
		this.requestString = request_string;
	}

	public String getRequestParameters() {
		return requestParameters;
	}

	public void setRequestParameters(String request_parameters) {
		this.requestParameters = request_parameters;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String security_code) {
		this.securityCode = security_code;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String payment_number) {
		this.paymentNumber = payment_number;
	}

	
	public String getExternalReference1() {
		return externalReference1;
	}

	public void setExternalReference1(String externalReference1) {
		this.externalReference1 = externalReference1;
	}

	public String getExternalReference2() {
		return externalReference2;
	}

	public void setExternalReference2(String externalReference2) {
		this.externalReference2 = externalReference2;
	}

	public String getExternalReference3() {
		return externalReference3;
	}

	public void setExternalReference3(String externalReference3) {
		this.externalReference3 = externalReference3;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getResponseErrorCode() {
		return responseErrorCode;
	}

	public void setResponseErrorCode(String responseErrorCode) {
		this.responseErrorCode = responseErrorCode;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseEntranceReference() {
		return responseEntranceReference;
	}

	public void setResponseEntranceReference(String responseEntranceReference) {
		this.responseEntranceReference = responseEntranceReference;
	}

	public String getResponseTechnicalErrorMessage() {
		return responseTechnicalErrorMessage;
	}

	public void setResponseTechnicalErrorMessage(
			String responseTechnicalErrorMessage) {
		this.responseTechnicalErrorMessage = responseTechnicalErrorMessage;
	}

	public String getResponseStackTrace() {
		return responseStackTrace;
	}

	public void setResponseStackTrace(String responseStackTrace) {
		this.responseStackTrace = responseStackTrace;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Database Request Object:" + "id=" + id + ", action=" + action
				+ ", provider=" + provider + ", merchant=" + merchant
				+ ", parameters=" + parameters + ", request_content="
				+ request_content + ", request_string=" + requestString
				+ ", request_parameters=" + requestParameters + ", password="
				+ password + ", token=" + token + ", security_code="
				+ securityCode + ", payment_number=" + paymentNumber
				+ ", client_request_id=" + getClientRequestId() + ",  external_reference_1="
				+ externalReference1 + ", external_reference_2="
				+ externalReference2 + ",  external_reference_3="
				+ externalReference3 + ", log=" + log + ", creator=" + creator
				+ ", creation_date="
				+ (creationDate != null ? creationDate.toString() : "")
				+ ", response_error_code=" + responseErrorCode
				+ ", response_status=" + responseStatus + ", response_message="
				+ responseMessage + ",  response_entrance_reference="
				+ responseEntranceReference
				+ ",  response_technical_error_message="
				+ responseTechnicalErrorMessage + ", response_stack_trace="
				+ responseStackTrace + ", transaction_coed=" + transactionCode
				+ ", trace_number=" + traceNumber
		        + ", type=" + type;
	}

	public String getTraceNumber() {
		return traceNumber;
	}

	public void setTraceNumber(String traceNumber) {
		this.traceNumber = traceNumber;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClientRequestId() {
		return clientRequestId;
	}

	public void setClientRequestId(String clientRequestId) {
		this.clientRequestId = clientRequestId;
	}
}