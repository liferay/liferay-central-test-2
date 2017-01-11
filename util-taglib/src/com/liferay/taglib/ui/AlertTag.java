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

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.aui.ScriptTag;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class AlertTag extends IncludeTag {

	@Override
	public int doStartTag() {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int processEndTag() throws Exception {
		Map<String, String> values = new HashMap<>();

		values.put("animationTime", String.valueOf(_animationTime));
		values.put("closeable", String.valueOf(_closeable));
		values.put("icon", String.valueOf(_icon));
		values.put("message", HtmlUtil.escapeJS(_message));

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse == null) {
			values.put("namespace", StringPool.BLANK);
		}
		else {
			values.put("namespace", portletResponse.getNamespace());
		}

		values.put("targetNode", _targetNode);
		values.put("timeout", String.valueOf(_timeout));
		values.put("title", _title);
		values.put("type", _type);

		String result = StringUtil.replace(
			_alertTemplate, StringPool.POUND, StringPool.POUND, values);

		ScriptTag.doTag(
			null, null, "liferay-alert", result, getBodyContent(), pageContext);

		return EVAL_PAGE;
	}

	public void setAnimationTime(Integer animationTime) {
		_animationTime = animationTime;
	}

	public void setCloseable(boolean closeable) {
		_closeable = closeable;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDestroyOnHide(boolean destroyOnHide) {
		_destroyOnHide = destroyOnHide;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setTargetNode(String targetNode) {
		_targetNode = targetNode;
	}

	public void setTimeout(Integer timeout) {
		_timeout = timeout;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_animationTime = 500;
		_closeable = true;
		_icon = "info-circle";
		_message = StringPool.BLANK;
		_cssClass = null;
		_destroyOnHide = false;
		_targetNode = StringPool.BLANK;
		_timeout = -1;
		_title = StringPool.BLANK;
		_type = "info";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
	}

	private static final String _alertTemplate;

	static {
		try (InputStream inputStream = AlertTag.class.getResourceAsStream(
				"alert/alert.tmpl")) {

			_alertTemplate = StringUtil.read(inputStream);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

	private static final String _ATTRIBUTE_NAMESPACE = "liferay-ui:alert:";

	private static final String _PAGE = "/html/taglib/ui/alert/page.jsp";

	private Integer _animationTime = 500;
	private boolean _closeable = true;
	private String _cssClass;
	private boolean _destroyOnHide;
	private String _icon = "info-circle";
	private String _message = StringPool.BLANK;
	private String _targetNode = StringPool.BLANK;
	private Integer _timeout = -1;
	private String _title = StringPool.BLANK;
	private String _type = "info";

}