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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.blogs.EntryContentException;
import com.liferay.portlet.blogs.service.permission.BlogsPermission;
import com.liferay.portlet.blogs.EntryDisplayDateException;
import com.liferay.portlet.blogs.EntryTitleException;
import com.liferay.portlet.blogs.NoSuchEntryException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditEntryAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 * @author Thiago Moreira
 */
public class EditEntryAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			BlogsEntry entry = null;
			String oldUrlTitle = StringPool.BLANK;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Object[] returnValue = updateEntry(actionRequest);

				entry = (BlogsEntry)returnValue[0];
				oldUrlTitle = ((String)returnValue[1]);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntry(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribe(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribe(actionRequest);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");
			boolean updateRedirect = false;

			if (redirect.indexOf(
					"/blogs/" + oldUrlTitle + "/maximized") != -1) {

				oldUrlTitle += "/maximized";
			}

			if ((entry != null) && (Validator.isNotNull(oldUrlTitle)) &&
				(redirect.endsWith("/blogs/" + oldUrlTitle) ||
				 redirect.indexOf("/blogs/" + oldUrlTitle + "?") != -1)) {

				int pos = redirect.indexOf("?");

				if (pos == -1) {
					pos = redirect.length();
				}

				String newRedirect = redirect.substring(
					0, pos - oldUrlTitle.length());

				newRedirect += entry.getUrlTitle();

				if (oldUrlTitle.indexOf("/maximized") != -1) {
					newRedirect += "/maximized";
				}

				if (pos < redirect.length()) {
					newRedirect +=
						"?" + redirect.substring(pos + 1, redirect.length());
				}

				redirect = newRedirect;
				updateRedirect = true;
			}

			if ((entry != null) &&
				(entry.getStatus() == StatusConstants.DRAFT)) {

				JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

				jsonObj.put("entryId", entry.getEntryId());
				jsonObj.put("redirect", redirect);
				jsonObj.put("updateRedirect", updateRedirect);

				HttpServletResponse response =
					PortalUtil.getHttpServletResponse(actionResponse);
				InputStream is = new ByteArrayInputStream(
					jsonObj.toString().getBytes());
				String contentType = ContentTypes.TEXT_JAVASCRIPT;

				ServletResponseUtil.sendFile(
					response, null, is, contentType);

				setForward(actionRequest, ActionConstants.COMMON_NULL);
			}
			else {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				LayoutTypePortlet layoutTypePortlet =
					themeDisplay.getLayoutTypePortlet();

				if (layoutTypePortlet.hasPortletId(
						portletConfig.getPortletName())) {

					sendRedirect(actionRequest, actionResponse, redirect);
				}
				else {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.blogs.error");
			}
			else if (e instanceof EntryContentException ||
					 e instanceof EntryDisplayDateException ||
					 e instanceof EntryTitleException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else if (e instanceof AssetTagException) {
				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getEntry(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.blogs.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.blogs.edit_entry"));
	}

	protected void deleteEntry(ActionRequest actionRequest) throws Exception {
		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		BlogsEntryServiceUtil.deleteEntry(entryId);
	}

	protected void subscribe(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (BlogsPermission.contains(
				permissionChecker, themeDisplay.getScopeGroupId(),
				ActionKeys.SUBSCRIBE)) {

			SubscriptionLocalServiceUtil.addSubscription(
				themeDisplay.getUserId(), BlogsEntry.class.getName(),
				themeDisplay.getScopeGroupId());
		}
	}

	protected void unsubscribe(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (BlogsPermission.contains(
				permissionChecker, themeDisplay.getScopeGroupId(),
				ActionKeys.SUBSCRIBE)) {

			SubscriptionLocalServiceUtil.deleteSubscription(
				themeDisplay.getUserId(), BlogsEntry.class.getName(),
				themeDisplay.getScopeGroupId());
		}
	}

	protected Object[] updateEntry(ActionRequest actionRequest)
		throws Exception {

		long entryId = ParamUtil.getLong(actionRequest, "entryId");

		String title = ParamUtil.getString(actionRequest, "title");
		String content = ParamUtil.getString(actionRequest, "content");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		boolean allowTrackbacks = ParamUtil.getBoolean(
			actionRequest, "allowTrackbacks");
		String[] trackbacks = StringUtil.split(
			ParamUtil.getString(actionRequest, "trackbacks"));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			BlogsEntry.class.getName(), actionRequest);

		BlogsEntry entry = null;
		String oldUrlTitle = StringPool.BLANK;

		if (entryId <= 0) {

			// Add entry

			entry = BlogsEntryServiceUtil.addEntry(
				title, content, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				allowTrackbacks, trackbacks, serviceContext);

			if (serviceContext.getStatus() == StatusConstants.APPROVED) {
				AssetPublisherUtil.addAndStoreSelection(
					actionRequest, BlogsEntry.class.getName(),
					entry.getEntryId(), -1);
			}
		}
		else {

			// Update entry

			entry = BlogsEntryLocalServiceUtil.getEntry(entryId);

			String tempOldUrlTitle = entry.getUrlTitle();
			int oldStatus = entry.getStatus();

			entry = BlogsEntryServiceUtil.updateEntry(
				entryId, title, content, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				allowTrackbacks, trackbacks, serviceContext);

			if (!tempOldUrlTitle.equals(entry.getUrlTitle())) {
				oldUrlTitle = tempOldUrlTitle;
			}

			if ((oldStatus == StatusConstants.DRAFT) &&
				(serviceContext.getStatus() == StatusConstants.APPROVED)) {

				AssetPublisherUtil.addAndStoreSelection(
					actionRequest, BlogsEntry.class.getName(),
					entry.getEntryId(), -1);
			}
		}

		return new Object[] {entry, oldUrlTitle};
	}

}