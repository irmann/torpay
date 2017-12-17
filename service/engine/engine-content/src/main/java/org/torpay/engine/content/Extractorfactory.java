package org.torpay.engine.content;

import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.util.ErrorCodes;

public class Extractorfactory {

	public static Extractor getExtractor(String type) throws GlobalExcetion{
		if (type == null)
			throw new GlobalExcetion(ErrorCodes.TECHNICAL_ERROR,
					"type of content is null", "ServiceContent");
		if (type.toLowerCase().contains("json"))
			return new JSONExtractor();
		throw new GlobalExcetion(ErrorCodes.TECHNICAL_ERROR,
				"type of content is not supported<" + type + ">",
				"ServiceContent");
	}

}
