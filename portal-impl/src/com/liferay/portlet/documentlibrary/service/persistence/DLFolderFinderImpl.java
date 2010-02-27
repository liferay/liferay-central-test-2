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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="DLFolderFinderImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFolderFinderImpl
	extends BasePersistenceImpl<DLFolder> implements DLFolderFinder {

	public static String COUNT_FE_FS_BY_G_F_S =
		DLFolderFinder.class.getName() + ".countFE_FS_ByG_F_S";

	public static String COUNT_F_FE_FS_BY_G_F_S =
		DLFolderFinder.class.getName() + ".countF_FE_FS_ByG_F_S";

	public static String FIND_FE_FS_BY_G_F_S =
		DLFolderFinder.class.getName() + ".findFE_FS_ByG_F_S";

	public static String FIND_F_FE_FS_BY_G_F_S =
		DLFolderFinder.class.getName() + ".findF_FE_FS_ByG_F_S";

	public int countFE_FS_ByG_F_S(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_FE_FS_BY_G_F_S);

			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileEntry"));
			sql = StringUtil.replace(
				sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileShortcut"));

			if (status != StatusConstants.APPROVED) {
				sql = StringUtil.replace(
					sql, "(DLFileEntry.version > 0) AND", "");
				sql = StringUtil.replace(
					sql, "(DLFileShortcut.status = 0) AND", "");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			qPos.add(groupId);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			int count = 0;

			Iterator<Long> itr = q.list().iterator();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countF_FE_FS_ByG_F_S(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_F_FE_FS_BY_G_F_S);

			sql = StringUtil.replace(
				sql, "[$FOLDER_PARENT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFolder"));
			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileEntry"));
			sql = StringUtil.replace(
				sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileShortcut"));

			if (status != StatusConstants.APPROVED) {
				sql = StringUtil.replace(
					sql, "(DLFileEntry.version > 0) AND", "");
				sql = StringUtil.replace(
					sql, "(DLFileShortcut.status = 0) AND", "");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			qPos.add(groupId);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			qPos.add(groupId);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			int count = 0;

			Iterator<Long> itr = q.list().iterator();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findFE_FS_ByG_F_S(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_FE_FS_BY_G_F_S);

			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileEntry"));
			sql = StringUtil.replace(
				sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileShortcut"));

			if (status != StatusConstants.APPROVED) {
				sql = StringUtil.replace(
					sql, "(DLFileEntry.version > 0) AND", "");
				sql = StringUtil.replace(
					sql, "(DLFileShortcut.status = 0) AND", "");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("modelFolderId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("title", Type.STRING);
			q.addScalar("fileShortcutId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			qPos.add(groupId);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			List<Object> models = new ArrayList<Object>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), start, end);

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long folderId = (Long)array[0];
				String name = (String)array[1];
				//String title = (String)array[2];
				long fileShortcutId = (Long)array[3];

				Object obj = null;

				if (fileShortcutId > 0) {
					obj = DLFileShortcutUtil.findByPrimaryKey(fileShortcutId);
				}
				else {
					obj = DLFileEntryUtil.findByG_F_N(groupId, folderId, name);
				}

				models.add(obj);
			}

			return models;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findF_FE_FS_ByG_F_S(
			long groupId, List<Long> folderIds, int status, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_F_FE_FS_BY_G_F_S);

			sql = StringUtil.replace(
				sql, "[$FOLDER_PARENT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFolder"));
			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileEntry"));
			sql = StringUtil.replace(
				sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileShortcut"));

			if (status != StatusConstants.APPROVED) {
				sql = StringUtil.replace(
					sql, "(DLFileEntry.version > 0) AND", "");
				sql = StringUtil.replace(
					sql, "(DLFileShortcut.status = 0) AND", "");
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("modelFolderId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("title", Type.STRING);
			q.addScalar("fileShortcutId", Type.LONG);
			q.addScalar("modelFolder", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			qPos.add(groupId);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			qPos.add(groupId);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			List<Object> models = new ArrayList<Object>();

			Iterator<Object[]> itr = (Iterator<Object[]>)QueryUtil.iterate(
				q, getDialect(), start, end);

			while (itr.hasNext()) {
				Object[] array = itr.next();

				long folderId = (Long)array[0];
				String name = (String)array[1];
				//String title = (String)array[2];
				long fileShortcutId = (Long)array[3];
				long modelFolder = (Long)array[4];

				Object obj = null;

				if (modelFolder == 1) {
					obj = DLFolderUtil.findByPrimaryKey(folderId);
				}
				else if (fileShortcutId > 0) {
					obj = DLFileShortcutUtil.findByPrimaryKey(fileShortcutId);
				}
				else {
					obj = DLFileEntryUtil.findByG_F_N(groupId, folderId, name);
				}

				models.add(obj);
			}

			return models;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getFolderIds(List<Long> folderIds, String table) {
		if (folderIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(folderIds.size() * 5 - 1);

		for (int i = 0; i < folderIds.size(); i++) {
			sb.append(table);
			sb.append(".");

			if (table.equals("DLFolder")) {
				sb.append("parentFolderId");
			}
			else {
				sb.append("folderId");
			}

			sb.append("= ? ");

			if ((i + 1) != folderIds.size()) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

}