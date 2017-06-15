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

package com.liferay.vulcan.response.control;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public class FieldsRetriever {

	public static Fields getFields(HttpServletRequest httpServletRequest) {
		Map<String, String[]> parameterMap =
			httpServletRequest.getParameterMap();

		Function<String, String> typeExtractFunction = key -> key.substring(
			key.indexOf("[") + 1, key.indexOf("]"));

		Set<Map.Entry<String, String[]>> set = parameterMap.entrySet();

		Stream<Map.Entry<String, String[]>> stream = set.stream();

		Map<String, List<String>> fieldsMap = stream.filter(
			entry -> {
				String key = entry.getKey();

				return key.matches(_FIELDS_PATTERN);
			}
		).filter(
			entry -> {
				String[] value = entry.getValue();

				if (value.length == 1) {
					return true;
				}

				return false;
			}
		).filter(
			entry -> !entry.getValue()[0].isEmpty()
		).collect(
			Collectors.toMap(
				entry -> typeExtractFunction.apply(entry.getKey()),
				entry -> Arrays.asList(entry.getValue()[0].split(",")))
		);

		return new FieldsImpl(fieldsMap);
	}

	public static class FieldsImpl implements Fields {

		public FieldsImpl(Map<String, List<String>> fieldsMap) {
			_fieldsMap = fieldsMap;
		}

		@Override
		public Predicate<String> getFieldIncludedPredicate(List<String> types) {
			return field -> {
				Stream<String> stream = types.stream();

				return stream.map(
					_fieldsMap::get
				).anyMatch(
					fields -> (fields != null) && fields.contains(field)
				);
			};
		}

		private final Map<String, List<String>> _fieldsMap;

	}

	private static final String _FIELDS_PATTERN = "fields\\[([A-Z|a-z]+)]";

}