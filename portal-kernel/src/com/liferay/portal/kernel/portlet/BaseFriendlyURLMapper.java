/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.PortalClassInvoker;

import java.util.Map;

/**
 * <a href="BaseFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public abstract class BaseFriendlyURLMapper implements FriendlyURLMapper {

	public abstract String getPortletId();

	public boolean isCheckMappingWithPrefix() {
		return _CHECK_MAPPING_WITH_PREFIX;
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
			if (!_isReservedParameter(name)) {
				Map<String, String> prpIdentifers =
					FriendlyURLMapperThreadLocal.getPRPIdentifiers();

				if (prpIdentifers.containsKey(name)) {
					name = prpIdentifers.get(name);
				}
				else {
					name = getNamespace() + name;
				}
			}

			params.put(name, new String[] {value});
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected String getNamespace() {
		try {
			return _getPortletNamespace(getPortletId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return getPortletId();
		}
	}

	private String _getPortletNamespace(String portletId)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GETPORTLETNAMESPACE, portletId, false);

		if (returnObj != null) {
			return (String)returnObj;
		}
		else {
			return null;
		}
	}

	private boolean _isReservedParameter(String name) throws Exception {
		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_ISRESERVEDPARAMETER, name, false);

		if (returnObj != null) {
			return (Boolean)returnObj;
		}
		else {
			return false;
		}
	}

	private static final boolean _CHECK_MAPPING_WITH_PREFIX = true;

	private static final String _CLASS = "com.liferay.portal.util.PortalUtil";

	private static final String _METHOD_GETPORTLETNAMESPACE =
		"getPortletNamespace";

	private static final String _METHOD_ISRESERVEDPARAMETER =
		"isReservedParameter";

	private static Log _log =
		LogFactoryUtil.getLog(BaseFriendlyURLMapper.class);

}