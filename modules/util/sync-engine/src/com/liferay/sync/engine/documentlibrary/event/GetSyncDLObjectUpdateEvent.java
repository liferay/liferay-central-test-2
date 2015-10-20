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

import com.liferay.sync.engine.documentlibrary.handler.GetSyncDLObjectUpdateHandler;
import com.liferay.sync.engine.documentlibrary.handler.Handler;
import com.liferay.sync.engine.model.SyncSite;
import com.liferay.sync.engine.service.SyncSiteService;
import com.liferay.sync.engine.util.ReleaseInfo;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class GetSyncDLObjectUpdateEvent extends BaseEvent {

	public GetSyncDLObjectUpdateEvent(
		long syncAccountId, Map<String, Object> parameters) {

		super(syncAccountId, _URL_PATH, parameters);

		_handler = new GetSyncDLObjectUpdateHandler(this);
	}

	@Override
	public Handler<Void> getHandler() {
		return _handler;
	}

	@Override
	protected void processRequest() throws Exception {
		Long parentFolderId = (Long)getParameterValue("parentFolderId");

		if (parentFolderId != null) {
			super.processRequest();

			return;
		}

		SyncSite syncSite = (SyncSite)getParameterValue("syncSite");

		// Refetch for updated last remote sync time

		syncSite = SyncSiteService.fetchSyncSite(
			syncSite.getGroupId(), syncSite.getSyncAccountId());

		if (!syncSite.getActive()) {
			return;
		}

		syncSite.setState(SyncSite.STATE_IN_PROGRESS);

		SyncSiteService.update(syncSite);

		Map<String, Object> parameters = getParameters();

		parameters.put("lastAccessTime", syncSite.getRemoteSyncTime());
		parameters.put("max", 0);
		parameters.put("repositoryId", syncSite.getGroupId());

		if ((syncSite.getRemoteSyncTime() == -1) &&
			ReleaseInfo.isServerCompatible(getSyncAccountId(), 5)) {

			parameters.put("retrieveFromCache", false);
		}

		parameters.put("syncSite", syncSite);

		executePost(_URL_PATH, parameters);
	}

	private static final String _URL_PATH =
		"/sync-web.syncdlobject/get-sync-dl-object-update";

	private final Handler<Void> _handler;

}