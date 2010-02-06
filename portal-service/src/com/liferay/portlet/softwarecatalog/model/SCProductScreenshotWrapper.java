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

package com.liferay.portlet.softwarecatalog.model;


/**
 * <a href="SCProductScreenshotSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCProductScreenshot}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductScreenshot
 * @generated
 */
public class SCProductScreenshotWrapper implements SCProductScreenshot {
	public SCProductScreenshotWrapper(SCProductScreenshot scProductScreenshot) {
		_scProductScreenshot = scProductScreenshot;
	}

	public long getPrimaryKey() {
		return _scProductScreenshot.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_scProductScreenshot.setPrimaryKey(pk);
	}

	public long getProductScreenshotId() {
		return _scProductScreenshot.getProductScreenshotId();
	}

	public void setProductScreenshotId(long productScreenshotId) {
		_scProductScreenshot.setProductScreenshotId(productScreenshotId);
	}

	public long getCompanyId() {
		return _scProductScreenshot.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_scProductScreenshot.setCompanyId(companyId);
	}

	public long getGroupId() {
		return _scProductScreenshot.getGroupId();
	}

	public void setGroupId(long groupId) {
		_scProductScreenshot.setGroupId(groupId);
	}

	public long getProductEntryId() {
		return _scProductScreenshot.getProductEntryId();
	}

	public void setProductEntryId(long productEntryId) {
		_scProductScreenshot.setProductEntryId(productEntryId);
	}

	public long getThumbnailId() {
		return _scProductScreenshot.getThumbnailId();
	}

	public void setThumbnailId(long thumbnailId) {
		_scProductScreenshot.setThumbnailId(thumbnailId);
	}

	public long getFullImageId() {
		return _scProductScreenshot.getFullImageId();
	}

	public void setFullImageId(long fullImageId) {
		_scProductScreenshot.setFullImageId(fullImageId);
	}

	public int getPriority() {
		return _scProductScreenshot.getPriority();
	}

	public void setPriority(int priority) {
		_scProductScreenshot.setPriority(priority);
	}

	public com.liferay.portlet.softwarecatalog.model.SCProductScreenshot toEscapedModel() {
		return _scProductScreenshot.toEscapedModel();
	}

	public boolean isNew() {
		return _scProductScreenshot.isNew();
	}

	public boolean setNew(boolean n) {
		return _scProductScreenshot.setNew(n);
	}

	public boolean isCachedModel() {
		return _scProductScreenshot.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_scProductScreenshot.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _scProductScreenshot.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_scProductScreenshot.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _scProductScreenshot.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scProductScreenshot.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scProductScreenshot.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _scProductScreenshot.clone();
	}

	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot) {
		return _scProductScreenshot.compareTo(scProductScreenshot);
	}

	public int hashCode() {
		return _scProductScreenshot.hashCode();
	}

	public java.lang.String toString() {
		return _scProductScreenshot.toString();
	}

	public java.lang.String toXmlString() {
		return _scProductScreenshot.toXmlString();
	}

	public SCProductScreenshot getWrappedSCProductScreenshot() {
		return _scProductScreenshot;
	}

	private SCProductScreenshot _scProductScreenshot;
}