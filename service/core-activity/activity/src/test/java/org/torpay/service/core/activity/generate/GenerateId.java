package org.torpay.service.core.activity.generate;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

public class GenerateId {
	
	@Test
	public void tesGenerateId(){
//		for(;;)
			System.out.println(RandomStringUtils.randomAlphanumeric(5));
		
	}

}
