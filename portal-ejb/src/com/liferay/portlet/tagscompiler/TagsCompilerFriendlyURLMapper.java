/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tagscompiler;

import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.util.StringUtil;

/**
 * <a href="TagsCompilerFriendlyURLMapper.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsCompilerFriendlyURLMapper implements FriendlyURLMapper {

	public String getMapping() {
		return _MAPPING;
	}

	public String[] getValues(String url, int pos) {
		String friendlyURL = url.substring(0, pos);

		String queryString = _QUERY_STRING;

		int x = url.indexOf(StringPool.SLASH, pos + 1);
		int y = url.length();

		String[] tagsEntries = StringUtil.split(
			url.substring(x + 1, y), StringPool.SLASH);

		if (tagsEntries.length > 0) {
			StringBuffer sb = new StringBuffer();

			sb.append(queryString);
			sb.append(StringPool.AMPERSAND);
			sb.append(
				PortalUtil.getPortletNamespace(PortletKeys.TAGS_COMPILER));
			sb.append("tagsEntries=");

			for (int i = 0; i < tagsEntries.length; i++) {
				String tagsEntry = StringUtil.replace(
					tagsEntries[i], StringPool.PLUS, StringPool.SPACE);

				if (i != 0) {
					sb.append(StringPool.COMMA);
				}

				sb.append(tagsEntry);
			}

			queryString = sb.toString();
		}

		return new String[] {friendlyURL, queryString};
	}

	private static final String _MAPPING = "tags";

	private static final String _QUERY_STRING = "&p_p_id=103&p_p_action=0";

}