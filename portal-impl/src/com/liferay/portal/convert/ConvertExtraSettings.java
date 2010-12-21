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

package com.liferay.portal.convert;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.documentlibrary.service.DLRepositoryLocalServiceUtil;

/**
 * @author Alexander Chow
 */
public class ConvertExtraSettings extends ConvertProcess {

	public String getDescription() {
		return "convert-extra-settings-from-document-library-files";
	}

	public String getPath() {
		return "/admin_server/edit_extra_settings";
	}

	public boolean isEnabled() {
		try {
			return DLRepositoryLocalServiceUtil.hasExtraSettings();
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	protected void doConvert() throws Exception {
	}

	private static Log _log = LogFactoryUtil.getLog(
		ConvertExtraSettings.class);

}