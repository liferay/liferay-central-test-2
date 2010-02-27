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

import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.workflow.BaseWorkflowHandler;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

/**
 * <a href="JournalArticleWorkflowHandler.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 * @author Marcellus Tavares
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
			long companyId, long groupId, long userId, long classPK, int status)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		return JournalArticleLocalServiceUtil.updateStatus(
			userId, classPK, status, serviceContext);
	}

}