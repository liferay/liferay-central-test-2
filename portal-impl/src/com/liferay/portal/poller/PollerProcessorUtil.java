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

package com.liferay.portal.poller;

import com.liferay.portal.kernel.poller.PollerProcessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="PollerProcessorUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PollerProcessorUtil {

	public static void addPollerProcessor(
		String portletId, PollerProcessor pollerProcessor) {

		_instance._addPollerProcessor(portletId, pollerProcessor);
	}

	public static void deletePollerProcessor(String portletId) {
		_instance._deletePollerProcessor(portletId);
	}

	public static PollerProcessor getPollerProcessor(String portletId) {
		return _instance._getPollerProcessor(portletId);
	}

	private PollerProcessorUtil() {
	}

	private void _addPollerProcessor(
		String portletId, PollerProcessor pollerProcessor) {

		_pollerPorcessors.put(portletId, pollerProcessor);
	}

	private void _deletePollerProcessor(String portletId) {
		_pollerPorcessors.remove(portletId);
	}

	private PollerProcessor _getPollerProcessor(String portletId) {
		return _pollerPorcessors.get(portletId);
	}

	private static PollerProcessorUtil _instance = new PollerProcessorUtil();

	private Map<String, PollerProcessor> _pollerPorcessors =
		new ConcurrentHashMap<String, PollerProcessor>();

}