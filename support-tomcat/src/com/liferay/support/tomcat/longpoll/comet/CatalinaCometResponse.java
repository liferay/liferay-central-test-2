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
import com.liferay.portal.kernel.longpoll.comet.CometResponse;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.CometEvent;

/**
 * @author Edward Han
 */
public class CatalinaCometResponse implements CometResponse {

    public CatalinaCometResponse(CometEvent event) {
		_httpServletResponse = event.getHttpServletResponse();

		_open = true;
    }

	public void close() throws SystemException {
		synchronized (this) {
			_open = false;
		}
	}

	public HttpServletResponse getHttpServletResponse() {
        return _httpServletResponse;
    }

	public boolean isOpen() {
		return _open;
	}

	public void writeData(byte[] data) throws Exception {
		synchronized (this) {
			if (_open) {
				_httpServletResponse.getOutputStream().write(data);
				_httpServletResponse.getOutputStream().flush();
			}
			else {
				throw new IOException("Stream is closed");
			}
		}
	}

	public void writeData(String data) throws Exception {
		synchronized (this) {
			if (_open) {
				_httpServletResponse.getWriter().write(data);
				_httpServletResponse.getWriter().flush();
			}
			else {
				throw new IOException("Stream is closed");
			}
		}
	}

	private HttpServletResponse _httpServletResponse;
	private boolean _open;

}