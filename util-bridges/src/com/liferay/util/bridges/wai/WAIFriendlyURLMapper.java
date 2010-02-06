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

package com.liferay.util.bridges.wai;

import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalUtil;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * <a href="WAIFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class WAIFriendlyURLMapper implements FriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String portletId = portletURL.getPortletId();

		String prefix = portletId;

		int pos = portletId.indexOf(PortletConstants.WAR_SEPARATOR);

		if (pos != -1) {
			prefix = portletId.substring(0, pos);
		}

		String appUrl = GetterUtil.getString(portletURL.getParameter("appURL"));

		portletURL.addParameterIncludedInPath("p_p_id");

		return StringPool.SLASH + _MAPPING + StringPool.SLASH + prefix +
			StringPool.SLASH + appUrl;
	}

	public String getMapping() {
		return _MAPPING;
	}

	public boolean isCheckMappingWithPrefix() {
		return _CHECK_MAPPING_WITH_PREFIX;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> params) {

		int x = friendlyURLPath.indexOf(_MAPPING);
		int y = friendlyURLPath.indexOf("/", x + _MAPPING.length() + 1);

		if (x == -1) {
			return;
		}

		String prefix = friendlyURLPath.substring(x + _MAPPING.length() + 1, y);

		String portletId = prefix + PortletConstants.WAR_SEPARATOR + prefix;

		params.put("p_p_id", new String[] {portletId});
		params.put("p_p_lifecycle", new String[] {"0"});

		if (hasBinaryExtension(friendlyURLPath)) {
			params.put(
				"p_p_state",
				new String[] {LiferayWindowState.EXCLUSIVE.toString()});
		}
		else {
			params.put(
				"p_p_state", new String[] {WindowState.MAXIMIZED.toString()});
		}

		params.put("p_p_mode", new String[] {PortletMode.VIEW.toString()});

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String path = friendlyURLPath.substring(y);

		params.put(namespace + "appURL", new String[] {path});
	}

	protected boolean hasBinaryExtension(String friendlyURLPath) {
		for (int i = 0; i < _BINARY_EXTENSIONS.length; i++) {
			String binaryExtension = _BINARY_EXTENSIONS[i];

			if (friendlyURLPath.endsWith(binaryExtension)) {
				return true;
			}
		}

		return false;
	}

	private static final boolean _CHECK_MAPPING_WITH_PREFIX = true;

	private static final String _MAPPING = "waiapp";

	private static final String[] _BINARY_EXTENSIONS = new String[] {
		".css", ".doc", ".gif", ".jpeg", ".jpg", ".js", ".odp", ".png", ".ppt",
		".tgz", ".xls", ".zip",
	};

}