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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Carlos Lancha
 * @generated
 */
public class PortletAlertTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return EVAL_BODY_INCLUDE;
	}

	public void setAnimationTime(Double animationTime) {
		_animationTime = animationTime;

		setScopedAttribute("animationTime", animationTime);
	}

	public void setCloseable(boolean closeable) {
		_closeable = closeable;

		setScopedAttribute("closeable", closeable);
	}

	public void setContent(String content) {
		_content = content;

		setScopedAttribute("content", content);
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setDestroyOnHide(boolean destroyOnHide) {
		_destroyOnHide = destroyOnHide;

		setScopedAttribute("destroyOnHide", destroyOnHide);
	}

	public void setTargetContainer(String targetContainer) {
		_targetContainer = targetContainer;

		setScopedAttribute("targetContainer", targetContainer);
	}

	public void setTimeout(Double timeout) {
		_timeout = timeout;

		setScopedAttribute("timeout", timeout);
	}

	public void setType(String type) {
		_type = type;

		setScopedAttribute("type", type);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_animationTime = 0.5;
		_closeable = true;
		_content = null;
		_cssClass = null;
		_destroyOnHide = false;
		_targetContainer = null;
		_timeout = -1.0;
		_type = "info";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "animationTime", _animationTime);
		setNamespacedAttribute(request, "closeable", _closeable);
		setNamespacedAttribute(request, "content", _content);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "destroyOnHide", _destroyOnHide);
		setNamespacedAttribute(request, "targetContainer", _targetContainer);
		setNamespacedAttribute(request, "timeout", _timeout);
		setNamespacedAttribute(request, "type", _type);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = 
		"liferay-ui:portlet-alert:";

	private static final String _PAGE =
		"/html/taglib/ui/portlet_alert/page.jsp";

	private Double _animationTime = 0.5;
	private boolean _closeable = true;
	private String _content = null;
	private String _cssClass = null;
	private boolean _destroyOnHide = false;
	private String _targetContainer = null;
	private Double _timeout = -1.0;
	private String _type = "info";

}