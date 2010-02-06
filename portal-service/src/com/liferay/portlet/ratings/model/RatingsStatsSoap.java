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

package com.liferay.portlet.ratings.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="RatingsStatsSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.ratings.service.http.RatingsStatsServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.ratings.service.http.RatingsStatsServiceSoap
 * @generated
 */
public class RatingsStatsSoap implements Serializable {
	public static RatingsStatsSoap toSoapModel(RatingsStats model) {
		RatingsStatsSoap soapModel = new RatingsStatsSoap();

		soapModel.setStatsId(model.getStatsId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setTotalEntries(model.getTotalEntries());
		soapModel.setTotalScore(model.getTotalScore());
		soapModel.setAverageScore(model.getAverageScore());

		return soapModel;
	}

	public static RatingsStatsSoap[] toSoapModels(RatingsStats[] models) {
		RatingsStatsSoap[] soapModels = new RatingsStatsSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static RatingsStatsSoap[][] toSoapModels(RatingsStats[][] models) {
		RatingsStatsSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new RatingsStatsSoap[models.length][models[0].length];
		}
		else {
			soapModels = new RatingsStatsSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static RatingsStatsSoap[] toSoapModels(List<RatingsStats> models) {
		List<RatingsStatsSoap> soapModels = new ArrayList<RatingsStatsSoap>(models.size());

		for (RatingsStats model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new RatingsStatsSoap[soapModels.size()]);
	}

	public RatingsStatsSoap() {
	}

	public long getPrimaryKey() {
		return _statsId;
	}

	public void setPrimaryKey(long pk) {
		setStatsId(pk);
	}

	public long getStatsId() {
		return _statsId;
	}

	public void setStatsId(long statsId) {
		_statsId = statsId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public int getTotalEntries() {
		return _totalEntries;
	}

	public void setTotalEntries(int totalEntries) {
		_totalEntries = totalEntries;
	}

	public double getTotalScore() {
		return _totalScore;
	}

	public void setTotalScore(double totalScore) {
		_totalScore = totalScore;
	}

	public double getAverageScore() {
		return _averageScore;
	}

	public void setAverageScore(double averageScore) {
		_averageScore = averageScore;
	}

	private long _statsId;
	private long _classNameId;
	private long _classPK;
	private int _totalEntries;
	private double _totalScore;
	private double _averageScore;
}