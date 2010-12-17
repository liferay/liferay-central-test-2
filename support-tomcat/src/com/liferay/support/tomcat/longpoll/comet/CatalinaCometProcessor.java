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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.longpoll.comet.CometHandler;
import com.liferay.portal.kernel.longpoll.comet.CometHandlerPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.CometEvent;
import org.apache.catalina.CometProcessor;

/**
 * @author Edward Han
 */
public class CatalinaCometProcessor
    extends HttpServlet implements CometProcessor {

    public static final String TARGET_COMET_HANDLER = "cometHandlerImpl";

    @Override
    public void destroy() {
        super.destroy();

        if (_log.isDebugEnabled()) {
            _log.debug("Comet destroy triggered");
        }

		try {
			_cometHandlerPool.closeAllCometHandlers();
		}
		catch (SystemException e) {
			if (_log.isErrorEnabled()) {
				_log.error("Error shutting down CometProcessor", e);
			}
		}
    }

	public void event(CometEvent cometEvent)
		throws IOException, ServletException {

		CometEvent.EventType type = cometEvent.getEventType();

		if (_log.isDebugEnabled()) {
			_log.debug("Comet event triggered, event type: " + type);
		}

		String sessionId =
			cometEvent.getHttpServletRequest().getSession().getId();

		try {
			if (type.equals(CometEvent.EventType.BEGIN)) {
				constructCometHandler(sessionId, cometEvent);
			}
			else if (type.equals(CometEvent.EventType.END) ||
				type.equals(CometEvent.EventType.ERROR)) {

				if (_log.isInfoEnabled()) {
					logCometSessionEventSubType(
						sessionId, cometEvent.getEventSubType());
				}

				closeConnection(sessionId, cometEvent);
			}
			else if (type.equals(CometEvent.EventType.READ)) {
				String data = readData(sessionId, cometEvent);

				if (_log.isDebugEnabled()) {
					_log.debug("Read: " + data);
				}

				processData(sessionId, data);
			}
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		if (_log.isDebugEnabled()) {
			_log.debug("Comet init triggered");
		}

		String cometHandler = config.getInitParameter(TARGET_COMET_HANDLER);

		try {
			ClassLoader classLoader =
				Thread.currentThread().getContextClassLoader();

			_cometHandler =
				(CometHandler)classLoader.loadClass(cometHandler).newInstance();
		}
		catch (Exception e) {
			throw new ServletException(
				"Unable to initialize Comet Processor, " +
					"invalid handler name: [" + cometHandler + "]", e);
		}
	}

    protected void closeConnection(String sessionId, CometEvent cometEvent)
		throws Exception {

        if (_log.isDebugEnabled()) {
            _log.debug("Closing connection: " + sessionId);
        }

        _cometHandlerPool.closeCometHandler(sessionId);

		cometEvent.close();
    }

    protected void constructCometHandler(String sessionId, CometEvent event)
		throws SystemException {

		event.getHttpServletResponse().setContentType(
			ContentTypes.TEXT_PLAIN_UTF8);

		CatalinaCometRequest cometRequest = new CatalinaCometRequest(
			event.getHttpServletRequest(), System.currentTimeMillis());

		CatalinaCometResponse cometResponse = new CatalinaCometResponse(event);

		CatalinaCometSession cometSession =
			new CatalinaCometSession(
				sessionId, cometRequest, cometResponse, event);

		CometHandler cometHandler = _cometHandler.clone();

        _cometHandlerPool.startCometHandler(cometSession, cometHandler);
    }

	protected void logCometSessionEventSubType(
		String sessionId, CometEvent.EventSubType subType) {

		String msg = StringPool.BLANK;

		switch (subType) {
			case CLIENT_DISCONNECT:
				msg = _ERROR_MESSAGE_CLIENT_DISCONNECT;
				break;

			case IOEXCEPTION:
				msg = _ERROR_MESSAGE_IOEXCEPTION;
				break;

			case SERVER_SHUTDOWN:
				msg = _ERROR_MESSAGE_SERVER_SHUTDOWN;
				break;

			case SESSION_END:
				msg = _ERROR_MESSAGE_SESSION_END;
				break;

			case TIMEOUT:
				msg = _ERROR_MESSAGE_TIMEOUT;
				break;

			case WEBAPP_RELOAD:
				msg = _ERROR_MESSAGE_WEBAPP_RELOAD;
				break;
		}

		StringBundler message = new StringBundler(5);

		message.append("Comet connection terminated for ");
		message.append(sessionId);
		message.append(_cometHandler.getClass().getName());
		message.append(StringPool.COLON);
		message.append(msg);

		_log.info(message.toString());
	}

	protected void processData(String sessionId, String data)
		throws Exception {

		CometHandler cometHandler = _cometHandlerPool.getCometHandler(
			sessionId);

		cometHandler.receiveData(data);
	}

    protected String readData(String sessionId, CometEvent cometEvent)
		throws IOException {

        HttpServletRequest request = cometEvent.getHttpServletRequest();

        InputStream inputStream = request.getInputStream();

        byte[] buffer = new byte[512];

        StringBundler data = new StringBundler();

        while (inputStream.available() > 0) {
            int numRead = inputStream.read(buffer);

            if (numRead < 0) {
				if (_log.isInfoEnabled()) {
					logCometSessionEventSubType(
						sessionId, cometEvent.getEventSubType());
				}

            } else {
				String dataSegment = new String(buffer);

                data.append(dataSegment);
            }
        }

        return data.toString();
    }

    private static final String _ERROR_MESSAGE_CLIENT_DISCONNECT =
		"Client disconnected";
    private static final String _ERROR_MESSAGE_IOEXCEPTION =
		"IOException";
    private static final String _ERROR_MESSAGE_SERVER_SHUTDOWN =
		"Server shutting down";
    private static final String _ERROR_MESSAGE_SESSION_END = "Session ended";
    private static final String _ERROR_MESSAGE_TIMEOUT = "Connection timeout";
    private static final String _ERROR_MESSAGE_WEBAPP_RELOAD =
		"Webapp reloading";

    private static Log _log = LogFactoryUtil.getLog(
		CatalinaCometProcessor.class);

	private CometHandler _cometHandler;

	private CometHandlerPool _cometHandlerPool = new CometHandlerPool();

}