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

package com.liferay.portlet.wiki.util;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.wiki.WikiFormatException;
import com.liferay.portlet.wiki.PageContentException;
import com.liferay.portlet.wiki.engines.WikiEngine;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;
import com.liferay.util.Http;

import com.germinus.easyconf.Filter;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * <a href="WikiUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class WikiUtil {

	public static String convert(
		WikiPage page, PortletURL pageURL, PortletURL editURL)
		throws PageContentException, WikiFormatException {

		return _instance._convert(page, pageURL, editURL);
	}

	public static String getDefaultEditPage() {
		return _instance._getDefaultEditPage();
	}

	public static String getEditPage(WikiPage page) {
		return _instance._getEditPage(page);
	}

	public static String getHelpPage(String format) {
		return _instance._getHelpPage(format);
	}

	public static String getHelpURL(String format) {
		return _instance._getHelpURL(format);
	}

	public static Map getLinks(WikiPage page)
		throws PageContentException, WikiFormatException {
		return _instance._getLinks(page);
	}

	public static boolean isLinkedTo(WikiPage page, String title)
		throws PageContentException, WikiFormatException {
		return _instance._isLinkedTo(page, title);
	}

	public static boolean validate(
		long nodeId, String content, String format) throws WikiFormatException {
		return _instance._validate(nodeId, content, format);
	}

	private String _convert(
		WikiPage page, PortletURL pageURL, PortletURL editURL)
		throws PageContentException, WikiFormatException {
		WikiEngine engine = _getEngine(page.getFormat());

		String content = engine.convert(page, editURL);

		if ((editURL != null) && (editURL instanceof LiferayPortletURL)) {
			LiferayPortletURL liferayPageURL = (LiferayPortletURL) pageURL;

			liferayPageURL.setParameter(
				"nodeId", Long.toString(page.getNodeId()));

			LiferayPortletURL liferayEditURL = (LiferayPortletURL) editURL;

			liferayEditURL.setParameter(
				"nodeId", Long.toString(page.getNodeId()));

			Iterator itr = engine.getLinks(page).keySet().iterator();

			while (itr.hasNext()) {
				String title = (String)itr.next();

				content = _replaceLinks(
					title, content, liferayPageURL, liferayEditURL);
			}
		}

		return content;
	}

	private String _getDefaultEditPage() {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.WIKI_FORMATS_EDIT_PAGE,
			Filter.by(WikiPageImpl.DEFAULT_FORMAT));
	}

	private String _getEditPage(WikiPage page) {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.WIKI_FORMATS_EDIT_PAGE, Filter.by(page.getFormat()));
	}

	private String _getHelpPage(String format) {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.WIKI_FORMATS_HELP_PAGE, Filter.by(format));
	}

	private String _getHelpURL(String format) {
		return PropsUtil.getComponentProperties().getString(
			PropsUtil.WIKI_FORMATS_HELP_URL, Filter.by(format));
	}

	private Map _getLinks(WikiPage page)
		throws PageContentException, WikiFormatException{

		try {
			return _getEngine(page.getFormat()).getLinks(page);
		}
		catch (WikiFormatException e) {
			return _EMPTY_MAP;
		}
	}

	private boolean _isLinkedTo(WikiPage page, String targetTitle)
		throws PageContentException, WikiFormatException {
		try {
			return _getEngine(page.getFormat()).isLinkedTo(page, targetTitle);
		}
		catch (WikiFormatException e) {
			return false;
		}
	}

	private String _replaceLinks(
		String title, String content, LiferayPortletURL pageURL,
		LiferayPortletURL editURL) {

		pageURL.setParameter("title", title, false);

		String pageURLToString = pageURL.toString();

		content = StringUtil.replace(
			content,
			"[$BEGIN_PAGE_TITLE$]" + title + "[$END_PAGE_TITLE$]",
			pageURLToString);

		editURL.setParameter("title", title, false);

		String editURLToString = editURL.toString();

		content = StringUtil.replace(
			content,
			"[$BEGIN_PAGE_TITLE_EDIT$]" + title + "[$END_PAGE_TITLE_EDIT$]",
			editURLToString);

		return content;
	}

	private boolean _validate(long nodeId, String content, String format)
		throws WikiFormatException {
		return _getEngine(format).validate(nodeId, content);
	}

	private WikiEngine _getEngine(String format) throws WikiFormatException {
		WikiEngine engine = (WikiEngine)_engines.get(format);

		if (engine == null) {
			try {
				String engineClass =
					PropsUtil.getComponentProperties().getString(
						PropsUtil.WIKI_FORMATS_ENGINE, Filter.by(format));

				if (engineClass != null) {
					if (!InstancePool.contains(engineClass)) {
						engine = (WikiEngine)InstancePool.get(engineClass);

						engine.setMainConfiguration(
							_readConfigurationFile(
								PropsUtil.WIKI_FORMATS_CONFIGURATION_MAIN,
								format));

						engine.setInterWikiConfiguration(
							_readConfigurationFile(
								PropsUtil.WIKI_FORMATS_CONFIGURATION_INTERWIKI,
								format));

					}
					else {
						engine = (WikiEngine)InstancePool.get(engineClass);
					}
					_engines.put(format, engine);
				}
			}
			catch (Exception e) {
				throw new WikiFormatException(e);
			}

			if (engine == null) {
				throw new WikiFormatException(format);
			}
		}

		return engine;
	}

	private String _readConfigurationFile(String propertyName, String format)
		throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();

		String configurationFile = PropsUtil.getComponentProperties().getString(
			propertyName, Filter.by(format));

		if (Validator.isNotNull(configurationFile)) {
			return Http.URLtoString(classLoader.getResource(configurationFile));
		}
		else {
			return StringPool.BLANK;
		}
	}

	private static WikiUtil _instance = new WikiUtil();
	private static final Map _EMPTY_MAP = new HashMap();

	private Map _engines = new HashMap();

}