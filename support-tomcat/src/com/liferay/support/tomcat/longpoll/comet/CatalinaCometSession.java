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

package com.liferay.support.tomcat.longpoll.comet;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.longpoll.comet.impl.BaseCometSessionImpl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.CometEvent;

/**
 * @author Edward Han
 */
public class CatalinaCometSession extends BaseCometSessionImpl {
    public CatalinaCometSession(
		String sessionId, CatalinaCometRequest request,
		CatalinaCometResponse response, CometEvent event) {

        super(sessionId, request, response);

		_cometEvent = event;
    }

    protected void doClose() throws SystemException {
		try {
			_cometEvent.close();
		}
		catch (IllegalStateException e) {
			//	Already closed, eat exception
		}
		catch (IOException e) {
			throw new SystemException(e);
		}
    }

    public Object getAttribute(String name) {
		HttpServletRequest httpServletRequest =
			_cometEvent.getHttpServletRequest();

		HttpSession httpSession = httpServletRequest.getSession();

        return httpSession.getAttribute(name);
    }

	public void setCometEvent(CometEvent cometEvent) {
		_cometEvent = cometEvent;
	}

    private CometEvent _cometEvent;

}