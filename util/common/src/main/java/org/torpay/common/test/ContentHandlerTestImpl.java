package org.torpay.common.test;

import java.util.List;

import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.interfaces.ContentHandler;
import org.torpay.common.util.Parameter;

public class ContentHandlerTestImpl implements ContentHandler {

	public List<Parameter> extractContent(String content, String type)
			throws GlobalExcetion {
		return null;
	}

	public Parameter findParameter(List<Parameter> list, String name) {
		return null;
	}

	public List<Parameter> findParameters(List<Parameter> list, String name) {
		return null;
	}

	public Parameter findParameter(List<Parameter> list, int count) {
		return null;
	}

}
