/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.taglib.aui.base.BaseFormTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class FormTag extends BaseFormTag {

	public void setAction(PortletURL portletURL) {
		if (portletURL != null) {
			setAction(portletURL.toString());
		}
	}

	protected void cleanUp() {
		super.cleanUp();

		_escapeXml = true;
		_name = "fm";
		_useNamespace = true;

		if (_validatorTagsMap != null) {
			for (List<ValidatorTag> validatorTags :
					_validatorTagsMap.values()) {

				for (ValidatorTag validatorTag : validatorTags) {
					validatorTag.cleanUp();
				}
			}

			_validatorTagsMap.clear();
		}
	}

	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		if (_escapeXml) {
			super.setAction(HtmlUtil.escape(_action));
		}

		request.setAttribute(
				"aui:form:inlineLabels", String.valueOf(_inlineLabels));
		request.setAttribute(
				"aui:form:useNamespace", String.valueOf(_useNamespace));
		request.setAttribute("aui:form:validatorTagsMap", _validatorTagsMap);
	}

	private Map<String, List<ValidatorTag>> _validatorTagsMap =
		new HashMap<String, List<ValidatorTag>>();

	protected boolean _useNamespace = true;

}