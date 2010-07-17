/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.ratings.model;

/**
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

	public void setNew(boolean n) {
		_ratingsStats.setNew(n);
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