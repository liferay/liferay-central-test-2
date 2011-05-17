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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.json.JSONTransformer;

import flexjson.transformer.Transformer;

/**
 * Wrapper over flexjson serializer.
 *
 * @author Igor Spasic
 */
public class JSONSerializerImpl implements JSONSerializer {

	public JSONSerializerImpl() {
		_serializer = new flexjson.JSONSerializer();
	}

	public JSONSerializerImpl exclude(String... fields) {
		_serializer.exclude(fields);

		return this;
	}

	public JSONSerializerImpl include(String... fields) {
		_serializer.include(fields);

		return this;
	}

	public String serialize(Object target) {
		return _serializer.serialize(target);
	}

	public String serializeDeep(Object target) {
		return _serializer.deepSerialize(target);
	}

	public JSONSerializerImpl transform(
		JSONTransformer jsonTransformer, Class... types) {

		Transformer transformer;

		if (jsonTransformer instanceof Transformer) {
			transformer = (Transformer)jsonTransformer;
		}
		else {
			transformer = new FlexjsonTransformer(jsonTransformer);
		}

		_serializer.transform(transformer, types);

		return this;
	}

	public JSONSerializerImpl transform(
		JSONTransformer jsonTransformer, String... fields) {

		Transformer transformer;

		if (jsonTransformer instanceof Transformer) {
			transformer = (Transformer)jsonTransformer;
		}
		else {
			transformer = new FlexjsonTransformer(jsonTransformer);
		}

		_serializer.transform(transformer, fields);

		return this;
	}

	private final flexjson.JSONSerializer _serializer;

}