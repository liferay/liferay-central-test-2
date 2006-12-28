/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journalcontent.action;

import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
 * <a href="EditConfigurationAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditConfigurationAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(req, Constants.CMD);

			if (!cmd.equals(Constants.UPDATE)) {
				return;
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

			String companyId = themeDisplay.getCompanyId();

			String groupId = ParamUtil.getString(req, "groupId");

			String languageId = LanguageUtil.getLanguageId(req);

			Map articleIdsMap = new TreeMap();

			Enumeration enu = req.getParameterNames();

			while (enu.hasMoreElements()) {
				String name = (String)enu.nextElement();

				if (name.startsWith("article")) {
					String articleId = ParamUtil.getString(
						req, name).toUpperCase();

					String content = JournalContentUtil.getContent(
						companyId, groupId, articleId, languageId,
						themeDisplay);

					if (Validator.isNull(content)) {
						throw new NoSuchArticleException();
					}

					// Use map to ensure it is sorted by user's choice

					articleIdsMap.put(name, articleId);
				}
			}

			String[] articleIds =
				(String[])articleIdsMap.values().toArray(new String[0]);

			String portletResource = ParamUtil.getString(
				req, "portletResource");

			PortletPreferences prefs =
				PortletPreferencesFactory.getPortletSetup(
					req, portletResource, true, true);

			prefs.setValue("group-id", groupId);
			prefs.setValues("article-id", articleIds);

			prefs.store();

			updateContentSearch(req, portletResource, articleIds);

			res.sendRedirect(ParamUtil.getString(req, "redirect"));
		}
		catch (NoSuchArticleException nsae) {
			SessionErrors.add(req, nsae.getClass().getName());
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		return mapping.findForward(
			"portlet.journal_content.edit_configuration");
	}

	protected void updateContentSearch(
			ActionRequest req, String portletResource, String[] articleIds)
		throws Exception {

		ThemeDisplay themeDisplay =
				(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		JournalContentSearchLocalServiceUtil.updateContentSearch(
			portletResource, layout.getLayoutId(), layout.getOwnerId(),
			layout.getCompanyId(), layout.getGroupId(), articleIds);
	}

}