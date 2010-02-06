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

package com.liferay.portlet.messageboards.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBMessageFlagSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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