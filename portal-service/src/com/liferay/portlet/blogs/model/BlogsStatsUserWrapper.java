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

package com.liferay.portlet.blogs.model;


/**
 * <a href="BlogsStatsUserSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link BlogsStatsUser}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsStatsUser
 * @generated
 */
public class BlogsStatsUserWrapper implements BlogsStatsUser {
	public BlogsStatsUserWrapper(BlogsStatsUser blogsStatsUser) {
		_blogsStatsUser = blogsStatsUser;
	}

	public long getPrimaryKey() {
		return _blogsStatsUser.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_blogsStatsUser.setPrimaryKey(pk);
	}

	public long getStatsUserId() {
		return _blogsStatsUser.getStatsUserId();
	}

	public void setStatsUserId(long statsUserId) {
		_blogsStatsUser.setStatsUserId(statsUserId);
	}

	public java.lang.String getStatsUserUuid()
		throws com.liferay.portal.SystemException {
		return _blogsStatsUser.getStatsUserUuid();
	}

	public void setStatsUserUuid(java.lang.String statsUserUuid) {
		_blogsStatsUser.setStatsUserUuid(statsUserUuid);
	}

	public long getGroupId() {
		return _blogsStatsUser.getGroupId();
	}

	public void setGroupId(long groupId) {
		_blogsStatsUser.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _blogsStatsUser.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_blogsStatsUser.setCompanyId(companyId);
	}

	public long getUserId() {
		return _blogsStatsUser.getUserId();
	}

	public void setUserId(long userId) {
		_blogsStatsUser.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _blogsStatsUser.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_blogsStatsUser.setUserUuid(userUuid);
	}

	public int getEntryCount() {
		return _blogsStatsUser.getEntryCount();
	}

	public void setEntryCount(int entryCount) {
		_blogsStatsUser.setEntryCount(entryCount);
	}

	public java.util.Date getLastPostDate() {
		return _blogsStatsUser.getLastPostDate();
	}

	public void setLastPostDate(java.util.Date lastPostDate) {
		_blogsStatsUser.setLastPostDate(lastPostDate);
	}

	public int getRatingsTotalEntries() {
		return _blogsStatsUser.getRatingsTotalEntries();
	}

	public void setRatingsTotalEntries(int ratingsTotalEntries) {
		_blogsStatsUser.setRatingsTotalEntries(ratingsTotalEntries);
	}

	public double getRatingsTotalScore() {
		return _blogsStatsUser.getRatingsTotalScore();
	}

	public void setRatingsTotalScore(double ratingsTotalScore) {
		_blogsStatsUser.setRatingsTotalScore(ratingsTotalScore);
	}

	public double getRatingsAverageScore() {
		return _blogsStatsUser.getRatingsAverageScore();
	}

	public void setRatingsAverageScore(double ratingsAverageScore) {
		_blogsStatsUser.setRatingsAverageScore(ratingsAverageScore);
	}

	public com.liferay.portlet.blogs.model.BlogsStatsUser toEscapedModel() {
		return _blogsStatsUser.toEscapedModel();
	}

	public boolean isNew() {
		return _blogsStatsUser.isNew();
	}

	public boolean setNew(boolean n) {
		return _blogsStatsUser.setNew(n);
	}

	public boolean isCachedModel() {
		return _blogsStatsUser.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_blogsStatsUser.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _blogsStatsUser.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_blogsStatsUser.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _blogsStatsUser.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _blogsStatsUser.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_blogsStatsUser.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _blogsStatsUser.clone();
	}

	public int compareTo(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser) {
		return _blogsStatsUser.compareTo(blogsStatsUser);
	}

	public int hashCode() {
		return _blogsStatsUser.hashCode();
	}

	public java.lang.String toString() {
		return _blogsStatsUser.toString();
	}

	public java.lang.String toXmlString() {
		return _blogsStatsUser.toXmlString();
	}

	public BlogsStatsUser getWrappedBlogsStatsUser() {
		return _blogsStatsUser;
	}

	private BlogsStatsUser _blogsStatsUser;
}