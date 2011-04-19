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

import com.liferay.portal.kernel.transformation.BaseTemplateParser;
import com.liferay.portal.kernel.transformation.TransformationContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;

import java.io.Writer;

import org.apache.velocity.exception.ParseErrorException;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Marcellus Tavares
 */
public abstract class BaseVelocityTemplateParser extends BaseTemplateParser {

	protected TransformationContext doGetTransformationContext() {
		return VelocityEngineUtil.getWrappedRestrictedToolsContext();
	}

	protected boolean doMergeTemplate(
			String templateId, String script, TransformationContext context,
			Writer writer, String errorTemplateId, String errorTemplateContent)
		throws Exception {

		VelocityContext velocityContext = (VelocityContext)context;

		boolean load = false;

		try {
			load = VelocityEngineUtil.mergeTemplate(
				templateId, script, velocityContext, writer);
		}
		catch (Exception e) {
			if (Validator.isNotNull(errorTemplateId) &&
				Validator.isNotNull(errorTemplateContent)) {

				velocityContext.put("exception", e.getMessage());
				velocityContext.put("script", script);

				if (e instanceof ParseErrorException) {
					ParseErrorException pe = (ParseErrorException)e;

					velocityContext.put(
						"column", new Integer(pe.getColumnNumber()));
					velocityContext.put(
						"line", new Integer(pe.getLineNumber()));
				}

				load = VelocityEngineUtil.mergeTemplate(
					errorTemplateId, errorTemplateContent, velocityContext,
					writer);
			}
			else {
				throw e;
			}
		}

		return load;
	}

}