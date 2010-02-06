/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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