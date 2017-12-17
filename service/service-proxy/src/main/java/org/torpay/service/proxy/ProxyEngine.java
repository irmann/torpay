package org.torpay.service.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.torpay.engine.content.EngineContent;
import org.torpay.engine.core.EngineCore;
import org.torpay.engine.metadata.EngineMetaData;
import org.torpay.engine.validation.EngineValidation;

public class ProxyEngine {
	private static EngineContent engineContent;
	private static EngineValidation engineValidation;
	private static EngineMetaData engineMetaData;
	private static EngineCore engineCore;

	static final Logger LOG = LoggerFactory.getLogger(ProxyEngine.class);
	static {
		if (getEngineContent() == null) {
			LOG.info("loading appContext from service-proxy.xml ... ");
			ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "spring/service-proxy.xml" });
			BeanFactory factory = (BeanFactory) appContext;
			engineContent = (EngineContent) factory.getBean("engineContent");
			engineValidation= (EngineValidation) factory
					.getBean("engineValidation");
			engineMetaData = (EngineMetaData) factory.getBean("engineMetaData");
			engineCore = (EngineCore) factory.getBean("engineCore");

		}
	}

	public static EngineContent getEngineContent() {
		return engineContent;
	}

	public static EngineValidation getEngineValidation() {
		return engineValidation;
	}

	public static EngineMetaData getEngineMetaData() {
		return engineMetaData;
	}
	
	public static EngineCore getEngineCore() {
		return engineCore;
	}
}
