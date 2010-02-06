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

package com.liferay.portlet.asset.model;


/**
 * <a href="AssetTagStatsSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetTagStats}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStats
 * @generated
 */
public class AssetTagStatsWrapper implements AssetTagStats {
	public AssetTagStatsWrapper(AssetTagStats assetTagStats) {
		_assetTagStats = assetTagStats;
	}

	public long getPrimaryKey() {
		return _assetTagStats.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetTagStats.setPrimaryKey(pk);
	}

	public long getTagStatsId() {
		return _assetTagStats.getTagStatsId();
	}

	public void setTagStatsId(long tagStatsId) {
		_assetTagStats.setTagStatsId(tagStatsId);
	}

	public long getTagId() {
		return _assetTagStats.getTagId();
	}

	public void setTagId(long tagId) {
		_assetTagStats.setTagId(tagId);
	}

	public java.lang.String getClassName() {
		return _assetTagStats.getClassName();
	}

	public long getClassNameId() {
		return _assetTagStats.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_assetTagStats.setClassNameId(classNameId);
	}

	public int getAssetCount() {
		return _assetTagStats.getAssetCount();
	}

	public void setAssetCount(int assetCount) {
		_assetTagStats.setAssetCount(assetCount);
	}

	public com.liferay.portlet.asset.model.AssetTagStats toEscapedModel() {
		return _assetTagStats.toEscapedModel();
	}

	public boolean isNew() {
		return _assetTagStats.isNew();
	}

	public boolean setNew(boolean n) {
		return _assetTagStats.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetTagStats.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetTagStats.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetTagStats.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetTagStats.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetTagStats.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetTagStats.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetTagStats.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetTagStats.clone();
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetTagStats assetTagStats) {
		return _assetTagStats.compareTo(assetTagStats);
	}

	public int hashCode() {
		return _assetTagStats.hashCode();
	}

	public java.lang.String toString() {
		return _assetTagStats.toString();
	}

	public java.lang.String toXmlString() {
		return _assetTagStats.toXmlString();
	}

	public AssetTagStats getWrappedAssetTagStats() {
		return _assetTagStats;
	}

	private AssetTagStats _assetTagStats;
}