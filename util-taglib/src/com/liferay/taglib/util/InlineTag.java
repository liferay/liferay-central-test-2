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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Brian Wing Shun Chan
 */
public class InlineTag extends IncludeTag {

	public int doEndTag() throws JspException {
		try {
			callSetAttributes();

			return processEndTag();
		}
		catch (JspException jspe) {
			throw jspe;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			Map<String, Object> dynamicAttributes = getDynamicAttributes();

			dynamicAttributes.clear();

			clearParams();
			clearProperties();

			cleanUpSetAttributes();

			if (!ServerDetector.isResin()) {
				setPage(null);

				cleanUp();
			}
		}
	}

	public int doStartTag() throws JspException {
		try {
			callSetAttributes();

			return processStartTag();
		}
		catch (JspException jspe) {
			throw jspe;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	protected int processEndTag() throws Exception {
		return EVAL_PAGE;
	}

	protected int processStartTag() throws Exception {
		return EVAL_BODY_BUFFERED;
	}

	protected void writeDynamicAttributes(JspWriter jspWriter)
		throws IOException {

		String dynamicAttributesString = InlineUtil.buildDynamicAttributes(
			getDynamicAttributes());

		if (Validator.isNotNull(dynamicAttributesString)) {
			jspWriter.write(dynamicAttributesString);
		}
	}

}