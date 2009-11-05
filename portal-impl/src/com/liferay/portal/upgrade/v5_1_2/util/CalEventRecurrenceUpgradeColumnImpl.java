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

package com.liferay.portal.upgrade.v5_1_2.util;

import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.TimeZone;

/**
 * <a href="CalEventRecurrenceUpgradeColumnImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author	   Samuel Kong
 * @deprecated
 */
public class CalEventRecurrenceUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public CalEventRecurrenceUpgradeColumnImpl(String name) {
		super(name);
	}

	public Object getNewValue(Object oldValue) throws Exception {
		if (Validator.isNull(oldValue)) {
			return StringPool.BLANK;
		}

		String recurrence = (String)oldValue;

		Object obj = Base64.stringToObject(recurrence);

		if (obj instanceof Recurrence) {
			Recurrence recurrenceObj = (Recurrence)obj;

			return serialize(recurrenceObj);
		}
		else if (obj instanceof com.liferay.util.cal.Recurrence) {
			com.liferay.util.cal.Recurrence oldRecurrence =
				(com.liferay.util.cal.Recurrence)obj;

			com.liferay.util.cal.Duration oldDuration =
				oldRecurrence.getDuration();

			Duration duration = new Duration(
				oldDuration.getDays(), oldDuration.getHours(),
				oldDuration.getMinutes(), oldDuration.getSeconds());

			duration.setWeeks(oldDuration.getWeeks());
			duration.setInterval(oldDuration.getInterval());

			Recurrence recurrenceObj = new Recurrence(
				oldRecurrence.getDtStart(), duration,
				oldRecurrence.getFrequency());

			com.liferay.util.cal.DayAndPosition[] oldDayPos =
				oldRecurrence.getByDay();

			DayAndPosition[] dayPos = null;

			if (oldDayPos != null) {
				dayPos = new DayAndPosition[oldDayPos.length];

				for (int i = 0; i < oldDayPos.length; i++) {
					dayPos[i] = new DayAndPosition(
						oldDayPos[i].getDayOfWeek(),
						oldDayPos[i].getDayPosition());
				}
			}

			recurrenceObj.setByDay(dayPos);
			recurrenceObj.setByMonth(oldRecurrence.getByMonth());
			recurrenceObj.setByMonthDay(oldRecurrence.getByMonthDay());
			recurrenceObj.setInterval(oldRecurrence.getInterval());
			recurrenceObj.setOccurrence(oldRecurrence.getOccurrence());
			recurrenceObj.setWeekStart(oldRecurrence.getWeekStart());
			recurrenceObj.setUntil(oldRecurrence.getUntil());

			return serialize(recurrenceObj);
		}
		else {
			return StringPool.BLANK;
		}
	}

	protected String serialize(Recurrence recurrence) throws JSONException {
		JSONObject recurrenceJSON = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.serialize(recurrence));

		recurrenceJSON.put("javaClass", TZSRecurrence.class.getName());

		TimeZone timeZone = TimeZone.getTimeZone(StringPool.UTC);

		JSONObject timeZoneJSON = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.serialize(timeZone));

		recurrenceJSON.put("timeZone", timeZoneJSON);

		return recurrenceJSON.toString();
	}

}