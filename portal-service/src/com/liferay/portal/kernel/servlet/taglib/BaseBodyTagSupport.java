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

package com.liferay.portal.kernel.servlet.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BodyContentWrapper;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * This class is a little tricky. It behaves like a base
 * {@link javax.servlet.jsp.tagext.BodyTag} implementation, but not implementing
 * the {@link javax.servlet.jsp.tagext.BodyTag} interface, just a standard
 * {@link javax.servlet.jsp.tagext.Tag} interface.<p>
 * I do this because {@link javax.servlet.jsp.tagext.BodyTag} extends
 * {@link javax.servlet.jsp.tagext.IterationTag} which will be compiled to a
 * do-while loop, even most time we just want to include the body content
 * without manipulating it. In a complex page with a lot nesting tags, this
 * could create a very deep nesting do-while loops which can totally disable
 * compiler's optimization.<p>
 * By just implementing {@link javax.servlet.jsp.tagext.Tag}, we are loop free.
 * For sub-classes who do need to manipulate body content, they can simply just
 * mark themselves implementing {@link javax.servlet.jsp.tagext.BodyTag}.
 * Since this class has already implemented all the needed methods for
 * {@link javax.servlet.jsp.tagext.BodyTag}, JVM can cleverly bind to the right
 * methods in this class.
 *
 * @author Shuyang Zhou
 */
public class BaseBodyTagSupport extends TagSupport {

	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}

	public void doInitBody() throws JspException {
	}

	public int doStartTag() throws JspException {
		return BodyTag.EVAL_BODY_BUFFERED;
	}

	public BodyContent getBodyContent() {
		return bodyContent;
	}

	public StringBundler getBodyContentAsStringBundler() {
		if (!(this instanceof BodyTag)) {
			throw new RuntimeException(
				getClass().getName() + " must implement " +
					BodyTag.class.getName());
		}

		BodyContent bodyContent = getBodyContent();

		if (bodyContent instanceof BodyContentWrapper) {
			BodyContentWrapper bodyContentWrapper =
				(BodyContentWrapper)bodyContent;

			return bodyContentWrapper.getStringBundler();
		}
		else {
			if (ServerDetector.isTomcat() && _log.isWarnEnabled()) {
				_log.warn(
					"BodyContent is not BodyContentWrapper. Check " +
						"JspFactorySwapper.");
			}

			String bodyContentString = bodyContent.getString();

			if (bodyContentString == null) {
				bodyContentString = StringPool.BLANK;
			}

			return new StringBundler(bodyContentString);
		}
	}

	public void release() {
		bodyContent = null;
		super.release();
	}

	public void setBodyContent(BodyContent bodyContent) {
		this.bodyContent = bodyContent;
	}

	public void writeBodyContent(Writer writer) throws IOException {
		StringBundler sb = getBodyContentAsStringBundler();

		sb.writeTo(writer);
	}

	private static Log _log = LogFactoryUtil.getLog(BaseBodyTagSupport.class);

	protected BodyContent bodyContent;

}