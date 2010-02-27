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

package com.liferay.portlet.wiki.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import javax.portlet.PortletURL;

/**
 * <a href="WikiPageAssetRendererFactory.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Julio Camarero
 */
public class WikiPageAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String CLASS_NAME =	WikiPage.class.getName();

	public static final String TYPE = "wiki";

	public AssetRenderer getAssetRenderer(long classPK) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(classPK);

		return new WikiPageAssetRenderer(page);
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	public String getType() {
		return TYPE;
	}

	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		return null;
	}

}