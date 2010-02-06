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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

/**
 * <a href="MessageStatus.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MessageStatus implements Serializable {

	public long getDuration() {
		return _endTime - _startTime;
	}

	public String getExceptionMessage() {
		return _exceptionMessage;
	}

	public String getExceptionStackTrace() {
		return _exceptionStackTrace;
	}

	public Object getPayload() {
		return _payload;
	}

	public boolean hasException() {
		if (_exceptionStackTrace != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setException(Exception e) {
		_exceptionMessage = e.getMessage();
		_exceptionStackTrace = StackTraceUtil.getStackTrace(e);
	}

	public void setPayload(Object payload) {
		_payload = payload;
	}

	public void startTimer() {
		_startTime = System.currentTimeMillis();
	}

	public void stopTimer() {
		_endTime = System.currentTimeMillis();
	}

	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{startTime=");
		sb.append(_startTime);
		sb.append(", endTime=");
		sb.append(_endTime);
		sb.append(", payload=");
		sb.append(_payload);
		sb.append(", errorMessage=");
		sb.append(_exceptionMessage);
		sb.append(", errorStackTrace=");
		sb.append(_exceptionStackTrace);
		sb.append("}");

		return sb.toString();
	}

	private long _endTime;
	private String _exceptionMessage;
	private String _exceptionStackTrace;
	private Object _payload;
	private long _startTime;

}