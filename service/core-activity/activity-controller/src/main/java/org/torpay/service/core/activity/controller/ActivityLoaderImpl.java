package org.torpay.service.core.activity.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;
import org.torpay.common.util.ErrorCodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityLoaderImpl implements ActivityLoader {

	private List<String> path;

	private static List<CoreActivity> activities;

	static final Logger LOG = LoggerFactory.getLogger(ActivityLoaderImpl.class);

	public List<CoreActivity> loadActivities() throws ActivityControllException {
		if (activities != null)
			return activities;
		activities = new ArrayList<CoreActivity>();
		if (path == null)
			throw new ActivityControllException(
					ErrorCodes.TECHNICAL_ERROR,
					"internal error during loading activities: path of activities is null",
					"ActivityLoader");
		else if (path.size() == 0)
			LOG.warn("path is empty");
		;

		List<Class> classes = new ArrayList<Class>();
		try {
			for (String p : path) {
				LOG.debug(" loading from path " + p);
				classes = findMyTypes(p);
				for (Class c : classes) {
					Object object = c.getConstructor().newInstance();
					if (object instanceof CoreActivity) {
						LOG.debug("object activity " + c.getName()
								+ " is created");
						activities.add((CoreActivity) object);
					}
				}
			}
		} catch (Exception e) {
			throw new ActivityControllException(ErrorCodes.TECHNICAL_ERROR,
					"internal error during loading activities",
					"ActivityLoader", e);
		}
		return activities;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	private List<Class> findMyTypes(String basePackage) throws IOException,
			ClassNotFoundException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
				resourcePatternResolver);

		List<Class> candidates = new ArrayList<Class>();
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ resolveBasePackage(basePackage) + "/" + "**/*.class";
		Resource[] resources = resourcePatternResolver
				.getResources(packageSearchPath);
		for (Resource resource : resources) {
			if (resource.isReadable()) {
				MetadataReader metadataReader = metadataReaderFactory
						.getMetadataReader(resource);
				if (isCandidate(metadataReader)) {
					candidates.add(Class.forName(metadataReader
							.getClassMetadata().getClassName()));
				}
			}
		}
		return candidates;
	}

	private String resolveBasePackage(String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils
				.resolvePlaceholders(basePackage));
	}

	private boolean isCandidate(MetadataReader metadataReader)
			throws ClassNotFoundException {
		try {
			Class c = Class.forName(metadataReader.getClassMetadata()
					.getClassName());
			// if (c.getAnnotation(XmlRootElement.class) != null) {
			return true;
			// }
		} catch (Throwable e) {
			e.printStackTrace();
			
		}
		return false;
	}
}
