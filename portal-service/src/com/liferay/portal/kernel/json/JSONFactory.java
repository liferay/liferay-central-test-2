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

package com.liferay.portal.kernel.json;

/**
 * @author Brian Wing Shun Chan
 */
public interface JSONFactory {

	public JSONArray createJSONArray();

	public JSONArray createJSONArray(String json) throws JSONException;

	public JSONObject createJSONObject();

	public JSONObject createJSONObject(String json) throws JSONException;

	public Object deserialize(JSONObject jsonObj);

	public Object deserialize(String json);

	public String serialize(Object obj);

}