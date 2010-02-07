/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
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
		int folderIdsSize = folderIds.size();

		if (folderIds.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(folderIdsSize * 5 - 1);

		for (int i = 0; i < folderIdsSize; i++) {
			sb.append(table);
			sb.append(".");

			if (table.equals("DLFolder")) {
				sb.append("parentFolderId");
			}
			else {
				sb.append("folderId");
			}

			sb.append("= ? ");

			if ((i + 1) != folderIdsSize) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

}