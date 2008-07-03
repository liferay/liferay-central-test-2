/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import com.liferay.portlet.calendar.model.CalEvent;

import java.util.List;

/**
 * <a href="CalEventJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portlet.calendar.service.http.CalEventServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.calendar.service.http.CalEventServiceJSON
 *
 */
public class CalEventJSONSerializer {
	public static JSONObject toJSONObject(CalEvent model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("uuid", model.getUuid());
		jsonObj.put("eventId", model.getEventId());
		jsonObj.put("groupId", model.getGroupId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("userId", model.getUserId());
		jsonObj.put("userName", model.getUserName());
		jsonObj.put("createDate", model.getCreateDate().getTime());
		jsonObj.put("modifiedDate", model.getModifiedDate().getTime());
		jsonObj.put("title", model.getTitle());
		jsonObj.put("description", model.getDescription());
		jsonObj.put("startDate", model.getStartDate().getTime());
		jsonObj.put("endDate", model.getEndDate().getTime());
		jsonObj.put("durationHour", model.getDurationHour());
		jsonObj.put("durationMinute", model.getDurationMinute());
		jsonObj.put("allDay", model.getAllDay());
		jsonObj.put("timeZoneSensitive", model.getTimeZoneSensitive());
		jsonObj.put("type", model.getType());
		jsonObj.put("repeating", model.getRepeating());
		jsonObj.put("recurrence", model.getRecurrence());
		jsonObj.put("remindBy", model.getRemindBy());
		jsonObj.put("firstReminder", model.getFirstReminder());
		jsonObj.put("secondReminder", model.getSecondReminder());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.calendar.model.CalEvent> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (CalEvent model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}