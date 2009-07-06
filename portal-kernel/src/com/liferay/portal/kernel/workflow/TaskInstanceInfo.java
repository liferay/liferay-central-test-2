/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.workflow;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * <a href="TaskInstanceInfo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class TaskInstanceInfo implements Serializable {

	public TaskInstanceInfo(
			long taskInstanceId, long userId, Date createDate) {
		_createDate = createDate;
		_taskInstanceId = taskInstanceId;
		_userId = userId;
	}

	public Map<String, Object> getContextInfo() {
		return _contextInfo;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getDescription() {
		return _description;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public String getName() {
		return _name;
	}

	public Set<Long> getPooledUserIds() {
		return _pooledUserIds;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public long getTaskInstanceId() {
		return _taskInstanceId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setContextInfo(Map<String, Object> contextInfo) {
		_contextInfo = contextInfo;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPooledUserIds(Set<Long> pooledUserIds) {
		_pooledUserIds = pooledUserIds;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@Override
	public String toString() {
		return "TaskInstanceInfo[" +
			"id:" + _taskInstanceId +
			", name:" + _name +
			", description:" + _description +
			", userId:" + _userId +
			", pooledUserIds:" + _pooledUserIds +
			", create:" + _createDate +
			", start:" + _startDate +
			", end:" + _endDate +
			", contextInfo:" + _contextInfo +
			"]";
	}

	private Map<String, Object> _contextInfo;
	private Date _createDate;
	private String _description;
	private Date _endDate;
	private String _name;
	private Set<Long> _pooledUserIds;
	private Date _startDate;
	private long _taskInstanceId;
	private long _userId;

}