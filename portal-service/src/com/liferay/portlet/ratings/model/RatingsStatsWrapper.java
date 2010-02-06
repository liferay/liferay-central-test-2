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


/**
 * <a href="RatingsStatsSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link RatingsStats}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsStats
 * @generated
 */
public class RatingsStatsWrapper implements RatingsStats {
	public RatingsStatsWrapper(RatingsStats ratingsStats) {
		_ratingsStats = ratingsStats;
	}

	public long getPrimaryKey() {
		return _ratingsStats.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_ratingsStats.setPrimaryKey(pk);
	}

	public long getStatsId() {
		return _ratingsStats.getStatsId();
	}

	public void setStatsId(long statsId) {
		_ratingsStats.setStatsId(statsId);
	}

	public java.lang.String getClassName() {
		return _ratingsStats.getClassName();
	}

	public long getClassNameId() {
		return _ratingsStats.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_ratingsStats.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _ratingsStats.getClassPK();
	}

	public void setClassPK(long classPK) {
		_ratingsStats.setClassPK(classPK);
	}

	public int getTotalEntries() {
		return _ratingsStats.getTotalEntries();
	}

	public void setTotalEntries(int totalEntries) {
		_ratingsStats.setTotalEntries(totalEntries);
	}

	public double getTotalScore() {
		return _ratingsStats.getTotalScore();
	}

	public void setTotalScore(double totalScore) {
		_ratingsStats.setTotalScore(totalScore);
	}

	public double getAverageScore() {
		return _ratingsStats.getAverageScore();
	}

	public void setAverageScore(double averageScore) {
		_ratingsStats.setAverageScore(averageScore);
	}

	public com.liferay.portlet.ratings.model.RatingsStats toEscapedModel() {
		return _ratingsStats.toEscapedModel();
	}

	public boolean isNew() {
		return _ratingsStats.isNew();
	}

	public boolean setNew(boolean n) {
		return _ratingsStats.setNew(n);
	}

	public boolean isCachedModel() {
		return _ratingsStats.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ratingsStats.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ratingsStats.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ratingsStats.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ratingsStats.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ratingsStats.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ratingsStats.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _ratingsStats.clone();
	}

	public int compareTo(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats) {
		return _ratingsStats.compareTo(ratingsStats);
	}

	public int hashCode() {
		return _ratingsStats.hashCode();
	}

	public java.lang.String toString() {
		return _ratingsStats.toString();
	}

	public java.lang.String toXmlString() {
		return _ratingsStats.toXmlString();
	}

	public RatingsStats getWrappedRatingsStats() {
		return _ratingsStats;
	}

	private RatingsStats _ratingsStats;
}