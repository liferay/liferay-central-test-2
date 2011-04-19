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

package com.liferay.portal.transformation;

import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.transformation.BaseTemplateParser;
import com.liferay.portal.kernel.transformation.TransformationContext;
import com.liferay.portal.kernel.util.Validator;

import freemarker.core.ParseException;

import freemarker.template.TemplateException;

import java.io.Writer;

/**
 * @author Mika Koivisto
 * @author Marcellus Tavares
 */
public abstract class BaseFreeMarkerTemplateParser extends BaseTemplateParser {

	protected TransformationContext doGetTransformationContext() {
		return FreeMarkerEngineUtil.getWrappedRestrictedToolsContext();
	}

	protected boolean doMergeTemplate(
			String templateId, String script, TransformationContext context,
			Writer writer, String errorTemplateId, String errorTemplateContent)
		throws Exception {

		FreeMarkerContext freeMarkerContext = (FreeMarkerContext)context;

		boolean load = false;

		try {
			load = FreeMarkerEngineUtil.mergeTemplate(
				templateId, script, freeMarkerContext, writer);
		}
		catch (Exception e) {
			if (Validator.isNotNull(errorTemplateId) &&
				Validator.isNotNull(errorTemplateContent)) {

				if (e instanceof ParseException) {
					ParseException pe = (ParseException)e;

					freeMarkerContext.put("column", pe.getColumnNumber());
					freeMarkerContext.put("exception", pe.getMessage());
					freeMarkerContext.put("line", pe.getLineNumber());
					freeMarkerContext.put("script", script);

					load = FreeMarkerEngineUtil.mergeTemplate(
						errorTemplateId, errorTemplateContent,
						freeMarkerContext, writer);
				}
				else if (e instanceof TemplateException) {
					TemplateException te = (TemplateException)e;

					freeMarkerContext.put("exception", te.getMessage());
					freeMarkerContext.put("script", script);

					load = FreeMarkerEngineUtil.mergeTemplate(
						errorTemplateId, errorTemplateContent,
						freeMarkerContext, writer);
				}
			}
			else {
				throw e;
			}
		}

		return load;
	}

}