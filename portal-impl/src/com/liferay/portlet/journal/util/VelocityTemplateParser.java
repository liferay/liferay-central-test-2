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

import com.liferay.portal.TransformException;
import com.liferay.portal.kernel.transformation.TemplateNode;
import com.liferay.portal.kernel.transformation.TransformationContext;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.transformation.BaseVelocityTemplateParser;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.velocity.VelocityResourceListener;
import com.liferay.util.ContentUtil;
import com.liferay.util.PwdGenerator;

import java.util.List;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Marcellus Tavares
 */
public class VelocityTemplateParser extends BaseVelocityTemplateParser {

	protected String doGetErrorTemplateContent() {
		return ContentUtil.get(PropsValues.JOURNAL_ERROR_TEMPLATE_VELOCITY);
	}

	protected String doGetErrorTemplateId() {
		return PropsValues.JOURNAL_ERROR_TEMPLATE_VELOCITY;
	}

	protected void doPopulateCustomContext(TransformationContext context) {
		String journalTemplatesPath =
			VelocityResourceListener.JOURNAL_SEPARATOR + StringPool.SLASH +
			context.get("companyId") + StringPool.SLASH +
			context.get("groupId");

		context.put("journalTemplatesPath", journalTemplatesPath);

		String randomNamespace =
			PwdGenerator.getPassword(PwdGenerator.KEY3, 4) +
			StringPool.UNDERLINE;

		context.put("randomNamespace", randomNamespace);
	}

	protected List<TemplateNode> extractDynamicContents(
			ThemeDisplay themeDisplay, Element parent)
		throws TransformException {

		return super.extractDynamicContents(themeDisplay, parent);
	}

}