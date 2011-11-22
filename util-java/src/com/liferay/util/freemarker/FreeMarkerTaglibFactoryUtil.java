/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.util.freemarker;

import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;

import freemarker.ext.jsp.TaglibFactory;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class FreeMarkerTaglibFactoryUtil implements CacheRegistryItem {

	public static TemplateHashModel createTaglibFactory(
		ServletContext servletContext) {

		return new TaglibFactoryCacheWrapper(servletContext);
	}

	public String getRegistryName() {
		return FreeMarkerTaglibFactoryUtil.class.getName();
	}

	public void invalidate() {
		_templateModels.clear();
	}

	private FreeMarkerTaglibFactoryUtil() {
	}

	private static class TaglibFactoryCacheWrapper
		implements TemplateHashModel {

		public TaglibFactoryCacheWrapper(ServletContext servletContext) {
			_contextPath = servletContext.getContextPath();
			_taglibFactory = new TaglibFactory(servletContext);
		}

		public TemplateModel get(String uri) throws TemplateModelException {
			String key = uri;

			if (_contextPath.length() > 0) {
				key = _contextPath.concat(uri);
			}

			TemplateModel templateModel = _templateModels.get(key);

			if (templateModel == null) {
				templateModel = _taglibFactory.get(uri);

				_templateModels.put(key, templateModel);
			}

			return templateModel;
		}

		public boolean isEmpty() {
			return false;
		}

		private String _contextPath;
		private TaglibFactory _taglibFactory;

	}

	private static Map<String, TemplateModel> _templateModels =
		new ConcurrentHashMap<String, TemplateModel>();

	static {
		CacheRegistryUtil.register(new FreeMarkerTaglibFactoryUtil());
	}

}