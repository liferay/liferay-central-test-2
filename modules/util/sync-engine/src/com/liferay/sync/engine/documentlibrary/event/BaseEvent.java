/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.sync.engine.documentlibrary.event;

import com.liferay.sync.engine.SyncEngine;
import com.liferay.sync.engine.documentlibrary.handler.Handler;
import com.liferay.sync.engine.documentlibrary.util.BatchEvent;
import com.liferay.sync.engine.documentlibrary.util.BatchEventManager;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.session.Session;
import com.liferay.sync.engine.session.SessionManager;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public abstract class BaseEvent implements Event {

	public BaseEvent(
		long syncAccountId, String urlPath, Map<String, Object> parameters) {

		_syncAccountId = syncAccountId;
		_urlPath = urlPath;
		_parameters = parameters;
	}

	public void executeAsynchronousGet(String urlPath) throws Exception {
		Session session = SessionManager.getSession(_syncAccountId);

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		session.executeAsynchronousGet(
			syncAccount.getUrl() + urlPath, _handler);
	}

	public void executeAsynchronousPost(
			String urlPath, Map<String, Object> parameters)
		throws Exception {

		Session session = SessionManager.getSession(_syncAccountId);

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		session.executeAsynchronousPost(
			syncAccount.getUrl() + "/api/jsonws" + urlPath, parameters,
			_handler);
	}

	public void executeGet(String urlPath) throws Exception {
		Session session = SessionManager.getSession(_syncAccountId);

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		session.executeGet(syncAccount.getUrl() + urlPath, _handler);
	}

	public void executePost(String urlPath, Map<String, Object> parameters)
		throws Exception {

		Session session = SessionManager.getSession(_syncAccountId);

		SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
			_syncAccountId);

		session.executePost(
			syncAccount.getUrl() + "/api/jsonws" + urlPath, parameters,
			_handler);
	}

	@Override
	public Map<String, Object> getParameters() {
		return _parameters;
	}

	@Override
	public Object getParameterValue(String key) {
		return _parameters.get(key);
	}

	@Override
	public long getSyncAccountId() {
		return _syncAccountId;
	}

	@Override
	public String getURLPath() {
		return _urlPath;
	}

	@Override
	public void run() {
		if (!SyncEngine.isRunning()) {
			return;
		}

		_handler = getHandler();

		try {
			if (_logger.isTraceEnabled()) {
				Class<?> clazz = this.getClass();

				SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

				if (syncFile != null) {
					_logger.trace(
						"Processing event {} file path {}",
						clazz.getSimpleName(), syncFile.getFilePathName());
				}
				else {
					_logger.trace("Processing event {}", clazz.getSimpleName());
				}
			}

			processRequest();
		}
		catch (Exception e) {
			_handler.handleException(e);
		}
	}

	protected void processAsynchronousRequest() throws Exception {
		BatchEvent batchEvent = BatchEventManager.getBatchEvent(_syncAccountId);

		if (!batchEvent.addEvent(this)) {
			executeAsynchronousPost(_urlPath, _parameters);
		}
	}

	protected void processRequest() throws Exception {
		executePost(_urlPath, _parameters);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		BaseEvent.class);

	private Handler<Void> _handler;
	private final Map<String, Object> _parameters;
	private final long _syncAccountId;
	private final String _urlPath;

}