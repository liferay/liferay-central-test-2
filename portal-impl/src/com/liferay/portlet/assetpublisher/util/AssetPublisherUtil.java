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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="AssetPublisherUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class AssetPublisherUtil {

	public static void addAndStoreSelection(
			ActionRequest actionRequest, String className, long classPK,
			int assetEntryOrder)
		throws Exception {

		String referringPortletResource =
			ParamUtil.getString(actionRequest, "referringPortletResource");

		if (Validator.isNull(referringPortletResource)) {
			return;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			className, classPK);

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, referringPortletResource);

		addSelection(
			className, assetEntry.getEntryId(), assetEntryOrder, preferences);

		preferences.store();
	}

	public static void addSelection(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String assetEntryType = ParamUtil.getString(
			actionRequest, "assetEntryType");
		long assetEntryId = ParamUtil.getLong(actionRequest, "assetEntryId");
		int assetEntryOrder = ParamUtil.getInteger(
			actionRequest, "assetEntryOrder");

		addSelection(
			assetEntryType, assetEntryId, assetEntryOrder, preferences);
	}

	public static void addSelection(
			String assetEntryType, long assetEntryId, int assetEntryOrder,
			PortletPreferences preferences)
		throws Exception {

		String[] assetEntryXmls = preferences.getValues(
			"asset-entry-xml", new String[0]);

		String assetEntryXml = _getAssetEntryXml(assetEntryType, assetEntryId);

		if (assetEntryOrder > -1) {
			assetEntryXmls[assetEntryOrder] = assetEntryXml;
		}
		else {
			assetEntryXmls = ArrayUtil.append(assetEntryXmls, assetEntryXml);
		}

		preferences.setValues("asset-entry-xml", assetEntryXmls);
	}

	public static void addRecentFolderId(
		PortletRequest portletRequest, String className, long classPK) {

		_getRecentFolderIds(portletRequest).put(className, classPK);
	}

	public static long getRecentFolderId(
		PortletRequest portletRequest, String className) {

		Long classPK = _getRecentFolderIds(portletRequest).get(className);

		if (classPK == null) {
			return 0;
		}
		else {
			return classPK.longValue();
		}
	}

	public static void removeAndStoreSelection(
			List<Long> assetEntryIds, PortletPreferences preferences)
		throws Exception {

		if (assetEntryIds.size() == 0) {
			return;
		}

		String[] assetEntryXmls = preferences.getValues(
			"asset-entry-xml", new String[0]);

		List<String> assetEntryXmlsList = ListUtil.fromArray(assetEntryXmls);

		Iterator<String> itr = assetEntryXmlsList.iterator();

		while (itr.hasNext()) {
			String assetEntryXml = itr.next();

			Document doc = SAXReaderUtil.read(assetEntryXml);

			Element root = doc.getRootElement();

			long assetEntryId = GetterUtil.getLong(
				root.element("asset-entry-id").getText());

			if (assetEntryIds.contains(assetEntryId)) {
				itr.remove();
			}
		}

		preferences.setValues(
			"asset-entry-xml",
			assetEntryXmlsList.toArray(new String[assetEntryXmlsList.size()]));

		preferences.store();
	}

	private static String _getAssetEntryXml(
		String assetEntryType, long assetEntryId) {

		String xml = null;

		try {
			Document doc = SAXReaderUtil.createDocument(StringPool.UTF8);

			Element assetEntryEl = doc.addElement("asset-entry");

			assetEntryEl.addElement("asset-entry-type").addText(assetEntryType);
			assetEntryEl.addElement("asset-entry-id").addText(
				String.valueOf(assetEntryId));

			xml = doc.formattedString(StringPool.BLANK);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe);
			}
		}

		return xml;
	}

	private static Map<String, Long> _getRecentFolderIds(
		PortletRequest portletRequest) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String key =
			AssetPublisherUtil.class + "_" + themeDisplay.getScopeGroupId();

		Map<String, Long> recentFolderIds =
			(Map<String, Long>)session.getAttribute(key);

		if (recentFolderIds == null) {
			recentFolderIds = new HashMap<String, Long>();
		}

		session.setAttribute(key, recentFolderIds);

		return recentFolderIds;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetPublisherUtil.class);

}