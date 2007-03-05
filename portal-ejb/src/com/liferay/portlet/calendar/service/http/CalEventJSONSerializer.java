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

import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.calendar.model.CalEvent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
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
		jsonObj.put("eventId", model.getEventId());
		jsonObj.put("groupId", model.getGroupId());

		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

		String userId = model.getUserId();

		if (userId == null) {
			jsonObj.put("userId", StringPool.BLANK);
		}
		else {
			jsonObj.put("userId", userId.toString());
		}

		String userName = model.getUserName();

		if (userName == null) {
			jsonObj.put("userName", StringPool.BLANK);
		}
		else {
			jsonObj.put("userName", userName.toString());
		}

		Date createDate = model.getCreateDate();

		if (createDate == null) {
			jsonObj.put("createDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("createDate", createDate.toString());
		}

		Date modifiedDate = model.getModifiedDate();

		if (modifiedDate == null) {
			jsonObj.put("modifiedDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("modifiedDate", modifiedDate.toString());
		}

		String title = model.getTitle();

		if (title == null) {
			jsonObj.put("title", StringPool.BLANK);
		}
		else {
			jsonObj.put("title", title.toString());
		}

		String description = model.getDescription();

		if (description == null) {
			jsonObj.put("description", StringPool.BLANK);
		}
		else {
			jsonObj.put("description", description.toString());
		}

		Date startDate = model.getStartDate();

		if (startDate == null) {
			jsonObj.put("startDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("startDate", startDate.toString());
		}

		Date endDate = model.getEndDate();

		if (endDate == null) {
			jsonObj.put("endDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("endDate", endDate.toString());
		}

		jsonObj.put("durationHour", model.getDurationHour());
		jsonObj.put("durationMinute", model.getDurationMinute());
		jsonObj.put("allDay", model.getAllDay());
		jsonObj.put("timeZoneSensitive", model.getTimeZoneSensitive());

		String type = model.getType();

		if (type == null) {
			jsonObj.put("type", StringPool.BLANK);
		}
		else {
			jsonObj.put("type", type.toString());
		}

		jsonObj.put("repeating", model.getRepeating());

		String recurrence = model.getRecurrence();

		if (recurrence == null) {
			jsonObj.put("recurrence", StringPool.BLANK);
		}
		else {
			jsonObj.put("recurrence", recurrence.toString());
		}

		String remindBy = model.getRemindBy();

		if (remindBy == null) {
			jsonObj.put("remindBy", StringPool.BLANK);
		}
		else {
			jsonObj.put("remindBy", remindBy.toString());
		}

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