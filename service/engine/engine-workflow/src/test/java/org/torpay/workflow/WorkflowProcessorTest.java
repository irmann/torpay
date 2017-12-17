package org.torpay.workflow;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.torpay.engine.workflow.SeedData;
import org.torpay.engine.workflow.WorkflowProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/engine.xml")
public class WorkflowProcessorTest {

	@Autowired
	WorkflowProcessor workflowProcessor;
	
	@Before
	public void setUp() {

	}
	
	@Test
	public void testDoActivities() {
		workflowProcessor.doActivities(createSeedData());
	}

	private SeedData createSeedData() {
		return null;
	}
}
