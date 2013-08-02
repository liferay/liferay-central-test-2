/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import freemarker.cache.CacheStorage;
import freemarker.cache.TemplateCache;
import freemarker.cache.TemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.util.Locale;

/**
 * @author Tina Tian
 * @author Raymond Augé
 */
public class LiferayTemplateCache extends TemplateCache {

	public LiferayTemplateCache(TemplateCache templateCache) {
		_templateCache = templateCache;
	}

	@Override
	public void clear() {
		_templateCache.clear();
	}

	@Override
	public boolean equals(Object obj) {
		return _templateCache.equals(obj);
	}

	@Override
	public CacheStorage getCacheStorage() {
		return _templateCache.getCacheStorage();
	}

	@Override
	public long getDelay() {
		return _templateCache.getDelay();
	}

	@Override
	public boolean getLocalizedLookup() {
		return _templateCache.getLocalizedLookup();
	}

	@Override
	public Template getTemplate(
			String templateId, Locale locale, String encoding, boolean parse)
		throws IOException {

		String[] macroTemplateIds = PropsUtil.getArray(
			PropsKeys.FREEMARKER_ENGINE_MACRO_LIBRARY);

		for (String macroTemplateId : macroTemplateIds) {
			int pos = macroTemplateId.indexOf(" as ");

			if (pos != -1) {
				macroTemplateId = macroTemplateId.substring(0, pos);
			}

			if (templateId.equals(macroTemplateId)) {

				// This template is provided by the portal, so invoke it from an
				// access controller

				try {
					return AccessController.doPrivileged(
						new TemplatePrivilegedExceptionAction(
							macroTemplateId, locale, encoding, parse));
				}
				catch (PrivilegedActionException pae) {
					throw (IOException)pae.getException();
				}
			}
		}

		return _templateCache.getTemplate(templateId, locale, encoding, parse);
	}

	@Override
	public TemplateLoader getTemplateLoader() {
		return _templateCache.getTemplateLoader();
	}

	@Override
	public int hashCode() {
		return _templateCache.hashCode();
	}

	@Override
	public void setConfiguration(Configuration config) {
		_templateCache.setConfiguration(config);
	}

	@Override
	public void setDelay(long delay) {
		_templateCache.setDelay(delay);
	}

	@Override
	public void setLocalizedLookup(boolean localizedLookup) {
		_templateCache.setLocalizedLookup(localizedLookup);
	}

	@Override
	public String toString() {
		return _templateCache.toString();
	}

	private TemplateCache _templateCache;

	private class TemplatePrivilegedExceptionAction
		implements PrivilegedExceptionAction<Template> {

		public TemplatePrivilegedExceptionAction(
			String templateId, Locale locale, String encoding, boolean parse) {

			_templateId = templateId;
			_locale = locale;
			_encoding = encoding;
			_parse = parse;
		}

		@Override
		public Template run() throws Exception {
			return _templateCache.getTemplate(
				_templateId, _locale, _encoding, _parse);
		}

		private String _encoding;
		private Locale _locale;
		private boolean _parse;
		private String _templateId;

	}

}