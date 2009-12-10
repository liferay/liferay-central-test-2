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

import com.liferay.portal.SystemException;
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
import java.io.StringWriter;

/**
 * <a href="FreeMarkerEngineImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class FreeMarkerEngineImpl implements FreeMarkerEngine {

	public void init() {

		String[] loaders =
			PropsValues.FREEMARKER_ENGINE_CONFIGURATION_TEMPLATE_LOADERS;

		LiferayTemplateLoader liferayTemplateLoader =
			new LiferayTemplateLoader();
		liferayTemplateLoader.setTemplateLoaders(loaders);
		_stringTemplateLoader = new StringTemplateLoader();

		MultiTemplateLoader multiTemplateLoader =
			new MultiTemplateLoader(new TemplateLoader[] {
				new ClassTemplateLoader(getClass(), "/"),
				_stringTemplateLoader, liferayTemplateLoader
			});

		_configuration = new Configuration();
		_configuration.setDefaultEncoding(StringPool.UTF8);
		_configuration.setLocalizedLookup(
			PropsValues.FREEMARKER_ENGINE_CONFIGURATION_LOCALIZED_LOOKUP);
		_configuration.setObjectWrapper(new DefaultObjectWrapper());
		_configuration.setTemplateUpdateDelay(
			PropsValues.
				FREEMARKER_ENGINE_CONFIGURATION_MODIFICATION_CHECK_INTERVAL);
		_configuration.setTemplateLoader(multiTemplateLoader);

		try {
			_configuration.setSetting(
				"template_exception_handler",
				PropsValues.
					FREEMARKER_ENGINE_CONFIGURATION_TEMPLATE_EXCEPTION_HANDLER);
			_configuration.setSetting(
				"auto_import", PropsValues.FREEMARKER_ENGINE_MACRO_LIBRARY);
			_configuration.setSetting(
				"cache_storage",
				PropsValues.FREEMARKER_ENGINE_CONFIGURATION_CACHE_STORAGE);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_restrictedToolsContext = new FreeMarkerContextImpl();

		FreeMarkerVariables.insertHelperUtilities(
			_restrictedToolsContext,
			PropsValues.JOURNAL_TEMPLATE_FREEMARKER_RESTRICTED_VARIABLES);

		_standardToolsContext = new FreeMarkerContextImpl();

		FreeMarkerVariables.insertHelperUtilities(_standardToolsContext, null);
	}

	public FreeMarkerContext getWrappedRestrictedToolsContext() {

		return new FreeMarkerContextImpl(
			_restrictedToolsContext.getWrappedContext());
	}

	public boolean mergeTemplate(
		String freeMarkerTemplateId, String script,
		FreeMarkerContext freeMarkerContext, StringWriter output)
		throws SystemException, IOException {

		try {
			if ((Validator.isNotNull(freeMarkerTemplateId)) &&
				(!PropsValues.LAYOUT_TEMPLATE_CACHE_ENABLED ||
				!resourceExists(freeMarkerTemplateId))) {

				_stringTemplateLoader.putTemplate(freeMarkerTemplateId, script);

				if (_log.isDebugEnabled()) {
					_log.debug("Added " + freeMarkerTemplateId +
						" to the FreeMarker template repository");
				}
			}

			FreeMarkerContextImpl freeMarkerContextImpl =
				(FreeMarkerContextImpl) freeMarkerContext;

			Template template =
				_configuration.getTemplate(
					freeMarkerTemplateId, StringPool.UTF8);

			template.process(freeMarkerContextImpl.getWrappedContext(), output);

			return true;
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public boolean resourceExists(String resource) {

		try {
			Template template = _configuration.getTemplate(resource);

			return (template != null);
		}
		catch (IOException ioe) {
			return false;
		}
	}

	private Configuration _configuration;
	private StringTemplateLoader _stringTemplateLoader;
	private FreeMarkerContextImpl _restrictedToolsContext;
	private FreeMarkerContextImpl _standardToolsContext;

	private static Log _log = LogFactoryUtil.getLog(FreeMarkerEngineImpl.class);

}