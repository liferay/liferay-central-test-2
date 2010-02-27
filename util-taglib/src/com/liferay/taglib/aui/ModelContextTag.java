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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.util.ServerDetector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <a href="ModelContextTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class ModelContextTag extends BodyTagSupport {

	public int doEndTag() throws JspException {
		try{
			return super.doEndTag();
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_bean = null;
				_model = null;
			}
		}
	}

	public int doStartTag() {
		if (_model != null) {
			pageContext.setAttribute("aui:model-context:bean", _bean);
			pageContext.setAttribute("aui:model-context:model", _model);
		}
		else {
			pageContext.removeAttribute("aui:model-context:bean");
			pageContext.removeAttribute("aui:model-context::model");
		}

		return SKIP_BODY;
	}

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setModel(Class<?> model) {
		_model = model;
	}

	private Object _bean;
	private Class<?> _model;

}