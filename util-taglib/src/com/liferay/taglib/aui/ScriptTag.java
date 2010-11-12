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
import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptTag extends BaseBodyTagSupport {

	public static final String PAGE = "/html/taglib/aui/script/page.jsp";

	public static void doTag(
			PageContext pageContext, String position, String use, String body)
		throws Exception {

		ScriptTag scriptTag = new ScriptTag();

		scriptTag.setPageContext(pageContext);

		scriptTag._position = position;
		scriptTag._use = use;

		BodyContent bodyContent = pageContext.pushBody();
		scriptTag.setBodyContent(bodyContent);

		bodyContent.write(body);

		pageContext.popBody();

		scriptTag.doEndTag();

		scriptTag.release();
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		String position = _position;

		String fragmentId = ParamUtil.getString(request, "p_f_id");

		if (Validator.isNotNull(fragmentId)) {
			position = _POSITION_INLINE;
		}

		try {
			if (Validator.isNull(position)) {
				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				if (themeDisplay.isIsolated() ||
					themeDisplay.isLifecycleResource() ||
					themeDisplay.isStateExclusive()) {

					position = _POSITION_INLINE;
				}
				else {
					position = _POSITION_AUTO;
				}
			}

			StringBundler bodyContentSB = getBodyContentAsStringBundler();

			if (position.equals(_POSITION_INLINE)) {
				ScriptData scriptData = new ScriptData();

				request.setAttribute(ScriptTag.class.getName(), scriptData);

				scriptData.append(bodyContentSB, _use);

				PortalIncludeUtil.include(pageContext, PAGE);
			}
			else {
				ScriptData scriptData = (ScriptData)request.getAttribute(
					WebKeys.AUI_SCRIPT_DATA);

				if (scriptData == null) {
					scriptData = new ScriptData();

					request.setAttribute(WebKeys.AUI_SCRIPT_DATA, scriptData);
				}

				scriptData.append(bodyContentSB, _use);
			}

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (position.equals(_POSITION_INLINE)) {
				request.removeAttribute(ScriptTag.class.getName());
			}

			if (!ServerDetector.isResin()) {
				cleanUp();
			}
		}
	}

	protected void cleanUp() {
		_position = null;
		_use = null;
	}

	public void setPosition(String position) {
		_position = position;
	}

	public void setUse(String use) {
		_use = use;
	}

	private static final String _POSITION_AUTO = "auto";

	private static final String _POSITION_INLINE = "inline";

	private String _position;
	private String _use;

}