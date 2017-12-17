package org.torpay.engine.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Parameter;

public class JsonExtractor {

	static final Logger LOG = LoggerFactory.getLogger(JsonExtractor.class);

	public static List<MetadataParameter> extractParametersMetadata(
			String provider, String action, EngineMetaData metaDataService)
			throws MetadataException {
		String errorMsg = "can not lookup metadata for a provider<" + provider
				+ ">";
		List<MetadataParameter> ret = new ArrayList<MetadataParameter>();
		try {
			String metadata = metaDataService.getMetaData(provider);
			if (metadata == null || metadata.isEmpty())
				throwException(errorMsg, null);
			List<MetadataParameter> gp = extractGeneralParameters(provider,
					action, metadata);
			String parentProvider = getParentProvider(provider, action,
					metadata);
			String parentAction = getParentAction(provider, action, metadata);

			String parentMetadata = null;
			if (parentProvider != null)
				parentMetadata = metaDataService.getMetaData(parentProvider);
			if (parentProvider != null && parentAction != null
					&& parentMetadata != null) {
				List<MetadataParameter> pgp = extractGeneralParameters(
						parentProvider, parentAction, parentMetadata);
				ret.addAll(pgp);
			}
			ret.addAll(gp);
		} catch (Exception e) {
			throwException(errorMsg, e);
		}
		return ret;
	}

	private static String getParentProvider(String provider, String action,
			String metadata) throws MetadataException {
		return getParentPart(provider, action, metadata, "provider");
	}

	private static String getParentAction(String provider, String action,
			String metadata) throws MetadataException {
		return getParentPart(provider, action, metadata, "action");
	}

	private static String getParentPart(String provider, String action,
			String metadata, String part) throws MetadataException {
		try {

			JSONObject actionMembersObject = getAction(action, metadata);
			if (actionMembersObject == null)
				throwException("action is null in JSON content: action="
						+ action + " , content:" + metadata, null);
			Iterator<Entry> actionMembersIt = actionMembersObject.entrySet()
					.iterator();
			while (actionMembersIt.hasNext()) {
				Entry actionMember = (Entry) actionMembersIt.next();
				String actionMemberName = (String) actionMember.getKey();

				if (actionMemberName.equals("parent")) {
					JSONObject parent = (JSONObject) actionMember.getValue();
					Iterator<Entry> parentParts = parent.entrySet().iterator();
					while (parentParts.hasNext()) {
						Entry parentPart = (Entry) parentParts.next();
						String parentPartName = (String) parentPart.getKey();
						String parentPartValue = (String) parentPart.getValue();
						if (parentPartName.equals(part))
							return parentPartValue;

					}

				}

			}
		} catch (Exception e) {
			throw new MetadataException(ErrorCodes.TECHNICAL_ERROR,
					"error during parsing json content", "EngonMetaData", e);
		}
		return null;
	}

	private static List<MetadataParameter> extractGeneralParameters(
			String provider, String action, String metadata)
			throws MetadataException {
		List<MetadataParameter> ret = new ArrayList<MetadataParameter>();
		try {

			JSONObject actionMembersObject = getAction(action, metadata);
			if (actionMembersObject == null)
				throwException("action is null in JSON content: action="
						+ action + " , content:" + metadata, null);
			Iterator<Entry> actionMembersIt = actionMembersObject.entrySet()
					.iterator();
			Boolean actionIsVisited = false;
			while (actionMembersIt.hasNext()) {
				Entry actionMember = (Entry) actionMembersIt.next();
				String actionMemberName = (String) actionMember.getKey();
				if (!actionIsVisited && actionMemberName.equals("name")) {
					String actionMemberValue = (String) actionMember.getValue();
					if (actionMemberValue.equals(action)) {
						actionIsVisited = true;
						continue;
					}
				}

				if (actionIsVisited
						&& actionMemberName.equals("content-parameters")) {
					Iterator<JSONObject> actionParameters = ((JSONArray) actionMember
							.getValue()).iterator();
					while (actionParameters.hasNext()) {
						JSONObject actionParameter = (JSONObject) actionParameters
								.next();
						Iterator<JSONObject> parameterPartsIt = actionParameter
								.entrySet().iterator();
						MetadataParameter metadataParameter = new MetadataParameter();
						while (parameterPartsIt.hasNext()) {
							Entry part = (Entry) parameterPartsIt.next();
							String partName = (String) part.getKey();
							String partValue = null;
							if (!partName.equals("custom"))
								partValue = (String) part.getValue();
							if (partName.equals("name"))
								metadataParameter.setName(partValue);
							else if (partName.equals("required"))
								metadataParameter.setRequired(Boolean
										.valueOf(partValue));
							else if (partName.equals("type"))
								metadataParameter.setType(partValue);
							else if (partName.equals("length"))
								metadataParameter.setLength(partValue);
							else if (partName.equals("array"))
								metadataParameter.setIsArray(Boolean
										.valueOf(partValue));
							else if (partName.equals("length"))
								metadataParameter.setLength(partValue);
							else if (partName.equals("min-length"))
								metadataParameter.setMinLength(partValue);
							else if (partName.equals("max-length"))
								metadataParameter.setMaxLength(partValue);
							else if (partName.equals("map-to"))
								metadataParameter.setMapTo(partValue);
							else if (partName.equals("custom")) {
								Iterator<JSONObject> customs = ((JSONArray) part
										.getValue()).iterator();
								while (customs.hasNext()) {
									JSONObject custom = (JSONObject) customs
											.next();
									Iterator<JSONObject> customIt = custom
											.entrySet().iterator();
									List<String> customList = new ArrayList<String>();

									while (customIt.hasNext()) {
										Entry customPart = (Entry) customIt
												.next();
										String customPartName = (String) customPart
												.getKey();
										String customPartValue = (String) customPart
												.getValue();
										if (customPartName.equals("class"))
											customList.add(customPartValue);
									}
									metadataParameter.setCustom(customList);
								}
							}

						}
						ret.add(metadataParameter);
					}

				}

			}

		} catch (Exception e) {
			throw new MetadataException(ErrorCodes.TECHNICAL_ERROR,
					"error during parsing json content", "metadataService", e);
		}
		if (ret.size() == 0 && LOG.isWarnEnabled())
			LOG.warn("List<MetadataParameter> for action " + action
					+ " provider " + provider + " is empty");
		return ret;

	}

	private static List<MetadataParameter> extractParentGeneralParameters(
			String provider, String action, EngineMetaData metaDataService) {
		List<MetadataParameter> ret = new ArrayList<MetadataParameter>();
		return ret;

	}

	private static void throwException(String message, Exception e)
			throws MetadataException {
		if (e != null)
			throw new MetadataException(ErrorCodes.TECHNICAL_ERROR, message,
					"metadataService", e);
		else
			throw new MetadataException(ErrorCodes.TECHNICAL_ERROR, message,
					"metadataService");
	}

	private static JSONObject getAction(String action, String metadata)
			throws MetadataException {
		List<MetadataParameter> ret = new ArrayList<MetadataParameter>();
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		List<Parameter> result = new ArrayList<Parameter>();
		try {
			json = (JSONObject) parser.parse(metadata);
		} catch (Exception e) {
			throw new MetadataException(ErrorCodes.TECHNICAL_ERROR,
					"can not parse the json content:<" + metadata + ">",
					"EngonMetaData", e);
		}
		try {
			if (!json.entrySet().isEmpty()) {
				Iterator<JSONObject> iterator = json.entrySet().iterator();
				while (iterator.hasNext()) {
					Object objectParameter = (Object) iterator.next();
					if (objectParameter instanceof Entry) {
						Entry entryParameter = (Entry) objectParameter;
						String name = (String) entryParameter.getKey();
						if (name.equals("actions")) {
							Object value = entryParameter.getValue();
							if (value instanceof JSONArray) {
								Iterator<JSONObject> it = ((JSONArray) value)
										.iterator();
								while (it.hasNext()) {
									JSONObject actionJSONObject = (JSONObject) it
											.next();
									JSONObject actionMembersObject = (JSONObject) actionJSONObject;
									Iterator<Entry> actionMembersIt = actionMembersObject
											.entrySet().iterator();
									while (actionMembersIt.hasNext()) {
										Entry actionMember = (Entry) actionMembersIt
												.next();
										String actionMemberName = (String) actionMember
												.getKey();
										if (actionMemberName.equals("name")) {
											String actionMemberValue = (String) actionMember
													.getValue();
											if (actionMemberValue
													.equals(action)) {
												return actionMembersObject;
											}
										}
									}
								}
							}

						}

					}
				}
			}
		} catch (Exception e) {
			throw new MetadataException(ErrorCodes.TECHNICAL_ERROR,
					"error during parsing json content", "EngonMetaData", e);
		}
		return null;
	}

	public static String extractActionHandlerClass(String provider,
			String action, EngineMetaData metaDataService)
			throws MetadataException {
		String actionHandlerClass = null;
		try {
			String metadata = metaDataService.getMetaData(provider);
			actionHandlerClass = JsonExtractor
					.extractProviderActionHandlerClass(provider, action,
							metaDataService, metadata);
			if (actionHandlerClass == null) {
				if (LOG.isDebugEnabled())
					LOG.debug("action handler class is null for provider "
							+ provider + " and action " + action
							+ ", try  to find it from its parent");

				String parentProvider = getParentProvider(provider, action,
						metadata);
				String parentAction = getParentAction(provider, action,
						metadata);

				String parentMetadata = null;
				if (parentProvider != null)
					parentMetadata = metaDataService
							.getMetaData(parentProvider);
				if (parentProvider != null && parentAction != null
						&& parentMetadata != null) {
					if (LOG.isDebugEnabled())
						LOG.debug("finding action handler class from parent provider "
								+ parentProvider
								+ " and parent action "
								+ parentAction
								+ " parentMetadta "
								+ parentMetadata);
					actionHandlerClass = JsonExtractor
							.extractProviderActionHandlerClass(parentProvider,
									parentAction, metaDataService,
									parentMetadata);
				}
			}
		} catch (Exception e) {
			throw new MetadataException(ErrorCodes.TECHNICAL_ERROR,
					"error during extracting action handler", "EngineMetadata",
					e);
		}

		if (LOG.isDebugEnabled())
			LOG.debug("action handler class = " + actionHandlerClass);
		return actionHandlerClass;
	}

	public static String extractProviderActionHandlerClass(String provider,
			String action, EngineMetaData metaDataService, String metadata)
			throws MetadataException {
		try {
			JSONObject actionMembersObject = getAction(action, metadata);
			if (actionMembersObject == null)
				throwException("action is null in JSON content: action="
						+ action + " , content:" + metadata, null);
			Iterator<Entry> actionMembersIt = actionMembersObject.entrySet()
					.iterator();
			while (actionMembersIt.hasNext()) {
				Entry actionMember = (Entry) actionMembersIt.next();
				String actionMemberName = (String) actionMember.getKey();

				if (actionMemberName.equals("action-hanlder-class")) {
					String actionHanlderClass = (String) actionMember
							.getValue();
					LOG.debug("actionHanlderClass = " + actionHanlderClass);
					if (actionHanlderClass == null) {
						String parentProvider = getParentProvider(provider,
								action, metadata);
						if (parentProvider != null) {
							String parentAction = getParentAction(provider,
									action, metadata);
							if (parentAction != null) {
								String parentActionHanlderClass = extractActionHandlerClass(
										parentProvider, parentAction,
										metaDataService);
								if (parentActionHanlderClass != null) {
									LOG.debug("parentActionHanlderClass = "
											+ parentActionHanlderClass);
								} else {
									LOG.debug("no parentActionHanlderClass");
								}
							}
						} else {
							LOG.debug("no parent for " + provider + "."
									+ action + " is found");
						}
					} else
						return actionHanlderClass;
				}

			}
		} catch (Exception e) {
			throw new MetadataException(ErrorCodes.TECHNICAL_ERROR,
					"error during parsing json content", "EngineMetadata", e);
		}
		return null;
	}

	public static String extractActionPart(String provider, String action,
			JsonMetaData jsonMetaData, String part) throws MetadataException {
		try {
			String metadata = jsonMetaData.getMetaData(provider);
			JSONObject actionMembersObject = getAction(action, metadata);
			if (actionMembersObject == null)
				throwException("action is null in JSON content: action="
						+ action + " , content:" + metadata, null);
			Iterator<Entry> actionMembersIt = actionMembersObject.entrySet()
					.iterator();
			while (actionMembersIt.hasNext()) {
				Entry actionMember = (Entry) actionMembersIt.next();
				String actionMemberName = (String) actionMember.getKey();

				if (actionMemberName.equals(part)) {
					return (String) actionMember.getValue();
				}

			}
		} catch (Exception e) {
			throw new MetadataException(ErrorCodes.TECHNICAL_ERROR,
					"error during parsing json content", "EinginMetaData", e);
		}
		return null;
	}

	public static String extractIsMapped(String provider, String action,
			JsonMetaData jsonMetaData) throws MetadataException {
		return extractActionPart(provider, action, jsonMetaData, "is-mapped");
	}

	public static String extractCustomMappedClass(String provider,
			String action, JsonMetaData jsonMetaData) throws MetadataException {
		return extractActionPart(provider, action, jsonMetaData,
				"map-custom-class");
	}

	public static String extractMappedProvider(String provider, String action,
			JsonMetaData jsonMetaData) throws MetadataException {
		return extractActionPart(provider, action, jsonMetaData,
				"map-to-provider");
	}

	public static String extractMappedAction(String provider, String action,
			JsonMetaData jsonMetaData) throws MetadataException {
		return extractActionPart(provider, action, jsonMetaData,
				"map-to-action");
	}

	public static String extractMappedParameter(String provider, String action,
			String name, JsonMetaData jsonMetaData) throws MetadataException {
		try {
			String metadata = jsonMetaData.getMetaData(provider);
			JSONObject actionMembersObject = getAction(action, metadata);
			if (actionMembersObject == null)
				throwException("action is null in JSON content: action="
						+ action + " , content:" + metadata, null);
			Iterator<Entry> actionMembersIt = actionMembersObject.entrySet()
					.iterator();
			Boolean actionIsVisited = false;
			while (actionMembersIt.hasNext()) {
				Entry actionMember = (Entry) actionMembersIt.next();
				String actionMemberName = (String) actionMember.getKey();
				if (!actionIsVisited && actionMemberName.equals("name")) {
					String actionMemberValue = (String) actionMember.getValue();
					if (actionMemberValue.equals(action)) {
						actionIsVisited = true;
						continue;
					}
				}

				if (actionIsVisited
						&& actionMemberName.equals("content-parameters")) {
					Iterator<JSONObject> actionParameters = ((JSONArray) actionMember
							.getValue()).iterator();
					while (actionParameters.hasNext()) {
						JSONObject actionParameter = (JSONObject) actionParameters
								.next();
						Iterator<JSONObject> parameterPartsIt = actionParameter
								.entrySet().iterator();
						MetadataParameter metadataParameter = new MetadataParameter();
						boolean visited = false;
						while (parameterPartsIt.hasNext()) {
							Entry part = (Entry) parameterPartsIt.next();
							String partName = (String) part.getKey();
							String partValue = (String) part.getValue();
							if (partName.equals("name")
									&& partValue.equals(name))
								visited = true;
							if (visited && partName.equals("map-to"))
								return partValue;
						}
					}

				}
			}

		} catch (Exception e) {
			throw new MetadataException(
					ErrorCodes.TECHNICAL_ERROR,
					"error during parsing json content to lookup map-to parameter",
					"metadataService", e);
		}
		return null;
	}

}
