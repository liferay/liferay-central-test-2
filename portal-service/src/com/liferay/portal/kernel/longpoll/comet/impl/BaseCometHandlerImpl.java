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
import com.liferay.portal.kernel.longpoll.comet.CometHandler;
import com.liferay.portal.kernel.longpoll.comet.CometSession;
import com.liferay.portal.kernel.longpoll.comet.CometState;

/**
 * @author Edward Han
 */
public abstract class BaseCometHandlerImpl implements CometHandler {

	protected BaseCometHandlerImpl() {
        _session = null;

        _state = CometState.STATE_OPEN;
    }

	public abstract CometHandler clone();

    public void destroy() throws SystemException {
        _state = CometState.STATE_CLOSED;

		_session.close();

		doDestroy();
    }

	public CometSession getSession() {
		return _session;
	}

    public CometState getState() {
        return _state;
    }

    public void init(CometSession session) throws SystemException {
        _state = CometState.STATE_READY;

		_session = session;

		doInit(session);
    }

    public void receiveData(char[] data) throws Exception {
        receiveData(new String(data));
    }

	protected abstract void doDestroy() throws SystemException;

	protected abstract void doInit(CometSession session) throws SystemException;

	private CometSession _session;
    private CometState _state;

}