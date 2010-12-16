/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.journal.ArticleContentException;
import com.liferay.portlet.journal.ArticleDisplayDateException;
import com.liferay.portlet.journal.ArticleExpirationDateException;
import com.liferay.portlet.journal.ArticleIdException;
import com.liferay.portlet.journal.ArticleSmallImageNameException;
import com.liferay.portlet.journal.ArticleSmallImageSizeException;
import com.liferay.portlet.journal.ArticleTitleException;
import com.liferay.portlet.journal.ArticleTypeException;
import com.liferay.portlet.journal.ArticleVersionException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.journal.util.JournalUtil;

import java.io.File;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Juan Fernández
 */
public class EditArticleAction extends PortletAction {

	public static final String VERSION_SEPARATOR = "_version_";

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		JournalArticle article = null;
		String oldUrlTitle = StringPool.BLANK;

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Object[] returnValue = updateArticle(actionRequest);

				article = (JournalArticle)returnValue[0];
				oldUrlTitle = ((String)returnValue[1]);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteArticles(actionRequest);
			}
			else if (cmd.equals(Constants.EXPIRE)) {
				expireArticles(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeArticles(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeArticles(actionRequest);
			}
			else if (cmd.equals("removeArticlesLocale")) {
				removeArticlesLocale(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				int workflowAction = ParamUtil.getInteger(
					actionRequest, "workflowAction",
					WorkflowConstants.ACTION_PUBLISH);

				if ((article != null) &&
					(workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT)) {

					redirect = getSaveAndContinueRedirect(
						portletConfig, actionRequest, article, redirect);
				}

				if (redirect.contains("/content/" + oldUrlTitle + "?")) {
					int pos = redirect.indexOf("?");

					if (pos == -1) {
						pos = redirect.length();
					}

					String newRedirect = redirect.substring(
						0, pos - oldUrlTitle.length());

					newRedirect += article.getUrlTitle();

					if (oldUrlTitle.contains("/maximized")) {
						newRedirect += "/maximized";
					}

					if (pos < redirect.length()) {
						newRedirect +=
							"?" +
								redirect.substring(pos + 1, redirect.length());
					}

					redirect = newRedirect;
				}

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
			if (e instanceof NoSuchArticleException ||
				e instanceof NoSuchStructureException ||
				e instanceof NoSuchTemplateException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.journal.error");
			}
			else if (e instanceof ArticleContentException ||
					 e instanceof ArticleDisplayDateException ||
					 e instanceof ArticleExpirationDateException ||
					 e instanceof ArticleIdException ||
					 e instanceof ArticleSmallImageNameException ||
					 e instanceof ArticleSmallImageSizeException ||
					 e instanceof ArticleTitleException ||
					 e instanceof ArticleTypeException ||
					 e instanceof ArticleVersionException ||
					 e instanceof DuplicateArticleIdException) {

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
			String cmd = ParamUtil.getString(renderRequest, Constants.CMD);

			if (!cmd.equals(Constants.ADD)) {
				ActionUtil.getArticle(renderRequest);
			}
		}
		catch (NoSuchArticleException nsse) {

			// Let this slide because the user can manually input a article id
			// for a new article that does not yet exist.

		}
		catch (Exception e) {
			if (//e instanceof NoSuchArticleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.journal.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.journal.edit_article"));
	}

	protected void deleteArticles(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String[] deleteArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteArticleIds"));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (int i = 0; i < deleteArticleIds.length; i++) {
			int pos = deleteArticleIds[i].lastIndexOf(VERSION_SEPARATOR);

			String articleId = deleteArticleIds[i];

			String articleURL = ParamUtil.getString(
				actionRequest, "articleURL");

			double version = 0;

			if (pos == -1) {
				JournalArticleServiceUtil.deleteArticle(
					groupId, articleId, articleURL, serviceContext);
			}
			else {
				articleId = articleId.substring(0, pos);
				version = GetterUtil.getDouble(
					deleteArticleIds[i].substring(
						pos + VERSION_SEPARATOR.length()));

				JournalArticleServiceUtil.deleteArticle(
					groupId, articleId, version, articleURL, serviceContext);
			}

			JournalUtil.removeRecentArticle(actionRequest, articleId, version);
		}
	}

	protected void expireArticles(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String[] expireArticleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "expireArticleIds"));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		for (int i = 0; i < expireArticleIds.length; i++) {
			int pos = expireArticleIds[i].lastIndexOf(VERSION_SEPARATOR);

			String articleId = expireArticleIds[i].substring(0, pos);
			double version = GetterUtil.getDouble(
				expireArticleIds[i].substring(
					pos + VERSION_SEPARATOR.length()));

			String articleURL = ParamUtil.getString(
				actionRequest, "articleURL");

			JournalArticleServiceUtil.expireArticle(
				groupId, articleId, version, articleURL, serviceContext);
		}
	}

	protected Map<String, byte[]> getImages(UploadPortletRequest uploadRequest)
		throws Exception {

		Map<String, byte[]> images = new HashMap<String, byte[]>();

		String imagePrefix = "structure_image_";

		Enumeration<String> enu = uploadRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(imagePrefix)) {
				File file = uploadRequest.getFile(name);
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					name = name.substring(imagePrefix.length(), name.length());

					images.put(name, bytes);
				}
			}
		}

		return images;
	}

	protected String getSaveAndContinueRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			JournalArticle article, String redirect)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String originalRedirect = ParamUtil.getString(
			actionRequest, "originalRedirect");

		String languageId = ParamUtil.getString(actionRequest, "languageId");

		PortletURLImpl portletURL = new PortletURLImpl(
			(ActionRequestImpl)actionRequest, portletConfig.getPortletName(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(actionRequest.getWindowState());

		portletURL.setParameter("struts_action", "/journal/edit_article");
		portletURL.setParameter(Constants.CMD, Constants.UPDATE, false);
		portletURL.setParameter("redirect", redirect, false);
		portletURL.setParameter("originalRedirect", originalRedirect, false);
		portletURL.setParameter(
			"groupId", String.valueOf(article.getGroupId()), false);
		portletURL.setParameter("articleId", article.getArticleId(), false);
		portletURL.setParameter(
			"version", String.valueOf(article.getVersion()), false);
		portletURL.setParameter("languageId", languageId, false);

		return portletURL.toString();
	}

	protected void removeArticlesLocale(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String[] removeArticleLocaleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteArticleIds"));

		for (int i = 0; i < removeArticleLocaleIds.length; i++) {
			int pos = removeArticleLocaleIds[i].lastIndexOf(VERSION_SEPARATOR);

			String articleId = removeArticleLocaleIds[i].substring(0, pos);
			double version = GetterUtil.getDouble(
				removeArticleLocaleIds[i].substring(
					pos + VERSION_SEPARATOR.length()));
			String languageId = ParamUtil.getString(
				actionRequest, "languageId");

			JournalArticleServiceUtil.removeArticleLocale(
				groupId, articleId, version, languageId);
		}
	}

	protected void subscribeArticles(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JournalArticleServiceUtil.subscribe(themeDisplay.getScopeGroupId());
	}

	protected void unsubscribeArticles(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JournalArticleServiceUtil.unsubscribe(themeDisplay.getScopeGroupId());
	}

	protected Object[] updateArticle(ActionRequest actionRequest)
		throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(uploadRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(uploadRequest, "groupId");

		String articleId = ParamUtil.getString(uploadRequest, "articleId");
		boolean autoArticleId = ParamUtil.getBoolean(
			uploadRequest, "autoArticleId");

		double version = ParamUtil.getDouble(uploadRequest, "version");

		String title = ParamUtil.getString(uploadRequest, "title");
		String description = ParamUtil.getString(uploadRequest, "description");
		String content = ParamUtil.getString(uploadRequest, "content");
		String type = ParamUtil.getString(uploadRequest, "type");
		String structureId = ParamUtil.getString(uploadRequest, "structureId");
		String templateId = ParamUtil.getString(uploadRequest, "templateId");

		String lastLanguageId = ParamUtil.getString(
			uploadRequest, "lastLanguageId");
		String defaultLanguageId = ParamUtil.getString(
			uploadRequest, "defaultLanguageId");

		int displayDateMonth = ParamUtil.getInteger(
			uploadRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			uploadRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			uploadRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			uploadRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			uploadRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			uploadRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			uploadRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			uploadRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			uploadRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			uploadRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			uploadRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			uploadRequest, "expirationDateAmPm");
		boolean neverExpire = ParamUtil.getBoolean(
			uploadRequest, "neverExpire");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		int reviewDateMonth = ParamUtil.getInteger(
			uploadRequest, "reviewDateMonth");
		int reviewDateDay = ParamUtil.getInteger(
			uploadRequest, "reviewDateDay");
		int reviewDateYear = ParamUtil.getInteger(
			uploadRequest, "reviewDateYear");
		int reviewDateHour = ParamUtil.getInteger(
			uploadRequest, "reviewDateHour");
		int reviewDateMinute = ParamUtil.getInteger(
			uploadRequest, "reviewDateMinute");
		int reviewDateAmPm = ParamUtil.getInteger(
			uploadRequest, "reviewDateAmPm");
		boolean neverReview = ParamUtil.getBoolean(
			uploadRequest, "neverReview");

		if (reviewDateAmPm == Calendar.PM) {
			reviewDateHour += 12;
		}

		boolean indexable = ParamUtil.getBoolean(uploadRequest, "indexable");

		boolean smallImage = ParamUtil.getBoolean(uploadRequest, "smallImage");
		String smallImageURL = ParamUtil.getString(
			uploadRequest, "smallImageURL");
		File smallFile = uploadRequest.getFile("smallFile");

		Map<String, byte[]> images = getImages(uploadRequest);

		String articleURL = ParamUtil.getString(uploadRequest, "articleURL");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			JournalArticle.class.getName(), actionRequest);

		JournalArticle article = null;
		String oldUrlTitle = StringPool.BLANK;

		if (cmd.equals(Constants.ADD)) {
			if (Validator.isNull(structureId)) {
				content = LocalizationUtil.updateLocalization(
					StringPool.BLANK, "static-content", content,
					lastLanguageId, defaultLanguageId, true);
			}

			// Add article

			article = JournalArticleServiceUtil.addArticle(
				groupId, articleId, autoArticleId, title, description,
				content, type, structureId, templateId, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
				reviewDateHour, reviewDateMinute, neverReview, indexable,
				smallImage, smallImageURL, smallFile, images, articleURL,
				serviceContext);

			AssetPublisherUtil.addAndStoreSelection(
				actionRequest, JournalArticle.class.getName(),
				article.getResourcePrimKey(), -1);
		}
		else {

			// Merge current content with new content

			JournalArticle curArticle = JournalArticleServiceUtil.getArticle(
				groupId, articleId, version);

			if (Validator.isNull(structureId)) {
				if (!curArticle.isTemplateDriven()) {
					String curContent = StringPool.BLANK;

					boolean localized = ParamUtil.getBoolean(
						uploadRequest, "localized");

					if (localized) {
						curContent = curArticle.getContent();
					}

					content = LocalizationUtil.updateLocalization(
						curContent, "static-content", content,
						lastLanguageId, defaultLanguageId, true);
				}
			}
			else {
				if (curArticle.isTemplateDriven()) {
					JournalStructure structure = null;

					try {
						structure =
							JournalStructureLocalServiceUtil.getStructure(
								groupId, structureId);
					}
					catch (NoSuchStructureException nsse) {
						structure =
							JournalStructureLocalServiceUtil.getStructure(
								themeDisplay.getCompanyGroupId(), structureId);
					}

					content = JournalUtil.mergeArticleContent(
						curArticle.getContent(), content);
					content = JournalUtil.removeOldContent(
						content, structure.getMergedXsd());
				}
			}

			// Update article

			article = JournalArticleServiceUtil.getArticle(
				groupId, articleId, version);

			String tempOldUrlTitle = article.getUrlTitle();

			article = JournalArticleServiceUtil.updateArticle(
				groupId, articleId, version, title, description, content, type,
				structureId, templateId, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, indexable, smallImage,
				smallImageURL, smallFile, images, articleURL, serviceContext);

			if (!tempOldUrlTitle.equals(article.getUrlTitle())) {
				oldUrlTitle = tempOldUrlTitle;
			}
		}

		// Recent articles

		JournalUtil.addRecentArticle(actionRequest, article);

		// Journal content

		String portletResource = ParamUtil.getString(
			uploadRequest, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			PortletPreferences preferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					uploadRequest, portletResource);

			preferences.setValue(
				"group-id", String.valueOf(article.getGroupId()));
			preferences.setValue("article-id", article.getArticleId());

			preferences.store();

			updateContentSearch(
				actionRequest, portletResource, article.getArticleId());
		}

		return new Object[] {article, oldUrlTitle};
	}

	protected void updateContentSearch(
			ActionRequest actionRequest, String portletResource,
			String articleId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		JournalContentSearchLocalServiceUtil.updateContentSearch(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			portletResource, articleId);
	}

}