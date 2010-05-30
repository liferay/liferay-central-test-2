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

package com.liferay.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BodyContentWrapper;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <a href="BaseBodyTagSupport.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class BaseBodyTagSupport extends BodyTagSupport {

	public StringBundler getBodyContentAsStringBundler() {
		BodyContent bodyContent = getBodyContent();
		if (bodyContent instanceof BodyContentWrapper) {
			BodyContentWrapper bodyContentWrapper =
				(BodyContentWrapper) bodyContent;
			return bodyContentWrapper.getStringBundler();
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("This BodyContent is not BodyContentWrapper " +
					"which should not happen, double check you deploy " +
					"JspFactorySwapper properly. Optimization will fall back " +
					"to Normal String handover.");
			}
			String bodyContentString = bodyContent.getString();
			if (bodyContentString == null) {
				bodyContentString = StringPool.BLANK;
			}
			return new StringBundler(bodyContentString);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BaseBodyTagSupport.class);

}