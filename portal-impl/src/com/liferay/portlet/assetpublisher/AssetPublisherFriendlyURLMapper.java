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

package com.liferay.portlet.assetpublisher;

import com.liferay.portal.kernel.portlet.BaseFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * <a href="AssetPublisherFriendlyURLMapper.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Julio Camarero
 */
public class AssetPublisherFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURLPath = null;

		String strutsAction = GetterUtil.getString(
			portletURL.getParameter("struts_action"));

		WindowState windowState = portletURL.getWindowState();

		if ((strutsAction.equals("/asset_publisher/view_content"))  &&
			((windowState == null) ||
			 (!windowState.equals(LiferayWindowState.EXCLUSIVE) &&
			  !windowState.equals(LiferayWindowState.POP_UP)))) {

			String portletId = portletURL.getPortletId();
			String assetEntryId = portletURL.getParameter("assetEntryId");
			String type = GetterUtil.getString(
				portletURL.getParameter("type"), "content");
			String urlTitle = portletURL.getParameter("urlTitle");

			if (Validator.isNotNull(portletId) &&
				Validator.isNotNull(assetEntryId)) {

				if (portletId.equals(_PORTLET_DEFAULT_INSTANCE)) {
					portletId = _PORTLET_ID;
				}

				int pos = portletId.indexOf("_INSTANCE_");

				String instanceId = null;

				if (pos > 0) {
					instanceId = portletId.substring(pos + 10);
				}
				else {
					instanceId = portletId;
				}

				friendlyURLPath =
					"/asset_publisher/" + instanceId + StringPool.SLASH + type +
						StringPool.SLASH;

				if (Validator.isNotNull(urlTitle)) {
					friendlyURLPath += urlTitle;

					portletURL.addParameterIncludedInPath("urlTitle");
				}
				else {
					friendlyURLPath += "id/" + assetEntryId;
				}

				portletURL.addParameterIncludedInPath("type");
				portletURL.addParameterIncludedInPath("assetEntryId");
			}
		}

		if (Validator.isNotNull(friendlyURLPath)) {
			portletURL.addParameterIncludedInPath("p_p_id");

			portletURL.addParameterIncludedInPath("struts_action");
		}

		return friendlyURLPath;
	}

	public String getMapping() {
		return _MAPPING;
	}

	public String getPortletId() {
		return _PORTLET_ID;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> params) {

		int x = friendlyURLPath.indexOf(StringPool.SLASH, 1);

		String[] urlFragments = StringUtil.split(
			friendlyURLPath.substring(x + 1), StringPool.SLASH);

		if (urlFragments.length > 2) {
			String instanceId = urlFragments[0];
			String type = urlFragments[1];
			String assetEntryId = null;
			String urlTitle = null;

			if (urlFragments.length > 3) {
				assetEntryId = urlFragments[3];
			}
			else {
				urlTitle = urlFragments[2];
			}

			String portletId = _PORTLET_ID + "_INSTANCE_" + instanceId;

		   if (Validator.equals(portletId, _PORTLET_ID)) {
				portletId = _PORTLET_DEFAULT_INSTANCE;

				params.put("p_p_id", new String[] {portletId});
				params.put(
					"p_p_state",
					new String[] {WindowState.MAXIMIZED.toString()});
			}
			else {
				params.put("p_p_id", new String[] {portletId});
				params.put(
					"p_p_state", new String[] {WindowState.NORMAL.toString()});
			}

			params.put("p_p_lifecycle", new String[] {"0"});
			params.put("p_p_mode", new String[] {PortletMode.VIEW.toString()});

			String namespace =
				StringPool.UNDERLINE + portletId + StringPool.UNDERLINE;

			params.put(
				namespace + "struts_action",
				new String[] {"/asset_publisher/view_content"});
			params.put(namespace + "type", new String[] {type});

			if (Validator.isNotNull(assetEntryId)) {
				params.put(
					namespace + "assetEntryId", new String[] {assetEntryId});
			}
			else {
				params.put(namespace + "urlTitle", new String[] {urlTitle});
			}
		}
	}

	private static final String _MAPPING = "asset_publisher";

	private static final String _PORTLET_DEFAULT_INSTANCE =
		PortletKeys.ASSET_PUBLISHER + "_INSTANCE_0000";

	private static final String _PORTLET_ID = PortletKeys.ASSET_PUBLISHER;

}