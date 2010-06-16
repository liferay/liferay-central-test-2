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

package com.liferay.portal.kernel.portlet;

import java.util.Map;

/**
 * <a href="FriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Myunghun Kim
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public interface FriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL);

	public String getMapping();

	public String getPortletId();

	public Router getRouter();

	public boolean isCheckMappingWithPrefix();

	public boolean isPortletInstanceable();

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> parameterMap,
		Map<String, Object> requestContext);

	public void setMapping(String mapping);

	public void setPortletId(String portletId);

	public void setPortletInstanceable(boolean portletInstanceable);

	public void setRouter(Router router);

}