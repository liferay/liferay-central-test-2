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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.PortletURL;

/**
 * <a href="AssetRendererFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public interface AssetRendererFactory {

	public AssetRenderer getAssetRenderer(long classPK)
		throws Exception;

	public AssetRenderer getAssetRenderer(long groupId, String urlTitle)
		throws Exception;

	public String getClassName();

	public long getClassNameId();

	public String getPortletId();

	public String getType();

	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public boolean isSelectable();

	public void setClassNameId(long classNameId);

	public void setPortletId(String portletId);

}