/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import javax.portlet.PortletPreferences;

/**
 * <a href="InvokerPortletDataHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class InvokerPortletDataHandler implements PortletDataHandler {

	public InvokerPortletDataHandler(
		PortletDataHandler portletDataHandler, ClassLoader classLoader) {

		_portletDataHandler = portletDataHandler;
		_classLoader = classLoader;
	}

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		Thread currentThread = Thread.currentThread();

		ClassLoader threadClassLoader = currentThread.getContextClassLoader();
		ClassLoader contextClassLoader = context.getClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);
			context.setClassLoader(_classLoader);

			return _portletDataHandler.deleteData(
				context, portletId, preferences);
		}
		finally {
			currentThread.setContextClassLoader(threadClassLoader);
			context.setClassLoader(contextClassLoader);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		Thread currentThread = Thread.currentThread();

		ClassLoader threadClassLoader = currentThread.getContextClassLoader();
		ClassLoader contextClassLoader = context.getClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);
			context.setClassLoader(_classLoader);

			return _portletDataHandler.exportData(
				context, portletId, preferences);
		}
		finally {
			currentThread.setContextClassLoader(threadClassLoader);
			context.setClassLoader(contextClassLoader);
		}
	}

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			return _portletDataHandler.getExportControls();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			return _portletDataHandler.getImportControls();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws PortletDataException {

		Thread currentThread = Thread.currentThread();

		ClassLoader threadClassLoader = currentThread.getContextClassLoader();
		ClassLoader contextClassLoader = context.getClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);
			context.setClassLoader(_classLoader);

			return _portletDataHandler.importData(
				context, portletId, preferences, data);
		}
		finally {
			currentThread.setContextClassLoader(threadClassLoader);
			context.setClassLoader(contextClassLoader);
		}
	}

	public boolean isAlwaysExportable() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			return _portletDataHandler.isAlwaysExportable();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public boolean isPublishToLiveByDefault() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			return _portletDataHandler.isPublishToLiveByDefault();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private PortletDataHandler _portletDataHandler;
	private ClassLoader _classLoader;

}