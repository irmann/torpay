package org.torpay.util.spring;

import java.io.File;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.torpay.common.util.Constants;
import org.torpay.common.util.FlushUtil;
import org.torpay.common.util.TorPayFileSystemUtil;

public class RemoteBeanFactoryUtil {
	static final Logger LOG = LoggerFactory
			.getLogger(RemoteBeanFactoryUtil.class);
	private static FileSystemXmlApplicationContext appContext;
	private static HashMap<String, String> remoteConfig;

	static {

	}

	private static void loadRemoteContext(String instance) {
		try {
			flush(instance);
			String file = TorPayFileSystemUtil
					.getFielPathFormTorPayUserHomeDirectory(instance,
							Constants.TORPAY_SPRING_REMOTE_APPLICATION_CONTEXT);
			LOG.info("loading TORPAY_SPRING_REMOTE_APPLICATION_CONTEXT from  "
					+ file);
			appContext = new FileSystemXmlApplicationContext(
					new String[] { "file:" + file });
		} catch (Exception e) {
			LOG.error(
					"error during loading remote torpay application context of Spring."+e.getMessage());
		}
	}

	private static BeanFactory getRemoteBeanFactory() {
		if (appContext != null)
			return (BeanFactory) appContext;
		else
			return null;
	}

	public static BeanFactory getPppropriateBeanFactory(String instance,
			BeanFactory defaultBeanfactory, String source, String target) {
		if (appContext == null)
			loadRemoteContext(instance);
		if (remoteConfig == null)
			loadRemoteConfiguration(instance);

		BeanFactory beanFactory = defaultBeanfactory;
		if (isRemote(source, target)
				&& RemoteBeanFactoryUtil.getRemoteBeanFactory() != null)
			beanFactory = RemoteBeanFactoryUtil.getRemoteBeanFactory();
		
		return beanFactory;
	}

	public static Object getPppropriateBeanFactory(String instance,
			BeanFactory defaultBeanfactory, String source, String target,
			String beanName) throws Exception {
		try {
			return getPppropriateBeanFactory(instance, defaultBeanfactory,
					source, target).getBean(beanName);
		} catch (Exception e) {
			String file = TorPayFileSystemUtil
					.getFielPathFormTorPayUserHomeDirectory(instance,
							Constants.TORPAY_SPRING_REMOTE_APPLICATION_CONTEXT);
			LOG.error("error during finding bean " + beanName + " in file "
					+ file + ". check if the bean is defined in the file. "+e.getMessage());
			throw e;
		}
	}

	private static boolean isRemote(String source, String target) {
		String key = createKey(source, target);
		if (remoteConfig != null && remoteConfig.get(key) != null)
			return true;
		return false;
	}

	public static String createKey(String source, String target) {
		StringBuffer sb = new StringBuffer();
		sb.append(source);
		sb.append("-");
		sb.append("to");
		sb.append("-");
		sb.append(target);
		return sb.toString();
	}

	synchronized public static void flush(String instance) {
		if (FlushUtil.UTIL_SPRING_FLUSH) {
			loadRemoteContext(instance);
			loadRemoteConfiguration(instance);
			FlushUtil.UTIL_SPRING_FLUSH = false;
		}

	}

	private static void loadRemoteConfiguration(String instance) {
		try {
			String fileName = TorPayFileSystemUtil
					.getFielPathFormTorPayUserHomeDirectory(instance,
							Constants.TORPAY_SPRING_REMOTE_CONFIGURATION);
			File file = new File(fileName);
			if (!file.exists()) {
				LOG.warn(fileName + " is not exsit");
				return;
			}

			LOG.info("reading TORPAY_SPRING_REMOTE_CONFIGURATION from  "
					+ fileName);
			String content = FileUtils.readFileToString(file);
			StringTokenizer st = new StringTokenizer(content, ",");
			remoteConfig = new HashMap<String, String>();
			while (st.hasMoreTokens())
				remoteConfig.put(st.nextToken(), "true");
		} catch (Exception e) {
			LOG.error("error during reading remote torpay configuration file."+e.getMessage());
		}

	}
}
