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
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;

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
 *
 */
public class AssetPublisherUtil {

	public static final String TYPE_BLOG = "blog";

	public static final String TYPE_BOOKMARK = "bookmark";

	public static final String TYPE_CONTENT = "content";

	public static final String TYPE_DOCUMENT = "document";

	public static final String TYPE_IMAGE = "image";

	public static final String TYPE_THREAD = "thread";

	public static final String TYPE_WIKI = "wiki";

	public static void addAndStoreSelection(
			ActionRequest actionRequest, String className, long classPK,
			int assetOrder)
		throws Exception {

		String referringPortletResource =
			ParamUtil.getString(actionRequest, "referringPortletResource");

		if (Validator.isNull(referringPortletResource)) {
			return;
		}

		TagsAsset asset = TagsAssetLocalServiceUtil.getAsset(
			className, classPK);

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, referringPortletResource);

		addSelection(className, asset.getAssetId(), assetOrder, preferences);

		preferences.store();
	}

	public static void addSelection(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String assetType = ParamUtil.getString(actionRequest, "assetType");
		long assetId = ParamUtil.getLong(actionRequest, "assetId");
		int assetOrder = ParamUtil.getInteger(actionRequest, "assetOrder");

		addSelection(assetType, assetId, assetOrder, preferences);
	}

	public static void addSelection(
			String assetType, long assetId, int assetOrder,
			PortletPreferences preferences)
		throws Exception {

		String[] manualEntries = preferences.getValues(
			"manual-entries", new String[0]);

		String assetConfig = _assetConfiguration(assetType, assetId);

		if (assetOrder > -1) {
			manualEntries[assetOrder] = assetConfig;
		}
		else {
			manualEntries = ArrayUtil.append(manualEntries, assetConfig);
		}

		preferences.setValues("manual-entries", manualEntries);
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
			List<Long> assetIds, PortletPreferences preferences)
		throws Exception {

		if (assetIds.size() == 0) {
			return;
		}

		String[] manualEntries = preferences.getValues(
			"manual-entries", new String[0]);

		List<String> manualEntriesList = ListUtil.fromArray(manualEntries);

		Iterator<String> itr = manualEntriesList.iterator();

		while (itr.hasNext()) {
			String assetEntry = itr.next();

			Document doc = SAXReaderUtil.read(assetEntry);

			Element root = doc.getRootElement();

			long assetId = GetterUtil.getLong(
				root.element("asset-id").getText());

			if (assetIds.contains(assetId)) {
				itr.remove();
			}
		}

		preferences.setValues(
			"manual-entries",
			manualEntriesList.toArray(new String[manualEntriesList.size()]));

		preferences.store();
	}

	private static String _assetConfiguration(String assetType, long assetId) {
		String xml = null;

		try {
			Document doc = SAXReaderUtil.createDocument(StringPool.UTF8);

			Element asset = doc.addElement("asset");

			asset.addElement("asset-type").addText(assetType);
			asset.addElement("asset-id").addText(String.valueOf(assetId));

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