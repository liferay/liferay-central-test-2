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

package com.liferay.portal.poller;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.poller.PollerException;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.poller.PollerWriterClosedException;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class JSONPollerResponseWriter implements PollerResponseWriter {

	public JSONPollerResponseWriter() {
		_jsonArray = JSONFactoryUtil.createJSONArray();
	}

	public void close() throws PollerException {
		synchronized (this) {
			if (!_closed) {
				try {
					doClose();
				}
				finally {
					_closed = true;
				}
			}
		}
	}

	public JSONArray getJSONArray() {
		return _jsonArray;
	}

	public void write(JSONArray jsonArray) throws PollerException {
		if (!_closed) {
			_jsonArray.put(jsonArray);
		}
		else {
			throw new PollerWriterClosedException();
		}
	}

	public void write(JSONObject jsonObject) throws PollerException {
		if (!_closed) {
			_jsonArray.put(jsonObject);
		}
		else {
			throw new PollerWriterClosedException();
		}
	}

	public void write(PollerResponse pollerResponse) throws PollerException {
		if (!_closed) {
			_jsonArray.put(pollerResponse.toJSONObject());
		}
		else {
			throw new PollerWriterClosedException();
		}
	}

	public void write(String string) throws PollerException {
		if (!_closed) {
			_jsonArray.put(string);
		}
		else {
			throw new PollerWriterClosedException();
		}
	}

	protected void doClose() throws PollerException {
	}

	private boolean _closed = false;
	private JSONArray _jsonArray;
}