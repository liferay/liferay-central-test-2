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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

/**
 * <a href="DLFileVersionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class DLFileVersionImpl
	extends DLFileVersionModelImpl implements DLFileVersion {

	public DLFileVersionImpl() {
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
				getCompanyId(), DLFileEntry.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public DLFileEntry getFileEntry() {
		try {
			return DLFileEntryLocalServiceUtil.getFileEntry(
				getGroupId(), getFolderId(), getName());
		}
		catch (Exception e) {
			_log.error(e);
		}

		return null;
	}

	public String getIcon() {
		return DLUtil.getFileIcon(getExtension());
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileVersionImpl.class);

	private ExpandoBridge _expandoBridge;

}