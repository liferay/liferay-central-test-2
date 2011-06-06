/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.search;

import com.liferay.portal.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFileShortcutPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

/**
 * @author Sergio González
 */
public class EntriesChecker extends RowChecker {

	public EntriesChecker(
		LiferayPortletResponse liferayPortletResponse,
		PermissionChecker permissionChecker, String namespace) {

		super(liferayPortletResponse);

		_namespace = namespace;
		_permissionChecker = permissionChecker;
	}

	public String getAllRowsCheckBox() {
		return null;
	}

	public String getRowCheckBox(boolean checked, String primaryKey) {
		FileEntry fileEntry = null;
		DLFileShortcut fileShortcut = null;
		Folder folder = null;

		long entryId = GetterUtil.getLong(primaryKey);

		try {
			fileEntry =	DLAppServiceUtil.getFileEntry(entryId);
		}
		catch (Exception e1) {
			if (e1 instanceof NoSuchFileEntryException ||
				e1 instanceof NoSuchRepositoryEntryException) {

				try {
					fileShortcut = DLAppServiceUtil.getFileShortcut(entryId);
				}
				catch (Exception e2) {
					if (e2 instanceof NoSuchFileShortcutException) {
						try {
							folder = DLAppServiceUtil.getFolder(entryId);
						}
						catch (Exception e3) {
							return StringPool.BLANK;
						}
					}
					else {
						return StringPool.BLANK;
					}
				}
			}
			else {
				return StringPool.BLANK;
			}
		}

		boolean showSelect = false;

		if (fileEntry != null) {
			try {
				showSelect =
					DLFileEntryPermission.contains(
						_permissionChecker, fileEntry, ActionKeys.UPDATE) ||
					DLFileEntryPermission.contains(
						_permissionChecker, fileEntry, ActionKeys.DELETE);
			}
			catch (Exception e) {
			}
		}
		else if (fileShortcut != null) {
			try {
				showSelect = DLFileShortcutPermission.contains(
					_permissionChecker, fileShortcut, ActionKeys.DELETE);
			}
			catch (Exception e) {
			}
		}
		else if (folder != null) {
			try {
				showSelect = DLFolderPermission.contains(
					_permissionChecker, folder, ActionKeys.DELETE);
			}
			catch (Exception e) {
			}
		}

		StringBuilder sb = new StringBuilder();

		if (showSelect) {
			sb.append("<input ");

			if (checked) {
				sb.append("checked ");
			}

			sb.append("name=\"");
			sb.append(getRowIds());
			sb.append("\" type=\"checkbox\" value=\"");
			sb.append(primaryKey);
			sb.append("\" ");

			if (Validator.isNotNull(getAllRowIds())) {
				sb.append("onClick=\"Liferay.Util.checkAllBox(");
				sb.append("AUI().one(this).ancestor('");
				sb.append("table.taglib-search-iterator'), '");
				sb.append(getRowIds());
				sb.append("', ");
				sb.append(getAllRowIds());
				sb.append("Checkbox); ");
				sb.append(_namespace);
				sb.append("toggleActionsButton();\"");
			}

			sb.append(">");
		}

		return sb.toString();
	}

	private String _namespace;
	private PermissionChecker _permissionChecker;

}