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

package com.liferay.portlet.wiki.engines;

import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.Map;

import javax.portlet.PortletURL;

/**
 * <a href="WikiEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public interface WikiEngine {

	/**
	 * Convert the content of the given page to HTML using the portletURL to
	 * build links.
	 *
	 * @return HTML string
	 */
	public String convert(WikiPage page, PortletURL portletURL)
		throws PageContentException;

	/**
	 * Get a map with the links included in the given page. The key of each map
	 * entry is the title of the linked page. The value is a Boolean object that
	 * indicates if the linked page exists or not.
	 *
	 * @return a map of links
	 */
	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException;

	/**
	 * Set the configuraton to support quick links to other wikis. The format of
	 * the configuration is specific to the wiki engine.
	 */
	public void setInterWikiConfiguration(String interWikiConfiguration);

	/**
	 * Set the main wiki configuraiton as a String. The format of the
	 * configuration is specific to the wiki engine.
	 */
	public void setMainConfiguration(String mainConfiguration);

	/**
	 * Validate the content of a wiki page for this engine.
	 *
	 * @return true if the content is valid
	 */
	public boolean validate(long nodeId, String content);

}