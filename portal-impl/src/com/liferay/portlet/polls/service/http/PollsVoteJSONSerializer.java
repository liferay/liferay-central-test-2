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

package com.liferay.portlet.polls.service.http;

import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.polls.model.PollsVote;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="PollsVoteJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.polls.service.http.PollsVoteServiceJSON</code>
 * to translate objects.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.polls.service.http.PollsVoteServiceJSON
 *
 */
public class PollsVoteJSONSerializer {
	public static JSONObject toJSONObject(PollsVote model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("questionId", model.getQuestionId());
		jsonObj.put("userId", model.getUserId());

		String choiceId = model.getChoiceId();

		if (choiceId == null) {
			jsonObj.put("choiceId", StringPool.BLANK);
		}
		else {
			jsonObj.put("choiceId", choiceId.toString());
		}

		Date voteDate = model.getVoteDate();

		if (voteDate == null) {
			jsonObj.put("voteDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("voteDate", voteDate.toString());
		}

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			PollsVote model = (PollsVote)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}