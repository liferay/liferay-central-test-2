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

package com.liferay.portlet;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class StrutsResourceBundle extends ResourceBundle {

	public StrutsResourceBundle(String portletName, Locale locale) {
		_portletName = portletName;
		_locale = locale;
	}

	public Enumeration<String> getKeys() {
		return null;
	}

	public Locale getLocale() {
		return _locale;
	}

	protected Object handleGetObject(String key) {
		if ((key != null) &&
			(key.equals(JavaConstants.JAVAX_PORTLET_TITLE) ||
			 key.equals(JavaConstants.JAVAX_PORTLET_SHORT_TITLE) ||
			 key.equals(JavaConstants.JAVAX_PORTLET_KEYWORDS) ||
			 key.equals(JavaConstants.JAVAX_PORTLET_DESCRIPTION))) {

			key = key.concat(StringPool.PERIOD).concat(_portletName);
		}

		return LanguageUtil.get(_locale, key);
	}

	private String _portletName;
	private Locale _locale;

}