/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.wiki.engines.jspwiki;

import com.ecyrd.jspwiki.WikiContext;
import com.ecyrd.jspwiki.WikiException;
import com.ecyrd.jspwiki.WikiPage;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.engines.WikiEngine;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.util.WikiUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletURL;

/**
 * @author Jorge Ferrer
 */
public class JSPWikiEngine implements WikiEngine {

	public String convert(
			com.liferay.portlet.wiki.model.WikiPage page, PortletURL portletURL)
		throws PageContentException {

		try {
			return convert(page);
		}
		catch (WikiException we) {
			throw new PageContentException(we);
		}
	}

	public Map<String, Boolean> getOutgoingLinks(
			com.liferay.portlet.wiki.model.WikiPage page)
		throws PageContentException {

		if (Validator.isNull(page.getContent())) {
			return Collections.EMPTY_MAP;
		}

		try {
			LiferayJSPWikiEngine engine = getEngine(page.getNodeId());

			WikiPage jspWikiPage = LiferayPageProvider.toJSPWikiPage(
				page, engine);

			Collection<String> titles = engine.scanWikiLinks(
				jspWikiPage, WikiUtil.encodeJSPWikiContent(page.getContent()));

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

					links.put(title.toLowerCase(), existsObj);
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

	public void setInterWikiConfiguration(String interWikiConfiguration) {
	}

	public void setMainConfiguration(String mainConfiguration) {
		setProperties(mainConfiguration);
	}

	public boolean validate(long nodeId, String newContent) {
		return true;
	}

	protected String convert(com.liferay.portlet.wiki.model.WikiPage page)
		throws WikiException {

		String content = WikiUtil.encodeJSPWikiContent(page.getContent());

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		com.ecyrd.jspwiki.WikiEngine engine = getEngine(page.getNodeId());

		WikiPage jspWikiPage = LiferayPageProvider.toJSPWikiPage(page, engine);

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

		InputStream is = new UnsyncByteArrayInputStream(
			configuration.getBytes());

		try {
			_props.load(is);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JSPWikiEngine.class);

	private Properties _props;
	private Map<Long, LiferayJSPWikiEngine> _engines =
		new HashMap<Long, LiferayJSPWikiEngine>();

}