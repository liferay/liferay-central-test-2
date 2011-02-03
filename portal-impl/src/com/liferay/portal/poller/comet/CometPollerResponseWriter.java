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

package com.liferay.portal.poller.comet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.poller.PollerException;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.poller.comet.CometResponse;
import com.liferay.portal.kernel.poller.comet.CometSession;
import com.liferay.portal.poller.PollerResponseWriter;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class CometPollerResponseWriter implements PollerResponseWriter {

	public CometPollerResponseWriter(CometSession cometSession) {
		_cometSession = cometSession;
	}

	public void close() throws PollerException {
		try {
			_cometSession.close();
		}
		catch (Exception e) {
			throw new PollerException(e);
		}
	}

	public JSONArray getJSONArray() {
		return null;
	}

	public void write(JSONArray jsonArray) throws PollerException {
		write(jsonArray.toString());
	}

	public void write(JSONObject jsonObject) throws PollerException {
		write(jsonObject.toString());
	}

	public void write(PollerResponse pollerResponse) throws PollerException {
		write(pollerResponse.toJSONObject());
	}

	public void write(String data) throws PollerException {
		synchronized (this) {
			try {
				CometResponse cometResponse = _cometSession.getCometResponse();

				cometResponse.writeData(data);
			}
			catch (Exception e) {
				throw new PollerException(e);
			}
		}
	}

	private CometSession _cometSession;

}