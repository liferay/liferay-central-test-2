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

import java.io.Serializable;

import java.util.Set;

import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

/**
 * <a href="LiferayPortletURL.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface LiferayPortletURL
	extends PortletURL, ResourceURL, Serializable {

	public void addParameterIncludedInPath(String name);

	public String getLifecycle();

	public String getParameter(String name);

	public String getPortletId();

	public String getResourceID();

	public boolean isAnchor();

	public boolean isCopyCurrentRenderParameters();

	public boolean isEncrypt();

	public boolean isEscapeXml();

	public boolean isParameterIncludedInPath(String name);

	public boolean isSecure();

	public void setLifecycle(String lifecycle);

	public void setParameter(String name, String value, boolean append);

	public void setParameter(String name, String[] values, boolean append);

	public Set<String> getParametersIncludedInPath();

	public void setAnchor(boolean anchor);

	public void setCopyCurrentRenderParameters(
		boolean copyCurrentRenderParameters);

	public void setDoAsUserId(long doAsUserId);

	public void setDoAsUserLanguageId(String doAsUserLanguageId);

	public void setEncrypt(boolean encrypt);

	public void setEscapeXml(boolean escapeXml);

	public void setPlid(long plid);

	public void setPortletId(String portletId);

}