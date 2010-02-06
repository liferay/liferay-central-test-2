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
 * <a href="RegionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link Region}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Region
 * @generated
 */
public class RegionWrapper implements Region {
	public RegionWrapper(Region region) {
		_region = region;
	}

	public long getPrimaryKey() {
		return _region.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_region.setPrimaryKey(pk);
	}

	public long getRegionId() {
		return _region.getRegionId();
	}

	public void setRegionId(long regionId) {
		_region.setRegionId(regionId);
	}

	public long getCountryId() {
		return _region.getCountryId();
	}

	public void setCountryId(long countryId) {
		_region.setCountryId(countryId);
	}

	public java.lang.String getRegionCode() {
		return _region.getRegionCode();
	}

	public void setRegionCode(java.lang.String regionCode) {
		_region.setRegionCode(regionCode);
	}

	public java.lang.String getName() {
		return _region.getName();
	}

	public void setName(java.lang.String name) {
		_region.setName(name);
	}

	public boolean getActive() {
		return _region.getActive();
	}

	public boolean isActive() {
		return _region.isActive();
	}

	public void setActive(boolean active) {
		_region.setActive(active);
	}

	public com.liferay.portal.model.Region toEscapedModel() {
		return _region.toEscapedModel();
	}

	public boolean isNew() {
		return _region.isNew();
	}

	public boolean setNew(boolean n) {
		return _region.setNew(n);
	}

	public boolean isCachedModel() {
		return _region.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_region.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _region.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_region.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _region.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _region.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_region.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _region.clone();
	}

	public int compareTo(com.liferay.portal.model.Region region) {
		return _region.compareTo(region);
	}

	public int hashCode() {
		return _region.hashCode();
	}

	public java.lang.String toString() {
		return _region.toString();
	}

	public java.lang.String toXmlString() {
		return _region.toXmlString();
	}

	public Region getWrappedRegion() {
		return _region;
	}

	private Region _region;
}