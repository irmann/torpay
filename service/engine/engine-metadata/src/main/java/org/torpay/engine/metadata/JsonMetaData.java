package org.torpay.engine.metadata;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.torpay.common.util.FlushUtil;
import org.torpay.common.util.JSONUtil;

public class JsonMetaData implements EngineMetaData {

	private static final Logger LOG = LoggerFactory
			.getLogger(JsonMetaData.class);

	// private @Autowired
	private static ClassPathXmlApplicationContext appContext;
	// private @Autowired
	// BeanFactory beanFactory;

	private HashMap<String, String> metadataMap = new HashMap<String, String>();

	public String getMetaData(String providerId) throws Exception {
		flushCache();
		if (providerId == null)
			new IllegalStateException("providerId is null");
		if (metadataMap == null || metadataMap.size() == 0
				|| metadataMap.get(providerId.toUpperCase()) == null) {
			LOG.info("metadadata loading ...");
			loadMetaData();
		} else
			LOG.debug("finding metadata for " + providerId);
		String metadata = metadataMap.get(providerId.toUpperCase());

		if (metadata == null) {
			LOG.error("there is not any metadata for " + providerId);
			logAllMetaData();
			new IllegalStateException("providerId is null");
		}
		return metadata;
	}

	private void logAllMetaData() {
		for (String name : metadataMap.keySet())
			LOG.info("metadata for " + name + " exist");

	}

	synchronized public void flushCache() throws Exception {
		if (FlushUtil.METEDATA_FLUSH) {
			loadMetaData();
			FlushUtil.METEDATA_FLUSH = false;
		}

	}

	private void loadMetaData() throws Exception {
		loadMetaDataFromFileSystem();
		loadMetaDataFromClasspath();
		LOG.info("metadadata has been loaded");
	}

	private void loadMetaDataFromFileSystem() {
		// TODO read file from a configured file name
	}

	private void loadMetaDataFromClasspath() throws IOException {
		if (appContext == null) {
			LOG.info("loading appContext from engine-metadata.xml ... ");
			appContext = new ClassPathXmlApplicationContext(
					new String[] { "spring/engine-metadata.xml" });
		}
		Resource defaultProviders = appContext
				.getResource("classpath:providers");
		File[] files = defaultProviders.getFile().listFiles();
		LOG.info(files.length + " files has been founded");
		for (File file : files) {
			String content = FileUtils.readFileToString(file);
			String providerId = extractProviderId(content);
			if (providerId == null)
				LOG.warn("can not extract provider id from " + file.getName()
						+ " file. content:\n" + content);
			else
				metadataMap.put(providerId.toUpperCase(), content);
		}
	}

	private String extractProviderId(String content) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(content);
			return JSONUtil.extractObject(json, "provider").toString();
		} catch (ParseException e) {
			LOG.error("error while parsing json content", e);
		}
		return null;
	}

	public List<MetadataParameter> extractParametersMetadata(String provider,
			String action) throws MetadataException {
		return JsonExtractor.extractParametersMetadata(provider, action, this);
	}

	@Override
	public String extractActionHandlerClass(String provider, String action)
			throws MetadataException {
		return JsonExtractor.extractActionHandlerClass(provider, action, this);
	}

	@Override
	public String extractMappedProvider(String provider, String action)
			throws MetadataException {
		return JsonExtractor.extractMappedProvider(provider, action, this);
	}

	@Override
	public String extractIsMapped(String provider, String action)
			throws MetadataException {
		return JsonExtractor.extractIsMapped(provider, action, this);
	}

	@Override
	public String extractMappedAction(String provider, String action)
			throws MetadataException {
		return JsonExtractor.extractMappedAction(provider, action, this);
	}

	@Override
	public String extractCustomMappedClass(String provider, String action)
			throws MetadataException {
		return JsonExtractor.extractCustomMappedClass(provider, action, this);
	}

	@Override
	public String extractMappedParameter(String provider, String action,
			String name) throws MetadataException {
		return JsonExtractor.extractMappedParameter(provider, action, name,
				this);
	}

	@Override
	public String extractChannelMappedParameter(String provider, String action,
			String name, String channel) throws MetadataException{
		// TODO implement me
		throw new IllegalArgumentException("this methos is not impelemented");
	}

	@Override
	public String extractChannelMappedProvider(String provider, String action,
			String channel) throws MetadataException{
		// TODO implement me
		throw new IllegalArgumentException("this methos is not impelemented");
	}

	@Override
	public String extractChannelMappedAction(String provider, String action,
			String channel) throws MetadataException{
		// TODO implement me
		throw new IllegalArgumentException("this methos is not impelemented");
	}

}
