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

package com.liferay.portal.json.serializer;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * @author Igor Spasic
 */
public class BaseModelJSONSerializer implements JSONSerializer {

	public BaseModelJSONSerializer(String serializerClassName, Class type) {

		_methodKey = new MethodKey(serializerClassName, "toJSONObject", type);
	}

	public String toJSONString(Object object) {

		MethodHandler methodHandler = new MethodHandler(_methodKey, object);

		try {
			JSONObject jsonObject = (JSONObject)methodHandler.invoke(false);

			return jsonObject.toString();
		}
		catch (Exception e) {
			_log.error(e,e);
			return null;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseModelJSONSerializer.class);

	private MethodKey _methodKey;

}