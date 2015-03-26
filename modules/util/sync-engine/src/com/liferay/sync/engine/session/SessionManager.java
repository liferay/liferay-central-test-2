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

package com.liferay.sync.engine.session;

import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.service.SyncAccountService;
import com.liferay.sync.engine.util.Encryptor;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class SessionManager {

	public static synchronized Session getSession(long syncAccountId) {
		Session session = _sessions.get(syncAccountId);

		if (session != null) {
			return session;
		}

		try {
			SyncAccount syncAccount = SyncAccountService.fetchSyncAccount(
				syncAccountId);

			URL url = new URL(syncAccount.getUrl());

			if (syncAccount.isOAuthEnabled()) {
				session = new Session(
					url, syncAccount.getOAuthConsumerKey(),
					syncAccount.getOAuthConsumerSecret(),
					syncAccount.getLogin(),
					Encryptor.decrypt(syncAccount.getPassword()),
					syncAccount.isTrustSelfSigned(),
					syncAccount.getMaxConnections());
			}
			else {
				session = new Session(
					url, syncAccount.getLogin(),
					Encryptor.decrypt(syncAccount.getPassword()),
					syncAccount.isTrustSelfSigned(),
					syncAccount.getMaxConnections());
			}

			session.startTrackTransferRate();

			_sessions.put(syncAccountId, session);

			return session;
		}
		catch (Exception e) {
			_logger.error(e.getMessage(), e);

			return null;
		}
	}

	public static void removeSession(long syncAccountId) {
		Session session = _sessions.remove(syncAccountId);

		if (session == null) {
			return;
		}

		session.stopTrackTransferRate();

		ExecutorService executorService = session.getExecutorService();

		executorService.shutdownNow();
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		SessionManager.class);

	private static final Map<Long, Session> _sessions = new HashMap<>();

}