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
import com.liferay.portal.poller.JSONPollerResponseWriter;
import com.liferay.portal.poller.PollerResponseWriter;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class CometPollerResponseWriter extends JSONPollerResponseWriter {

	public CometPollerResponseWriter(CometSession cometSession) {
		_cometSession = cometSession;
	}

	@Override
	protected void doClose() throws PollerException {
		try {
			CometResponse cometResponse = _cometSession.getCometResponse();

			cometResponse.writeData(getJSONArray().toString());
		}
		catch (Exception e) {
			throw new PollerException(e);
		}
	}

	private CometSession _cometSession;
}