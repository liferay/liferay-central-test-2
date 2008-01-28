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

import com.liferay.portlet.wiki.engines.WikiEngine;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.StringPool;
import com.ecyrd.jspwiki.WikiException;
import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.providers.LiferayPageProvider;

import javax.portlet.PortletURL;
import java.util.Map;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * <a href="JSPWikiEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class JSPWikiEngine implements WikiEngine {

	public JSPWikiEngine() {
	}

	public String convert(WikiPage page, PortletURL portletURL)
		throws PageContentException {

		try {
			return _convert(page);
		}
		catch (WikiException e) {
			throw new PageContentException(e);
		}
	}

	public Map getLinks(WikiPage page) throws PageContentException {
		try {
			LiferayJSPWikiEngine engine = _getEngine(page.getNodeId());

			com.ecyrd.jspwiki.WikiPage jspWikiPage =
				LiferayPageProvider.toJSPWikiPage(page, engine);

			Collection titles = engine.scanWikiLinks(
				jspWikiPage, page.getContent());

			Map links = new HashMap();

			Iterator iterator = titles.iterator();

			while (iterator.hasNext()) {
				String title = (String) iterator.next();

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

				Boolean existsObj = (Boolean)links.get(title);

				if (existsObj == null) {
					try {
						if (WikiPageLocalServiceUtil.getPagesCount(
								page.getNodeId(), title, true) > 0) {

							existsObj = Boolean.TRUE;
						}
						else {
							existsObj = Boolean.FALSE;
						}

					}
					catch (SystemException e) {
						existsObj = Boolean.FALSE;
					}

					links.put(title, existsObj);
				}

			}
			return links;
		}
		catch (WikiException e) {
			throw new PageContentException(e);
		}
	}

	public boolean isLinkedTo(WikiPage page, String targetTitle)
		throws PageContentException {

		return false;
	}

	public void setMainConfiguration(String mainConfiguration) {
		setProperties(mainConfiguration);
	}

	public void setInterWikiConfiguration(String interWikiConfiguration) {
	}

	public boolean validate(long nodeId, String newContent) {
		return true;
	}

	protected synchronized void setProperties(String configuration) {
		_props = new Properties();

		InputStream is = new ByteArrayInputStream(configuration.getBytes());

		try {
			_props.load(is);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private String _convert(WikiPage page) throws WikiException {
		String content = page.getContent();

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		com.ecyrd.jspwiki.WikiEngine engine = _getEngine(page.getNodeId());

		com.ecyrd.jspwiki.WikiPage jspWikiPage =
			LiferayPageProvider.toJSPWikiPage(page, engine);

		WikiContext wikiContext = new WikiContext(engine, jspWikiPage);

		return engine.textToHTML(wikiContext, content);
	}

	private LiferayJSPWikiEngine _getEngine(long nodeId)
		throws WikiException {
		Long nodeIdObj = new Long(nodeId);

		LiferayJSPWikiEngine engine =
			(LiferayJSPWikiEngine)_engines.get(nodeIdObj);

		if (engine == null) {
			Properties nodeProps = new Properties(_props);

			nodeProps.setProperty("nodeId", nodeIdObj.toString());

			String appName = nodeProps.getProperty("jspwiki.applicationName");
			nodeProps.setProperty(
				"jspwiki.applicationName", appName + " for node " + nodeId);

			engine = new LiferayJSPWikiEngine(nodeProps);

			_engines.put(nodeIdObj, engine);
		}

		return engine;
	}

	protected Properties _props;

	private Map _engines = new HashMap();

}