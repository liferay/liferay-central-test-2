/*
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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.util.StackTraceUtil;

import java.io.Serializable;

/**
 * <a href="ServiceResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ServiceRequestStatus implements Serializable {

	public Object getOriginalServiceRequest() {
		return _originalServiceRequest;
	}

	public long getDuration() {
		return _endTime - _startTime;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getErrorStack() {
		return _errorStack;
	}

	public String getRequestType() {
		return _requestType;
	}

	public boolean isError() {
		return (_errorStack != null);
	}

	public void startTimer() {
		_startTime = System.currentTimeMillis();
	}

	public void setError(Throwable t) {
		_errorMessage = t.getMessage();
		_errorStack = StackTraceUtil.getStackTrace(t);
	}

	public void setOriginalServiceRequest(Object originalServiceRequest) {
		_originalServiceRequest = originalServiceRequest;
		_requestType = _originalServiceRequest.getClass().getName();
	}

	public void stopTimer() {
		_endTime = System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return "ServiceRequestStatus{" +
			   ", _requestType='" + _requestType + '\'' +
			   ", _originalServiceRequest=" + _originalServiceRequest +
			   ", _startTime=" + _startTime +
			   "_endTime=" + _endTime +
			   "duration=" + getDuration() +
			   ", _errorMessage='" + _errorMessage + '\'' +
			   ", _errorStack='" + _errorStack + '\'' +
			   '}';
	}

	private long _endTime;
	private String _errorMessage;
	private String _errorStack;
	private Object _originalServiceRequest;
	private String _requestType;
	private long _startTime;
}
