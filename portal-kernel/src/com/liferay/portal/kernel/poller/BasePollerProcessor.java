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

package com.liferay.portal.kernel.poller;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * <a href="BasePollerProcessor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class BasePollerProcessor implements PollerProcessor {

	public void receive(
			PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws PollerException {

		try {
			doReceive(pollerRequest, pollerResponse);
		}
		catch (Exception e) {
			throw new PollerException(e);
		}
	}

	public void send(PollerRequest pollerRequest) throws PollerException {
		try {
			doSend(pollerRequest);
		}
		catch (Exception e) {
			throw new PollerException(e);
		}
	}

	protected abstract void doReceive(
			PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws Exception;

	protected abstract void doSend(PollerRequest pollerRequest)
		throws Exception;

	protected boolean getBoolean(PollerRequest pollerRequest, String name) {
		return getBoolean(pollerRequest, name, GetterUtil.DEFAULT_BOOLEAN);
	}

	protected boolean getBoolean(
		PollerRequest pollerRequest, String name, boolean defaultValue) {

		return GetterUtil.getBoolean(
			pollerRequest.getParameterMap().get(name), defaultValue);
	}

	protected double getDouble(
		PollerRequest pollerRequest, String name) {

		return getDouble(pollerRequest, name, -1);
	}

	protected double getDouble(
		PollerRequest pollerRequest, String name, double defaultValue) {

		return GetterUtil.getDouble(
			pollerRequest.getParameterMap().get(name), defaultValue);
	}

	protected int getInteger(PollerRequest pollerRequest, String name) {
		return getInteger(pollerRequest, name, -1);
	}

	protected int getInteger(
		PollerRequest pollerRequest, String name, int defaultValue) {

		return GetterUtil.getInteger(
			pollerRequest.getParameterMap().get(name), defaultValue);
	}

	protected long getLong(PollerRequest pollerRequest, String name) {
		return getLong(pollerRequest, name, -1);
	}

	protected long getLong(
		PollerRequest pollerRequest, String name, long defaultValue) {

		return GetterUtil.getLong(
			pollerRequest.getParameterMap().get(name), defaultValue);
	}

	protected String getString(PollerRequest pollerRequest, String name) {
		return getString(pollerRequest, name, null);
	}

	protected String getString(
		PollerRequest pollerRequest, String name, String defaultValue) {

		return GetterUtil.getString(
			pollerRequest.getParameterMap().get(name), defaultValue);
	}

}