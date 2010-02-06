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

package com.liferay.portal.scripting;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;

import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * <a href="ScriptingProxyBean.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ScriptingProxyBean extends BaseProxyBean implements Scripting {

	public void addScriptionExecutor(
		String language, ScriptingExecutor scriptingExecutor) {

		throw new UnsupportedOperationException();
	}

	public void clearCache(String language) {
		throw new UnsupportedOperationException();
	}

	public Map<String, Object> eval(
		Set<String> allowedClasses, Map<String, Object> inputObjects,
		Set<String> outputNames, String language, String script) {

		throw new UnsupportedOperationException();
	}

	public void exec(
		Set<String> allowedClasses, Map<String, Object> inputObjects,
		String language, String script) {

		throw new UnsupportedOperationException();
	}

	public Map<String, Object> getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		throw new UnsupportedOperationException();
	}

	public Set<String> getSupportedLanguages() {
		throw new UnsupportedOperationException();
	}

	public void setScriptingExecutors(
		Map<String, ScriptingExecutor> scriptingExecutors) {

		throw new UnsupportedOperationException();
	}

}