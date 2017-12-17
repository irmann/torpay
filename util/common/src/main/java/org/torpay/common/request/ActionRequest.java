package org.torpay.common.request;

import java.util.HashMap;
import java.util.List;

import org.torpay.common.util.Parameter;

public class ActionRequest {

	private String action;
	private String flowName;
	private String provider;
	private String merchant;
	private HashMap<String, String> parameters;
	private String request_content;
	private String request_string;
	private String request_parameters;
	private String request_type;
	private String password;
	private String token;
	private String security_code;
	private String payment_number;
	private String request_id;
	private List<Parameter> values;
	private String externalReference1;
	private String externalReference2;
	private String externalReference3;
	private StringBuffer log = new StringBuffer();
	private ActionResponse actionResponse;
	private Long request_db_id;
	private Boolean isMapped;
	private String mappedAction;
	private String mappedProvider;
	private List<Parameter> mappedValues;
	//TODO save in db instance channel 
	private String instance;
	private String channel;

	private String traceNumber;
	private String transactionCode;

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

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getRequest_content() {
		return request_content;
	}

	public void setRequest_content(String request_content) {
		this.request_content = request_content;
	}

	public String getRequest_string() {
		return request_string;
	}

	public void setRequest_string(String request_string) {
		this.request_string = request_string;
	}

	public String getRequest_parameters() {
		return request_parameters;
	}

	public void setRequest_parameters(String request_parameters) {
		this.request_parameters = request_parameters;
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

	public String getSecurity_code() {
		return security_code;
	}

	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	public String getPayment_number() {
		return payment_number;
	}

	public void setPayment_number(String payment_number) {
		this.payment_number = payment_number;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	@Override
	public String toString() {
		return "ActionRequest:action=" + action + ",provider=" + provider
				+ ",merchant=" + merchant + ",request_content="
				+ request_content + ",request_string=" + request_string
				+ ",request_parameters=" + request_parameters + ",token="
				+ token + ",security_code=" + security_code
				+ ",payment_number=" + request_id + ",parameters="
				+ toStringParameters() + ",values=" + toStringValues()
				+ ", transactionCoed=" + transactionCode + ", traceNumber="
				+ traceNumber + ", type=" + request_type;
	}

	public String toStringParameters() {
		StringBuffer parametersStr = new StringBuffer();
		if (parameters != null) {
			for (String name : this.parameters.keySet()) {
				parametersStr.append("<");
				parametersStr.append(name);
				parametersStr.append(",");
				parametersStr.append(parameters.get(name));
				parametersStr.append(">");
			}
		}
		return parametersStr.toString();
	}

	public String toStringValues() {
		StringBuffer parametersStr = new StringBuffer();
		if (values != null) {
			for (Parameter parameter : this.values) {
				parametersStr.append("<");
				parametersStr.append(parameter.getName());
				parametersStr.append(",");
				parametersStr.append(parameter.getValue());
				parametersStr.append(">");
			}
		}
		return parametersStr.toString();
	}

	public List<Parameter> getValues() {
		return values;
	}

	public void setValues(List<Parameter> values) {
		this.values = values;
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

	public void appendLog(String msgLog) {
		log.append("<");
		log.append(msgLog);
		log.append(">");

	}

	public String getLog() {
		return log.toString();
	}

	public ActionResponse getActionResponse() {
		return actionResponse;
	}

	public void setActionResponse(ActionResponse actionResponse) {
		this.actionResponse = actionResponse;
	}

	public Long getRequest_db_id() {
		return request_db_id;
	}

	public void setRequest_db_id(Long request_db_id) {
		this.request_db_id = request_db_id;
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

	public String getRequest_type() {
		return request_type;
	}

	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public Boolean getIsMapped() {
		return isMapped;
	}

	public void setIsMapped(Boolean isMapped) {
		this.isMapped = isMapped;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getMappedAction() {
		return mappedAction;
	}

	public void setMappedAction(String mappedAction) {
		this.mappedAction = mappedAction;
	}

	public String getMappedProvider() {
		return mappedProvider;
	}

	public void setMappedProvider(String mappedProvider) {
		this.mappedProvider = mappedProvider;
	}

	public List<Parameter> getMappedValues() {
		return mappedValues;
	}

	public void setMappedValues(List<Parameter> mappedValues) {
		this.mappedValues = mappedValues;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
