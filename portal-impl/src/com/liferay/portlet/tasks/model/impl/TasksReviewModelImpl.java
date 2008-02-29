/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tasks.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.tasks.model.TasksReview;

import com.liferay.util.Html;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="TasksReviewModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>TasksReview</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tasks.service.model.TasksReview
 * @see com.liferay.portlet.tasks.service.model.TasksReviewModel
 * @see com.liferay.portlet.tasks.service.model.impl.TasksReviewImpl
 *
 */
public class TasksReviewModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "TasksReview";
	public static final Object[][] TABLE_COLUMNS = {
			{ "reviewId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "assigningUserId", new Integer(Types.BIGINT) },
			

			{ "assigningUserName", new Integer(Types.VARCHAR) },
			

			{ "proposalId", new Integer(Types.BIGINT) },
			

			{ "stage", new Integer(Types.INTEGER) },
			

			{ "completed", new Integer(Types.BOOLEAN) },
			

			{ "rejected", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table TasksReview (reviewId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,assigningUserId LONG,assigningUserName VARCHAR(75) null,proposalId LONG,stage INTEGER,completed BOOLEAN,rejected BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table TasksReview";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.tasks.model.TasksReview"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.tasks.model.TasksReview"));

	public TasksReviewModelImpl() {
	}

	public long getPrimaryKey() {
		return _reviewId;
	}

	public void setPrimaryKey(long pk) {
		setReviewId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_reviewId);
	}

	public long getReviewId() {
		return _reviewId;
	}

	public void setReviewId(long reviewId) {
		if (reviewId != _reviewId) {
			_reviewId = reviewId;
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
			_userId = userId;
		}
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			_userName = userName;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public long getAssigningUserId() {
		return _assigningUserId;
	}

	public void setAssigningUserId(long assigningUserId) {
		if (assigningUserId != _assigningUserId) {
			_assigningUserId = assigningUserId;
		}
	}

	public String getAssigningUserName() {
		return GetterUtil.getString(_assigningUserName);
	}

	public void setAssigningUserName(String assigningUserName) {
		if (((assigningUserName == null) && (_assigningUserName != null)) ||
				((assigningUserName != null) && (_assigningUserName == null)) ||
				((assigningUserName != null) && (_assigningUserName != null) &&
				!assigningUserName.equals(_assigningUserName))) {
			_assigningUserName = assigningUserName;
		}
	}

	public long getProposalId() {
		return _proposalId;
	}

	public void setProposalId(long proposalId) {
		if (proposalId != _proposalId) {
			_proposalId = proposalId;
		}
	}

	public int getStage() {
		return _stage;
	}

	public void setStage(int stage) {
		if (stage != _stage) {
			_stage = stage;
		}
	}

	public boolean getCompleted() {
		return _completed;
	}

	public boolean isCompleted() {
		return _completed;
	}

	public void setCompleted(boolean completed) {
		if (completed != _completed) {
			_completed = completed;
		}
	}

	public boolean getRejected() {
		return _rejected;
	}

	public boolean isRejected() {
		return _rejected;
	}

	public void setRejected(boolean rejected) {
		if (rejected != _rejected) {
			_rejected = rejected;
		}
	}

	public TasksReview toEscapedModel() {
		if (isEscapedModel()) {
			return (TasksReview)this;
		}
		else {
			TasksReview model = new TasksReviewImpl();

			model.setEscapedModel(true);

			model.setReviewId(getReviewId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(Html.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setAssigningUserId(getAssigningUserId());
			model.setAssigningUserName(Html.escape(getAssigningUserName()));
			model.setProposalId(getProposalId());
			model.setStage(getStage());
			model.setCompleted(getCompleted());
			model.setRejected(getRejected());

			model = (TasksReview)Proxy.newProxyInstance(TasksReview.class.getClassLoader(),
					new Class[] { TasksReview.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		TasksReviewImpl clone = new TasksReviewImpl();

		clone.setReviewId(getReviewId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setAssigningUserId(getAssigningUserId());
		clone.setAssigningUserName(getAssigningUserName());
		clone.setProposalId(getProposalId());
		clone.setStage(getStage());
		clone.setCompleted(getCompleted());
		clone.setRejected(getRejected());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		TasksReviewImpl tasksReview = (TasksReviewImpl)obj;

		int value = 0;

		value = DateUtil.compareTo(getCreateDate(), tasksReview.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		TasksReviewImpl tasksReview = null;

		try {
			tasksReview = (TasksReviewImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = tasksReview.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	private long _reviewId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _assigningUserId;
	private String _assigningUserName;
	private long _proposalId;
	private int _stage;
	private boolean _completed;
	private boolean _rejected;
}