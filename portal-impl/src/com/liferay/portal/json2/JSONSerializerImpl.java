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

package com.liferay.portal.json2;

import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.json2.JSONSerializable;
import com.liferay.portal.kernel.json2.JSONSerializer;

import flexjson.TransformerUtil;

import flexjson.transformer.NullTransformer;
import flexjson.transformer.Transformer;
import flexjson.transformer.TypeTransformerMap;

import java.io.InputStream;

import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JSONSerializerImpl implements JSONSerializer {

	public JSONSerializerImpl() {
		_serializer = new flexjson.JSONSerializer();

		_serializer.exclude("*.class");

		_registerDefaultTransformers();

		_serializer.transform(new NullTransformer(), InputStream.class);

		_serializer.transform(
			new JSONSerializableTransformer(), JSONSerializable.class);
		_serializer.transform(new FlexjsonObjectTransformer(), Object.class);
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

	public JSONSerializerImpl transform(
		JSONTransformer jsonTransformer, Class... types) {

		Transformer transformer;

		if (jsonTransformer instanceof Transformer) {
			transformer = (Transformer)jsonTransformer;
		}
		else {
			transformer = new FlexjsonTransformerAdapter(jsonTransformer);
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
			transformer = new FlexjsonTransformerAdapter(jsonTransformer);
		}

		_serializer.transform(transformer, fields);

		return this;
	}

	/**
	 * https://sourceforge.net/tracker/?func=detail&aid=3277973&group_id=194042&atid=947842
	 */
	private void _registerDefaultTransformers() {

		TypeTransformerMap defaultTransformers =
			TransformerUtil.getDefaultTypeTransformers();

		for (Map.Entry<Class, Transformer> entry :
			defaultTransformers.entrySet()) {

			_serializer.transform(entry.getValue(), entry.getKey());
		}
	}

	private final flexjson.JSONSerializer _serializer;

}