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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="MBDiscussionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBDiscussionSoap implements Serializable {
	public static MBDiscussionSoap toSoapModel(MBDiscussion model) {
		MBDiscussionSoap soapModel = new MBDiscussionSoap();
		soapModel.setDiscussionId(model.getDiscussionId());
		soapModel.setClassName(model.getClassName());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setThreadId(model.getThreadId());

		return soapModel;
	}

	public static MBDiscussionSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			MBDiscussion model = (MBDiscussion)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (MBDiscussionSoap[])soapModels.toArray(new MBDiscussionSoap[0]);
	}

	public MBDiscussionSoap() {
	}

	public String getPrimaryKey() {
		return _discussionId;
	}

	public void setPrimaryKey(String pk) {
		setDiscussionId(pk);
	}

	public String getDiscussionId() {
		return _discussionId;
	}

	public void setDiscussionId(String discussionId) {
		_discussionId = discussionId;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public String getClassPK() {
		return _classPK;
	}

	public void setClassPK(String classPK) {
		_classPK = classPK;
	}

	public String getThreadId() {
		return _threadId;
	}

	public void setThreadId(String threadId) {
		_threadId = threadId;
	}

	private String _discussionId;
	private String _className;
	private String _classPK;
	private String _threadId;
}