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

package com.liferay.portal.plugin;

import com.liferay.util.Validator;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <a href="RepositoryReport.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class RepositoryReport {
	public static final String SUCCESS = "success";

	public void addSuccess(String repositoryURL) {
		_reportMap.put(repositoryURL, SUCCESS);
	}

	public void addError(String repositoryURL, PluginException pe) {
		StringBuffer message = new StringBuffer();

		if (Validator.isNotNull(pe.getMessage())) {
			message.append(pe.getMessage());
		}

		if ((pe.getCause() != null)
				&& Validator.isNull(pe.getCause().getMessage())) {
			message.append(pe.getCause().getMessage());
		}

		if (message.length() == 0) {
			message.append(pe.toString());
		}

		_reportMap.put(repositoryURL, message.toString());
	}

	public Set getRepositoryURLs() {
		return _reportMap.keySet();
	}

	public Object getState(String repositoryURL) {
		return _reportMap.get(repositoryURL);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		Iterator it = getRepositoryURLs().iterator();
		while (it.hasNext()) {
			String repositoryURL = (String) it.next();
			sb.append(repositoryURL).append(": ");
			sb.append("" + _reportMap.get(repositoryURL));
		}
		return sb.toString();
	}

	private Map _reportMap = new TreeMap();

}