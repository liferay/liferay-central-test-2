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

import java.util.Collections;
import java.util.List;

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

		List<String> attributeNames = Collections.list(
			expandoBridge.getAttributeNames());

		for (String attributeName : attributeNames) {
			int attributeType = expandoBridge.getAttributeType(attributeName);
			UnicodeProperties attributeProperties =
				expandoBridge.getAttributeProperties(attributeName);

			boolean propertyIndexable = GetterUtil.getBoolean(
				attributeProperties.get(ExpandoBridgeIndexer.INDEXABLE));

			if ((attributeType == ExpandoColumnConstants.STRING) &&
				(propertyIndexable)) {

				try {
					String attributeValue =
						ExpandoValueLocalServiceUtil.getData(
							expandoBridge.getClassName(),
							ExpandoTableConstants.DEFAULT_TABLE_NAME,
							attributeName, expandoBridge.getClassPK(),
							StringPool.BLANK);

					doc.addText(attributeName, attributeValue);
				}
				catch (Exception e) {
					_log.error("Indexing " + attributeName, e);
				}
			}
		}
	}

	private static Log _log = LogFactory.getLog(ExpandoBridgeIndexerImpl.class);

}
