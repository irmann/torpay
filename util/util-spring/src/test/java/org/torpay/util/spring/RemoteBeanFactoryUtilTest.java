package org.torpay.util.spring;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.torpay.common.util.Constants;
import org.torpay.common.util.TorPayFileSystemUtil;

public class RemoteBeanFactoryUtilTest {

	private static final String CONTEXT_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <!-- Workfolow engine and activities --> <beans xmlns=\"http://www.springframework.org/schema/beans\" 	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:p=\"http://www.springframework.org/schema/p\" 	xmlns:context=\"http://www.springframework.org/schema/context\" xmlns:tx=\"http://www.springframework.org/schema/tx\" 	xmlns:jpa=\"http://www.springframework.org/schema/data/jpa\" 	xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd 		http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa.xsd 		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd 		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd\"> <bean id=\"remoteBeanFactoryTestBean\" class=\"org.torpay.util.spring.RemoteBeanFactoryTestBeanImpl2\" /> </beans>";

	private static final String REMOTE_BEANFACTORY_TEST_BEAN = "remote-beanfactory-test-bean";

	private static final String REMOTE_BEAN_FACTORY_TEST_BEAN = "remoteBeanFactoryTestBean";
	private static final String REMOTE_BEAN_FACTORY_TEST_BEAN_2 = "remoteBeanFactoryTestBean2";

	private static final String UTIL_SPRING = "util-spring";

	private String INSTANCE = "test-remote";

	private static ClassPathXmlApplicationContext appContext;

	static {
		if (appContext == null) {
			appContext = new ClassPathXmlApplicationContext(
					new String[] { "spring/util-spring-test.xml" });
		}
	}

	@Before
	public void setup() throws Exception {
		// create parent
		File homeTorPay = new File(Constants.TORPAY_USER_HOME_DIRECTORY);
		if (!homeTorPay.exists()) {
			homeTorPay.mkdir();
		}
		// delete instance
		File instance = new File(
				TorPayFileSystemUtil
						.getInstancePathFormTorPayUserHomeDirectory(INSTANCE));
		FileUtils.forceDeleteOnExit(instance);
		// create instance
		instance.mkdir();

	}

	public void reset() throws Exception {
		setup();
	}

	@Test
	public void test1() throws Exception {
		reset();
		// there are not context file and config file
		RemoteBeanFactoryTestBean remoteBeanFactoryTestBean = (RemoteBeanFactoryTestBean) RemoteBeanFactoryUtil
				.getPppropriateBeanFactory(INSTANCE, (BeanFactory) appContext,
						UTIL_SPRING, REMOTE_BEANFACTORY_TEST_BEAN,
						REMOTE_BEAN_FACTORY_TEST_BEAN); 
		Assert.assertTrue(remoteBeanFactoryTestBean.test());

	}

	@Test
	public void test2() throws Exception {
		reset();
		writeContextFile();
		// there is not config file but there is context file
		RemoteBeanFactoryTestBean remoteBeanFactoryTestBean = (RemoteBeanFactoryTestBean) RemoteBeanFactoryUtil
				.getPppropriateBeanFactory(INSTANCE, (BeanFactory) appContext,
						UTIL_SPRING, REMOTE_BEANFACTORY_TEST_BEAN,
						REMOTE_BEAN_FACTORY_TEST_BEAN);
		Assert.assertTrue(remoteBeanFactoryTestBean.test());

	}

	@Test
	public void test3() throws Exception {
		reset();
		writeConfigFile();
		// there is not context file but there is config file
		RemoteBeanFactoryTestBean remoteBeanFactoryTestBean = (RemoteBeanFactoryTestBean) RemoteBeanFactoryUtil
				.getPppropriateBeanFactory(INSTANCE, (BeanFactory) appContext,
						UTIL_SPRING, REMOTE_BEANFACTORY_TEST_BEAN,
						REMOTE_BEAN_FACTORY_TEST_BEAN);
		Assert.assertTrue(remoteBeanFactoryTestBean.test());

	}

	@Test
	public void test4() throws Exception {
		reset();
		writeConfigFile();
		writeContextFile();
		// there are context file and config file
		RemoteBeanFactoryTestBean remoteBeanFactoryTestBean = (RemoteBeanFactoryTestBean) RemoteBeanFactoryUtil
				.getPppropriateBeanFactory(INSTANCE, (BeanFactory) appContext,
						UTIL_SPRING, REMOTE_BEANFACTORY_TEST_BEAN,
						REMOTE_BEAN_FACTORY_TEST_BEAN);
		Assert.assertFalse(remoteBeanFactoryTestBean.test());

	}

	@Test
	public void test5() throws Exception {
		try {
			reset();
			writeConfigFile();
			writeContextFile();
			// there is not a bean in context file
			RemoteBeanFactoryTestBean remoteBeanFactoryTestBean = (RemoteBeanFactoryTestBean) RemoteBeanFactoryUtil
					.getPppropriateBeanFactory(INSTANCE,
							(BeanFactory) appContext, UTIL_SPRING,
							REMOTE_BEANFACTORY_TEST_BEAN,
							REMOTE_BEAN_FACTORY_TEST_BEAN_2);
			Assert.fail();
		} catch (Exception e) {

		}

	}

	private void writeConfigFile() throws IOException {
		File configFile = new File(
				TorPayFileSystemUtil.getFielPathFormTorPayUserHomeDirectory(
						INSTANCE, Constants.TORPAY_SPRING_REMOTE_CONFIGURATION));
		FileUtils.write(
				configFile,
				RemoteBeanFactoryUtil.createKey(UTIL_SPRING,
						REMOTE_BEANFACTORY_TEST_BEAN)
						+ ","
						+ RemoteBeanFactoryUtil.createKey("x", "y"));

	}

	private void writeContextFile() throws IOException {
		File contextFile = new File(
				TorPayFileSystemUtil.getFielPathFormTorPayUserHomeDirectory(
						INSTANCE,
						Constants.TORPAY_SPRING_REMOTE_APPLICATION_CONTEXT));
		FileUtils.write(contextFile, CONTEXT_STRING);
	}
}
