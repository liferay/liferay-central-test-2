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

package com.liferay.portlet.assetpublisher.provider;

import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.provider.AddPortletProvider;
import com.liferay.portlet.asset.provider.PortletProvider;
import com.liferay.portlet.asset.provider.ViewPortletProvider;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Eudaldo Alonso
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=" + PortletProvider.DEFAULT
	}
)
public class AssetPublisherPortletProvider
	implements AddPortletProvider, ViewPortletProvider {

	@Override
	public String getPortletId() {
		return PortletKeys.ASSET_PUBLISHER;
	}

	@Override
	public void setPortletPreferences(
			PortletPreferences portletPreferences, String portletId,
			String className, long classPK, ThemeDisplay themeDisplay)
		throws Exception {

		portletPreferences.setValue("displayStyle", "full-content");
		portletPreferences.setValue(
			"emailAssetEntryAddedEnabled", Boolean.FALSE.toString());
		portletPreferences.setValue("selectionStyle", "manual");
		portletPreferences.setValue(
			"showAddContentButton", Boolean.FALSE.toString());

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			className, classPK);

		AssetPublisherUtil.addSelection(
			themeDisplay, portletPreferences, portletId,
			assetEntry.getEntryId(), -1, assetEntry.getClassName());
	}

}