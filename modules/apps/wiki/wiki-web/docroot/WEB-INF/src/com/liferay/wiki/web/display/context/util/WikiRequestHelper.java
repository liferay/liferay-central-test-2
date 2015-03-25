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

package com.liferay.wiki.web.display.context.util;

import com.liferay.portal.kernel.display.context.util.BaseStrutsRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.settings.GroupServiceSettingsProvider;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsProvider;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.wiki.constants.WikiWebKeys;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.settings.WikiGroupServiceSettings;
import com.liferay.wiki.web.settings.WikiPortletInstanceSettings;
import com.liferay.wiki.web.settings.WikiWebSettingsProvider;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class WikiRequestHelper extends BaseStrutsRequestHelper {

	public WikiRequestHelper(HttpServletRequest request) {
		super(request);
	}

	public long getCategoryId() {
		if (_categoryId == null) {
			_categoryId = ParamUtil.getLong(getRequest(), "categoryId", 0);
		}

		return _categoryId;
	}

	public WikiGroupServiceSettings getWikiGroupServiceSettings() {
		try {
			if (_wikiGroupServiceSettings == null) {
				String portletId = getPortletId();

				WikiWebSettingsProvider wikiWebSettingsProvider =
					WikiWebSettingsProvider.getWikiWebSettingsProvider();

				GroupServiceSettingsProvider<WikiGroupServiceSettings>
					groupServiceSettingsProvider =
						wikiWebSettingsProvider.
							getGroupServiceSettingsProvider();

				if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
					_wikiGroupServiceSettings =
						groupServiceSettingsProvider.getGroupServiceSettings(
							getSiteGroupId(), getRequest().getParameterMap());
				}
				else {
					_wikiGroupServiceSettings =
						groupServiceSettingsProvider.getGroupServiceSettings(
							getSiteGroupId());
				}
			}

			return _wikiGroupServiceSettings;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public WikiPage getWikiPage() {
		if (_wikiPage == null) {
			HttpServletRequest request = getRequest();

			_wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);
		}

		return _wikiPage;
	}

	public WikiPortletInstanceSettings getWikiPortletInstanceSettings() {
		try {
			if (_wikiPortletInstanceSettings == null) {
				String portletId = getPortletId();

				WikiWebSettingsProvider wikiWebSettingsProvider =
					WikiWebSettingsProvider.getWikiWebSettingsProvider();

				PortletInstanceSettingsProvider<WikiPortletInstanceSettings>
					portletInstanceSettingsProvider =
						wikiWebSettingsProvider.
							getPortletInstanceSettingsProvider();

				if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
					_wikiPortletInstanceSettings =
						portletInstanceSettingsProvider.
							getPortletInstanceSettings(
								getLayout(), getResourcePortletId(),
								getRequest().getParameterMap());
				}
				else {
					_wikiPortletInstanceSettings =
						portletInstanceSettingsProvider.
							getPortletInstanceSettings(
								getLayout(), getPortletId());
				}
			}

			return _wikiPortletInstanceSettings;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private Long _categoryId;
	private WikiGroupServiceSettings _wikiGroupServiceSettings;
	private WikiPage _wikiPage;
	private WikiPortletInstanceSettings _wikiPortletInstanceSettings;

}