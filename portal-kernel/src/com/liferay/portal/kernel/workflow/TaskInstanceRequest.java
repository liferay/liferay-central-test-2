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

import java.util.Map;
import java.util.Set;

/**
 * <a href="TaskInstanceRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class TaskInstanceRequest implements Serializable {

	public static TaskInstanceRequest createAssignRequest(
			long taskInstanceId, long userId, String description,
			Map<String, Object> contextInfo) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.ASSIGN);
		request._taskInstanceId = taskInstanceId;
		request._userId = userId;
		request._description = description;
		request._contextInfo = contextInfo;
		return request;
	}

	public static TaskInstanceRequest createFulfillRequest(
			long taskInstanceId, long userId, String description,
			Map<String, Object> contextInfo) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.FULFIL);
		request._taskInstanceId = taskInstanceId;
		request._userId = userId;
		request._description = description;
		request._contextInfo = contextInfo;
		return request;
	}

	public static TaskInstanceRequest createGetByProcessInstanceIdRequest(
			long processInstanceId) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.GET);
		request._processInstanceId = processInstanceId;
		request._getAll = true;
		return request;
	}

	public static TaskInstanceRequest createGetByProcessInstanceIdRequest(
			long processInstanceId, boolean finished) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.GET);
		request._processInstanceId = processInstanceId;
		request._finished = finished;
		request._getAll = false;
		return request;
	}

	public static TaskInstanceRequest createGetByRoleRequest(long roleId) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.GET);
		request._roleId = roleId;
		request._getAll = true;
		return request;
	}

	public static TaskInstanceRequest createGetByRoleRequest(
			long roleId, boolean finished) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.GET);
		request._roleId = roleId;
		request._finished = finished;
		request._getAll = false;
		return request;
	}

	public static TaskInstanceRequest createGetByRolesRequest(
			Set<Long> roleIds) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.GET);
		request._roleIds = roleIds;
		request._getAll = true;
		return request;
	}

	public static TaskInstanceRequest createGetByRolesRequest(
			Set<Long> roleIds, boolean finished) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.GET);
		request._roleIds = roleIds;
		request._finished = finished;
		request._getAll = false;
		return request;
	}

	public static TaskInstanceRequest createGetByTokenIdRequest(long tokenId) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.GET);
		request._tokenId = tokenId;
		request._getAll = true;
		return request;
	}

	public static TaskInstanceRequest createGetByTokenIdRequest(
			long tokenId, boolean finished) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.GET);
		request._tokenId = tokenId;
		request._finished = finished;
		request._getAll = false;
		return request;
	}

	public static TaskInstanceRequest createGetByUserIdRequest(long userId) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.GET);
		request._userId = userId;
		request._getAll = true;
		return request;
	}

	public static TaskInstanceRequest createGetByUserIdRequest(
			long userId, boolean finished) {
		TaskInstanceRequest request =
			new TaskInstanceRequest(TaskInstanceRequestType.GET);
		request._userId = userId;
		request._finished = finished;
		request._getAll = false;
		return request;
	}

	private TaskInstanceRequest(TaskInstanceRequestType type) {
		_type = type;
	}

	public long getUserId() {
		return _userId;
	}

	public Map<String, Object> getContextInfo() {
		return _contextInfo;
	}

	public String getDescription() {
		return _description;
	}

	public long getProcessInstanceId() {
		return _processInstanceId;
	}

	public long getRoleId() {
		return _roleId;
	}

	public Set<Long> getRoleIds() {
		return _roleIds;
	}

	public long getTaskInstanceId() {
		return _taskInstanceId;
	}

	public long getTokenId() {
		return _tokenId;
	}

	public TaskInstanceRequestType getType() {
		return _type;
	}

	public boolean isFinished() {
		return _finished;
	}

	public boolean isGetAll() {
		return _getAll;
	}

	@Override
	public String toString() {
		return "TaskInstanceRequest[" +
			"type:" + _type +
			", processInstanceId:" + _processInstanceId +
			", tokenId:" + _tokenId +
			", taskInstanceId:" + _taskInstanceId +
			", userId:" + _userId +
			", roleId:" + _roleId +
			", roleIds" + _roleIds +
			", description:" + _description +
			", getAll:" + _getAll +
			", finished:" + _finished +
			", contextInfo:" + _contextInfo +
			"]";
	}

	private Map<String, Object> _contextInfo;
	private String _description;
	private boolean _finished;
	private boolean _getAll;
	private long _processInstanceId = -1;
	private long _roleId = -1;
	private Set<Long> _roleIds;
	private long _taskInstanceId = -1;
	private long _tokenId = -1;
	private TaskInstanceRequestType _type;
	private long _userId = -1;

}