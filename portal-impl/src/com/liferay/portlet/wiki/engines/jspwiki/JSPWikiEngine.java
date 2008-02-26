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

package com.liferay.portlet.wiki.engines.jspwiki;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.WikiException;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.engines.WikiEngine;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JSPWikiEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class JSPWikiEngine implements WikiEngine {

	public String convert(WikiPage page, PortletURL portletURL)
		throws PageContentException {

		try {
			return convert(page);
		}
		catch (WikiException we) {
			throw new PageContentException(we);
		}
	}

	public Map<String, Boolean> getOutgoingLinks(WikiPage page)
		throws PageContentException {

		try {
			LiferayJSPWikiEngine engine = getEngine(page.getNodeId());

			com.ecyrd.jspwiki.WikiPage jspWikiPage =
				LiferayPageProvider.toJSPWikiPage(page, engine);

			Collection<String> titles = engine.scanWikiLinks(
				jspWikiPage, page.getContent());

			Map<String, Boolean> links = new HashMap<String, Boolean>();

			for (String title : titles) {
				if (title.startsWith("[[")) {
					title = title.substring(2);
				}
				else if (title.startsWith("[")) {
					title = title.substring(1);
				}

				if (title.endsWith("]]")) {
					title = title.substring(title.length() - 2, title.length());
				}
				else if (title.startsWith("[")) {
					title = title.substring(title.length() - 1, title.length());
				}

				Boolean existsObj = links.get(title);

				if (existsObj == null) {
					if (WikiPageLocalServiceUtil.getPagesCount(
							page.getNodeId(), title, true) > 0) {

						existsObj = Boolean.TRUE;
					}
					else {
						existsObj = Boolean.FALSE;
					}

					links.put(title, existsObj);
				}
			}

			return links;
		}
		catch (SystemException se) {
			throw new PageContentException(se);
		}
		catch (WikiException we) {
			throw new PageContentException(we);
		}
	}

	public boolean isLinkedTo(WikiPage page, String targetTitle)
		throws PageContentException {

		Map<String, Boolean> links = getOutgoingLinks(page);

		Boolean link = links.get(targetTitle);

		if (link != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setInterWikiConfiguration(String interWikiConfiguration) {
	}

	public void setMainConfiguration(String mainConfiguration) {
		setProperties(mainConfiguration);
	}

	public boolean validate(long nodeId, String newContent) {
		return true;
	}

	protected String convert(WikiPage page) throws WikiException {
		String content = page.getContent();

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		com.ecyrd.jspwiki.WikiEngine engine = getEngine(page.getNodeId());

		com.ecyrd.jspwiki.WikiPage jspWikiPage =
			LiferayPageProvider.toJSPWikiPage(page, engine);

		WikiContext wikiContext = new WikiContext(engine, jspWikiPage);

		return engine.textToHTML(wikiContext, content);
	}

	protected LiferayJSPWikiEngine getEngine(long nodeId)
		throws WikiException {

		LiferayJSPWikiEngine engine = _engines.get(nodeId);

		if (engine == null) {
			Properties nodeProps = new Properties(_props);

			nodeProps.setProperty("nodeId", String.valueOf(nodeId));

			String appName = nodeProps.getProperty("jspwiki.applicationName");

			nodeProps.setProperty(
				"jspwiki.applicationName", appName + " for node " + nodeId);

			engine = new LiferayJSPWikiEngine(nodeProps);

			_engines.put(nodeId, engine);
		}

		return engine;
	}

	protected synchronized void setProperties(String configuration) {
		_props = new Properties();

		InputStream is = new ByteArrayInputStream(configuration.getBytes());

		try {
			_props.load(is);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
	}

	private static Log _log = LogFactory.getLog(JSPWikiEngine.class);

	private Properties _props;
	private Map<Long, LiferayJSPWikiEngine> _engines =
		new HashMap<Long, LiferayJSPWikiEngine>();

}