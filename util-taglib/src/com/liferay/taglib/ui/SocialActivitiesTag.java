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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="SocialActivitiesTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class SocialActivitiesTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute(
			"liferay-ui:social-activities:className", _className);
		request.setAttribute(
			"liferay-ui:social-activities:classPK", String.valueOf(_classPK));

		if (_activities != null) {
			request.setAttribute(
				"liferay-ui:social-activities:activities", _activities);
		}

		request.setAttribute(
			"liferay-ui:social-activities:feedEnabled",
			String.valueOf(_feedEnabled));
		request.setAttribute(
			"liferay-ui:social-activities:feedTitle", _feedTitle);
		request.setAttribute(
			"liferay-ui:social-activities:feedLink", _feedLink);
		request.setAttribute(
			"liferay-ui:social-activities:feedLinkMessage", _feedLinkMessage);

		return EVAL_BODY_BUFFERED;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setActivities(List<SocialActivity> activities) {
		_activities = activities;
	}

	public void setFeedEnabled(boolean feedEnabled) {
		_feedEnabled = feedEnabled;
	}

	public void setFeedTitle(String feedTitle) {
		_feedTitle = feedTitle;
	}

	public void setFeedLink(String feedLink) {
		_feedLink = feedLink;
	}

	public void setFeedLinkMessage(String feedLinkMessage) {
		_feedLinkMessage = feedLinkMessage;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/ui/social_activities/page.jsp";

	private String _className = StringPool.BLANK;
	private long _classPK;
	private List<SocialActivity> _activities;
	private boolean _feedEnabled;
	private String _feedTitle;
	private String _feedLink = StringPool.BLANK;
	private String _feedLinkMessage = StringPool.BLANK;

}