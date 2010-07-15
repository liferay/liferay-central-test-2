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

package com.liferay.portlet.messageboards.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.messageboards.service.http.MBMessageFlagServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.messageboards.service.http.MBMessageFlagServiceSoap
 * @generated
 */
public class MBMessageFlagSoap implements Serializable {
	public static MBMessageFlagSoap toSoapModel(MBMessageFlag model) {
		MBMessageFlagSoap soapModel = new MBMessageFlagSoap();

		soapModel.setMessageFlagId(model.getMessageFlagId());
		soapModel.setUserId(model.getUserId());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setThreadId(model.getThreadId());
		soapModel.setMessageId(model.getMessageId());
		soapModel.setFlag(model.getFlag());

		return soapModel;
	}

	public static MBMessageFlagSoap[] toSoapModels(MBMessageFlag[] models) {
		MBMessageFlagSoap[] soapModels = new MBMessageFlagSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MBMessageFlagSoap[][] toSoapModels(MBMessageFlag[][] models) {
		MBMessageFlagSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MBMessageFlagSoap[models.length][models[0].length];
		}
		else {
			soapModels = new MBMessageFlagSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MBMessageFlagSoap[] toSoapModels(List<MBMessageFlag> models) {
		List<MBMessageFlagSoap> soapModels = new ArrayList<MBMessageFlagSoap>(models.size());

		for (MBMessageFlag model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MBMessageFlagSoap[soapModels.size()]);
	}

	public MBMessageFlagSoap() {
	}

	public long getPrimaryKey() {
		return _messageFlagId;
	}

	public void setPrimaryKey(long pk) {
		setMessageFlagId(pk);
	}

	public long getMessageFlagId() {
		return _messageFlagId;
	}

	public void setMessageFlagId(long messageFlagId) {
		_messageFlagId = messageFlagId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getThreadId() {
		return _threadId;
	}

	public void setThreadId(long threadId) {
		_threadId = threadId;
	}

	public long getMessageId() {
		return _messageId;
	}

	public void setMessageId(long messageId) {
		_messageId = messageId;
	}

	public int getFlag() {
		return _flag;
	}

	public void setFlag(int flag) {
		_flag = flag;
	}

	private long _messageFlagId;
	private long _userId;
	private Date _modifiedDate;
	private long _threadId;
	private long _messageId;
	private int _flag;
}