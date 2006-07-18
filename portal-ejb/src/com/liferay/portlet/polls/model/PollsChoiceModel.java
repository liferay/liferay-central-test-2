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

import com.liferay.portlet.polls.service.persistence.PollsChoicePK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

/**
 * <a href="PollsChoiceModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PollsChoiceModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.polls.model.PollsChoice"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_QUESTIONID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.polls.model.PollsChoice.questionId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CHOICEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.polls.model.PollsChoice.choiceId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.polls.model.PollsChoice.description"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.polls.model.PollsChoiceModel"));

	public PollsChoiceModel() {
	}

	public PollsChoicePK getPrimaryKey() {
		return new PollsChoicePK(_questionId, _choiceId);
	}

	public void setPrimaryKey(PollsChoicePK pk) {
		setQuestionId(pk.questionId);
		setChoiceId(pk.choiceId);
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

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		if (((description == null) && (_description != null)) ||
				((description != null) && (_description == null)) ||
				((description != null) && (_description != null) &&
				!description.equals(_description))) {
			if (!XSS_ALLOW_DESCRIPTION) {
				description = XSSUtil.strip(description);
			}

			_description = description;
		}
	}

	public Object clone() {
		PollsChoice clone = new PollsChoice();
		clone.setQuestionId(getQuestionId());
		clone.setChoiceId(getChoiceId());
		clone.setDescription(getDescription());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		PollsChoice pollsChoice = (PollsChoice)obj;
		int value = 0;
		value = getChoiceId().compareTo(pollsChoice.getChoiceId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		PollsChoice pollsChoice = null;

		try {
			pollsChoice = (PollsChoice)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		PollsChoicePK pk = pollsChoice.getPrimaryKey();

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
	private String _choiceId;
	private String _description;
}