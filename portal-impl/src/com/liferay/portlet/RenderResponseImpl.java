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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayRenderResponse;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Collection;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * <a href="RenderResponseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class RenderResponseImpl
	extends MimeResponseImpl implements LiferayRenderResponse {

	public String getLifecycle() {
		return PortletRequest.RENDER_PHASE;
	}

	public String getResourceName() {
		return _resourceName;
	}

	public String getTitle() {
		return _title;
	}

	public Boolean getUseDefaultTemplate() {
		return _useDefaultTemplate;
	}

	public void setResourceName(String resourceName) {
		_resourceName = resourceName;
	}

	public void setNextPossiblePortletModes(
		Collection<PortletMode> portletModes) {
	}

	public void setTitle(String title) {
		_title = title;

		// See LEP-2188

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_portletRequestImpl.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setTitle(_title);
	}

	public void setUseDefaultTemplate(Boolean useDefaultTemplate) {
		_useDefaultTemplate = useDefaultTemplate;
	}

	protected void init(
		PortletRequestImpl portletRequestImpl, HttpServletResponse response,
		String portletName, long companyId, long plid) {

		super.init(portletRequestImpl, response, portletName, companyId, plid);

		_portletRequestImpl = portletRequestImpl;
	}

	private PortletRequestImpl _portletRequestImpl;
	private String _title;
 	private Boolean _useDefaultTemplate;
	private String _resourceName;

}