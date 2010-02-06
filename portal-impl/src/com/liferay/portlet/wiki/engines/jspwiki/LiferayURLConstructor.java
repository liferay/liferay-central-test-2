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

package com.liferay.portlet.wiki.engines.jspwiki;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.url.URLConstructor;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="LiferayURLConstructor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class LiferayURLConstructor implements URLConstructor {

	public String getForwardPage(HttpServletRequest request) {
		return "Wiki.jsp";
	}

	public void initialize(
		com.ecyrd.jspwiki.WikiEngine engine, Properties props) {
	}

	public String makeURL(
		String context, String name, boolean absolute, String parameters) {

		if (Validator.isNotNull(parameters)) {
			if (context.equals(WikiContext.ATTACH)) {
				parameters = StringPool.QUESTION + parameters;
			}
			else if (context.equals(WikiContext.NONE)) {
				if (name.indexOf(StringPool.QUESTION) != -1) {
					parameters = "&amp;" + parameters;
				}
				else {
					parameters = StringPool.QUESTION + parameters;
				}
			}
			else {
				parameters = "&amp;" + parameters;
			}
		}
		else {
			parameters = StringPool.BLANK;
		}

		String path;

		if (context.equals(WikiContext.EDIT)) {
			path =
				"[$BEGIN_PAGE_TITLE_EDIT$]" + name + "[$END_PAGE_TITLE_EDIT$]";
		}
		else if (context.equals(WikiContext.VIEW)) {
			path = "[$BEGIN_PAGE_TITLE$]" + name + "[$END_PAGE_TITLE$]";
		}
		else if (context.equals(WikiContext.ATTACH)) {
			if (name.indexOf(StringPool.SLASH) == -1) {
				path =
					"[$ATTACHMENT_URL_PREFIX$][$WIKI_PAGE_NAME$]/" +
						HttpUtil.encodeURL(name);
			}
			else {
				path = "[$ATTACHMENT_URL_PREFIX$]" + HttpUtil.encodeURL(name);
			}
		}
		else {
			path = name;
		}

		return path + parameters;
 	}

	public String parsePage(
		String context, HttpServletRequest request, String encoding) {

		return "Wiki.jsp";
	}

}