/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
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
	extends BasePersistenceImpl implements DLFolderFinder {

	public static String COUNT_FE_FS_BY_FOLDER_IDS =
		DLFolderFinder.class.getName() + ".countFE_FS_ByFolderIds";

	public static String COUNT_F_FE_FS_BY_FOLDER_IDS =
		DLFolderFinder.class.getName() + ".countF_FE_FS_ByFolderIds";

	public static String FIND_FE_FS_BY_FOLDER_IDS =
		DLFolderFinder.class.getName() + ".findFE_FS_ByFolderIds";

	public static String FIND_F_FE_FS_BY_FOLDER_IDS =
		DLFolderFinder.class.getName() + ".findF_FE_FS_ByFolderIds";

	public int countFE_FS_ByFolderIds(List<Long> folderIds)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_FE_FS_BY_FOLDER_IDS);

			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileEntry"));
			sql = StringUtil.replace(
				sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileShortcut"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

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

	public int countF_FE_FS_ByFolderIds(List<Long> folderIds)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_F_FE_FS_BY_FOLDER_IDS);

			sql = StringUtil.replace(
				sql, "[$FOLDER_PARENT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFolder"));
			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileEntry"));
			sql = StringUtil.replace(
				sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileShortcut"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

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

	public List<Object> findFE_FS_ByFolderIds(
			List<Long> folderIds, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_FE_FS_BY_FOLDER_IDS);

			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileEntry"));
			sql = StringUtil.replace(
				sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileShortcut"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("modelFolderId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("title", Type.STRING);
			q.addScalar("fileShortcutId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

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
					obj = DLFileEntryUtil.findByF_N(folderId, name);
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

	public List<Object> findF_FE_FS_ByFolderIds(
			List<Long> folderIds, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_F_FE_FS_BY_FOLDER_IDS);

			sql = StringUtil.replace(
				sql, "[$FOLDER_PARENT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFolder"));
			sql = StringUtil.replace(
				sql, "[$FILE_ENTRY_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileEntry"));
			sql = StringUtil.replace(
				sql, "[$FILE_SHORTCUT_FOLDER_ID$]",
				getFolderIds(folderIds, "DLFileShortcut"));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("modelFolderId", Type.LONG);
			q.addScalar("name", Type.STRING);
			q.addScalar("title", Type.STRING);
			q.addScalar("fileShortcutId", Type.LONG);
			q.addScalar("modelFolder", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

			for (Long folderId : folderIds) {
				qPos.add(folderId);
			}

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
					obj = DLFileEntryUtil.findByF_N(folderId, name);
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
		StringBuilder sb = new StringBuilder();

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