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

package com.liferay.sync.engine.service.persistence;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import com.liferay.sync.engine.model.SyncFile;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author Shinn Lok
 */
public class SyncFilePersistence extends BasePersistenceImpl<SyncFile, Long> {

	public SyncFilePersistence() throws SQLException {
		super(SyncFile.class);
	}

	public long countByS_U(long syncAccountId, int uiEvent)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		Where<SyncFile, Long> where = queryBuilder.where();

		where.eq("syncAccountId", syncAccountId);

		where.and();

		where.eq("uiEvent", uiEvent);

		return where.countOf();
	}

	public long countByUIEvent(int uiEvent) throws SQLException {
		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		Where<SyncFile, Long> where = queryBuilder.where();

		where.eq("uiEvent", uiEvent);

		return where.countOf();
	}

	public SyncFile fetchByFilePathName(String filePathName)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("filePathName", filePathName);

		List<SyncFile> syncFiles = queryForFieldValuesArgs(fieldValues);

		if ((syncFiles == null) || syncFiles.isEmpty()) {
			return null;
		}

		return syncFiles.get(0);
	}

	public SyncFile fetchByFK_S(String fileKey, long syncAccountId)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("fileKey", fileKey);
		fieldValues.put("syncAccountId", syncAccountId);

		List<SyncFile> syncFiles = queryForFieldValues(fieldValues);

		if ((syncFiles == null) || syncFiles.isEmpty()) {
			return null;
		}

		return syncFiles.get(0);
	}

	public SyncFile fetchByR_S_T(
			long repositoryId, long syncAccountId, long typePK)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("repositoryId", repositoryId);
		fieldValues.put("syncAccountId", syncAccountId);
		fieldValues.put("typePK", typePK);

		List<SyncFile> syncFiles = queryForFieldValues(fieldValues);

		if ((syncFiles == null) || syncFiles.isEmpty()) {
			return null;
		}

		return syncFiles.get(0);
	}

	public List<SyncFile> findBySyncAccountId(long syncAccountId)
		throws SQLException {

		return queryForEq("syncAccountId", syncAccountId);
	}

	public List<SyncFile> findByF_L(String filePathName, long localSyncTime)
		throws SQLException {

		QueryBuilder<SyncFile, Long> queryBuilder = queryBuilder();

		Where<SyncFile, Long> where = queryBuilder.where();

		filePathName = StringUtils.replace(filePathName, "\\", "\\\\");

		where.like("filePathName", new SelectArg(filePathName + "%"));

		where.and();

		where.lt("localSyncTime", localSyncTime);

		where.and();

		where.ne("type", SyncFile.TYPE_SYSTEM);

		where.and();

		where.ne("uiEvent", SyncFile.UI_EVENT_DOWNLOADING);

		return query(queryBuilder.prepare());
	}

	public List<SyncFile> findByP_S(long parentFolderId, long syncAccountId)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("parentFolderId", parentFolderId);
		fieldValues.put("syncAccountId", syncAccountId);

		return queryForFieldValues(fieldValues);
	}

	public List<SyncFile> findByR_S(long repositoryId, long syncAccountId)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("repositoryId", repositoryId);
		fieldValues.put("syncAccountId", syncAccountId);

		return queryForFieldValues(fieldValues);
	}

	public List<SyncFile> findByS_U(long syncAccountId, int uiEvent)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<>();

		fieldValues.put("syncAccountId", syncAccountId);
		fieldValues.put("uiEvent", uiEvent);

		return queryForFieldValues(fieldValues);
	}

	public void updateByFilePathName(
			String filePathName, int state, int uiEvent)
		throws SQLException {

		UpdateBuilder<SyncFile, Long> updateBuilder = updateBuilder();

		Where<SyncFile, Long> where = updateBuilder.where();

		filePathName = StringUtils.replace(filePathName, "\\", "\\\\");

		where.like("filePathName", new SelectArg(filePathName + "%"));

		updateBuilder.updateColumnValue("state", state);
		updateBuilder.updateColumnValue("uiEvent", uiEvent);

		updateBuilder.update();
	}

}