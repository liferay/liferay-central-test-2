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

package com.liferay.sync.engine.documentlibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.liferay.sync.engine.model.SyncFile;

import java.util.List;

/**
 * @author Michael Young
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncDLObjectUpdate {

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public List<SyncFile> getSyncDLObjects() {
		return syncDLObjects;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public void setSyncDLObjects(List<SyncFile> syncDLObjects) {
		this.syncDLObjects = syncDLObjects;
	}

	protected long lastAccessTime;
	protected List<SyncFile> syncDLObjects;

}