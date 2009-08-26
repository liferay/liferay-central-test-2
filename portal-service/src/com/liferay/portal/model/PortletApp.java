/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.kernel.xml.QName;

import java.io.Serializable;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * <a href="PortletApp.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface PortletApp extends Serializable {

	public String getServletContextName();

	public Set<String> getServletURLPatterns();

	public Set<String> getUserAttributes();

	public Map<String, String> getCustomUserAttributes();

	public void addPortletFilter(PortletFilter portletFilter);

	public PortletFilter getPortletFilter(String filterName);

	public Set<PortletFilter> getPortletFilters();

	public String getDefaultNamespace();

	public void setDefaultNamespace(String defaultNamespace);

	public void addEventDefinition(EventDefinition eventDefinition);

	public void addPublicRenderParameter(String identifier, QName qName);

	public void addPublicRenderParameter(
		PublicRenderParameter publicRenderParameter);

	public PublicRenderParameter getPublicRenderParameter(String identifier);

	public void addPortletURLListener(PortletURLListener portletURLListener);

	public PortletURLListener getPortletURLListener(String listenerClass);

	public Set<PortletURLListener> getPortletURLListeners();

	public Map<String, String[]> getContainerRuntimeOptions();

	public SpriteImage getSpriteImage(String fileName);

	public void setSpriteImages(String spriteFileName, Properties properties);

	public boolean isWARFile();

	public void setWARFile(boolean warFile);

}