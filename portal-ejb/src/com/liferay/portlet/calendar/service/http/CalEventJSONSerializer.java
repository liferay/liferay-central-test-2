/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.service.http;

import com.liferay.portlet.calendar.model.CalEvent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="CalEventJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CalEventJSONSerializer {
	public static JSONObject toJSONObject(CalEvent model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("eventId", model.getEventId().toString());
		jsonObj.put("groupId", model.getGroupId());
		jsonObj.put("companyId", model.getCompanyId().toString());
		jsonObj.put("userId", model.getUserId().toString());
		jsonObj.put("userName", model.getUserName().toString());
		jsonObj.put("createDate", model.getCreateDate().toString());
		jsonObj.put("modifiedDate", model.getModifiedDate().toString());
		jsonObj.put("title", model.getTitle().toString());
		jsonObj.put("description", model.getDescription().toString());
		jsonObj.put("startDate", model.getStartDate().toString());
		jsonObj.put("endDate", model.getEndDate().toString());
		jsonObj.put("durationHour", model.getDurationHour());
		jsonObj.put("durationMinute", model.getDurationMinute());
		jsonObj.put("allDay", model.getAllDay());
		jsonObj.put("timeZoneSensitive", model.getTimeZoneSensitive());
		jsonObj.put("type", model.getType().toString());
		jsonObj.put("repeating", model.getRepeating());
		jsonObj.put("recurrence", model.getRecurrence().toString());
		jsonObj.put("remindBy", model.getRemindBy().toString());
		jsonObj.put("firstReminder", model.getFirstReminder());
		jsonObj.put("secondReminder", model.getSecondReminder());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			CalEvent model = (CalEvent)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}