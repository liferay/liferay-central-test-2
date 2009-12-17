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

package com.liferay.portlet.calendar.model.impl;

import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.calendar.model.CalEvent;

/**
 * <a href="CalEventImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CalEventImpl extends CalEventModelImpl implements CalEvent {

	public CalEventImpl() {
	}

	public TZSRecurrence getRecurrenceObj() {
		if (_recurrenceObj == null) {
			String recurrence = getRecurrence();

			if (Validator.isNotNull(recurrence)) {
				_recurrenceObj = (TZSRecurrence)JSONFactoryUtil.deserialize(
					recurrence);
			}
		}

		return _recurrenceObj;
	}

	public void setRecurrence(String recurrence) {
		_recurrenceObj = null;

		super.setRecurrence(recurrence);
	}

	public void setRecurrenceObj(TZSRecurrence recurrenceObj) {
		_recurrenceObj = recurrenceObj;

		super.setRecurrence(JSONFactoryUtil.serialize(recurrenceObj));
	}

	private TZSRecurrence _recurrenceObj = null;

}