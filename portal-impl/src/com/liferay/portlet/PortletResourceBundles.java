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

package com.liferay.portlet;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.struts.util.RequestUtils;

/**
 * <a href="PortletResourceBundles.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletResourceBundles {

	public static String getString(PageContext pageContext, String key) {
		return _instance._getString(pageContext, key);
	}

	public static String getString(Locale locale, String key) {
		return _instance._getString(locale, key);
	}

	public static String getString(String languageId, String key) {
		return _instance._getString(languageId, key);
	}

	public static String getString(
		String servletContextName, String languageId, String key) {

		return _instance._getString(servletContextName, languageId, key);
	}

	public static void put(
		String servletContextName, String languageId, ResourceBundle bundle) {

		_instance._put(servletContextName, languageId, bundle);
	}

	public static void remove(String servletContextName) {
		_instance._remove(servletContextName);
	}

	private PortletResourceBundles() {
		_contexts = new ConcurrentHashMap<String, Map<String, ResourceBundle>>(
			new LinkedHashMap<String, Map<String, ResourceBundle>>());
	}

	private ResourceBundle _getBundle(
		String servletContextName, String languageId) {

		Map<String, ResourceBundle> bundles = _getBundles(servletContextName);

		return _getBundle(bundles, languageId);
	}

	private ResourceBundle _getBundle(
		Map<String, ResourceBundle> bundles, String languageId) {

		ResourceBundle bundle = bundles.get(languageId);

		if (bundle == null) {
			try {
				bundle = new PropertyResourceBundle(
					new UnsyncByteArrayInputStream(new byte[0]));

				bundles.put(languageId, bundle);
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}

		return bundle;
	}

	private Map<String, ResourceBundle> _getBundles(String servletContextName) {
		Map<String, ResourceBundle> bundles = _contexts.get(servletContextName);

		if (bundles == null) {
			bundles = new HashMap<String, ResourceBundle>();

			_contexts.put(servletContextName, bundles);
		}

		return bundles;
	}

	private String _getString(PageContext pageContext, String key) {
		Locale locale = RequestUtils.getUserLocale(
			(HttpServletRequest)pageContext.getRequest(), null);

		return _getString(locale, key);
	}

	private String _getString(Locale locale, String key) {
		return _getString(LocaleUtil.toLanguageId(locale), key);
	}

	private String _getString(String languageId, String key) {
		return _getString(null, languageId, key);
	}

	private String _getString(
		String servletContextName, String languageId, String key) {

		if (servletContextName != null) {
			ResourceBundle bundle = _getBundle(servletContextName, languageId);

			return bundle.getString(key);
		}

		Iterator<Map.Entry<String, Map<String, ResourceBundle>>> itr =
			_contexts.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Map<String, ResourceBundle>> entry = itr.next();

			Map<String, ResourceBundle> bundles = entry.getValue();

			ResourceBundle bundle = _getBundle(bundles, languageId);

			try {
				return bundle.getString(key);
			}
			catch (MissingResourceException mre) {
			}
		}

		return null;
	}

	private void _put(
		String servletContextName, String languageId, ResourceBundle bundle) {

		Map<String, ResourceBundle> bundles = _getBundles(servletContextName);

		bundles.put(languageId, bundle);
	}

	private void _remove(String servletContextName) {
		_contexts.remove(servletContextName);
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletResourceBundles.class);

	private static PortletResourceBundles _instance =
		new PortletResourceBundles();

	private Map<String, Map<String, ResourceBundle>> _contexts;

}