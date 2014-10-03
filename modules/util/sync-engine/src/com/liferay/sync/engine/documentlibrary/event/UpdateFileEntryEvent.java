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

import com.liferay.sync.engine.documentlibrary.handler.Handler;
import com.liferay.sync.engine.documentlibrary.handler.UpdateFileEntryHandler;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.service.SyncFileService;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class UpdateFileEntryEvent extends BaseEvent {

	public UpdateFileEntryEvent(
		long syncAccountId, Map<String, Object> parameters) {

		super(syncAccountId, _URL_PATH, parameters);

		_handler = new UpdateFileEntryHandler(this);
	}

	@Override
	public Handler<Void> getHandler() {
		return _handler;
	}

	@Override
	protected void processRequest() throws Exception {
		SyncFile syncFile = (SyncFile)getParameterValue("syncFile");

		syncFile.setState(SyncFile.STATE_IN_PROGRESS);

		if (getParameterValue("filePath") != null) {
			syncFile.setUiEvent(SyncFile.UI_EVENT_UPLOADING);
		}

		SyncFileService.update(syncFile);

		super.processAsynchronousRequest();
	}

	private static final String _URL_PATH =
		"/sync-web.syncdlobject/update-file-entry";

	private Handler<Void> _handler;

}