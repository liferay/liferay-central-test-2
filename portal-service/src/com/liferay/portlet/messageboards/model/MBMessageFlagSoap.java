/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="MBMessageFlagSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageFlagSoap implements Serializable {
	public static MBMessageFlagSoap toSoapModel(MBMessageFlag model) {
		MBMessageFlagSoap soapModel = new MBMessageFlagSoap();
		soapModel.setTopicId(model.getTopicId());
		soapModel.setMessageId(model.getMessageId());
		soapModel.setUserId(model.getUserId());
		soapModel.setFlag(model.getFlag());

		return soapModel;
	}

	public static MBMessageFlagSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			MBMessageFlag model = (MBMessageFlag)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (MBMessageFlagSoap[])soapModels.toArray(new MBMessageFlagSoap[0]);
	}

	public MBMessageFlagSoap() {
	}

	public MBMessageFlagPK getPrimaryKey() {
		return new MBMessageFlagPK(_topicId, _messageId, _userId);
	}

	public void setPrimaryKey(MBMessageFlagPK pk) {
		setTopicId(pk.topicId);
		setMessageId(pk.messageId);
		setUserId(pk.userId);
	}

	public String getTopicId() {
		return _topicId;
	}

	public void setTopicId(String topicId) {
		_topicId = topicId;
	}

	public String getMessageId() {
		return _messageId;
	}

	public void setMessageId(String messageId) {
		_messageId = messageId;
	}

	public String getUserId() {
		return _userId;
	}

	public void setUserId(String userId) {
		_userId = userId;
	}

	public String getFlag() {
		return _flag;
	}

	public void setFlag(String flag) {
		_flag = flag;
	}

	private String _topicId;
	private String _messageId;
	private String _userId;
	private String _flag;
}