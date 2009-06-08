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

package com.liferay.portlet.assetpublisher.action;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;

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
public class ConfigurationActionImpl extends BaseConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			String portletResource = ParamUtil.getString(
				actionRequest, "portletResource");

			PortletPreferences preferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					actionRequest, portletResource);

			if (cmd.equals("add-selection")) {
				AssetPublisherUtil.addSelection(actionRequest, preferences);
			}
			else if (cmd.equals("move-selection-down")) {
				moveSelectionDown(actionRequest, preferences);
			}
			else if (cmd.equals("move-selection-up")) {
				moveSelectionUp(actionRequest, preferences);
			}
			else if (cmd.equals("remove-selection")) {
				removeSelection(actionRequest, preferences);
			}
			else if (cmd.equals("selection-style")) {
				setSelectionStyle(actionRequest, preferences);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				String selectionStyle = preferences.getValue(
					"selection-style", "dynamic");

				if (selectionStyle.equals("dynamic")) {
					updateDynamicSettings(actionRequest, preferences);
				}
				else if (selectionStyle.equals("manual")) {
					updateManualSettings(actionRequest, preferences);
				}
			}

			if (SessionErrors.isEmpty(actionRequest)) {
				preferences.store();

				SessionMessages.add(
					actionRequest,
					portletConfig.getPortletName() + ".doConfigure");
			}
		}
		catch (Exception e) {
			if (e instanceof AssetTagException) {
				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/asset_publisher/configuration.jsp";
	}

	protected void moveSelectionDown(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		int assetOrder = ParamUtil.getInteger(actionRequest, "assetOrder");

		String[] manualEntries = preferences.getValues(
			"manual-entries", new String[0]);

		if ((assetOrder >= (manualEntries.length - 1)) || (assetOrder < 0)) {
			return;
		}

		String temp = manualEntries[assetOrder + 1];

		manualEntries[assetOrder + 1] = manualEntries[assetOrder];
		manualEntries[assetOrder] = temp;

		preferences.setValues("manual-entries", manualEntries);
	}

	protected void moveSelectionUp(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		int assetOrder = ParamUtil.getInteger(actionRequest, "assetOrder");

		String[] manualEntries = preferences.getValues(
			"manual-entries", new String[0]);

		if ((assetOrder >= manualEntries.length) || (assetOrder <= 0)) {
			return;
		}

		String temp = manualEntries[assetOrder - 1];

		manualEntries[assetOrder - 1] = manualEntries[assetOrder];
		manualEntries[assetOrder] = temp;

		preferences.setValues("manual-entries", manualEntries);
	}

	protected void removeSelection(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		int assetOrder = ParamUtil.getInteger(actionRequest, "assetOrder");

		String[] manualEntries = preferences.getValues(
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

		preferences.setValues("manual-entries", newEntries);
	}

	protected void setSelectionStyle(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String selectionStyle = ParamUtil.getString(
			actionRequest, "selectionStyle");
		String displayStyle = ParamUtil.getString(
			actionRequest, "displayStyle");

		preferences.setValue("selection-style", selectionStyle);

		if (selectionStyle.equals("manual") ||
			selectionStyle.equals("view-count")) {

			preferences.setValue("show-query-logic", String.valueOf(false));
		}

		if (!selectionStyle.equals("view-count") &&
			displayStyle.equals("view-count-details")) {

			preferences.setValue("display-style", "full-content");
		}
	}

	protected void updateDynamicSettings(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getScopeGroupId();

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
		boolean showAssetTitle = ParamUtil.getBoolean(
			actionRequest, "showAssetTitle");
		boolean showContextLink = ParamUtil.getBoolean(
			actionRequest, "showContextLink");
		int abstractLength = ParamUtil.getInteger(
			actionRequest, "abstractLength");
		String assetLinkBehaviour = ParamUtil.getString(
			actionRequest, "assetLinkBehaviour");
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

		preferences.setValue("selection-style", "dynamic");

		preferences.setValues("entries", entries);
		preferences.setValues("not-entries", notEntries);
		preferences.setValue("merge-url-tags", String.valueOf(mergeUrlTags));
		preferences.setValue("and-operator", String.valueOf(andOperator));

		preferences.setValue("class-name-id", String.valueOf(classNameId));
		preferences.setValue("category", category);
		preferences.setValue("display-style", displayStyle);
		preferences.setValue(
			"show-asset-title", String.valueOf(showAssetTitle));
		preferences.setValue(
			"show-context-link", String.valueOf(showContextLink));
		preferences.setValue("abstract-length", String.valueOf(abstractLength));
		preferences.setValue("asset-link-behaviour", assetLinkBehaviour);
		preferences.setValue("order-by-column-1", orderByColumn1);
		preferences.setValue("order-by-column-2", orderByColumn2);
		preferences.setValue("order-by-type-1", orderByType1);
		preferences.setValue("order-by-type-2", orderByType2);
		preferences.setValue(
			"exclude-zero-view-count", String.valueOf(excludeZeroViewCount));
		preferences.setValue("delta", String.valueOf(delta));
		preferences.setValue("pagination-type", paginationType);
		preferences.setValue(
			"show-available-locales", String.valueOf(showAvailableLocales));
		preferences.setValue("enable-ratings", String.valueOf(enableRatings));
		preferences.setValue("enable-comments", String.valueOf(enableComments));
		preferences.setValue(
			"enable-comment-ratings", String.valueOf(enableCommentRatings));
		preferences.setValue("metadata-fields", medatadaFields);

		AssetTagLocalServiceUtil.checkTags(userId, groupId, entries);
		AssetTagLocalServiceUtil.checkTags(userId, groupId, notEntries);
	}

	protected void updateManualSettings(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String displayStyle = ParamUtil.getString(
			actionRequest, "displayStyle");
		boolean showAssetTitle = ParamUtil.getBoolean(
			actionRequest, "showAssetTitle");
		boolean showContextLink = ParamUtil.getBoolean(
			actionRequest, "showContextLink");
		int abstractLength = ParamUtil.getInteger(
			actionRequest, "abstractLength");
		String assetLinkBehaviour = ParamUtil.getString(
			actionRequest, "assetLinkBehaviour");
		boolean showAvailableLocales = ParamUtil.getBoolean(
			actionRequest, "showAvailableLocales");
		boolean enableComments = ParamUtil.getBoolean(
			actionRequest, "enableComments");
		boolean enableCommentRatings = ParamUtil.getBoolean(
			actionRequest, "enableCommentRatings");
		boolean enableRatings = ParamUtil.getBoolean(
			actionRequest, "enableRatings");
		boolean enableTagBasedNavigation = ParamUtil.getBoolean(
			actionRequest, "enableTagBasedNavigation");
		String medatadaFields = ParamUtil.getString(
			actionRequest, "metadataFields");

		preferences.setValue("selection-style", "manual");
		preferences.setValue("display-style", displayStyle);
		preferences.setValue(
			"show-asset-title", String.valueOf(showAssetTitle));
		preferences.setValue(
			"show-context-link", String.valueOf(showContextLink));
		preferences.setValue("abstract-length", String.valueOf(abstractLength));
		preferences.setValue("asset-link-behaviour", assetLinkBehaviour);
		preferences.setValue(
			"show-available-locales", String.valueOf(showAvailableLocales));
		preferences.setValue("enable-comments", String.valueOf(enableComments));
		preferences.setValue(
			"enable-comment-ratings", String.valueOf(enableCommentRatings));
		preferences.setValue("enable-ratings", String.valueOf(enableRatings));
		preferences.setValue(
			"enable-tag-based-navigation",
			String.valueOf(enableTagBasedNavigation));
		preferences.setValue("metadata-fields", medatadaFields);
	}

}