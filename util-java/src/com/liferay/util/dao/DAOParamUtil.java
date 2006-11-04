/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.util.dao;

import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import javax.portlet.PortletRequest;

import javax.servlet.ServletRequest;

/**
 * <a href="DAOParamUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DAOParamUtil {

	// Servlet Request

	public static String getLike(ServletRequest req, String param) {
		return getLike(req, param, true);
	}

	public static String getLike(
		ServletRequest req, String param, boolean toLowerCase) {

		String value = req.getParameter(param);

		if (value != null) {
			value = value.trim();

			if (toLowerCase) {
				value = value.toLowerCase();
			}
		}

		if (Validator.isNull(value)) {
			value = null;
		}
		else {
			value = StringPool.PERCENT + value + StringPool.PERCENT;
		}

		return value;
	}

	public static String getString(ServletRequest req, String param) {
		String value = ParamUtil.getString(req, param);

		if (Validator.isNull(value)) {
			return null;
		}
		else {
			return value;
		}
	}

	// Portlet Request

	public static String getLike(PortletRequest req, String param) {
		return getLike(req, param, true);
	}

	public static String getLike(
		PortletRequest req, String param, boolean toLowerCase) {

		String value = req.getParameter(param);

		if (value != null) {
			value = value.trim();

			if (toLowerCase) {
				value = value.toLowerCase();
			}
		}

		if (Validator.isNull(value)) {
			value = null;
		}
		else {
			value = StringPool.PERCENT + value + StringPool.PERCENT;
		}

		return value;
	}

	public static String getString(PortletRequest req, String param) {
		String value = ParamUtil.getString(req, param);

		if (Validator.isNull(value)) {
			return null;
		}
		else {
			return value;
		}
	}

}