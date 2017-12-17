package org.torpay.common.util;

public class ErrorCodes {

	final public static ErrorCode SUCCESS = new ErrorCode(0,"success");
	final public static ErrorCode TECHNICAL_ERROR= new ErrorCode(9999,"Internal Error");
	
	final public static ErrorCode ENTRANCE_CONVERTOR_HTTP_REQUEST_MISSED_CONTENT_TYPE= new ErrorCode(101,"content type of http request is missed");
	final public static ErrorCode ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_CONTENT_TYPE= new ErrorCode(102,"content type of http request is invalid");
	final public static ErrorCode ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_SERVICE_NAME_IN_URL= new ErrorCode(103,"service name in url is wrong");
	final public static ErrorCode ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_URL_FORMAT= new ErrorCode(104,"url has wrong format");
	final public static ErrorCode ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_JSON_CONTENT= new ErrorCode(105,"json content is not valid");
	final public static ErrorCode ENTRANCE_CONVERTOR_HTTP_REQUEST_NULL_CONTENT= new ErrorCode(106,"content body of a request is empty");
	final public static ErrorCode ENTRANCE_CONVERTOR_HTTP_REQUEST_NULL_METHOD= new ErrorCode(107,"method a request is empty");
	
	final public static ErrorCode SERVICE_CONTENT_INVALID_JSON_CONTENT= new ErrorCode(211,"json content is not valid");
	
	final public static ErrorCode SERVICE_VALIDATION_REQUIRED_PARAMETER_MISSED= new ErrorCode(311,"a required parameter is missed");
	final public static ErrorCode SERVICE_VALIDATION_REQUIRED_PARAMETER_VALUE_MISSED= new ErrorCode(312,"a required parameter has empty value");
	final public static ErrorCode SERVICE_VALIDATION_TYPE_NUMBER_EXPECTED= new ErrorCode(313,"wrong parameter type. expected type is number");
	final public static ErrorCode SERVICE_VALIDATION_TYPE_FLOAT_EXPECTED= new ErrorCode(314,"wrong parameter type. expected type is float");
	final public static ErrorCode SERVICE_VALIDATION_LENGTH= new ErrorCode(315,"length of parameter is invalid");
	final public static ErrorCode SERVICE_VALIDATION_LENGTH_MAX= new ErrorCode(316,"length of parameter is invalid");
	final public static ErrorCode SERVICE_VALIDATION_LENGTH_MIN= new ErrorCode(317,"length of parameter is invalid");


}
