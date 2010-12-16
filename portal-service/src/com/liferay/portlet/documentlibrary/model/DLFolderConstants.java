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

package com.liferay.portlet.documentlibrary.model;

/**
 * @author Samuel Kong
 */
public class DLFolderConstants {

	public static final long DEFAULT_PARENT_FOLDER_ID = 0;

	public static String getClassName() {
		return DLFolder.class.getName();
	}

	public static long getFolderId(long groupId, long folderId) {
		long repositoryId = getSearchRepositoryId(groupId, folderId);

		if (groupId != repositoryId) {
			return repositoryId;
		}
		else {
			return DEFAULT_PARENT_FOLDER_ID;
		}
	}

	public static long getSearchRepositoryId(long groupId, long folderId) {
		if (folderId == DEFAULT_PARENT_FOLDER_ID) {
			return groupId;
		}
		else {
			return folderId;
		}
	}

}