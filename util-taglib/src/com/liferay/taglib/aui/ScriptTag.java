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

import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptTag extends BaseBodyTagSupport implements BodyTag {

	public static final String PAGE = "/html/taglib/aui/script/page.jsp";

	public static void doTag(
			String position, String use, String bodyContentString,
			PageContext pageContext)
		throws Exception {

		ScriptTag scriptTag = new ScriptTag();

		scriptTag.setPageContext(pageContext);
		scriptTag.setPosition(position);
		scriptTag.setUse(use);

		BodyContent bodyContent = pageContext.pushBody();

		scriptTag.setBodyContent(bodyContent);

		bodyContent.write(bodyContentString);

		pageContext.popBody();

		scriptTag.doEndTag();

		scriptTag.release();
	}

	public static void flushScriptData(PageContext pageContext)
		throws Exception {

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ScriptData scriptData = (ScriptData)request.getAttribute(
			ScriptTag.class.getName());

		if (scriptData == null) {
			scriptData = (ScriptData)request.getAttribute(
				WebKeys.AUI_SCRIPT_DATA);

			if (scriptData != null) {
				request.removeAttribute(WebKeys.AUI_SCRIPT_DATA);
			}
		}

		if (scriptData != null) {
			ScriptTag scriptTag = new ScriptTag();

			scriptTag.setPageContext(pageContext);

			scriptTag.processEndTag(scriptData);
		}
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

				if (FileAvailabilityUtil.isAvailable(
						pageContext.getServletContext(), PAGE)) {

					PortalIncludeUtil.include(pageContext, PAGE);
				}
				else {
					processEndTag(scriptData);
				}
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

	public void setPosition(String position) {
		_position = position;
	}

	public void setUse(String use) {
		_use = use;
	}

	protected void cleanUp() {
		_position = null;
		_use = null;
	}

	protected void processEndTag(ScriptData scriptData) throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<script type=\"text/javascript\">\n// <![CDATA[\n");

		StringBundler rawSB = scriptData.getRawSB();

		rawSB.writeTo(jspWriter);

		StringBundler callbackSB = scriptData.getCallbackSB();

		if (callbackSB.index() > 0) {
			String loadMethod = "use";

			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			if (BrowserSnifferUtil.isIe(request) &&
				(BrowserSnifferUtil.getMajorVersion(request) < 8)) {

				loadMethod = "ready";
			}

			jspWriter.write("AUI().");
			jspWriter.write( loadMethod );
			jspWriter.write("(");

			Set<String> useSet = scriptData.getUseSet();

			for (String use : useSet) {
				jspWriter.write(StringPool.APOSTROPHE);
				jspWriter.write(use);
				jspWriter.write(StringPool.APOSTROPHE);
				jspWriter.write(StringPool.COMMA_AND_SPACE);
			}

			jspWriter.write("function(A) {");

			callbackSB.writeTo(jspWriter);

			jspWriter.write("});");
		}

		jspWriter.write("\n// ]]>\n</script>");
	}

	private static final String _POSITION_AUTO = "auto";

	private static final String _POSITION_INLINE = "inline";

	private String _position;
	private String _use;

}