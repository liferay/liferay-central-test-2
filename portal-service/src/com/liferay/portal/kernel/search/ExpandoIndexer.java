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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexer;
import com.liferay.portlet.expando.util.ExpandoBridgeIndexerUtil;

import java.util.Set;

/**
 * @author Hugo Huijser
 */
public abstract class ExpandoIndexer extends BaseIndexer {

	protected void addSearchQueryParams(
			BooleanQuery searchQuery, SearchContext searchContext,
			ExpandoBridge expandoBridge, Set<String> attributeNames, String key,
			Object value)
		throws Exception {

		if (attributeNames.contains(key)) {
			UnicodeProperties properties = expandoBridge.getAttributeProperties(
				key);

			if (GetterUtil.getBoolean(
					properties.getProperty(ExpandoBridgeIndexer.INDEXABLE))) {

				String fieldName = ExpandoBridgeIndexerUtil.encodeFieldName(
					key);

				if (Validator.isNotNull((String)value)) {
					if (searchContext.isAndSearch()) {
						searchQuery.addRequiredTerm(
							fieldName, (String)value, true);
					}
					else {
						searchQuery.addTerm(fieldName, (String)value, true);
					}
				}
			}
		}
		else if (Validator.isNotNull(key) && Validator.isNotNull(value)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm(key, String.valueOf(value));
			}
			else {
				searchQuery.addTerm(key, String.valueOf(value));
			}
		}
	}

}