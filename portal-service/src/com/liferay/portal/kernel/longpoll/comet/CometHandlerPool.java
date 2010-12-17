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

package com.liferay.portal.kernel.longpoll.comet;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Edward Han
 */
public class CometHandlerPool {

	public void startCometHandler(
			CometSession cometSession, CometHandler cometHandler)
		throws SystemException {

		Lock writeLock = _cometHandlerPoolRWLock.writeLock();

		try {
			writeLock.lock();

			String sessionId = cometSession.getSessionId();

			if (_cometHandlerPool.containsKey(sessionId)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Initializing handler on CometProcessor with " +
						"live handler, closing original handler");
				}

				closeCometHandler(sessionId);
			}

			_cometHandlerPool.put(sessionId, cometHandler);

			cometHandler.init(cometSession);
		}
		finally {
			writeLock.unlock();
		}
	}

	public void closeAllCometHandlers() throws SystemException {
		Lock writeLock = _cometHandlerPoolRWLock.writeLock();

		try {
			writeLock.lock();

			Iterator<Map.Entry<String, CometHandler>> comethandlerPoolIterator =
				_cometHandlerPool.entrySet().iterator();

			while (comethandlerPoolIterator.hasNext()) {
				Map.Entry<String, CometHandler> entry =
					comethandlerPoolIterator.next();

				CometHandler cometHandler = entry.getValue();

				if (cometHandler != null) {
					cometHandler.destroy();
				}

				comethandlerPoolIterator.remove();
			}
		}
		finally {
			writeLock.unlock();
		}
	}

	public void closeCometHandler(String sessionId) throws SystemException {
		Lock writeLock = _cometHandlerPoolRWLock.writeLock();

		try {
			writeLock.lock();

			CometHandler cometHandler = _cometHandlerPool.remove(sessionId);

			if (cometHandler != null) {
				cometHandler.destroy();
			}
		}
		finally {
			writeLock.unlock();
		}
	}

	public CometHandler getCometHandler(String sessionId) {
		Lock readLock = _cometHandlerPoolRWLock.readLock();

		try {
			readLock.lock();

			return _cometHandlerPool.get(sessionId);
		}
		finally {
			readLock.unlock();
		}
	}

	private static final Log _log =
		LogFactoryUtil.getLog(CometHandlerPool.class);

	private Map<String, CometHandler> _cometHandlerPool =
		new HashMap<String, CometHandler>();

	private ReadWriteLock _cometHandlerPoolRWLock =
		new ReentrantReadWriteLock();

}