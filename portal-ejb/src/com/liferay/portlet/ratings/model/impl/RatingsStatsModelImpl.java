/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

/**
 * <a href="RatingsStatsModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RatingsStatsModelImpl extends BaseModelImpl {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.ratings.model.RatingsStats"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_CLASSNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.ratings.model.RatingsStats.className"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CLASSPK = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.ratings.model.RatingsStats.classPK"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.ratings.model.RatingsStatsModel"));

	public RatingsStatsModelImpl() {
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
		if (statsId != _statsId) {
			_statsId = statsId;
		}
	}

	public String getClassName() {
		return GetterUtil.getString(_className);
	}

	public void setClassName(String className) {
		if (((className == null) && (_className != null)) ||
				((className != null) && (_className == null)) ||
				((className != null) && (_className != null) &&
				!className.equals(_className))) {
			if (!XSS_ALLOW_CLASSNAME) {
				className = XSSUtil.strip(className);
			}

			_className = className;
		}
	}

	public String getClassPK() {
		return GetterUtil.getString(_classPK);
	}

	public void setClassPK(String classPK) {
		if (((classPK == null) && (_classPK != null)) ||
				((classPK != null) && (_classPK == null)) ||
				((classPK != null) && (_classPK != null) &&
				!classPK.equals(_classPK))) {
			if (!XSS_ALLOW_CLASSPK) {
				classPK = XSSUtil.strip(classPK);
			}

			_classPK = classPK;
		}
	}

	public int getTotalEntries() {
		return _totalEntries;
	}

	public void setTotalEntries(int totalEntries) {
		if (totalEntries != _totalEntries) {
			_totalEntries = totalEntries;
		}
	}

	public double getTotalScore() {
		return _totalScore;
	}

	public void setTotalScore(double totalScore) {
		if (totalScore != _totalScore) {
			_totalScore = totalScore;
		}
	}

	public double getAverageScore() {
		return _averageScore;
	}

	public void setAverageScore(double averageScore) {
		if (averageScore != _averageScore) {
			_averageScore = averageScore;
		}
	}

	public Object clone() {
		RatingsStatsImpl clone = new RatingsStatsImpl();
		clone.setStatsId(getStatsId());
		clone.setClassName(getClassName());
		clone.setClassPK(getClassPK());
		clone.setTotalEntries(getTotalEntries());
		clone.setTotalScore(getTotalScore());
		clone.setAverageScore(getAverageScore());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		RatingsStatsImpl ratingsStats = (RatingsStatsImpl)obj;
		long pk = ratingsStats.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		RatingsStatsImpl ratingsStats = null;

		try {
			ratingsStats = (RatingsStatsImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = ratingsStats.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	private long _statsId;
	private String _className;
	private String _classPK;
	private int _totalEntries;
	private double _totalScore;
	private double _averageScore;
}