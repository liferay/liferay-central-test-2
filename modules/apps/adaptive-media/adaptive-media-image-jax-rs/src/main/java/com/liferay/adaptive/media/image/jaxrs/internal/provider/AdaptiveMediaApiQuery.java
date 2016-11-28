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

package com.liferay.adaptive.media.image.jaxrs.internal.provider;

import com.liferay.adaptive.media.AdaptiveMediaAttribute;

import java.util.List;
import java.util.Map;

/**
 * @author Alejandro Hernández
 */
@FunctionalInterface
public interface AdaptiveMediaApiQuery {

	public List<QueryAttribute> select(
		Map<String, AdaptiveMediaAttribute<?, ?>> availableAttributes);

	public final class QueryAttribute {

		public QueryAttribute(
			AdaptiveMediaAttribute<?, ?> attribute, Object value) {

			_attribute = attribute;
			_value = value;
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}

			if ((o == null) || (getClass() != o.getClass())) {
				return false;
			}

			QueryAttribute that = (QueryAttribute)o;

			if (_value.equals(that._value) &&
				_attribute.equals(that._attribute)) {

				return true;
			}

			return false;
		}

		public AdaptiveMediaAttribute<?, ?> getAttribute() {
			return _attribute;
		}

		public Object getValue() {
			return _value;
		}

		public int hashCode() {
			int result = _attribute.hashCode();

			result = 31 * result + _value.hashCode();

			return result;
		}

		private final AdaptiveMediaAttribute<?, ?> _attribute;
		private final Object _value;

	}

}