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

package com.liferay.portal.repository.liferayrepository.util;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Chow
 */
public abstract class LiferayBase {

	public List<FileEntry> convertFileEntries(List<DLFileEntry> list) {
		List<FileEntry> list2 = new ArrayList<FileEntry>(list.size());

		for (DLFileEntry fileEntry : list) {
			list2.add(new LiferayFileEntry(fileEntry));
		}

		if (list instanceof UnmodifiableList) {
			return new UnmodifiableList<FileEntry>(list2);
		}
		else {
			return list2;
		}
	}

	public List<Object> convertFileEntriesAndFolders(List<Object> list) {
		List<Object> list2 = new ArrayList<Object>(list.size());

		for (Object object : list) {
			if (object instanceof DLFileEntry) {
				list2.add(new LiferayFileEntry((DLFileEntry)object));
			}
			else if (object instanceof DLFolder) {
				list2.add(new LiferayFolder((DLFolder)object));
			}
			else {
				list2.add(object);
			}
		}

		if (list instanceof UnmodifiableList) {
			return new UnmodifiableList<Object>(list2);
		}
		else {
			return list2;
		}
	}

	public List<FileVersion> convertFileVersions(List<DLFileVersion> list) {
		List<FileVersion> list2 = new ArrayList<FileVersion>(list.size());

		for (DLFileVersion fileVersion : list) {
			list2.add(new LiferayFileVersion(fileVersion));
		}

		if (list instanceof UnmodifiableList) {
			return new UnmodifiableList<FileVersion>(list2);
		}
		else {
			return list2;
		}
	}

	public List<Folder> convertFolders(List<DLFolder> list) {
		List<Folder> list2 = new ArrayList<Folder>(list.size());

		for (DLFolder folder : list) {
			list2.add(new LiferayFolder(folder));
		}

		if (list instanceof UnmodifiableList) {
			return new UnmodifiableList<Folder>(list2);
		}
		else {
			return list2;
		}
	}

}