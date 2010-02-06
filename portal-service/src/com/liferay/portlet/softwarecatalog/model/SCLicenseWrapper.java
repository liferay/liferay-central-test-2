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
 * <a href="SCLicenseSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCLicense}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCLicense
 * @generated
 */
public class SCLicenseWrapper implements SCLicense {
	public SCLicenseWrapper(SCLicense scLicense) {
		_scLicense = scLicense;
	}

	public long getPrimaryKey() {
		return _scLicense.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_scLicense.setPrimaryKey(pk);
	}

	public long getLicenseId() {
		return _scLicense.getLicenseId();
	}

	public void setLicenseId(long licenseId) {
		_scLicense.setLicenseId(licenseId);
	}

	public java.lang.String getName() {
		return _scLicense.getName();
	}

	public void setName(java.lang.String name) {
		_scLicense.setName(name);
	}

	public java.lang.String getUrl() {
		return _scLicense.getUrl();
	}

	public void setUrl(java.lang.String url) {
		_scLicense.setUrl(url);
	}

	public boolean getOpenSource() {
		return _scLicense.getOpenSource();
	}

	public boolean isOpenSource() {
		return _scLicense.isOpenSource();
	}

	public void setOpenSource(boolean openSource) {
		_scLicense.setOpenSource(openSource);
	}

	public boolean getActive() {
		return _scLicense.getActive();
	}

	public boolean isActive() {
		return _scLicense.isActive();
	}

	public void setActive(boolean active) {
		_scLicense.setActive(active);
	}

	public boolean getRecommended() {
		return _scLicense.getRecommended();
	}

	public boolean isRecommended() {
		return _scLicense.isRecommended();
	}

	public void setRecommended(boolean recommended) {
		_scLicense.setRecommended(recommended);
	}

	public com.liferay.portlet.softwarecatalog.model.SCLicense toEscapedModel() {
		return _scLicense.toEscapedModel();
	}

	public boolean isNew() {
		return _scLicense.isNew();
	}

	public boolean setNew(boolean n) {
		return _scLicense.setNew(n);
	}

	public boolean isCachedModel() {
		return _scLicense.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_scLicense.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _scLicense.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_scLicense.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _scLicense.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _scLicense.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_scLicense.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _scLicense.clone();
	}

	public int compareTo(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense) {
		return _scLicense.compareTo(scLicense);
	}

	public int hashCode() {
		return _scLicense.hashCode();
	}

	public java.lang.String toString() {
		return _scLicense.toString();
	}

	public java.lang.String toXmlString() {
		return _scLicense.toXmlString();
	}

	public SCLicense getWrappedSCLicense() {
		return _scLicense;
	}

	private SCLicense _scLicense;
}