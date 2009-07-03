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

/**
 * <a href="ProcessInstanceInfo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class ProcessInstanceInfo implements Serializable {

	public ProcessInstanceInfo(
			long processInstanceId, int processInstanceVersion) {
		this(processInstanceId, processInstanceVersion, null, null, null, null);
	}

	public ProcessInstanceInfo(
			long processInstanceId, int processInstanceVersion, Date startDate,
			Date endDate) {
		this(
			processInstanceId, processInstanceVersion, startDate, endDate,
			null, null);
	}

	public ProcessInstanceInfo(
			long processInstanceId, int processInstanceVersion, Date startDate,
			Date endDate, TokenInfo tokenInfo,
			Map<String, Object> contextInfo) {
		_processInstanceId = processInstanceId;
		_processInstanceVersion = processInstanceVersion;
		_startDate = startDate;
		_endDate = endDate;
		_tokenInfo = tokenInfo;
		_contextInfo = contextInfo;
	}

	public Map<String, Object> getContextInfo() {
		return _contextInfo;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public long getProcessInstanceId() {
		return _processInstanceId;
	}

	public int getProcessInstanceVersion() {
		return _processInstanceVersion;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public TokenInfo getTokenInfo() {
		return _tokenInfo;
	}

	public void setContextInfo(Map<String, Object> contextInfo) {
		_contextInfo = contextInfo;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public void setTokenInfo(TokenInfo rootTokenInfo) {
		_tokenInfo = rootTokenInfo;
	}

	@Override
	public String toString() {
		return "ProcessInstanceInfo[" +
			"processInstanceId:" + _processInstanceId +
			", processInstanceVersion:" + _processInstanceVersion +
			", startDate:" + _startDate +
			", endDate:" + _endDate +
			", tokenInfo:" + _tokenInfo +
			", contextInfo:" + _contextInfo + "]";
	}

	private Date _endDate;
	private Date _startDate;
	private int _processInstanceVersion;
	private long _processInstanceId;
	private Map<String, Object> _contextInfo;
	private TokenInfo _tokenInfo;

}