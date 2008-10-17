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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexer;

import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ExpandoBridgeIndexerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class ExpandoBridgeIndexerImpl implements ExpandoBridgeIndexer {

	public void addAttributes(Document doc, ExpandoBridge expandoBridge) {
		if (expandoBridge == null) {
			return;
		}

		Enumeration<String> enu = expandoBridge.getAttributeNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			int type = expandoBridge.getAttributeType(name);

			UnicodeProperties properties = expandoBridge.getAttributeProperties(
				name);

			boolean indexable = GetterUtil.getBoolean(
				properties.get(ExpandoBridgeIndexer.INDEXABLE));

			if (!indexable || (type != ExpandoColumnConstants.STRING)) {
				continue;
			}

			try {
				String value = ExpandoValueLocalServiceUtil.getData(
					expandoBridge.getClassName(),
					ExpandoTableConstants.DEFAULT_TABLE_NAME, name,
					expandoBridge.getClassPK(), StringPool.BLANK);

				doc.addText(name, value);
			}
			catch (Exception e) {
				_log.error("Indexing " + name, e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(ExpandoBridgeIndexerImpl.class);

}