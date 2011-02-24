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

package com.liferay.portal.kernel.search;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

/**
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 * @author Allen Chiang
 * @author Alex Wallace
 */
public class Field implements Serializable {

	public static final String ASSET_CATEGORY_IDS = "assetCategoryIds";

	public static final String ASSET_CATEGORY_NAMES = "assetCategoryNames";

	public static final String ASSET_TAG_NAMES = "assetTagNames";

	public static final String CATEGORY_ID = "categoryId";

	public static final String COMMENTS = "comments";

	public static final String COMPANY_ID = "companyId";

	public static final String CONTENT = "content";

	public static final String DESCRIPTION = "description";

	public static final String ENTRY_CLASS_NAME = "entryClassName";

	public static final String ENTRY_CLASS_PK = "entryClassPK";

	public static final String FOLDER_ID = "folderId";

	public static final String GROUP_ID = "groupId";

	public static final String GROUP_ROLE_ID = "groupRoleId";

	public static final String MODIFIED = "modified";

	public static final String NAME = "name";

	public static final String NODE_ID = "nodeId";

	public static final String ORGANIZATION_ID = "organzationId";

	public static final String PORTLET_ID = "portletId";

	public static final String PROPERTIES = "properties";

	public static final String ROLE_ID = "roleId";

	public static final String ROOT_ENTRY_CLASS_PK = "rootEntryClassPK";

	public static final String SCOPE_GROUP_ID = "scopeGroupId";

	public static final String STAGING_GROUP = "stagingGroup";

	public static final String TITLE = "title";

	public static final String TYPE = "type";

	public static final String UID = "uid";

	public static final String URL = "url";

	public static final String USER_ID = "userId";

	public static final String USER_NAME = "userName";

	public static final String VERSION = "version";

	public Field(String name, Map<Locale, String> localizedValues) {
		_name = name;
		_localizedValues = localizedValues;
		_localized = true;
	}

	public Field(String name, String value) {
		this(name, new String[] {value});
	}

	public Field(String name, String[] values) {
		_name = name;
		_values = values;
	}

	/**
	 * @deprecated
	 */
	public Field(String name, String value, boolean tokenized) {
		this(name, value);

		setTokenized(tokenized);
	}

	/**
	 * @deprecated
	 */
	public Field(String name, String[] values, boolean tokenized) {
		this(name, values);

		setTokenized(tokenized);
	}

	/**
	 * @deprecated
	 */
	public Field(String name, String[] values, boolean tokenized, float boost) {
		this(name, values);

		setBoost(boost);
		setTokenized(tokenized);
	}

	public float getBoost() {
		return _boost;
	}

	public Map<Locale, String> getLocalizedValues() {
		return _localizedValues;
	}

	public String getName() {
		return _name;
	}

	public String getValue() {
		if ((_values != null) && (_values.length > 0)) {
			return _values[0];
		}
		else {
			return null;
		}
	}

	public String[] getValues() {
		return _values;
	}

	public boolean isLocalized() {
		return _localized;
	}

	public boolean isNumeric() {
		return _numeric;
	}

	public boolean isTokenized() {
		return _tokenized;
	}

	public void setBoost(float boost) {
		_boost = boost;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNumeric(boolean numeric) {
		_numeric = numeric;
	}

	public void setTokenized(boolean tokenized) {
		_tokenized = tokenized;
	}

	public void setValue(String value) {
		setValues(new String[] {value});
	}

	public void setValues(String[] values) {
		_values = values;
	}

	private float _boost = 1;
	private Map<Locale,String> _localizedValues;
	private boolean _localized = false;
	private String _name;
	private boolean _numeric = false;
	private boolean _tokenized = false;
	private String[] _values;
}