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

import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.Serializable;

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletPreferencesWrapper
	implements PortletPreferences, Serializable {

	public PortletPreferencesWrapper(
		PortletPreferences preferences, String lifecycle) {

		_preferences = preferences;
		_lifecycle = lifecycle;
	}

	public Map<String, String[]> getMap() {
		return _preferences.getMap();
	}

	public Enumeration<String> getNames() {
		return _preferences.getNames();
	}

	public String getValue(String key, String def) {
		return _preferences.getValue(key, def);
	}

	public void setValue(String key, String value) throws ReadOnlyException {
		_preferences.setValue(key, value);
	}

	public String[] getValues(String key, String[] def) {
		return _preferences.getValues(key, def);
	}

	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		_preferences.setValues(key, values);
	}

	public boolean isReadOnly(String key) {
		return _preferences.isReadOnly(key);
	}

	public void reset(String key) throws ReadOnlyException {
		_preferences.reset(key);
	}

	public void store() throws IOException, ValidatorException {
		if (PropsValues.TCK_URL) {

			// Be strict to pass the TCK

			if (_lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				_preferences.store();
			}
			else {
				throw new IllegalStateException(
					"Preferences cannot be stored inside a render call");
			}
		}
		else {

			// Relax so that poorly written portlets can still work

			_preferences.store();
		}
	}

	public PortletPreferencesImpl getPreferencesImpl() {
		return (PortletPreferencesImpl)_preferences;
	}

	public boolean equals(Object obj) {
		PortletPreferencesWrapper portletPreferences =
			(PortletPreferencesWrapper)obj;

		if (this == portletPreferences) {
			return true;
		}

		if (getPreferencesImpl().equals(
				portletPreferences.getPreferencesImpl())) {

			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return _preferences.hashCode();
	}

	private PortletPreferences _preferences;
	private String _lifecycle;

}