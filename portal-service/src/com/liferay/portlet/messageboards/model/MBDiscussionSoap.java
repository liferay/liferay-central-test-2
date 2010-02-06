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
import java.util.List;

/**
 * <a href="MBDiscussionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.messageboards.service.http.MBDiscussionServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.messageboards.service.http.MBDiscussionServiceSoap
 * @generated
 */
public class MBDiscussionSoap implements Serializable {
	public static MBDiscussionSoap toSoapModel(MBDiscussion model) {
		MBDiscussionSoap soapModel = new MBDiscussionSoap();

		soapModel.setDiscussionId(model.getDiscussionId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setThreadId(model.getThreadId());

		return soapModel;
	}

	public static MBDiscussionSoap[] toSoapModels(MBDiscussion[] models) {
		MBDiscussionSoap[] soapModels = new MBDiscussionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MBDiscussionSoap[][] toSoapModels(MBDiscussion[][] models) {
		MBDiscussionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MBDiscussionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new MBDiscussionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MBDiscussionSoap[] toSoapModels(List<MBDiscussion> models) {
		List<MBDiscussionSoap> soapModels = new ArrayList<MBDiscussionSoap>(models.size());

		for (MBDiscussion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MBDiscussionSoap[soapModels.size()]);
	}

	public MBDiscussionSoap() {
	}

	public long getPrimaryKey() {
		return _discussionId;
	}

	public void setPrimaryKey(long pk) {
		setDiscussionId(pk);
	}

	public long getDiscussionId() {
		return _discussionId;
	}

	public void setDiscussionId(long discussionId) {
		_discussionId = discussionId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getThreadId() {
		return _threadId;
	}

	public void setThreadId(long threadId) {
		_threadId = threadId;
	}

	private long _discussionId;
	private long _classNameId;
	private long _classPK;
	private long _threadId;
}