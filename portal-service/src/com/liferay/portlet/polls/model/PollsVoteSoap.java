/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.polls.service.http.PollsVoteServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.polls.service.http.PollsVoteServiceSoap
 * @generated
 */
public class PollsVoteSoap implements Serializable {
	public static PollsVoteSoap toSoapModel(PollsVote model) {
		PollsVoteSoap soapModel = new PollsVoteSoap();

		soapModel.setVoteId(model.getVoteId());
		soapModel.setUserId(model.getUserId());
		soapModel.setQuestionId(model.getQuestionId());
		soapModel.setChoiceId(model.getChoiceId());
		soapModel.setVoteDate(model.getVoteDate());

		return soapModel;
	}

	public static PollsVoteSoap[] toSoapModels(PollsVote[] models) {
		PollsVoteSoap[] soapModels = new PollsVoteSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PollsVoteSoap[][] toSoapModels(PollsVote[][] models) {
		PollsVoteSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PollsVoteSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PollsVoteSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PollsVoteSoap[] toSoapModels(List<PollsVote> models) {
		List<PollsVoteSoap> soapModels = new ArrayList<PollsVoteSoap>(models.size());

		for (PollsVote model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PollsVoteSoap[soapModels.size()]);
	}

	public PollsVoteSoap() {
	}

	public long getPrimaryKey() {
		return _voteId;
	}

	public void setPrimaryKey(long pk) {
		setVoteId(pk);
	}

	public long getVoteId() {
		return _voteId;
	}

	public void setVoteId(long voteId) {
		_voteId = voteId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getQuestionId() {
		return _questionId;
	}

	public void setQuestionId(long questionId) {
		_questionId = questionId;
	}

	public long getChoiceId() {
		return _choiceId;
	}

	public void setChoiceId(long choiceId) {
		_choiceId = choiceId;
	}

	public Date getVoteDate() {
		return _voteDate;
	}

	public void setVoteDate(Date voteDate) {
		_voteDate = voteDate;
	}

	private long _voteId;
	private long _userId;
	private long _questionId;
	private long _choiceId;
	private Date _voteDate;
}