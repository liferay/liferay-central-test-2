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

import com.liferay.portal.json.transformer.FlexjsonObjectTransformer;
import com.liferay.portal.json.transformer.JSONArrayTransformer;
import com.liferay.portal.json.transformer.JSONObjectTransformer;
import com.liferay.portal.json.transformer.JSONSerializableTransformer;
import com.liferay.portal.json.transformer.RepositoryModelTransformer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.repository.model.RepositoryModel;

import flexjson.TransformerUtil;

import flexjson.transformer.NullTransformer;
import flexjson.transformer.Transformer;
import flexjson.transformer.TransformerWrapper;
import flexjson.transformer.TypeTransformerMap;

import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Map;

/**
 * Initializes JSON framework by setting default transformers.
 *
 * @author Igor Spasic
 */
public class JSONInit {

	/**
	 * Initializes JSON serializer by registering default transformers.
	 * Due to current 'closed' flexjson configuration, this is done
	 * using reflection.
	 *
	 * https://sourceforge.net/tracker/?func=detail&aid=3280551&group_id=194042&atid=947845
	 * https://sourceforge.net/tracker/?func=detail&aid=3277973&group_id=194042&atid=947842
	 */
	public static synchronized void init() {
		if (_initalized == true) {
			return;
		}
		try {

			// bypass private static final

			Field defaultTransformers =
				TransformerUtil.class.getDeclaredField("defaultTransformers");

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(defaultTransformers,
				defaultTransformers.getModifiers() & ~Modifier.FINAL);

			defaultTransformers.setAccessible(true);

			// replace original default transformers

			TypeTransformerMap originalTransformersMap =
				TransformerUtil.getDefaultTypeTransformers();

			TypeTransformerMap newTransformersMap = new TypeTransformerMap();

			for (Map.Entry<Class, Transformer> entry :
				originalTransformersMap.entrySet()) {

				newTransformersMap.put(entry.getKey(), entry.getValue());
			}

			_registerDefaultTransformers(newTransformersMap);

			defaultTransformers.set(null, newTransformersMap);
		}
		catch (Exception ex) {
			throw new RuntimeException("FLEXJson initialization failed.", ex);
		}

		_initalized = true;
	}

	/**
	 * Registers application-wide default transformers.
	 */
	private static void _registerDefaultTransformers(
		TypeTransformerMap transformersMap) {

		transformersMap.put(InputStream.class,
			new TransformerWrapper(new NullTransformer()));

		transformersMap.put(JSONObject.class,
			new TransformerWrapper(new JSONObjectTransformer()));

		transformersMap.put(JSONArray.class,
			new TransformerWrapper(new JSONArrayTransformer()));

		transformersMap.put(JSONSerializable.class,
			new TransformerWrapper(new JSONSerializableTransformer()));

		transformersMap.put(RepositoryModel.class,
			new TransformerWrapper(new RepositoryModelTransformer()));

		transformersMap.put(Object.class,
			new TransformerWrapper(new FlexjsonObjectTransformer()));
	}

	private static boolean _initalized = false;

}