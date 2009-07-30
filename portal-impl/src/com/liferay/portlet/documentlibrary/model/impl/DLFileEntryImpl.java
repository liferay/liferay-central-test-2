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
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

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

	public static String stripExtension(String name, String title) {
		String extension = FileUtil.getExtension(name);

		if (extension == null) {
			return title;
		}

		int pos = title.toLowerCase().lastIndexOf(
			StringPool.PERIOD + extension);

		if (pos > 0) {
			title = title.substring(0, pos);
		}

		return title;
	}

	public DLFileEntryImpl() {
	}

	public DLFolder getFolder() {
		DLFolder folder = null;

		try {
			folder = DLFolderLocalServiceUtil.getFolder(getFolderId());
		}
		catch (Exception e) {
			folder = new DLFolderImpl();

			_log.error(e);
		}

		return folder;
	}

	public String getTitleWithExtension() {
		StringBuilder sb = new StringBuilder();

		sb.append(getTitle());
		sb.append(StringPool.PERIOD);
		sb.append(FileUtil.getExtension(getName()));

		return sb.toString();
	}

	public String getExtraSettings() {
		if (_extraSettingsProperties == null) {
			return super.getExtraSettings();
		}
		else {
			return PropertiesUtil.toString(_extraSettingsProperties);
		}
	}

	public void setExtraSettings(String extraSettings) {
		_extraSettingsProperties = null;

		super.setExtraSettings(extraSettings);
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

	public void setExtraSettingsProperties(Properties extraSettingsProperties) {
		_extraSettingsProperties = extraSettingsProperties;

		super.setExtraSettings(
			PropertiesUtil.toString(_extraSettingsProperties));
	}

	public String getLuceneProperties() {
		StringBuilder sb = new StringBuilder();

		sb.append(getTitle());
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

	private static Log _log = LogFactoryUtil.getLog(DLFileEntryImpl.class);

	private Properties _extraSettingsProperties = null;

}