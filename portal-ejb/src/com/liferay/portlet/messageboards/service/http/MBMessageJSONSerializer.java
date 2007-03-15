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

package com.liferay.portlet.messageboards.service.http;

import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.messageboards.model.MBMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="MBMessageJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.messageboards.service.http.MBMessageServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.http.MBMessageServiceJSON
 *
 */
public class MBMessageJSONSerializer {
	public static JSONObject toJSONObject(MBMessage model) {
		JSONObject jsonObj = new JSONObject();
		String topicId = model.getTopicId();

		if (topicId == null) {
			jsonObj.put("topicId", StringPool.BLANK);
		}
		else {
			jsonObj.put("topicId", topicId.toString());
		}

		String messageId = model.getMessageId();

		if (messageId == null) {
			jsonObj.put("messageId", StringPool.BLANK);
		}
		else {
			jsonObj.put("messageId", messageId.toString());
		}

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

		String categoryId = model.getCategoryId();

		if (categoryId == null) {
			jsonObj.put("categoryId", StringPool.BLANK);
		}
		else {
			jsonObj.put("categoryId", categoryId.toString());
		}

		String threadId = model.getThreadId();

		if (threadId == null) {
			jsonObj.put("threadId", StringPool.BLANK);
		}
		else {
			jsonObj.put("threadId", threadId.toString());
		}

		String parentMessageId = model.getParentMessageId();

		if (parentMessageId == null) {
			jsonObj.put("parentMessageId", StringPool.BLANK);
		}
		else {
			jsonObj.put("parentMessageId", parentMessageId.toString());
		}

		String subject = model.getSubject();

		if (subject == null) {
			jsonObj.put("subject", StringPool.BLANK);
		}
		else {
			jsonObj.put("subject", subject.toString());
		}

		String body = model.getBody();

		if (body == null) {
			jsonObj.put("body", StringPool.BLANK);
		}
		else {
			jsonObj.put("body", body.toString());
		}

		jsonObj.put("attachments", model.getAttachments());
		jsonObj.put("anonymous", model.getAnonymous());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			MBMessage model = (MBMessage)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}