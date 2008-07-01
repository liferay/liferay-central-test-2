/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.taggedcontent.util.AssetPublisherUtil;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="ConfigurationActionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ConfigurationActionImpl implements ConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(
				actionRequest, portletResource);

		if (cmd.equals("add-selection")) {
			AssetPublisherUtil.addSelection(actionRequest, prefs);
		}
		else if (cmd.equals("move-selection-down")) {
			moveSelectionDown(actionRequest, prefs);
		}
		else if (cmd.equals("move-selection-up")) {
			moveSelectionUp(actionRequest, prefs);
		}
		else if (cmd.equals("remove-selection")) {
			removeSelection(actionRequest, prefs);
		}
		else if (cmd.equals("selection-style")) {
			setSelectionStyle(actionRequest, prefs);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			String selectionStyle = prefs.getValue(
				"selection-style", "dynamic");

			if (selectionStyle.equals("dynamic")) {
				updateDynamicSettings(actionRequest, prefs);
			}
			else if (selectionStyle.equals("manual")) {
				updateManualSettings(actionRequest, prefs);
			}
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			prefs.store();

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/tagged_content/configuration.jsp";
	}

	protected void moveSelectionDown(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		int assetOrder = ParamUtil.getInteger(actionRequest, "assetOrder");

		String[] manualEntries = prefs.getValues(
			"manual-entries", new String[0]);

		if ((assetOrder >= (manualEntries.length - 1)) || (assetOrder < 0)) {
			return;
		}

		String temp = manualEntries[assetOrder + 1];

		manualEntries[assetOrder + 1] = manualEntries[assetOrder];
		manualEntries[assetOrder] = temp;

		prefs.setValues("manual-entries", manualEntries);
	}

	protected void moveSelectionUp(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		int assetOrder = ParamUtil.getInteger(actionRequest, "assetOrder");

		String[] manualEntries = prefs.getValues(
			"manual-entries", new String[0]);

		if ((assetOrder >= manualEntries.length) || (assetOrder <= 0)) {
			return;
		}

		String temp = manualEntries[assetOrder - 1];

		manualEntries[assetOrder - 1] = manualEntries[assetOrder];
		manualEntries[assetOrder] = temp;

		prefs.setValues("manual-entries", manualEntries);
	}

	protected void removeSelection(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		int assetOrder = ParamUtil.getInteger(actionRequest, "assetOrder");

		String[] manualEntries = prefs.getValues(
			"manual-entries", new String[0]);

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

	protected void setSelectionStyle(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		String selectionStyle = ParamUtil.getString(
			actionRequest, "selectionStyle");
		String displayStyle = ParamUtil.getString(
			actionRequest, "displayStyle");

		prefs.setValue("selection-style", selectionStyle);

		if (selectionStyle.equals("manual") ||
			selectionStyle.equals("view-count")) {

			prefs.setValue("show-query-logic", String.valueOf(false));
		}

		if (!selectionStyle.equals("view-count") &&
			displayStyle.equals("view-count-details")) {

			prefs.setValue("display-style", "full-content");
		}
	}

	protected void updateDynamicSettings(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();

		String[] entries = StringUtil.split(
			ParamUtil.getString(actionRequest, "entries"));
		String[] notEntries = StringUtil.split(
			ParamUtil.getString(actionRequest, "notEntries"));
		boolean mergeUrlTags = ParamUtil.getBoolean(
			actionRequest, "mergeUrlTags");
		boolean andOperator = ParamUtil.getBoolean(
			actionRequest, "andOperator");

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		String category = ParamUtil.getString(actionRequest, "category");
		String displayStyle = ParamUtil.getString(
			actionRequest, "displayStyle");
		String orderByColumn1 = ParamUtil.getString(
			actionRequest, "orderByColumn1");
		String orderByColumn2 = ParamUtil.getString(
			actionRequest, "orderByColumn2");
		String orderByType1 = ParamUtil.getString(
			actionRequest, "orderByType1");
		String orderByType2 = ParamUtil.getString(
			actionRequest, "orderByType2");
		boolean excludeZeroViewCount = ParamUtil.getBoolean(
			actionRequest, "excludeZeroViewCount");
		boolean showQueryLogic = ParamUtil.getBoolean(
			actionRequest, "showQueryLogic");
		int delta = ParamUtil.getInteger(actionRequest, "delta");
		String paginationType = ParamUtil.getString(
			actionRequest, "paginationType");
		boolean showAvailableLocales = ParamUtil.getBoolean(
			actionRequest, "showAvailableLocales");
		boolean enableComments = ParamUtil.getBoolean(
			actionRequest, "enableComments");
		boolean enableCommentRatings = ParamUtil.getBoolean(
			actionRequest, "enableCommentRatings");
		boolean enableRatings = ParamUtil.getBoolean(
			actionRequest, "enableRatings");
		String medatadaFields = ParamUtil.getString(
			actionRequest, "metadataFields");

		prefs.setValues("entries", entries);
		prefs.setValues("not-entries", notEntries);
		prefs.setValue("merge-url-tags", String.valueOf(mergeUrlTags));
		prefs.setValue("and-operator", String.valueOf(andOperator));

		prefs.setValue("class-name-id", String.valueOf(classNameId));
		prefs.setValue("category", category);
		prefs.setValue("display-style", displayStyle);
		prefs.setValue("order-by-column-1", orderByColumn1);
		prefs.setValue("order-by-column-2", orderByColumn2);
		prefs.setValue("order-by-type-1", orderByType1);
		prefs.setValue("order-by-type-2", orderByType2);
		prefs.setValue(
			"exclude-zero-view-count", String.valueOf(excludeZeroViewCount));
		prefs.setValue("show-query-logic", String.valueOf(showQueryLogic));
		prefs.setValue("delta", String.valueOf(delta));
		prefs.setValue("pagination-type", paginationType);
		prefs.setValue(
			"show-available-locales", String.valueOf(showAvailableLocales));
		prefs.setValue("enable-ratings", String.valueOf(enableRatings));
		prefs.setValue("enable-comments", String.valueOf(enableComments));
		prefs.setValue(
			"enable-comment-ratings", String.valueOf(enableCommentRatings));
		prefs.setValue("metadata-fields", medatadaFields);

		TagsEntryLocalServiceUtil.checkEntries(userId, entries);
		TagsEntryLocalServiceUtil.checkEntries(userId, notEntries);
	}

	protected void updateManualSettings(
			ActionRequest actionRequest, PortletPreferences prefs)
		throws Exception {

		String displayStyle = ParamUtil.getString(
			actionRequest, "displayStyle");
		boolean showAvailableLocales = ParamUtil.getBoolean(
			actionRequest, "showAvailableLocales");
		boolean enableComments = ParamUtil.getBoolean(
			actionRequest, "enableComments");
		boolean enableCommentRatings = ParamUtil.getBoolean(
			actionRequest, "enableCommentRatings");
		boolean enableRatings = ParamUtil.getBoolean(
			actionRequest, "enableRatings");

		prefs.setValue("display-style", displayStyle);
		prefs.setValue(
			"show-available-locales", String.valueOf(showAvailableLocales));
		prefs.setValue("enable-comments", String.valueOf(enableComments));
		prefs.setValue(
			"enable-comment-ratings", String.valueOf(enableCommentRatings));
		prefs.setValue("enable-ratings", String.valueOf(enableRatings));
	}

}