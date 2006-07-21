/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.polls.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.polls.service.persistence.PollsVotePK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="PollsVoteModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsVoteModel extends BaseModel {
	public static boolean CACHEABLE = GetterUtil.get(PropsUtil.get(
				"value.object.cacheable.com.liferay.portlet.polls.model.PollsVote"),
			VALUE_OBJECT_CACHEABLE);
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.polls.model.PollsVote"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_QUESTIONID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.polls.model.PollsVote.questionId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.polls.model.PollsVote.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CHOICEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.polls.model.PollsVote.choiceId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.polls.model.PollsVoteModel"));

	public PollsVoteModel() {
	}

	public PollsVotePK getPrimaryKey() {
		return new PollsVotePK(_questionId, _userId);
	}

	public void setPrimaryKey(PollsVotePK pk) {
		setQuestionId(pk.questionId);
		setUserId(pk.userId);
	}

	public String getQuestionId() {
		return GetterUtil.getString(_questionId);
	}

	public void setQuestionId(String questionId) {
		if (((questionId == null) && (_questionId != null)) ||
				((questionId != null) && (_questionId == null)) ||
				((questionId != null) && (_questionId != null) &&
				!questionId.equals(_questionId))) {
			if (!XSS_ALLOW_QUESTIONID) {
				questionId = XSSUtil.strip(questionId);
			}

			_questionId = questionId;
		}
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
		}
	}

	public String getChoiceId() {
		return GetterUtil.getString(_choiceId);
	}

	public void setChoiceId(String choiceId) {
		if (((choiceId == null) && (_choiceId != null)) ||
				((choiceId != null) && (_choiceId == null)) ||
				((choiceId != null) && (_choiceId != null) &&
				!choiceId.equals(_choiceId))) {
			if (!XSS_ALLOW_CHOICEID) {
				choiceId = XSSUtil.strip(choiceId);
			}

			_choiceId = choiceId;
		}
	}

	public Date getVoteDate() {
		return _voteDate;
	}

	public void setVoteDate(Date voteDate) {
		if (((voteDate == null) && (_voteDate != null)) ||
				((voteDate != null) && (_voteDate == null)) ||
				((voteDate != null) && (_voteDate != null) &&
				!voteDate.equals(_voteDate))) {
			_voteDate = voteDate;
		}
	}

	public Object clone() {
		PollsVote clone = new PollsVote();
		clone.setQuestionId(getQuestionId());
		clone.setUserId(getUserId());
		clone.setChoiceId(getChoiceId());
		clone.setVoteDate(getVoteDate());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		PollsVote pollsVote = (PollsVote)obj;
		PollsVotePK pk = pollsVote.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		PollsVote pollsVote = null;

		try {
			pollsVote = (PollsVote)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		PollsVotePK pk = pollsVote.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _questionId;
	private String _userId;
	private String _choiceId;
	private Date _voteDate;
}