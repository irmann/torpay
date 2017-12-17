package org.torpay.engine.metadata;

import java.util.List;

import org.torpay.common.util.Parameter;

public interface EngineMetaData {

	public String getMetaData(String providerId) throws Exception;

	public void flushCache() throws Exception;

	public List<MetadataParameter> extractParametersMetadata(String provider,
			String action) throws MetadataException;

	public String extractActionHandlerClass(String provider, String action)
			throws MetadataException;

	public String extractMappedProvider(String provider, String action)
			throws MetadataException;

	public String extractMappedAction(String provider, String action)
			throws MetadataException;

	public String extractIsMapped(String provider, String action)
			throws MetadataException;

	public String extractCustomMappedClass(String provider, String action)
			throws MetadataException;

	public String extractMappedParameter(String provider, String action,
			String name) throws MetadataException;

	public String extractChannelMappedParameter(String provider, String action,
			String name, String channel) throws MetadataException;

	public String extractChannelMappedProvider(String provider, String action,
			String channel) throws MetadataException;

	public String extractChannelMappedAction(String provider, String action,
			String channel) throws MetadataException;

}
