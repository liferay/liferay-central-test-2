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
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.announcements.AnnouncementEntryContentException;
import com.liferay.portlet.announcements.AnnouncementEntryDisplayDateException;
import com.liferay.portlet.announcements.AnnouncementEntryExpirationDateException;
import com.liferay.portlet.announcements.AnnouncementEntryTitleException;
import com.liferay.portlet.announcements.NoSuchAnnouncementEntryException;
import com.liferay.portlet.announcements.NoSuchEntryException;
import com.liferay.portlet.announcements.service.AnnouncementEntryServiceUtil;
import com.liferay.util.servlet.SessionErrors;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditAnnouncementAction.java.html"><b><i>View Source</i></b></a>
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

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(
				req, PortletKeys.ANNOUNCEMENTS);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateEntry(req, prefs);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEntry(req);
			}

			String redirect = ParamUtil.getString(req, "redirect");

			res.sendRedirect(redirect);
		}
		catch (Exception e) {
			if (e instanceof AnnouncementEntryContentException ||
				e instanceof AnnouncementEntryDisplayDateException ||
				e instanceof AnnouncementEntryExpirationDateException ||
				e instanceof AnnouncementEntryTitleException ||
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

		AnnouncementEntryServiceUtil.deleteEntry(entryId);
	}

	protected void updateEntry(
			ActionRequest req, PortletPreferences prefs)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);

		long entryId = ParamUtil.getLong(req, "entryId");

		String[] distributionScopeParts =
			StringUtil.split(ParamUtil.getString(req, "distributionScope"));

		long classNameId = 0;
		long classPK = 0;

		if (distributionScopeParts.length == 2) {
			classNameId = GetterUtil.getLong(distributionScopeParts[0]);

			if (classNameId > 0) {
				classPK = GetterUtil.getLong(distributionScopeParts[1]);
			}
		}

		String type = ParamUtil.getString(req, "type");
		String title = ParamUtil.getString(req, "title");
		String content = ParamUtil.getString(req, "content");
		String url = ParamUtil.getString(req, "url");
		int priority = ParamUtil.getInteger(req, "priority");
		boolean alert = ParamUtil.getBoolean(req, "alert");

		int displayMonth = ParamUtil.getInteger(req, "displayMonth");
		int displayDay = ParamUtil.getInteger(req, "displayDay");
		int displayYear = ParamUtil.getInteger(req, "displayYear");

		int expirationMonth = ParamUtil.getInteger(req, "expirationMonth");
		int expirationDay = ParamUtil.getInteger(req, "expirationDay");
		int expirationYear = ParamUtil.getInteger(req, "expirationYear");

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (cmd.equals(Constants.ADD)) {
			AnnouncementEntryServiceUtil.addEntry(
				themeDisplay.getUserId(), themeDisplay.getPlid(), classNameId,
				classPK, title, content, url, type, displayMonth, displayDay,
				displayYear, expirationMonth, expirationDay, expirationYear,
				priority, alert);
		}
		else {
			AnnouncementEntryServiceUtil.updateEntry(
				entryId, title, content, url, type, displayMonth, displayDay,
				displayYear, expirationMonth, expirationDay, expirationYear,
				priority);
		}
	}

}
