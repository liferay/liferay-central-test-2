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

package com.liferay.search.web.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.search.web.constants.SearchPortletKeys;
import com.liferay.search.web.util.SearchFacet;
import com.liferay.search.web.util.SearchFacetTracker;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SearchPortletKeys.SEARCH
	},
	service = ConfigurationAction.class
)
public class SearchConfigurationAction extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		JSONArray newFacetsJSONArray = JSONFactoryUtil.createJSONArray();

		for (SearchFacet searchFacet : _searchFacetTracker.getSearchFacets()) {
			boolean displaySearchFacet = GetterUtil.getBoolean(
				getParameter(actionRequest, searchFacet.getClassName()));

			if (displaySearchFacet) {
				newFacetsJSONArray.put(StringPool.BLANK);
			}
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("facets", newFacetsJSONArray);

		setPreference(
			actionRequest, "searchConfiguration", jsonObject.toString());

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Reference(unbind = "-")
	protected void setSearchFacetTracker(
		SearchFacetTracker searchFacetTracker) {

		_searchFacetTracker = searchFacetTracker;
	}

	protected void unsetSearchFacetTracker() {
		_searchFacetTracker = null;
	}

	private SearchFacetTracker _searchFacetTracker;

}