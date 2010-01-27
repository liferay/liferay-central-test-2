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

package com.liferay.documentlibrary.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;

import javax.portlet.PortletURL;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class Indexer extends BaseIndexer {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public Summary getSummary(
		Document document, String snippet, PortletURL portletURL) {

		return null;
	}

	public void reindex(String className, long classPK) {
	}

	public void reindex(String[] ids) throws SearchException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		Hook hook = HookFactory.getInstance();

		hook.reindex(ids);
	}

	private static final String[] _CLASS_NAMES = new String[0];

}