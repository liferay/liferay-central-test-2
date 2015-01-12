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

package com.liferay.sync.engine.documentlibrary.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.sync.engine.documentlibrary.event.DownloadFileEvent;
import com.liferay.sync.engine.documentlibrary.event.DownloadFilesEvent;
import com.liferay.sync.engine.documentlibrary.handler.DownloadFileHandler;
import com.liferay.sync.engine.model.SyncFile;
import com.liferay.sync.engine.util.PropsValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shinn Lok
 */
public class BatchDownloadEvent {

	public BatchDownloadEvent(long syncAccountId) throws Exception {
		_syncAccountId = syncAccountId;
	}

	public synchronized boolean addEvent(DownloadFileEvent downloadFileEvent) {
		if (!PropsValues.SYNC_BATCH_EVENTS_ENABLED) {
			return false;
		}

		Map<String, Object> parameters = downloadFileEvent.getParameters();

		SyncFile syncFile = (SyncFile)parameters.get("syncFile");

		long size = 0;

		if ((boolean)parameters.get("patch")) {
			size =
				syncFile.getSize() *
					(1 / PropsValues.SYNC_FILE_PATCHING_THRESHOLD_SIZE_RATIO);
		}
		else {
			size = syncFile.getSize();
		}

		if (size >= PropsValues.SYNC_BATCH_EVENTS_MAX_FILE_SIZE) {
			return false;
		}

		_totalFileSize += size;

		_eventCount++;

		String zipFileId =
			syncFile.getSyncFileId() + "_" + System.currentTimeMillis();

		parameters.put("groupId", syncFile.getRepositoryId());
		parameters.put("uuid", syncFile.getTypeUuid());

		if ((boolean)parameters.get("patch")) {
			parameters.put("version", syncFile.getVersion());
		}

		parameters.put("zipFileId", zipFileId);

		parameters = new HashMap<String, Object>(parameters);

		parameters.remove("syncFile");

		_batchParameters.add(parameters);

		_handlers.put(
			zipFileId, (DownloadFileHandler)downloadFileEvent.getHandler());

		if ((_eventCount >= PropsValues.SYNC_BATCH_EVENTS_MAX_COUNT) ||
			(_totalFileSize >=
				PropsValues.SYNC_BATCH_EVENTS_MAX_TOTAL_FILE_SIZE)) {

			fireBatchEvent();
		}

		return true;
	}

	public synchronized void fireBatchEvent() {
		try {
			if (_closed || (_eventCount == 0)) {
				return;
			}

			ObjectMapper objectMapper = new ObjectMapper();

			Map<String, Object> parameters = new HashMap<>();

			parameters.put("handlers", _handlers);
			parameters.put(
				"zipFileIds",
				objectMapper.writeValueAsString(_batchParameters));

			DownloadFilesEvent downloadFilesEvent = new DownloadFilesEvent(
				_syncAccountId, parameters);

			downloadFilesEvent.run();

			_closed = true;
		}
		catch (Exception e) {
			_logger.debug(e.getMessage(), e);
		}
	}

	public synchronized boolean isClosed() {
		return _closed;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		BatchDownloadEvent.class);

	private List<Map<String, Object>> _batchParameters = new ArrayList<>();
	private boolean _closed;
	private int _eventCount;
	private Map<String, DownloadFileHandler> _handlers = new HashMap<>();
	private long _syncAccountId;
	private long _totalFileSize;

}