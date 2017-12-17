package org.torpay.engine.core;

import org.torpay.common.request.ProviderRequest;

public interface EngineCore {

	public void process(ProviderRequest request) throws EngineCoreException;

}
