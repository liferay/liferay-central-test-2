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

package com.liferay.portal.kernel.notifications.event;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

/**
 * @author Edward Han
 */
public class NotificationEvent implements Serializable {

	public static final String KEY_PAYLOAD = "payload";
	public static final String KEY_TIMESTAMP = "timestamp";
	public static final String KEY_TYPE = "type";
	public static final String KEY_UUID = "uuid";

	public NotificationEvent(
		long timestamp, String type, JSONObject payload) {

		_payload = payload;
		_timestamp = timestamp;
		_type = type;
		_uuid = PortalUUIDUtil.generate();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof NotificationEvent) {
			String uuid = ((NotificationEvent) o).getUuid();

			return _uuid.equals(uuid);
		}

		return false;
	}

	public long getDeliverBy() {
		return _deliverBy;
	}

	public String getJSONString() {
		JSONObject obj = JSONFactoryUtil.createJSONObject();

		obj.put(KEY_PAYLOAD, _payload);
		obj.put(KEY_TIMESTAMP, _timestamp);
		obj.put(KEY_TYPE, _type);
		obj.put(KEY_UUID, _uuid);

		return obj.toString();
	}

	public JSONObject getPayload() {
		return _payload;
	}

	public long getTimestamp() {
		return _timestamp;
	}

	public String getType() {
		return _type;
	}

	public String getUuid() {
		return _uuid;
	}

	@Override
	public int hashCode() {
		return _uuid != null ? _uuid.hashCode() : 0;
	}

	public boolean isDeliveryRequired() {
		return _deliveryRequired;
	}

	public void setDeliverBy(long deliverBy)
		throws IllegalArgumentException {

		if (deliverBy <= 0 && _deliveryRequired) {
			throw new IllegalArgumentException(DELIVER_BY_ERROR);
		}

		_deliverBy = deliverBy;
	}

	public void setDeliveryRequired(long deliverBy)
		throws IllegalArgumentException {

		if (deliverBy <= 0) {
			throw new IllegalArgumentException(DELIVER_BY_ERROR);
		}

		_deliveryRequired = true;
		_deliverBy = deliverBy;
	}

	public void setTimestamp(long timestamp) {
		_timestamp = timestamp;
	}

	private static final String DELIVER_BY_ERROR =
		"DeliverBy timestamp must be greater than zero if delivery is required";

	private JSONObject _payload;
	private long _deliverBy = 0;
	private boolean _deliveryRequired = false;
	private long _timestamp;
	private String _type;
	private String _uuid;
}