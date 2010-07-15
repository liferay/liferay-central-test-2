/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.polls.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.polls.model.PollsVote;

import java.util.Date;
import java.util.List;

/**
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class PollsVoteJSONSerializer {
	public static JSONObject toJSONObject(PollsVote model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("voteId", model.getVoteId());
		jsonObj.put("userId", model.getUserId());
		jsonObj.put("questionId", model.getQuestionId());
		jsonObj.put("choiceId", model.getChoiceId());

		Date voteDate = model.getVoteDate();

		String voteDateJSON = StringPool.BLANK;

		if (voteDate != null) {
			voteDateJSON = String.valueOf(voteDate.getTime());
		}

		jsonObj.put("voteDate", voteDateJSON);

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.polls.model.PollsVote[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PollsVote model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.polls.model.PollsVote[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PollsVote[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.polls.model.PollsVote> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PollsVote model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}