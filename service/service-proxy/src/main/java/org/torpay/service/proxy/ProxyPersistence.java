package org.torpay.service.proxy;

import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.torpay.common.exception.ServiceException;
import org.torpay.persistence.model.RequestLog;
import org.torpay.persistence.service.PersistenceCoreActivityService;
import org.torpay.persistence.service.PersistenceEntranceService;
import org.torpay.persistence.service.PersistenceException;

public class ProxyPersistence {

	@Autowired
	private static PersistenceCoreActivityService persistenceCoreActivityService;

	@Autowired
	private static PersistenceEntranceService persistenceEntranceService;

	static final Logger LOG = LoggerFactory.getLogger(ProxyPersistence.class);
	static {
		if (getPersistenceEntranceService() == null) {
			LOG.info("loading appContext from service-proxy.xml ... ");
			ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "spring/service-proxy.xml" });
			BeanFactory factory = (BeanFactory) appContext;
			persistenceEntranceService = (PersistenceEntranceService) factory
					.getBean("persistenceEntranceService");
			persistenceCoreActivityService = (PersistenceCoreActivityService) factory
					.getBean("persistenceCoreActivityService");
			
		}
	}

	static public void storeRequestLog(String request, String response,
			Long reference) throws ServiceException, PersistenceException {
		RequestLog requestLog = new RequestLog();
		requestLog.setReference(reference);
		requestLog.setRequest(request);
		requestLog.setResponse(response);
		requestLog.setCreationDate(new DateTime(new Date()));
		getPersistenceEntranceService().saveRequestLog(requestLog);
	}

	public static PersistenceEntranceService getPersistenceEntranceService() {
		return persistenceEntranceService;
	}

	public static PersistenceCoreActivityService getPersistenceCoreActivityService() {
		return persistenceCoreActivityService;
	}

}
