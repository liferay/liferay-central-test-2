/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.model.PortletInfo;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class PortletResourceBundle extends ResourceBundle {

	public PortletResourceBundle(
		ResourceBundle parentResourceBundle, PortletInfo portletInfo) {

		_parentResourceBundle = parentResourceBundle;
		_portletInfo = portletInfo;
	}

	public Enumeration<String> getKeys() {
		return _parentResourceBundle.getKeys();
	}

	public Locale getLocale() {
		return _parentResourceBundle.getLocale();
	}

	protected Object handleGetObject(String key) {
		try {
			if (_parentResourceBundle == null) {
				return _getJavaxPortletString(key);
			}
			else {
				return _parentResourceBundle.getObject(key);
			}
		}
		catch (MissingResourceException mre) {
			String value = _getJavaxPortletString(key);

			if (value != null) {
				return value;
			}
			else {
				throw mre;
			}
		}
	}
	private String _getJavaxPortletString(String key) {
		if (key == null) {
			return null;
		}
		else if (key.equals(JavaConstants.JAVAX_PORTLET_TITLE)) {
			return _portletInfo.getTitle();
		}
		else if (key.equals(JavaConstants.JAVAX_PORTLET_SHORT_TITLE)) {
			return _portletInfo.getShortTitle();
		}
		else if (key.equals(JavaConstants.JAVAX_PORTLET_KEYWORDS)) {
			return _portletInfo.getKeywords();
		}
		else if (key.equals(JavaConstants.JAVAX_PORTLET_DESCRIPTION)) {
			return _portletInfo.getDescription();
		}

		return null;
	}

	private ResourceBundle _parentResourceBundle;
	private PortletInfo _portletInfo;

}