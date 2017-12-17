package org.torpay.common.interfaces;

import java.util.List;

import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.util.Parameter;

public interface ContentHandler {

	public List<Parameter> extractContent(String content, String type) throws GlobalExcetion;

	public Parameter findParameter(List<Parameter> list, String name);

	public List<Parameter> findParameters(List<Parameter> list, String name);

	public Parameter findParameter(List<Parameter> list, int count);

	
}