/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.adaptive.media.web.internal.search;

import com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class ImageConfigurationEntriesChecker extends EmptyOnClickRowChecker {

	public ImageConfigurationEntriesChecker(
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletResponse);

		_liferayPortletResponse = liferayPortletResponse;
	}

	@Override
	public String getAllRowsCheckBox() {
		return null;
	}

	@Override
	public String getAllRowsCheckBox(HttpServletRequest request) {
		return null;
	}

	@Override
	public String getRowCheckBox(
		HttpServletRequest request, boolean checked, boolean disabled,
		String primaryKey) {

		String name =
			ImageAdaptiveMediaConfigurationEntry.class.getSimpleName();

		String checkBoxRowIds = getEntryRowIds();

		return getRowCheckBox(
			request, checked, disabled,
			_liferayPortletResponse.getNamespace() + RowChecker.ROW_IDS + name,
			primaryKey, checkBoxRowIds, "'#" + getAllRowIds() + "'",
			StringPool.BLANK);
	}

	protected String getEntryRowIds() {
		StringBundler sb = new StringBundler(5);

		sb.append("['");
		sb.append(_liferayPortletResponse.getNamespace());
		sb.append(RowChecker.ROW_IDS);
		sb.append(ImageAdaptiveMediaConfigurationEntry.class.getSimpleName());
		sb.append("']");

		return sb.toString();
	}

	private final LiferayPortletResponse _liferayPortletResponse;

}