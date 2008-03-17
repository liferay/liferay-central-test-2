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

package com.liferay.portlet.announcements.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="AnnouncementFlagSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portlet.announcements.service.http.AnnouncementFlagServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.http.AnnouncementFlagServiceSoap
 *
 */
public class AnnouncementFlagSoap implements Serializable {
	public static AnnouncementFlagSoap toSoapModel(AnnouncementFlag model) {
		AnnouncementFlagSoap soapModel = new AnnouncementFlagSoap();

		soapModel.setAnnouncementFlagId(model.getAnnouncementFlagId());
		soapModel.setUserId(model.getUserId());
		soapModel.setAnnouncementId(model.getAnnouncementId());
		soapModel.setFlag(model.getFlag());
		soapModel.setFlagDate(model.getFlagDate());

		return soapModel;
	}

	public static AnnouncementFlagSoap[] toSoapModels(
		List<AnnouncementFlag> models) {
		List<AnnouncementFlagSoap> soapModels = new ArrayList<AnnouncementFlagSoap>(models.size());

		for (int i = 0; i < models.size(); i++) {
			AnnouncementFlag model = models.get(i);

			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AnnouncementFlagSoap[soapModels.size()]);
	}

	public AnnouncementFlagSoap() {
	}

	public long getPrimaryKey() {
		return _announcementFlagId;
	}

	public void setPrimaryKey(long pk) {
		setAnnouncementFlagId(pk);
	}

	public long getAnnouncementFlagId() {
		return _announcementFlagId;
	}

	public void setAnnouncementFlagId(long announcementFlagId) {
		_announcementFlagId = announcementFlagId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getAnnouncementId() {
		return _announcementId;
	}

	public void setAnnouncementId(long announcementId) {
		_announcementId = announcementId;
	}

	public int getFlag() {
		return _flag;
	}

	public void setFlag(int flag) {
		_flag = flag;
	}

	public Date getFlagDate() {
		return _flagDate;
	}

	public void setFlagDate(Date flagDate) {
		_flagDate = flagDate;
	}

	private long _announcementFlagId;
	private long _userId;
	private long _announcementId;
	private int _flag;
	private Date _flagDate;
}