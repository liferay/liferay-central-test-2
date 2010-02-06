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

package com.liferay.portlet.announcements.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="AnnouncementsFlagSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.announcements.service.http.AnnouncementsFlagServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.announcements.service.http.AnnouncementsFlagServiceSoap
 * @generated
 */
public class AnnouncementsFlagSoap implements Serializable {
	public static AnnouncementsFlagSoap toSoapModel(AnnouncementsFlag model) {
		AnnouncementsFlagSoap soapModel = new AnnouncementsFlagSoap();

		soapModel.setFlagId(model.getFlagId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setEntryId(model.getEntryId());
		soapModel.setValue(model.getValue());

		return soapModel;
	}

	public static AnnouncementsFlagSoap[] toSoapModels(
		AnnouncementsFlag[] models) {
		AnnouncementsFlagSoap[] soapModels = new AnnouncementsFlagSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AnnouncementsFlagSoap[][] toSoapModels(
		AnnouncementsFlag[][] models) {
		AnnouncementsFlagSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AnnouncementsFlagSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AnnouncementsFlagSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AnnouncementsFlagSoap[] toSoapModels(
		List<AnnouncementsFlag> models) {
		List<AnnouncementsFlagSoap> soapModels = new ArrayList<AnnouncementsFlagSoap>(models.size());

		for (AnnouncementsFlag model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AnnouncementsFlagSoap[soapModels.size()]);
	}

	public AnnouncementsFlagSoap() {
	}

	public long getPrimaryKey() {
		return _flagId;
	}

	public void setPrimaryKey(long pk) {
		setFlagId(pk);
	}

	public long getFlagId() {
		return _flagId;
	}

	public void setFlagId(long flagId) {
		_flagId = flagId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public int getValue() {
		return _value;
	}

	public void setValue(int value) {
		_value = value;
	}

	private long _flagId;
	private long _userId;
	private Date _createDate;
	private long _entryId;
	private int _value;
}