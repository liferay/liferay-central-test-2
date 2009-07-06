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

import java.util.Date;
import java.util.Map;

/**
 * <a href="ProcessInstanceHistoryEntry.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 *
 */
public class ProcessInstanceHistoryEntry {

	public ProcessInstanceHistoryEntry(
			Date date, long userId, int sequence, String description) {
		_date = date;
		_userId = userId;
		_sequence = sequence;
		_description = description;
	}

	public Map<String, Object> getAdditionalData() {
		return _additionalData;
	}

	public Date getDate() {
		return _date;
	}

	public String getDescription() {
		return _description;
	}

	public int getSequence() {
		return _sequence;
	}

	public long getUserId() {
		return _userId;
	}

	public void setAdditionalData(Map<String, Object> additionalData) {
		_additionalData = additionalData;
	}

	@Override
	public String toString() {
		return "ProcessInstanceHistoryEntry[" +
			"date:" + _date +
			", index:" + _sequence +
			", userId:" + _userId +
			", description:" + _description +
			", additionalData:" + _additionalData +
			"]";
	}

	private Date _date;
	private int _sequence;
	private long _userId;
	private Map<String, Object> _additionalData;
	private String _description;

}