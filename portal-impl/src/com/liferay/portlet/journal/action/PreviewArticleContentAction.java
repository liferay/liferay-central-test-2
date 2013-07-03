/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;

import java.util.Date;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Roberto Díaz
 */
public class PreviewArticleContentAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			String articleId = ParamUtil.getString(actionRequest, "articleId");
			double version = ParamUtil.getDouble(
				actionRequest, "version",
				JournalArticleConstants.VERSION_DEFAULT);

			String languageId = LanguageUtil.getLanguageId(actionRequest);

			String output = null;

			if (cmd.equals(Constants.PREVIEW)) {
				String title = ParamUtil.getString(actionRequest, "title");
				String description = ParamUtil.getString(
					actionRequest, "description");
				String type = ParamUtil.getString(actionRequest, "type");
				String structureId = ParamUtil.getString(
					actionRequest, "structureId");
				String templateId = ParamUtil.getString(
					actionRequest, "templateId");

				Date now = new Date();

				Date createDate = now;
				Date modifiedDate = now;
				Date displayDate = now;

				User user = PortalUtil.getUser(actionRequest);

				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(
						JournalArticle.class.getName(), actionRequest);

				Object[] returnValue = ActionUtil.getContentAndImages(
					groupId, structureId, themeDisplay.getLocale(),
					serviceContext);

				String content = (String)returnValue[0];

				Map<String, String> tokens = JournalUtil.getTokens(
					groupId, themeDisplay);

				tokens.put("article_resource_pk", "-1");

				JournalArticle article = new JournalArticleImpl();

				article.setGroupId(groupId);
				article.setCompanyId(user.getCompanyId());
				article.setUserId(user.getUserId());
				article.setUserName(user.getFullName());
				article.setCreateDate(createDate);
				article.setModifiedDate(modifiedDate);
				article.setArticleId(articleId);
				article.setVersion(version);
				article.setTitle(title);
				article.setDescription(description);
				article.setContent(content);
				article.setType(type);
				article.setStructureId(structureId);
				article.setTemplateId(templateId);
				article.setDisplayDate(displayDate);

				output = JournalArticleLocalServiceUtil.getArticleContent(
					article, templateId, null, languageId, themeDisplay);
			}
			else if (cmd.equals(Constants.VIEW)) {
				JournalArticle article = JournalArticleServiceUtil.getArticle(
					groupId, articleId, version);

				output = JournalArticleLocalServiceUtil.getArticleContent(
					article, article.getTemplateId(), null, languageId,
					themeDisplay);
			}
			else {
				output = JournalArticleServiceUtil.getArticleContent(
					groupId, articleId, version, languageId, themeDisplay);
			}

			actionRequest.setAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT, output);

			if (output.startsWith("<?xml ")) {
				setForward(
					actionRequest, "portlet.journal.raw_article_content");
			}
			else {
				setForward(
					actionRequest, "portlet.journal.view_article_content");
			}
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());

			setForward(actionRequest, "portlet.journal.error");
		}
	}

}