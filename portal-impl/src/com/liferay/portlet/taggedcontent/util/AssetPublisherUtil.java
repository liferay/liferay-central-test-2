/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.taggedcontent.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class AssetPublisherUtil {
	
	public static void addAndStoreSelection(
			ActionRequest req, String className, long classPK, int assetOrder)
		throws Exception {

		String referringPortletResource = 
			ParamUtil.getString(req, "referringPortletResource");
		
		if (Validator.isNull(referringPortletResource)) {
			return;
		}
		
		TagsAsset asset = 
			TagsAssetLocalServiceUtil.getAsset(className, classPK);

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(
				req, referringPortletResource, true, true);

		addSelection(className, assetOrder, asset.getAssetId(), prefs);
		
		prefs.store();
	}

	public static void addSelection(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		String assetType = ParamUtil.getString(req, "assetType");
		int assetOrder = ParamUtil.getInteger(req, "assetOrder");
		long assetId = ParamUtil.getLong(req, "assetId");

		addSelection(assetType, assetOrder, assetId, prefs);
	}
	
	public static void addSelection(
			String assetType, int assetOrder, long assetId, 
			PortletPreferences prefs) 
		throws Exception {
		
		String[] manualEntries = prefs.getValues("manual-entries", new String[0]);

		String assetConfig = _assetConfiguration(
				assetType, assetId);

		if (assetOrder > -1) {
			manualEntries[assetOrder] = assetConfig;
		}
		else {			
			manualEntries = ArrayUtil.append(manualEntries, assetConfig);
		}

		prefs.setValues("manual-entries", manualEntries);
	}

	private static String _assetConfiguration(String assetType, long assetId) {
		String assetString = null;
		
		try {
			Document assetDoc = 
				DocumentFactory.getInstance().createDocument("UTF-8");

			Element asset = assetDoc.addElement("asset");
			
			asset.addElement("asset-type").addText(assetType);
			asset.addElement("asset-id").addText(String.valueOf(assetId));

			assetString = XMLFormatter.toString(assetDoc, "");
		}
		catch (IOException de) {
		}
		
		return assetString;
	}

}
