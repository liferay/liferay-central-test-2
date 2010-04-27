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

package com.liferay.portlet.journal.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.workflow.BaseWorkflowHandler;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.journal.asset.JournalArticleAssetRenderer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * <a href="JournalArticleWorkflowHandler.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public class JournalArticleWorkflowHandler extends BaseWorkflowHandler {

	public static final String CLASS_NAME = JournalArticle.class.getName();

	public String getClassName() {
		return CLASS_NAME;
	}

	public String getType() {
		return TYPE_CONTENT;
	}

	public JournalArticle updateStatus(
			int status, Map<String, Serializable> contextVariables)
		throws PortalException, SystemException {

		long userId = (Long) contextVariables.get("userId");
		long classPK = (Long) contextVariables.get("classPK");
		long groupId = (Long) contextVariables.get("groupId");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		return JournalArticleLocalServiceUtil.updateStatus(
			userId, classPK, status, serviceContext);
	}

	protected AssetRenderer getAssetRenderer(long classPK)
		throws PortalException, SystemException {

		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			classPK);

		return new JournalArticleAssetRenderer(article);
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

}