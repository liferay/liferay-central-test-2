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

package com.liferay.portal.template;

import com.liferay.portal.kernel.security.pacl.NotPrivileged;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public abstract class BaseTemplateManager implements TemplateManager {

	@NotPrivileged
	@Override
	public Template getTemplate(
		TemplateResource templateResource, boolean restricted) {

		return getTemplate(templateResource, null, restricted);
	}

	@NotPrivileged
	@Override
	public Template getTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted) {

		TemplateContextHelper templateContextHelper =
			getTemplateContextHelper();

		Map<String, Object> helperUtilities =
			templateContextHelper.getHelperUtilities(restricted);

		return AccessController.doPrivileged(
			new DoGetTemplatePrivilegedAction(
				templateResource, errorTemplateResource, restricted,
				helperUtilities));
	}

	protected abstract Template doGetTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted,
		Map<String, Object> helperUtilities);

	protected abstract TemplateContextHelper getTemplateContextHelper();

	private class DoGetTemplatePrivilegedAction
		implements PrivilegedAction<Template> {

		public DoGetTemplatePrivilegedAction(
			TemplateResource templateResource,
			TemplateResource errorTemplateResource, boolean restricted,
			Map<String, Object> helperUtilities) {

			_templateResource = templateResource;
			_errorTemplateResource = errorTemplateResource;
			_restricted = restricted;
			_helperUtilities = helperUtilities;
		}

		@Override
		public Template run() {
			return doGetTemplate(
				_templateResource, _errorTemplateResource, _restricted,
				_helperUtilities);
		}

		private TemplateResource _errorTemplateResource;
		private Map<String, Object> _helperUtilities;
		private boolean _restricted;
		private TemplateResource _templateResource;

	}

}