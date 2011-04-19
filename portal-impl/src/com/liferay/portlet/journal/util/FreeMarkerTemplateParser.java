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

package com.liferay.portlet.journal.util;

import com.liferay.portal.freemarker.JournalTemplateLoader;
import com.liferay.portal.kernel.transformation.TransformationContext;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.transformation.BaseFreeMarkerTemplateParser;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.PwdGenerator;

/**
 * @author Mika Koivisto
 * @author Marcellus Tavares
 */
public class FreeMarkerTemplateParser extends BaseFreeMarkerTemplateParser {

	protected String doGetErrorTemplateContent() {
		return PropsValues.JOURNAL_ERROR_TEMPLATE_FREEMARKER;
	}

	protected String doGetErrorTemplateId() {
		return PropsValues.JOURNAL_ERROR_TEMPLATE_FREEMARKER;
	}

	protected void doPopulateCustomContext(TransformationContext context) {
		String journalTemplatesPath =
			JournalTemplateLoader.JOURNAL_SEPARATOR + StringPool.SLASH +
			context.get("companyId") + StringPool.SLASH +
			context.get("groupId");

		context.put("journalTemplatesPath", journalTemplatesPath);

		String randomNamespace =
			PwdGenerator.getPassword(PwdGenerator.KEY3, 4) +
			StringPool.UNDERLINE;

		context.put("randomNamespace", randomNamespace);
	}

}