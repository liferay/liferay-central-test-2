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
 * <p>
 * This contains several utility methods for the purpose of determining
 * folderIds and dataRepositoryIds as used by back-end data systems like search
 * and Document Library hooks.  These repository IDs should not be confused with
 * the repositoryId used by in
 * {@link com.liferay.portal.kernel.repository.RepositoryFactory}.
 * </p>
 *
 * @author Samuel Kong
 * @author Alexander Chow
 */
public class DLFolderConstants {

	public static final long DEFAULT_PARENT_FOLDER_ID = 0;

	public static String getClassName() {
		return DLFolder.class.getName();
	}

	/**
	 * Determine the folderId when no knowledge of it currently exists.
	 */
	public static long getFolderId(long groupId, long dataRepositoryId) {
		if (groupId != dataRepositoryId) {
			return dataRepositoryId;
		}
		else {
			return DEFAULT_PARENT_FOLDER_ID;
		}
	}

	/**
	 * Determine the data repository ID from the groupId and folderId.  The
	 * folderId may be zero, implying that it is the root folder for the given
	 * group.
	 */
	public static long getDataRepositoryId(long groupId, long folderId) {
		if (folderId != DEFAULT_PARENT_FOLDER_ID) {
			return folderId;
		}
		else {
			return groupId;
		}
	}

}