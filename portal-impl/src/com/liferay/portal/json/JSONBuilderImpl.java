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

import com.liferay.portal.kernel.json.JSONBuilder;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * @author Igor Spasic
 */
public class JSONBuilderImpl implements JSONBuilder {

	public String createJSONString(Object object) {

		if (object == null) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			return jsonObject.toString();
		}

		if (object instanceof JSONSerializable) {
			return ((JSONSerializable) object).toJSONString();
		}

		if (object instanceof Number || object instanceof Boolean) {
			return object.toString();
		}

		if (object instanceof String) {
			return JSONFactoryUtil.serialize(object);
		}

		if (object instanceof List) {
			List list = (List) object;

			StringBundler jsonArray = new StringBundler(list.size() * 2 + 2);

			jsonArray.append(StringPool.OPEN_BRACKET);

			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				if (i != 0) {
					jsonArray.append(StringPool.COMMA);
				}
				jsonArray.append(createJSONString(o));
			}

			jsonArray.append(StringPool.CLOSE_BRACKET);

			return jsonArray.toString();
		}

		Class type = object.getClass();

		if (type.isArray()) {
			Object[] array = (Object[])object;

			StringBundler jsonArray = new StringBundler(array.length * 2 + 2);

			jsonArray.append(StringPool.OPEN_BRACKET);

			for (int i = 0; i < array.length; i++) {
				Object o = array[i];
				if (i != 0) {
					jsonArray.append(StringPool.COMMA);
				}
				jsonArray.append(createJSONString(o));
			}

			jsonArray.append(StringPool.CLOSE_BRACKET);

			return jsonArray.toString();
		}

		JSONSerializer jsonSerializer = _serializerManager.lookup(object);

		return jsonSerializer.toJSONString(object);
	}

	private JSONSerializerManager _serializerManager
		= new JSONSerializerManager();

}