/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Map;

/**
 * <a href="BaseFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public abstract class BaseFriendlyURLMapper implements FriendlyURLMapper {

	public abstract String getPortletId();

	protected String getNamespace() {
		try {
			return PortalUtil.getPortletNamespace(getPortletId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return getPortletId();
		}
	}

	protected void addParam(
		Map<String, String[]> params, String name, boolean value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, double value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, int value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, long value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, short value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, Object value) {

		addParam(params, name, String.valueOf(value));
	}

	protected void addParam(
		Map<String, String[]> params, String name, String value) {

		try {
			if (PortalUtil.isReservedParameter(name)) {
				name = getNamespace() + name;
			}

			params.put(name, new String[] {value});
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(BaseFriendlyURLMapper.class);

}