/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngine;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.IOException;
import java.io.Writer;

/**
 * <a href="FreeMarkerEngineImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class FreeMarkerEngineImpl implements FreeMarkerEngine {

	public FreeMarkerContext getWrappedRestrictedToolsContext() {
		return new FreeMarkerContextImpl(
			_restrictedToolsContext.getWrappedContext());
	}

	public FreeMarkerContext getWrappedStandardToolsContext() {
		return new FreeMarkerContextImpl(
			_standardToolsContext.getWrappedContext());
	}

	public void init() throws Exception {
		LiferayTemplateLoader liferayTemplateLoader =
			new LiferayTemplateLoader();

		liferayTemplateLoader.setTemplateLoaders(
			PropsValues.FREEMARKER_ENGINE_TEMPLATE_LOADERS);

		_stringTemplateLoader = new StringTemplateLoader();

		MultiTemplateLoader multiTemplateLoader =
			new MultiTemplateLoader(
				new TemplateLoader[] {
					new ClassTemplateLoader(getClass(), StringPool.SLASH),
					_stringTemplateLoader, liferayTemplateLoader
				});

		_configuration = new Configuration();

		_configuration.setDefaultEncoding(StringPool.UTF8);
		_configuration.setLocalizedLookup(
			PropsValues.FREEMARKER_ENGINE_LOCALIZED_LOOKUP);
		_configuration.setObjectWrapper(new DefaultObjectWrapper());
		_configuration.setSetting(
			"auto_import", PropsValues.FREEMARKER_ENGINE_MACRO_LIBRARY);
		_configuration.setSetting(
			"cache_storage", PropsValues.FREEMARKER_ENGINE_CACHE_STORAGE);
		_configuration.setSetting(
			"template_exception_handler",
			PropsValues.FREEMARKER_ENGINE_TEMPLATE_EXCEPTION_HANDLER);
		_configuration.setTemplateLoader(multiTemplateLoader);
		_configuration.setTemplateUpdateDelay(
			PropsValues.FREEMARKER_ENGINE_MODIFICATION_CHECK_INTERVAL);

		_restrictedToolsContext = new FreeMarkerContextImpl();

		FreeMarkerVariables.insertHelperUtilities(
			_restrictedToolsContext,
			PropsValues.JOURNAL_TEMPLATE_FREEMARKER_RESTRICTED_VARIABLES);

		_standardToolsContext = new FreeMarkerContextImpl();

		FreeMarkerVariables.insertHelperUtilities(_standardToolsContext, null);
	}

	public boolean mergeTemplate(
			String freeMarkerTemplateId, FreeMarkerContext freeMarkerContext,
			Writer writer)
		throws Exception {

		return mergeTemplate(
			freeMarkerTemplateId, null, freeMarkerContext, writer);
	}

	public boolean mergeTemplate(
			String freeMarkerTemplateId, String freemarkerTemplateContent,
			FreeMarkerContext freeMarkerContext, Writer writer)
		throws Exception {

		if ((Validator.isNotNull(freeMarkerTemplateId)) &&
			(Validator.isNotNull(freemarkerTemplateContent)) &&
			(!PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED ||
			 !resourceExists(freeMarkerTemplateId))) {

			_stringTemplateLoader.putTemplate(
				freeMarkerTemplateId, freemarkerTemplateContent);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Added " + freeMarkerTemplateId +
						" to the FreeMarker template repository");
			}
		}

		FreeMarkerContextImpl freeMarkerContextImpl =
			(FreeMarkerContextImpl)freeMarkerContext;

		Template template = _configuration.getTemplate(
			freeMarkerTemplateId, StringPool.UTF8);

		template.process(freeMarkerContextImpl.getWrappedContext(), writer);

		return true;
	}

	public boolean resourceExists(String resource) {
		try {
			Template template = _configuration.getTemplate(resource);

			if (template != null) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}

			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(FreeMarkerEngineImpl.class);

	private Configuration _configuration;
	private FreeMarkerContextImpl _restrictedToolsContext;
	private FreeMarkerContextImpl _standardToolsContext;
	private StringTemplateLoader _stringTemplateLoader;

}