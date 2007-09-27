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

package com.liferay.portlet.taggedcontent.action;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;
import java.util.Arrays;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

/**
 * <a href="ConfigurationActionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ConfigurationActionImpl implements ConfigurationAction {

	public void processAction(
			PortletConfig config, ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		String portletResource = ParamUtil.getString(
				req, "portletResource");

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(
				req, portletResource, true, true);

		String selectionStyle = prefs.getValue("selection-style", "dynamic");
		
		if (cmd.equals("selection-style")) {
			setSelectionStyle(req, prefs);
		}
		else if (cmd.equals("add-selection")) {
			addSelection(req, prefs);
		}
		else if (cmd.equals("move-selection-down")) {
			moveSelectionDown(req, prefs);
		}
		else if (cmd.equals("move-selection-up")) {
			moveSelectionUp(req, prefs);
		}
		else if (cmd.equals("remove-selection")) {
			removeSelection(req, prefs);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			if (selectionStyle.equals("dynamic")) {
				updateDynamicSettings(req, prefs);
			}
			else if (selectionStyle.equals("manual")) {
				updateManualSettings(req, prefs);
			}
		}

		if (SessionErrors.isEmpty(req)) {
			prefs.store();

			SessionMessages.add(req, config.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig config, RenderRequest req, RenderResponse res)
		throws Exception {

		return "/html/portlet/tagged_content/configuration.jsp";
	}
	
	protected void addSelection(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		String assetType = ParamUtil.getString(req, "assetType");
		int assetOrder = ParamUtil.getInteger(req, "assetOrder");
		long assetId = ParamUtil.getLong(req, "assetId");

		String[] manualEntries = prefs.getValues("manual-entries", new String[0]);

		String assetConfig = assetConfiguration(
				assetType, assetId);

		if (assetOrder > -1) {
			manualEntries[assetOrder] = assetConfig;
		}
		else {			
			manualEntries = ArrayUtil.append(manualEntries, assetConfig);
		}

		prefs.setValues("manual-entries", manualEntries);
	}
	
	protected void moveSelectionDown(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {
				
		int assetOrder = ParamUtil.getInteger(req, "assetOrder");

		String[] manualEntries = prefs.getValues("manual-entries", new String[0]);
		
		if (assetOrder >= (manualEntries.length-1) || assetOrder < 0) {
			return;
		}
		
		String temp = manualEntries[assetOrder+1];
		
		manualEntries[assetOrder+1] = manualEntries[assetOrder];
		manualEntries[assetOrder] = temp;
		
		prefs.setValues("manual-entries", manualEntries);
	}
	
	protected void moveSelectionUp(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {
		
		int assetOrder = ParamUtil.getInteger(req, "assetOrder");

		String[] manualEntries = prefs.getValues("manual-entries", new String[0]);
		
		if (assetOrder >= (manualEntries.length) || assetOrder <= 0) {
			return;
		}
				
		String temp = manualEntries[assetOrder-1];
		
		manualEntries[assetOrder-1] = manualEntries[assetOrder];
		manualEntries[assetOrder] = temp;
		
		prefs.setValues("manual-entries", manualEntries);
	}
	
	protected void removeSelection(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {
		
		int assetOrder = ParamUtil.getInteger(req, "assetOrder");

		String[] manualEntries = prefs.getValues("manual-entries", new String[0]);
		
		if (assetOrder >= manualEntries.length) {
			return;
		}
		
		String[] newEntries = new String[manualEntries.length -1];
		
		int i = 0;
		int j = 0;
		
		for (; i < manualEntries.length; i++) {
			if (i != assetOrder) {
				newEntries[j++] = manualEntries[i];
			}
		}
		
		prefs.setValues("manual-entries", newEntries);
	}
	
	private String assetConfiguration(String assetType, long assetId) {
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
	
	protected void setSelectionStyle(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {
		
		String selectionStyle = ParamUtil.getString(req, "selectionStyle");
		
		prefs.setValue("selection-style", selectionStyle);
		
		if (selectionStyle.equals("manual")) {
			prefs.setValue("show-query-logic", String.valueOf(false));
		}
	}
	
	protected void updateDynamicSettings(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {
		
		String[] entries = StringUtil.split(
				ParamUtil.getString(req, "entries"));
		String[] notEntries = StringUtil.split(
			ParamUtil.getString(req, "notEntries"));
		boolean andOperator = ParamUtil.getBoolean(req, "andOperator");

		String displayStyle = ParamUtil.getString(req, "displayStyle");
		boolean showQueryLogic = ParamUtil.getBoolean(req, "showQueryLogic");
		boolean showAvailableLocales = ParamUtil.getBoolean(
			req, "showAvailableLocales");

		prefs.setValues("entries", entries);
		prefs.setValues("not-entries", notEntries);
		prefs.setValue("and-operator", String.valueOf(andOperator));

		prefs.setValue("display-style", displayStyle);
		prefs.setValue("show-query-logic", String.valueOf(showQueryLogic));
		prefs.setValue(
			"show-available-locales", String.valueOf(showAvailableLocales));
	}
	
	protected void updateManualSettings(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {
		
		String displayStyle = ParamUtil.getString(req, "displayStyle");
		boolean showAvailableLocales = ParamUtil.getBoolean(
			req, "showAvailableLocales");

		prefs.setValue("display-style", displayStyle);
		prefs.setValue(
			"show-available-locales", String.valueOf(showAvailableLocales));
	}

}