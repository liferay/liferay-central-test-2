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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashCode;
import com.liferay.portal.kernel.util.HashCodeFactoryUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletPreferencesImpl
	implements Cloneable, PortletPreferences, Serializable {

	public PortletPreferencesImpl() {
		this(0, 0, 0, 0, null, Collections.<String, Preference>emptyMap());
	}

	public PortletPreferencesImpl(
		long companyId, long ownerId, int ownerType, long plid,
		String portletId, Map<String, Preference> preferences) {

		_companyId = companyId;
		_ownerId = ownerId;
		_ownerType = ownerType;
		_plid = plid;
		_portletId = portletId;
		_originalPreferences = preferences;
	}

	public Object clone() {
		return new PortletPreferencesImpl(
			_companyId, _ownerId, _ownerType, _plid, _portletId,
			_originalPreferences);
	}

	public boolean equals(Object obj) {
		PortletPreferencesImpl portletPreferences = (PortletPreferencesImpl)obj;

		if (this == portletPreferences) {
			return true;
		}

		if ((getCompanyId() == portletPreferences.getCompanyId()) &&
			(getOwnerId() == portletPreferences.getOwnerId()) &&
			(getOwnerType() == portletPreferences.getOwnerType()) &&
			(getPlid() == portletPreferences.getPlid()) &&
			(getPortletId().equals(portletPreferences.getPortletId())) &&
			(getMap().equals(portletPreferences.getMap()))) {

			return true;
		}
		else {
			return false;
		}
	}

	public Map<String, String[]> getMap() {
		Map<String, String[]> map = new HashMap<String, String[]>();

		for (Map.Entry<String, Preference> entry :
				getPreferences().entrySet()) {

			String key = entry.getKey();
			Preference preference = entry.getValue();

			map.put(key, _getActualValues(preference.getValues()));
		}

		return Collections.unmodifiableMap(map);
	}

	public Enumeration<String> getNames() {
		return Collections.enumeration(getPreferences().keySet());
	}

	public String getValue(String key, String def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		Preference preference = getPreferences().get(key);

		String[] values = null;

		if (preference != null) {
			values = preference.getValues();
		}

		if ((values != null) && (values.length > 0)) {
			return _getActualValue(values[0]);
		}
		else {
			return _getActualValue(def);
		}
	}

	public String[] getValues(String key, String[] def) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		Preference preference = getPreferences().get(key);

		String[] values = null;
		if (preference != null) {
			values = preference.getValues();
		}

		if ((values != null) && (values.length > 0)) {
			return _getActualValues(values);
		}
		else {
			return _getActualValues(def);
		}
	}

	public int hashCode() {
		HashCode hashCode = HashCodeFactoryUtil.getHashCode();

		hashCode.append(_companyId);
		hashCode.append(_ownerId);
		hashCode.append(_ownerType);
		hashCode.append(_plid);
		hashCode.append(_portletId);
		hashCode.append(getPreferences());

		return hashCode.toHashCode();
	}

	public boolean isReadOnly(String key) {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		Preference preference = getPreferences().get(key);

		if ((preference != null) && preference.isReadOnly()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void reset() {
		_getModifiedPreferences().clear();
	}

	public void reset(String key) throws ReadOnlyException {
		if (isReadOnly(key)) {
			throw new ReadOnlyException(key);
		}

		if (_defaultPreferences == null) {
			try {
				if ((_portletId != null) &&
					(!_portletId.equals(PortletKeys.LIFERAY_PORTAL))) {

					_defaultPreferences = PortletPreferencesLocalServiceUtil.
						getDefaultPreferences(_companyId, _portletId);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		String[] defaultValues = null;

		if (_defaultPreferences != null) {
			defaultValues = _defaultPreferences.getValues(key, defaultValues);
		}

		if (defaultValues != null) {
			setValues(key, defaultValues);
		}
		else {
			_getModifiedPreferences().remove(key);
		}
	}

	public void setValue(String key, String value) throws ReadOnlyException {
		if (key == null) {
			throw new IllegalArgumentException();
		}

		value = _getXmlSafeValue(value);

		Preference preference = _getModifiedPreferences().get(key);

		if (preference == null) {
			preference = new Preference(key, value);

			_getModifiedPreferences().put(key, preference);
		}

		if (preference.isReadOnly()) {
			throw new ReadOnlyException(key);
		}
		else {
			preference.setValues(new String[] {value});
		}
	}

	public void setValues(String key, String[] values)
		throws ReadOnlyException {

		if (key == null) {
			throw new IllegalArgumentException();
		}

		values = _getXmlSafeValues(values);

		Preference preference = _getModifiedPreferences().get(key);

		if (preference == null) {
			preference = new Preference(key, values);

			_getModifiedPreferences().put(key, preference);
		}

		if (preference.isReadOnly()) {
			throw new ReadOnlyException(key);
		}
		else {
			preference.setValues(values);
		}
	}

	public void store() throws IOException, ValidatorException {
		if (_portletId == null) {
			throw new UnsupportedOperationException();
		}

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				_companyId, _portletId);

			if (!_portletId.equals(PortletKeys.LIFERAY_PORTAL)) {
				PreferencesValidator preferencesValidator =
					PortalUtil.getPreferencesValidator(portlet);

				if (preferencesValidator != null) {
					preferencesValidator.validate(this);
				}
			}

			PortletPreferencesLocalServiceUtil.updatePreferences(
				_ownerId, _ownerType, _plid, _portletId, this);
		}
		catch (SystemException se) {
			throw new IOException(se.getMessage());
		}
	}

	protected long getCompanyId() {
		return  _companyId;
	}

	protected long getOwnerId() {
		return _ownerId;
	}

	protected int getOwnerType() {
		return _ownerType;
	}

	protected long getPlid() {
		return _plid;
	}

	protected String getPortletId() {
		return _portletId;
	}

	protected Map<String, Preference> getPreferences() {
		if (_modifiedPreferences == null) {
			if (_originalPreferences ==
					Collections.<String, Preference>emptyMap()) {

				_originalPreferences = new HashMap<String, Preference>();
			}

			return _originalPreferences;
		}
		else {
			return _modifiedPreferences;
		}
	}

	private String _getActualValue(String value) {
		if ((value == null) || (value.equals(_NULL_VALUE))) {
			return null;
		}
		else {
			return XMLFormatter.fromCompactSafe(value);
		}
	}

	private String[] _getActualValues(String[] values) {
		if (values == null) {
			return null;
		}

		if ((values.length == 1) && (_getActualValue(values[0]) == null)) {
			return null;
		}

		String[] actualValues = new String[values.length];

		System.arraycopy(values, 0, actualValues, 0, values.length);

		for (int i = 0; i < actualValues.length; i++) {
			actualValues[i] = _getActualValue(actualValues[i]);
		}

		return actualValues;
	}

	private Map<String, Preference> _getModifiedPreferences() {
		if (_modifiedPreferences == null) {
			_modifiedPreferences = new HashMap<String, Preference>();

			for (Map.Entry<String, Preference> entry :
					_originalPreferences.entrySet()) {

				String key = entry.getKey();
				Preference preference = entry.getValue();

				_modifiedPreferences.put(key, (Preference)preference.clone());
			}
		}

		return _modifiedPreferences;
	}

	private String _getXmlSafeValue(String value) {
		if (value == null) {
			return _NULL_VALUE;
		}
		else {
			return XMLFormatter.toCompactSafe(value);
		}
	}

	private String[] _getXmlSafeValues(String[] values) {
		if (values == null) {
			return new String[] {_getXmlSafeValue(null)};
		}

		String[] xmlSafeValues = new String[values.length];

		System.arraycopy(values, 0, xmlSafeValues, 0, values.length);

		for (int i = 0; i < xmlSafeValues.length; i++) {
			if (xmlSafeValues[i] == null) {
				xmlSafeValues[i] = _getXmlSafeValue(xmlSafeValues[i]);
			}
		}

		return xmlSafeValues;
	}

	private static final String _NULL_VALUE = "NULL_VALUE";

	private static Log _log = LogFactoryUtil.getLog(
		PortletPreferencesImpl.class);

	private long _companyId;
	private PortletPreferences _defaultPreferences;
	private Map<String, Preference> _modifiedPreferences;
	private Map<String, Preference> _originalPreferences;
	private long _ownerId;
	private int _ownerType;
	private long _plid;
	private String _portletId;

}