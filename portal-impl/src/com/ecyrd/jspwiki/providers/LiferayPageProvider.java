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

package com.ecyrd.jspwiki.providers;

import com.ecyrd.jspwiki.NoRequiredPropertyException;
import com.ecyrd.jspwiki.QueryItem;
import com.ecyrd.jspwiki.WikiEngine;

import com.liferay.portal.SystemException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LiferayPageProvider.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class LiferayPageProvider implements WikiPageProvider {

	public static com.ecyrd.jspwiki.WikiPage toJSPWikiPage(
		WikiPage page, WikiEngine engine) {

		com.ecyrd.jspwiki.WikiPage jspWikiPage;
		jspWikiPage = new com.ecyrd.jspwiki.WikiPage(
			engine, page.getTitle());

		jspWikiPage.setAuthor(page.getUserName());
		jspWikiPage.setVersion((int) page.getVersion());
		jspWikiPage.setLastModified(new Date());

		return jspWikiPage;
	}

	public void putPageText(com.ecyrd.jspwiki.WikiPage page, String text)
		throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invocation to putPageText(" + page + ", " + text + ")");
		}
	}

	public boolean pageExists(String title) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invocation to pageExists(" + title + ")");
		}

		boolean exists = false;

		try {
			Boolean existsObj = (Boolean) _titles.get(title);

			if (existsObj == null) {
				if (WikiPageLocalServiceUtil.getPagesCount(
					_nodeId, title, true) > 0) {

					existsObj = Boolean.TRUE;
				}
				else {
					existsObj = Boolean.FALSE;
				}

				_titles.put(title, existsObj);
			}

			exists = existsObj.booleanValue();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return exists;
	}

	public Collection findPages(QueryItem[] query) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invocation to findPages(" + query + ")");
		}

		return _EMPTY_LIST;
	}

	public com.ecyrd.jspwiki.WikiPage getPageInfo(String title, int version)
		throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Invocation to getPageInfo(" + title + ", " + version +
					")");
		}

		try {
			WikiPage page = WikiPageLocalServiceUtil.getPage(
				_nodeId, title);

			return toJSPWikiPage(page, _engine);
		}
		catch (NoSuchPageException e) {
			return null;
		}
		catch (Exception e) {
			throw new ProviderException(e.toString());
		}
	}

	public Collection getAllPages() throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invocation to getAllPages()");
		}

		List jspWikiPages = new ArrayList();

		try {
			int count = WikiPageLocalServiceUtil.getPagesCount(_nodeId);

			List pages = WikiPageLocalServiceUtil.getPages(
				_nodeId, 0, count);

			Iterator iterator = pages.iterator();

			while (iterator.hasNext()) {
				WikiPage page = (WikiPage)iterator.next();

				jspWikiPages.add(toJSPWikiPage(page, _engine));
			}
		}
		catch (SystemException e) {
			throw new ProviderException(e.toString());
		}

		return jspWikiPages;
	}

	public Collection getAllChangedSince(Date date) {
		if (_log.isDebugEnabled()) {
			_log.debug("Invocation to getAllChangedSince(" + date + ")");
		}

		try {
			return getAllPages();
		}
		catch (ProviderException e) {
			_log.error("Could not find changed pages", e);

			return _EMPTY_LIST;
		}
	}

	public int getPageCount() throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invocation to getPageCount()");
		}

		try {
			return WikiPageLocalServiceUtil.getPagesCount(_nodeId);
		}
		catch (SystemException e) {
			throw new ProviderException(e.toString());
		}
	}

	public List getVersionHistory(String title) throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invocation to getVersionHistory(" + title + ")");
		}

		return _EMPTY_LIST;
	}

	public String getPageText(String title, int version)
		throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Invocation to getPageText(" + title + ", " + version + ")");
		}

		try {
			WikiPage page = WikiPageLocalServiceUtil.getPage(
				_nodeId, title);

			return page.getContent();
		}
		catch (Exception e) {
			throw new ProviderException(e.toString());
		}
	}

	public void deleteVersion(String title, int version)
		throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Invocation to deleteVersion(" + title + ", " +
					version + ")");
		}
	}

	public void deletePage(String name) throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invocation to deletePage(" + name + ")");
		}
	}

	public void movePage(String from, String to) throws ProviderException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invocation to movePage(" + from + ", " + to + ")");
		}
	}

	public void initialize(WikiEngine engine, Properties properties)
		throws NoRequiredPropertyException, IOException {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Invocation to initialize(" + engine + ", " + properties + ")");
		}

		_engine = engine;
		_nodeId = Long.parseLong(properties.getProperty("nodeId"));
	}

	public String getProviderInfo() {
		if (_log.isDebugEnabled()) {
			_log.debug("Invocation to getProviderInfo()");
		}

		return LiferayPageProvider.class.getName();
	}

	private static Log _log = LogFactory.getLog(LiferayPageProvider.class);

	private static final List _EMPTY_LIST = new ArrayList();

	private Map _titles;
	private long _nodeId;
	private WikiEngine _engine;

}