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

/**
 * <a href="ProcessInstanceRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class ProcessInstanceRequest implements Serializable {

	public static ProcessInstanceRequest createGetRequest(
			long processInstanceId) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.GET);
		request._processInstanceId = processInstanceId;
		request._getAll = true;
		return request;
	}

	public static ProcessInstanceRequest createGetRequest(
			long processInstanceId, boolean retrieveTokenInfo) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.GET);
		request._processInstanceId = processInstanceId;
		request._retrieveTokenInfo = retrieveTokenInfo;
		request._getAll = true;
		return request;
	}

	public static ProcessInstanceRequest createGetRequest(
			String workflowDefinitionName) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.GET);
		request._workflowDefinitionName = workflowDefinitionName;
		request._getAll = true;
		return request;
	}

	public static ProcessInstanceRequest createGetRequest(
			String workflowDefinitionName, boolean retrieveTokenInfo) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.GET);
		request._workflowDefinitionName = workflowDefinitionName;
		request._retrieveTokenInfo = retrieveTokenInfo;
		request._getAll = true;
		return request;
	}

	public static ProcessInstanceRequest createGetRequest(
			String workflowDefinitionName, boolean retrieveTokenInfo,
		boolean finished) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.GET);
		request._workflowDefinitionName = workflowDefinitionName;
		request._retrieveTokenInfo = retrieveTokenInfo;
		request._getAll = false;
		request._finished = finished;
		return request;
	}

	public static ProcessInstanceRequest createGetHistoryRequest(
			long processInstanceId) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(
			ProcessInstanceRequestType.GET_HISTORY);
		request._processInstanceId = processInstanceId;
		return request;
	}

	public static ProcessInstanceRequest createRemoveRequest(
			long processInstanceId) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.REMOVE);
		request._processInstanceId = processInstanceId;
		return request;
	}

	public static ProcessInstanceRequest createSignalProcessInstanceByIdRequest(
			long processInstanceId, Map<String, Object> contextInfo) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.SIGNAL);
		request._processInstanceId = processInstanceId;
		request._contextInfo = contextInfo;
		return request;
	}

	public static ProcessInstanceRequest createSignalProcessInstanceByIdRequest(
			long processInstanceId, String activityName,
		Map<String, Object> contextInfo) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.SIGNAL);
		request._processInstanceId = processInstanceId;
		request._activityName = activityName;
		request._contextInfo = contextInfo;
		return request;
	}

	public static ProcessInstanceRequest
		createSignalProcessInstanceByTokenIdRequest(
			long tokenId, Map<String, Object> contextInfo) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.SIGNAL);
		request._tokenId = tokenId;
		request._contextInfo = contextInfo;
		return request;
	}

	public static ProcessInstanceRequest
		createSignalProcessInstanceByTokenIdRequest(
			long tokenId, String activityName,
		Map<String, Object> contextInfo) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.SIGNAL);
		request._tokenId = tokenId;
		request._activityName = activityName;
		request._contextInfo = contextInfo;
		return request;
	}

	public static ProcessInstanceRequest createStartRequest(
			String workflowDefinitionName, Map<String, Object> contextInfo,
			long userId) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.START);
		request._workflowDefinitionName = workflowDefinitionName;
		request._contextInfo = contextInfo;
		request._userId = userId;
		return request;
	}

	public static ProcessInstanceRequest createStartRequest(
			String workflowDefinitionName, Map<String, Object> contextInfo,
			long userId, String activityName) {
		ProcessInstanceRequest request =
			new ProcessInstanceRequest(ProcessInstanceRequestType.START);
		request._workflowDefinitionName = workflowDefinitionName;
		request._contextInfo = contextInfo;
		request._userId = userId;
		request._activityName = activityName;
		return request;
	}

	private ProcessInstanceRequest(ProcessInstanceRequestType type) {
		_type = type;
	}

	public String getActivityName() {
		return _activityName;
	}

	public Map<String, Object> getContextInfo() {
		return _contextInfo;
	}

	public long getProcessInstanceId() {
		return _processInstanceId;
	}

	public long getTokenId() {
		return _tokenId;
	}

	public ProcessInstanceRequestType getType() {
		return _type;
	}

	public long getUserId() {
		return _userId;
	}

	public String getWorkflowDefinitionName() {
		return _workflowDefinitionName;
	}

	public boolean isFinished() {
		return _finished;
	}

	public boolean isGetAll() {
		return _getAll;
	}

	public boolean isRetrieveTokenInfo() {
		return _retrieveTokenInfo;
	}

	@Override
	public String toString() {
		return "ProcessInstanceRequest[" +
			"type:" + _type +
			", definitionName:" + _workflowDefinitionName +
			", userId:" + _userId +
			", processInstanceId:" + _processInstanceId +
			", tokenId:" + _tokenId +
			", activityName:" + _activityName +
			", retrieveTokeInfo:" + _retrieveTokenInfo +
			", getAll:" + _getAll +
			", finished:" + _finished +
			", contextInfo:" + _contextInfo +
			"]";
	}

	private String _activityName;
	private Map<String, Object> _contextInfo;
	private boolean _finished;
	private boolean _getAll;
	private long _processInstanceId = -1;
	private boolean _retrieveTokenInfo;
	private long _tokenId = -1;
	private ProcessInstanceRequestType _type;
	private long _userId = -1;
	private String _workflowDefinitionName;

}