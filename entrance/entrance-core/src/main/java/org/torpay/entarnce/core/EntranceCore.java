package org.torpay.entarnce.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.torpay.common.exception.EntranceException;

public interface EntranceCore {
	public String processWebRequest(HttpServletRequest request,
			HttpServletResponse response, String content)
			throws EntranceException;
}
