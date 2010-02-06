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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="EventsProcessorUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class EventsProcessorUtil {

	public static void process(String key, String[] classes)
		throws ActionException {

		_instance.process(key, classes, null, null, null, null);
	}

	public static void process(String key, String[] classes, String[] ids)
		throws ActionException {

		_instance.process(key, classes, ids, null, null, null);
	}

	public static void process(
			String key, String[] classes, HttpSession session)
		throws ActionException {

		_instance.process(key, classes, null, null, null, session);
	}

	public static void process(
			String key, String[] classes, HttpServletRequest request,
			HttpServletResponse response)
		throws ActionException {

		_instance.process(key, classes, null, request, response, null);
	}

	public static void registerEvent(String key, Object event) {
		_instance.registerEvent(key, event);
	}

	public static void unregisterEvent(String key, Object event) {
		_instance.unregisterEvent(key, event);
	}

	private static EventsProcessor _instance = new EventsProcessorImpl();

}