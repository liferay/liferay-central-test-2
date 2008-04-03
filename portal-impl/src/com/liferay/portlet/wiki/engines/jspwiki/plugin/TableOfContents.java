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

package com.liferay.portlet.wiki.engines.jspwiki.plugin;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.plugin.PluginException;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.util.PwdGenerator;

import java.util.Map;

/**
 * <a href="TableOfContents.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This is a modification of JSPWiki's core TableOfContents plugin for use
 * within Liferay.  There are two modifications done to the plugin:
 * <ol>
 * <li>Defaults to produce ordered lists</li>
 * <li>Makes contents collapsable using Liferay's javascript classes</li>
 * </ol>
 * </p>
 *
 * @author Alexander Chow
 *
 */
public class TableOfContents extends com.ecyrd.jspwiki.plugin.TableOfContents {

	public String execute(WikiContext context, Map params)
		throws PluginException {

		if (!params.containsKey(PARAM_NUMBERED)) {
			params.put(PARAM_NUMBERED, Boolean.TRUE.toString());
		}

		String result = super.execute(context, params);

		int index1 = result.indexOf("<div class=\"collapsebox\">\n");
		index1 = result.indexOf("</h4>", index1);

		int index2 = index1 + "</h4>".length();

		if (index1 != -1 && index2 != -1) {
			String tocBoxId = "tocbox_" + PwdGenerator.getPassword();

			StringBuffer sb = new StringBuffer();

			sb.append(result.substring(0, index1) + StringPool.NBSP);
			sb.append("<span style=\"cursor: pointer;\" ");
			sb.append(
				"onClick=\"Liferay.Util.toggleByIdSpan(this, '" +
					tocBoxId + "'); self.focus();\">[");
			sb.append("<span>-</span>");
			sb.append("<span style=\"display: none;\">+</span>");
			sb.append("]</span>\n");
			sb.append("</h4>");

			sb.append("<div id=\"" + tocBoxId + "\">\n");
			sb.append(result.substring(index2));
			sb.append("</div>\n");

			result = sb.toString();
		}

		return result;
	}

}