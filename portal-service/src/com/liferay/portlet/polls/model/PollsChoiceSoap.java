/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="PollsChoiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.polls.service.http.PollsChoiceServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.polls.service.http.PollsChoiceServiceSoap
 * @generated
 */
public class PollsChoiceSoap implements Serializable {
	public static PollsChoiceSoap toSoapModel(PollsChoice model) {
		PollsChoiceSoap soapModel = new PollsChoiceSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setChoiceId(model.getChoiceId());
		soapModel.setQuestionId(model.getQuestionId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());

		return soapModel;
	}

	public static PollsChoiceSoap[] toSoapModels(PollsChoice[] models) {
		PollsChoiceSoap[] soapModels = new PollsChoiceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PollsChoiceSoap[][] toSoapModels(PollsChoice[][] models) {
		PollsChoiceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PollsChoiceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PollsChoiceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PollsChoiceSoap[] toSoapModels(List<PollsChoice> models) {
		List<PollsChoiceSoap> soapModels = new ArrayList<PollsChoiceSoap>(models.size());

		for (PollsChoice model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PollsChoiceSoap[soapModels.size()]);
	}

	public PollsChoiceSoap() {
	}

	public long getPrimaryKey() {
		return _choiceId;
	}

	public void setPrimaryKey(long pk) {
		setChoiceId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getChoiceId() {
		return _choiceId;
	}

	public void setChoiceId(long choiceId) {
		_choiceId = choiceId;
	}

	public long getQuestionId() {
		return _questionId;
	}

	public void setQuestionId(long questionId) {
		_questionId = questionId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	private String _uuid;
	private long _choiceId;
	private long _questionId;
	private String _name;
	private String _description;
}