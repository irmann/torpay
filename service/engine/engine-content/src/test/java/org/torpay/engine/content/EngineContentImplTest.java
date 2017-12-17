package org.torpay.engine.content;

import org.junit.Test;
import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.test.JSONSample;

public class EngineContentImplTest {

	@Test
	public void extractContentTest() throws GlobalExcetion {
		EngineContent engineContent = new EngineContentImpl();
		JSONAssert.doAssert(
				engineContent.extractContent(JSONSample.content, "json"),
				engineContent);
	}

	@Test
	public void extractWrongContentTest() throws GlobalExcetion {
		EngineContent engineContent = new EngineContentImpl();
		JSONAssert.doAssert2(
				engineContent.extractContent(JSONSample.content2, "json"),
				engineContent);
	}
}
