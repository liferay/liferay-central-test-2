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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SafeProperties;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.IOException;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * <a href="DLFileEntryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFileEntryImpl
	extends DLFileEntryModelImpl implements DLFileEntry {

	public static long getRepositoryId(long groupId, long folderId) {
		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return groupId;
		}
		else {
			return folderId;
		}
	}

	public DLFileEntryImpl() {
	}

	public String getExtraSettings() {
		if (_extraSettingsProperties == null) {
			return super.getExtraSettings();
		}
		else {
			return PropertiesUtil.toString(_extraSettingsProperties);
		}
	}

	public Properties getExtraSettingsProperties() {
		if (_extraSettingsProperties == null) {
			_extraSettingsProperties = new SafeProperties();

			try {
				PropertiesUtil.load(
					_extraSettingsProperties, super.getExtraSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}

		return _extraSettingsProperties;
	}

	public DLFolder getFolder() {
		DLFolder folder = null;

		if (getFolderId() > 0) {
			try {
				folder = DLFolderLocalServiceUtil.getFolder(getFolderId());
			}
			catch (Exception e) {
				folder = new DLFolderImpl();

				_log.error(e);
			}
		}
		else {
			folder = new DLFolderImpl();
		}

		return folder;
	}

	public Lock getLock() {
		try {
			String lockId =
				DLUtil.getLockId(getGroupId(), getFolderId(), getName());

			return LockLocalServiceUtil.getLock(
				DLFileEntry.class.getName(), lockId);
		}
		catch (Exception e) {
		}

		return null;
	}

	public String getLuceneProperties() {
		StringBuilder sb = new StringBuilder();

		sb.append(FileUtil.stripExtension(getTitle()));
		sb.append(StringPool.SPACE);
		sb.append(getDescription());
		sb.append(StringPool.SPACE);

		Properties extraSettingsProps = getExtraSettingsProperties();

		Iterator<Map.Entry<Object, Object>> itr =
			extraSettingsProps.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Object, Object> entry = itr.next();

			String value = GetterUtil.getString((String)entry.getValue());

			sb.append(value);
		}

		return sb.toString();
	}

	public long getRepositoryId() {
		return getRepositoryId(getGroupId(), getFolderId());
	}

	public boolean hasLock(long userId) {
		try {
			String lockId =
				DLUtil.getLockId(getGroupId(), getFolderId(), getName());

			return LockLocalServiceUtil.hasLock(
				userId, DLFileEntry.class.getName(), lockId);
		}
		catch (Exception e) {
		}

		return false;
	}

	public boolean isLocked() {
		try {
			String lockId =
				DLUtil.getLockId(getGroupId(), getFolderId(), getName());

			return LockLocalServiceUtil.isLocked(
				DLFileEntry.class.getName(), lockId);
		}
		catch (Exception e) {
		}

		return false;
	}

	public void setExtraSettings(String extraSettings) {
		_extraSettingsProperties = null;

		super.setExtraSettings(extraSettings);
	}

	public void setExtraSettingsProperties(Properties extraSettingsProperties) {
		_extraSettingsProperties = extraSettingsProperties;

		super.setExtraSettings(
			PropertiesUtil.toString(_extraSettingsProperties));
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileEntryImpl.class);

	private Properties _extraSettingsProperties = null;

}