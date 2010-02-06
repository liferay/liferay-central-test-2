/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.poller;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <a href="PollerResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PollerResponse {

	public static final String POLLER_HINT_HIGH_CONNECTIVITY =
		"pollerHintHighConnectivity";

	public PollerResponse(String portletId, String chunkId) {
		_portletId = portletId;
		_chunkId = chunkId;
	}

	public void setParameter(String name, JSONArray value) {
		_parameterMap.put(name, value);
	}

	public void setParameter(String name, JSONObject value) {
		_parameterMap.put(name, value);
	}

	public void setParameter(String name, String value) {
		_parameterMap.put(name, value);
	}

	public JSONObject toJSONObject() {
		JSONObject pollerResponseJSON = JSONFactoryUtil.createJSONObject();

		pollerResponseJSON.put("portletId", _portletId);

		if (Validator.isNotNull(_chunkId)) {
			pollerResponseJSON.put("chunkId", _chunkId);
		}

		JSONObject dataJSON = JSONFactoryUtil.createJSONObject();

		Iterator<Map.Entry<String, Object>> itr =
			_parameterMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Object> entry = itr.next();

			String name = entry.getKey();
			Object value = entry.getValue();

			if (value instanceof JSONArray) {
				dataJSON.put(name, (JSONArray)value);
			}
			else if (value instanceof JSONObject) {
				dataJSON.put(name, (JSONObject)value);
			}
			else {
				dataJSON.put(name, String.valueOf(value));
			}
		}

		pollerResponseJSON.put("data", dataJSON);

		return pollerResponseJSON;
	}

	private String _chunkId;
	private Map<String, Object> _parameterMap = new HashMap<String, Object>();
	private String _portletId;

}