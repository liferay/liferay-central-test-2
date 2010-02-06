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


/**
 * <a href="ReleaseSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link Release}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Release
 * @generated
 */
public class ReleaseWrapper implements Release {
	public ReleaseWrapper(Release release) {
		_release = release;
	}

	public long getPrimaryKey() {
		return _release.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_release.setPrimaryKey(pk);
	}

	public long getReleaseId() {
		return _release.getReleaseId();
	}

	public void setReleaseId(long releaseId) {
		_release.setReleaseId(releaseId);
	}

	public java.util.Date getCreateDate() {
		return _release.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_release.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _release.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_release.setModifiedDate(modifiedDate);
	}

	public java.lang.String getServletContextName() {
		return _release.getServletContextName();
	}

	public void setServletContextName(java.lang.String servletContextName) {
		_release.setServletContextName(servletContextName);
	}

	public int getBuildNumber() {
		return _release.getBuildNumber();
	}

	public void setBuildNumber(int buildNumber) {
		_release.setBuildNumber(buildNumber);
	}

	public java.util.Date getBuildDate() {
		return _release.getBuildDate();
	}

	public void setBuildDate(java.util.Date buildDate) {
		_release.setBuildDate(buildDate);
	}

	public boolean getVerified() {
		return _release.getVerified();
	}

	public boolean isVerified() {
		return _release.isVerified();
	}

	public void setVerified(boolean verified) {
		_release.setVerified(verified);
	}

	public java.lang.String getTestString() {
		return _release.getTestString();
	}

	public void setTestString(java.lang.String testString) {
		_release.setTestString(testString);
	}

	public com.liferay.portal.model.Release toEscapedModel() {
		return _release.toEscapedModel();
	}

	public boolean isNew() {
		return _release.isNew();
	}

	public boolean setNew(boolean n) {
		return _release.setNew(n);
	}

	public boolean isCachedModel() {
		return _release.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_release.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _release.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_release.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _release.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _release.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_release.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _release.clone();
	}

	public int compareTo(com.liferay.portal.model.Release release) {
		return _release.compareTo(release);
	}

	public int hashCode() {
		return _release.hashCode();
	}

	public java.lang.String toString() {
		return _release.toString();
	}

	public java.lang.String toXmlString() {
		return _release.toXmlString();
	}

	public Release getWrappedRelease() {
		return _release;
	}

	private Release _release;
}