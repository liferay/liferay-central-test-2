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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="BrowserTrackerSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.BrowserTrackerServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.BrowserTrackerServiceSoap
 * @generated
 */
public class BrowserTrackerSoap implements Serializable {
	public static BrowserTrackerSoap toSoapModel(BrowserTracker model) {
		BrowserTrackerSoap soapModel = new BrowserTrackerSoap();

		soapModel.setBrowserTrackerId(model.getBrowserTrackerId());
		soapModel.setUserId(model.getUserId());
		soapModel.setBrowserKey(model.getBrowserKey());

		return soapModel;
	}

	public static BrowserTrackerSoap[] toSoapModels(BrowserTracker[] models) {
		BrowserTrackerSoap[] soapModels = new BrowserTrackerSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static BrowserTrackerSoap[][] toSoapModels(BrowserTracker[][] models) {
		BrowserTrackerSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new BrowserTrackerSoap[models.length][models[0].length];
		}
		else {
			soapModels = new BrowserTrackerSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static BrowserTrackerSoap[] toSoapModels(List<BrowserTracker> models) {
		List<BrowserTrackerSoap> soapModels = new ArrayList<BrowserTrackerSoap>(models.size());

		for (BrowserTracker model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new BrowserTrackerSoap[soapModels.size()]);
	}

	public BrowserTrackerSoap() {
	}

	public long getPrimaryKey() {
		return _browserTrackerId;
	}

	public void setPrimaryKey(long pk) {
		setBrowserTrackerId(pk);
	}

	public long getBrowserTrackerId() {
		return _browserTrackerId;
	}

	public void setBrowserTrackerId(long browserTrackerId) {
		_browserTrackerId = browserTrackerId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getBrowserKey() {
		return _browserKey;
	}

	public void setBrowserKey(long browserKey) {
		_browserKey = browserKey;
	}

	private long _browserTrackerId;
	private long _userId;
	private long _browserKey;
}