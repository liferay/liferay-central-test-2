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

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.taglib.aui.ScriptData;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <a href="ScriptTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ScriptTag extends BodyTagSupport {

	public static final String PAGE = "/html/taglib/aui/script/page.jsp";

	public int doStartTag() {
		return EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() {
		_bodyContentString = getBodyContent().getString();

		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		try {
			if (Validator.isNull(_position)) {
				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				if (themeDisplay.isIsolated() ||
					themeDisplay.isLifecycleResource() ||
					themeDisplay.isStateExclusive()) {

					_position = _POSITION_INLINE;
				}
				else {
					_position = _POSITION_AUTO;
				}
			}

			if (_position.equals(_POSITION_INLINE)) {
				ScriptData scriptData = new ScriptData();

				request.setAttribute(ScriptTag.class.getName(), scriptData);

				scriptData.append(_bodyContentString, _use);

				PortalIncludeUtil.include(pageContext, PAGE);
			}
			else {
				ScriptData scriptData = (ScriptData)request.getAttribute(
					WebKeys.AUI_SCRIPT_DATA);

				if (scriptData == null) {
					scriptData = new ScriptData();

					request.setAttribute(WebKeys.AUI_SCRIPT_DATA, scriptData);
				}

				scriptData.append(_bodyContentString, _use);
			}

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (_position.equals(_POSITION_INLINE)) {
				request.removeAttribute(ScriptTag.class.getName());
			}

			if (!ServerDetector.isResin()) {
				_bodyContentString = StringPool.BLANK;
				_position = null;
				_use = null;
			}
		}
	}

	public void setPosition(String position) {
		_position = position;
	}

	public void setUse(String use) {
		_use = use;
	}

	private static final String _POSITION_AUTO = "auto";

	private static final String _POSITION_INLINE = "inline";

	private String _bodyContentString = StringPool.BLANK;
	private String _position;
	private String _use;

}