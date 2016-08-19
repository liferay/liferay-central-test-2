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

package com.liferay.portal.search.internal.analysis;

import com.liferay.portal.kernel.search.query.QueryPreProcessConfiguration;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.FieldQueryBuilderFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
@Component(
	immediate = true,
	property = {"description.fields=description", "title.fields=title"},
	service = FieldQueryBuilderFactory.class
)
public class FieldQueryBuilderFactoryImpl implements FieldQueryBuilderFactory {

	@Override
	public FieldQueryBuilder getQueryBuilder(String field) {
		if (queryPreProcessConfiguration.isSubstringSearchAlways(field)) {
			return substringQueryBuilder;
		}

		if (_descriptionFields.contains(field)) {
			return descriptionQueryBuilder;
		}

		if (_titleFields.contains(field)) {
			return titleQueryBuilder;
		}

		return null;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_descriptionFields = getFields(properties, "description.fields");
		_titleFields = getFields(properties, "title.fields");
	}

	protected Collection<String> getFields(
		Map<String, Object> properties, String key) {

		String[] values = StringUtil.split(
			GetterUtil.getString(properties.get(key)), CharPool.PIPE);

		return new HashSet<>(Arrays.asList(values));
	}

	@Reference
	protected DescriptionFieldQueryBuilder descriptionQueryBuilder;

	@Reference
	protected QueryPreProcessConfiguration queryPreProcessConfiguration;

	@Reference
	protected SubstringFieldQueryBuilder substringQueryBuilder;

	@Reference
	protected TitleFieldQueryBuilder titleQueryBuilder;

	private volatile Collection<String> _descriptionFields =
		Collections.singleton("description");
	private volatile Collection<String> _titleFields = Collections.singleton(
		"title");

}