/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.template.soy;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.template.TemplateContextHelper;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bruno Basto
 */
public class SoyTemplateContextHelper extends TemplateContextHelper {

	@Override
	public Map<String, Object> getHelperUtilities(
		ClassLoader classLoader, boolean restricted) {

		return Collections.emptyMap();
	}

	@Override
	public Set<String> getRestrictedVariables() {
		return Collections.emptySet();
	}

	@Override
	public void prepare(Template template, HttpServletRequest request) {
	}

}