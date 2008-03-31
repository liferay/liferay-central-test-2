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

package com.liferay.portlet.announcements.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.announcements.EntryContentException;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.announcements.EntryExpirationDateException;
import com.liferay.portlet.announcements.EntryTitleException;
import com.liferay.portlet.announcements.NoSuchEntryException;
import com.liferay.portlet.announcements.service.AnnouncementsEntryServiceUtil;
import com.liferay.util.servlet.SessionErrors;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditEntryAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class EditEntryAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateEntry(req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntry(req);
			}

			String redirect = ParamUtil.getString(req, "redirect");

			res.sendRedirect(redirect);
		}
		catch (Exception e) {
			if (e instanceof EntryContentException ||
				e instanceof EntryDisplayDateException ||
				e instanceof EntryExpirationDateException ||
				e instanceof EntryTitleException ||
				e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			ActionUtil.getEntry(req);
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.announcements.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.announcements.edit_entry"));
	}

	protected void deleteEntry(ActionRequest req) throws Exception {
		long entryId = ParamUtil.getLong(req, "entryId");

		AnnouncementsEntryServiceUtil.deleteEntry(entryId);
	}

	protected void updateEntry(ActionRequest req) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		long entryId = ParamUtil.getLong(req, "entryId");

		String[] distributionScopeParts = StringUtil.split(
			ParamUtil.getString(req, "distributionScope"));

		long classNameId = 0;
		long classPK = 0;

		if (distributionScopeParts.length == 2) {
			classNameId = GetterUtil.getLong(distributionScopeParts[0]);

			if (classNameId > 0) {
				classPK = GetterUtil.getLong(distributionScopeParts[1]);
			}
		}

		String title = ParamUtil.getString(req, "title");
		String content = ParamUtil.getString(req, "content");
		String url = ParamUtil.getString(req, "url");
		String type = ParamUtil.getString(req, "type");

		int displayDateMonth = ParamUtil.getInteger(req, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(req, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(req, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(req, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(req, "displayDateMinute");

		int displayDateAmPm = ParamUtil.getInteger(req, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			req, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(req, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			req, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			req, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			req, "expirationDateMinute");

		int expirationDateAmPm =
			ParamUtil.getInteger(req, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		int priority = ParamUtil.getInteger(req, "priority");
		boolean alert = ParamUtil.getBoolean(req, "alert");

		if (entryId <= 0) {

			// Add entry

			AnnouncementsEntryServiceUtil.addEntry(
				themeDisplay.getPlid(), classNameId, classPK, title, content,
				url, type, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, priority, alert);
		}
		else {

			// Update entry

			AnnouncementsEntryServiceUtil.updateEntry(
				entryId, title, content, url, type, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				priority);
		}
	}

}