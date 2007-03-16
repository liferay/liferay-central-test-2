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

import java.util.Map;

import javax.portlet.PortletURL;

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

	public String buildPath(PortletURL portletURL) {
		return null;
	}

	public void populateParams(String friendlyURLPath, Map params) {
		params.put("p_p_id", _PORTLET_ID);
		params.put("p_p_action", "0");

		int x = friendlyURLPath.indexOf(StringPool.SLASH, 1);
		int y = friendlyURLPath.length();

		String[] entries = StringUtil.split(
			friendlyURLPath.substring(x + 1, y), StringPool.SLASH);

		if (entries.length > 0) {
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < entries.length; i++) {
				String entry = StringUtil.replace(
					entries[i], StringPool.PLUS, StringPool.SPACE);

				if (i != 0) {
					sb.append(StringPool.COMMA);
				}

				sb.append(entry);
			}

			addParam(params, "entries", sb.toString());
		}

	}

	protected void addParam(Map params, String name, String value) {
		params.put(PortalUtil.getPortletNamespace(_PORTLET_ID) + name, value);
	}

	private static final String _MAPPING = "tags";

	private static final String _PORTLET_ID = PortletKeys.TAGS_COMPILER;

}