package org.torpay.engine.content;

import java.util.List;

import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.util.Parameter;

public interface Extractor {
	public List<Parameter> extractContent(String content)
			throws GlobalExcetion;
}
