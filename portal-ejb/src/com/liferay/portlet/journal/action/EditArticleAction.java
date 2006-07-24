/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.journal.action;

import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.journal.ArticleContentException;
import com.liferay.portlet.journal.ArticleDisplayDateException;
import com.liferay.portlet.journal.ArticleExpirationDateException;
import com.liferay.portlet.journal.ArticleIdException;
import com.liferay.portlet.journal.ArticleTitleException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.spring.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.spring.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.spring.JournalStructureServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.UploadPortletRequest;

import java.io.File;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
 * <a href="EditArticleAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditArticleAction extends PortletAction {

	public static final String VERSION_SEPARATOR = "_version_";

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateArticle(req);
			}
			else if (cmd.equals(Constants.APPROVE)) {
				approveArticle(req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteArticles(req);
			}
			else if (cmd.equals(Constants.EXPIRE)) {
				expireArticles(req);
			}

			if (Validator.isNotNull(cmd)) {
				sendRedirect(req, res);
			}
		}
		catch (Exception e) {
			if (e != null &&
				e instanceof NoSuchArticleException ||
				e instanceof NoSuchStructureException ||
				e instanceof NoSuchTemplateException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.journal.error");
			}
			else if (e != null &&
					 e instanceof ArticleContentException ||
					 e instanceof ArticleDisplayDateException ||
					 e instanceof ArticleExpirationDateException ||
					 e instanceof ArticleIdException ||
					 e instanceof ArticleTitleException ||
					 e instanceof DuplicateArticleIdException) {

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
			String cmd = ParamUtil.getString(req, Constants.CMD);

			if (!cmd.equals(Constants.ADD)) {
				ActionUtil.getArticle(req);
			}
		}
		catch (NoSuchArticleException nsse) {

			// Let this slide because the user can manually input a article id
			// for a new article that does not yet exist.

		}
		catch (Exception e) {
			if (e != null &&
				//e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.journal.edit_article"));
	}

	protected void approveArticle(ActionRequest req) throws Exception {
		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		String articleId = ParamUtil.getString(req, "articleId");
		double version = ParamUtil.getDouble(req, "version");

		String articleURL = ParamUtil.getString(req, "articleURL");

		JournalArticleServiceUtil.approveArticle(
			articleId, version, layout.getPlid(), articleURL,
			req.getPreferences());
	}

	protected void deleteArticles(ActionRequest req) throws Exception {
		String companyId = PortalUtil.getCompanyId(req);

		String[] deleteArticleIds = StringUtil.split(
			ParamUtil.getString(req, "deleteArticleIds"));

		for (int i = 0; i < deleteArticleIds.length; i++) {
			int pos = deleteArticleIds[i].lastIndexOf(VERSION_SEPARATOR);

			String articleId = deleteArticleIds[i].substring(0, pos);
			double version = GetterUtil.getDouble(
				deleteArticleIds[i].substring(
					pos + VERSION_SEPARATOR.length()));

			JournalArticleServiceUtil.deleteArticle(
				companyId, articleId, version, null, null);

			JournalUtil.removeRecentArticle(req, deleteArticleIds[i]);
		}
	}

	protected void expireArticles(ActionRequest req) throws Exception {
		String companyId = PortalUtil.getCompanyId(req);

		String[] expireArticleIds = StringUtil.split(
			ParamUtil.getString(req, "expireArticleIds"));

		for (int i = 0; i < expireArticleIds.length; i++) {
			int pos = expireArticleIds[i].lastIndexOf(VERSION_SEPARATOR);

			String articleId = expireArticleIds[i].substring(0, pos);
			double version = GetterUtil.getDouble(
				expireArticleIds[i].substring(
					pos + VERSION_SEPARATOR.length()));

			JournalArticleServiceUtil.expireArticle(
				companyId, articleId, version, null, null);
		}
	}

	protected Map getImages(UploadPortletRequest uploadReq) throws Exception {
		Map images = new HashMap();

		String imagePrefix = "structure_image_";

		Enumeration enu = uploadReq.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = (String)enu.nextElement();

			if (name.startsWith(imagePrefix)) {
				File file = uploadReq.getFile(name);
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					name = name.substring(imagePrefix.length(), name.length());

					images.put(name, bytes);
				}
			}
		}

		return images;
	}

	protected void updateArticle(ActionRequest req) throws Exception {
		String cmd = ParamUtil.getString(req, Constants.CMD);

		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		String companyId = PortalUtil.getCompanyId(req);

		String articleId = ParamUtil.getString(req, "articleId");
		boolean autoArticleId = ParamUtil.getBoolean(req, "autoArticleId");

		double version = ParamUtil.getDouble(req, "version");
		boolean incrementVersion = ParamUtil.getBoolean(
			req, "incrementVersion");

		String title = ParamUtil.getString(req, "title");
		String content = ParamUtil.getString(req, "content");
		String type = ParamUtil.getString(req, "type");
		String structureId = ParamUtil.getString(req, "structureId");
		String templateId = ParamUtil.getString(req, "templateId");

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
		int expirationDateAmPm = ParamUtil.getInteger(
			req, "expirationDateAmPm");
		boolean neverExpire = ParamUtil.getBoolean(req, "neverExpire");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		int reviewDateMonth = ParamUtil.getInteger(req, "reviewDateMonth");
		int reviewDateDay = ParamUtil.getInteger(req, "reviewDateDay");
		int reviewDateYear = ParamUtil.getInteger(req, "reviewDateYear");
		int reviewDateHour = ParamUtil.getInteger(req, "reviewDateHour");
		int reviewDateMinute = ParamUtil.getInteger(req, "reviewDateMinute");
		int reviewDateAmPm = ParamUtil.getInteger(req, "reviewDateAmPm");
		boolean neverReview = ParamUtil.getBoolean(req, "neverReview");

		if (reviewDateAmPm == Calendar.PM) {
			reviewDateHour += 12;
		}

		Map images = getImages(PortalUtil.getUploadPortletRequest(req));

		String articleURL = ParamUtil.getString(req, "articleURL");

		boolean addCommunityPermissions = ParamUtil.getBoolean(
			req, "addCommunityPermissions");
		boolean addGuestPermissions = ParamUtil.getBoolean(
			req, "addGuestPermissions");

		boolean approve = ParamUtil.getBoolean(req, "approve");

		JournalArticle article = null;

		if (cmd.equals(Constants.ADD)) {

			// Add article

			article = JournalArticleServiceUtil.addArticle(
				articleId, autoArticleId, layout.getPlid(), title, content,
				type, structureId, templateId, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, images, articleURL,
				req.getPreferences(), addCommunityPermissions,
				addGuestPermissions);
		}
		else {

			// Merge current content with new content

			if (Validator.isNotNull(structureId)) {
				JournalArticle curArticle =
					JournalArticleServiceUtil.getArticle(
						companyId, articleId, version);

				if (Validator.isNotNull(curArticle.getStructureId())) {
					JournalStructure structure =
						JournalStructureServiceUtil.getStructure(
							companyId, structureId);

					content = JournalUtil.mergeLocaleContent(
						curArticle.getContent(), content, structure.getXsd());
				}
			}

			// Update article

			article = JournalArticleServiceUtil.updateArticle(
				companyId, articleId, version, incrementVersion, title, content,
				type, structureId, templateId, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, images, articleURL,
				req.getPreferences());
		}

		if (approve) {
			article = JournalArticleServiceUtil.approveArticle(
				article.getArticleId(), article.getVersion(), layout.getPlid(),
				articleURL, req.getPreferences());
		}

		// Recent articles

		JournalUtil.addRecentArticle(req, article);

		// Journal content

		String portletResource = ParamUtil.getString(req, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			PortletPreferences prefs =
				PortletPreferencesFactory.getPortletSetup(
					req, portletResource, true, true);

			prefs.setValue("article-id", article.getArticleId());

			prefs.store();

			updateContentSearch(req, portletResource, article.getArticleId());
		}
	}

	protected void updateContentSearch(
			ActionRequest req, String portletResource, String articleId)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		JournalContentSearchLocalServiceUtil.updateContentSearch(
			portletResource, layout.getLayoutId(), layout.getOwnerId(),
			layout.getCompanyId(), articleId);
	}

}