/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.longpoll.comet.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.longpoll.comet.CometRequest;
import com.liferay.portal.kernel.longpoll.comet.CometResponse;
import com.liferay.portal.kernel.longpoll.comet.CometSession;

/**
 * @author Edward Han
 */
public abstract class BaseCometSessionImpl implements CometSession {

	protected BaseCometSessionImpl(
		String sessionId, CometRequest cometRequest,
		CometResponse cometResponse) {

        _cometRequest = cometRequest;
		_cometResponse = cometResponse;
		_sessionId = sessionId;
	}

    public void close() throws SystemException {
		_cometResponse.close();

		doClose();
	}

    public CometRequest getCometRequest() {
        return _cometRequest;
    }

    public CometResponse getCometResponse() {
        return _cometResponse;
    }

	public String getSessionId() {
		return _sessionId;
	}

	public void setCometRequest(CometRequest cometRequest) {
		_cometRequest = cometRequest;
	}

    public void setCometResponse(CometResponse cometResponse) {
        _cometResponse = cometResponse;
    }

	protected abstract void doClose() throws SystemException;

    private CometRequest _cometRequest;
	private CometResponse _cometResponse;
	private String _sessionId;

}