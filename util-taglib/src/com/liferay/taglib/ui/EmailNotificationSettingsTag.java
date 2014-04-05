/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EmailNotificationSettingsTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setBodyLabel(String bodyLabel) {
		_bodyLabel = bodyLabel;
	}

	public void setEmailBody(String emailBody) {
		_emailBody = emailBody;
	}

	public void setEmailDefinitionTerms(
		Map<String, String> emailDefinitionTerms) {

		_emailDefinitionTerms = emailDefinitionTerms;
	}

	public void setEmailEnabled(boolean emailEnabled) {
		_emailEnabled = emailEnabled;
	}

	public void setEmailParam(String emailParam) {
		_emailParam = emailParam;
	}

	public void setEmailSubject(String emailSubject) {
		_emailSubject = emailSubject;
	}

	public void setFieldPrefix(String fieldPrefix) {
		_fieldPrefix = fieldPrefix;
	}

	public void setFieldPrefixSeparator(String fieldPrefixSeparator) {
		_fieldPrefixSeparator = fieldPrefixSeparator;
	}

	public void setHelpMessage(String helpMessage) {
		_helpMessage = helpMessage;
	}

	public void setShowEmailEnabled(boolean showEmailEnabled) {
		_showEmailEnabled = showEmailEnabled;
	}

	public void setShowSubject(boolean showSubject) {
		_showSubject = showSubject;
	}

	@Override
	protected void cleanUp() {
		_bodyLabel = null;
		_emailBody = null;
		_emailDefinitionTerms = null;
		_emailEnabled = false;
		_emailParam = null;
		_emailSubject = null;
		_fieldPrefix = null;
		_fieldPrefixSeparator = null;
		_helpMessage = null;
		_showEmailEnabled = true;
		_showSubject = true;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if (Validator.isNull(_bodyLabel)) {
			_bodyLabel = "body";
		}

		if (Validator.isNull(_fieldPrefix)) {
			_fieldPrefix = "preferences";
		}

		if (Validator.isNull(_fieldPrefixSeparator)) {
			_fieldPrefixSeparator = "--";
		}

		request.setAttribute(
			"liferay-ui:email-notification-settings:bodyLabel", _bodyLabel);
		request.setAttribute(
			"liferay-ui:email-notification-settings:emailBody", _emailBody);
		request.setAttribute(
			"liferay-ui:email-notification-settings:emailDefinitionTerms",
			_emailDefinitionTerms);
		request.setAttribute(
			"liferay-ui:email-notification-settings:emailEnabled",
			String.valueOf(_emailEnabled));
		request.setAttribute(
			"liferay-ui:email-notification-settings:emailParam", _emailParam);
		request.setAttribute(
			"liferay-ui:email-notification-settings:emailSubject",
			_emailSubject);
		request.setAttribute(
			"liferay-ui:email-notification-settings:fieldPrefix", _fieldPrefix);
		request.setAttribute(
			"liferay-ui:email-notification-settings:fieldPrefixSeparator",
			_fieldPrefixSeparator);
		request.setAttribute(
			"liferay-ui:email-notification-settings:helpMessage", _helpMessage);
		request.setAttribute(
			"liferay-ui:email-notification-settings:showEmailEnabled",
			_showEmailEnabled);
		request.setAttribute(
			"liferay-ui:email-notification-settings:showSubject", _showSubject);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE =
		"/html/taglib/ui/email_notification_settings/page.jsp";

	private String _bodyLabel;
	private String _emailBody;
	private Map<String, String> _emailDefinitionTerms;
	private boolean _emailEnabled;
	private String _emailParam;
	private String _emailSubject;
	private String _fieldPrefix;
	private String _fieldPrefixSeparator;
	private String _helpMessage;
	private boolean _showEmailEnabled = true;
	private boolean _showSubject = true;

}